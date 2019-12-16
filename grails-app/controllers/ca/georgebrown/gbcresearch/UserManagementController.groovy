package ca.georgebrown.gbcresearch

import ca.georgebrown.gbcresearch.email.EmailDef
import ca.georgebrown.gbcresearch.security.Role
import ca.georgebrown.gbcresearch.security.Appuser
import ca.georgebrown.gbcresearch.security.AppuserRole
import org.springframework.security.access.annotation.Secured


@Secured('ROLE_USER_ADMIN')
class UserManagementController {

    def springSecurityService
    def ldapService
    def mailerService
    def securityService
    def profileService

    def index() {
        redirect action: "list"
    }

    def listUsers() {
        Appuser appuser = springSecurityService.currentUser
        def c = Appuser.createCriteria()

        def userList = c.list(max: params.max, offset: params.offset) {
            order("firstName", "asc")
        }

        render(template: "userList", model: [list: userList, div_id: 'userList', action: 'listUsers', total: userList.totalCount, offset: params.offset, max: params.max])
    }

    def list() {
        Appuser appuser = springSecurityService.currentUser
        def firstName = ""
        def lastName = ""
        def username = ""

        if (params.containsKey('search') || (params.firstName || params.lastName || params.username)) {
            firstName = params.firstName ?: ""
            lastName = params.lastName ?: ""
            username = params.username ?: ""
        }

        def c = Appuser.createCriteria()

        def userList = c.list(max: params.max ?: 10, offset: params.offset ?: 0) {
            order("firstName", "asc")
            if (firstName) ilike("firstName", '%' + firstName + '%')
            if (lastName) ilike("lastName", '%' + lastName + '%')
            if (username) ilike("username", '%' + username + '%')
        }

        [appuser: appuser, userList: userList, firstName: firstName, lastName: lastName, username: username, total: userList.totalCount, firstName: firstName, lastName: lastName, username: username]
    }

    def edit(Long id) {
        Appuser appuser = springSecurityService.currentUser
        def user = Appuser.get(id)

        if (!user) {
            flash.message = "User ${id} not found"
            redirect(action: "list")
            return
        }

        [appuser: appuser, user: user]
    }

    def update(Long id, Long version) {
        def user = Appuser.get(id)

        if (!user) {
            flash.message = "Setting ${id} not found"
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (user.version > version) {
                user.errors.rejectValue("version", "default.optimistic.locking.failure",
                        ['Appuser'] as Object[],
                        "Another user has updated this Appuser while you were editing")
                render(view: "edit", model: [user: user])
                return
            }
        }

        user.properties = params

        def setRolesList = []
        params.role.each() { arId, on ->
            if (on == 'on') {
                setRolesList.add(Role.get(arId.toLong()))
            }
        }

        user.authorities.each() { Role role ->
            if (!(role in setRolesList)) AppuserRole.remove user, role
        }

        setRolesList.each() { Role role ->
            if (!(role in user.authorities)) {
                AppuserRole.create user, role
            }
        }

        if (!user.save(flush: true)) {
            render(view: "edit", model: [user: user])
            return
        }

        flash.message = message(code: 'default.updated.message', args: ['', user.displayName])
        redirect(action: "edit", params: [id: user.id])
    }

    def newUser() {
        def spridenId = params.spridenId
        def notFound = false

        if (spridenId) {
            def user = Appuser.findByUsername(spridenId)

            if (user) {
                redirect(action: "edit", id: user.id)
                return
            }
            else {
                def attribs = ldapService.getAttributes(spridenId)
                if (!attribs.size()) {
                    flash.message = "Person with id ${spridenId} not found in corporate directory."
                    redirect(action: "list")
                    return
                }
                else {
                    user = new Appuser(username: spridenId, firstName: attribs.givenName, lastName: attribs.sn, email: attribs.mail, password: ldapService.randomPassword())
                    user.save(flush: true)
                    def url = createLink(controller: 'login', absolute: true)
                    mailerService.sendEmailUsingTemplate(EmailDef.TEMPLATE_CODE.NEW_ACCOUNT, user.email, [appuser: user, url: url])

                    if (user) {
                        profileService.addProfile(user)
                        profileService.defaultSettings(user)
                    }

                    redirect(action: 'edit', id: user.id)
                    return
                }
            }
        }

        if ((!spridenId) || notFound) {
            redirect(action: 'create', params: [])
            return
        }

        redirect action: 'list'
    }

    def create() {
        Appuser appuser = springSecurityService.currentUser
        def user
        def userName = params.userName

        if (userName) {
            if (userName) user = Appuser.findByUsername(userName)
            if (user) {
                redirect(action: "edit", id: user.id)
                return
            }
            else {
                user = new Appuser(authenticationType: Appuser.AUTHENTICATION_TYPE.DAO, username: userName, firstName: params.firstName, lastName: params.lastName, email: userName, password: 'Mgbcia592') //ldapService.randomPassword())
                user.save(flush: true)

                if (user) {
                    profileService.addProfile(user)
                    profileService.defaultSettings(user)
                }
                mailerService.sendEmailUsingTemplate(EmailDef.TEMPLATE_CODE.ACTIVATE_ACCOUNT, user.email, [appuser: user, url: securityService.resetPasswordLink(user)])
                redirect(action: 'edit', id: user.id)
                return
            }
        }
        return [appuser: appuser]
    }
}
