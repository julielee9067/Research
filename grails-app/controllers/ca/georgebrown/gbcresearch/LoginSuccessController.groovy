package ca.georgebrown.gbcresearch

import ca.georgebrown.gbcresearch.security.Role
import ca.georgebrown.gbcresearch.security.Appuser
import org.springframework.security.access.annotation.Secured
import javax.servlet.http.HttpSession


@Secured('permitAll')
class LoginSuccessController {

    def springSecurityService
    def profileService
    def settingService

    def index() {
        HttpSession session = request.getSession()
        def targetUrl = session.getAttribute("targetUrl")

        if (targetUrl) {
            redirect uri: targetUrl
            return
        }

        Appuser user = springSecurityService.currentUser

        if (!user) {
            if (settingService.get(Setting.CODE.PUBLIC_ACCESS)) {
                redirect controller: 'homePage', action: 'index'
            } else {
                redirect controller:'login', action: "auth"
            }
            return
        }
        else if (!user.active) {
            print("User ${user.id} is now active")
            user.active = true
            user.save(flush: true, failOnError: true)
        }
        else {
            println "User ${user.id} has successfully logged in. "
        }

        if (!Profile.findByAppuser(user) || !ProfileSetting.findByAppuser(user)) {
            profileService.addProfile(user)
            profileService.defaultSettings(user)
            user.active = true
        }

        if (Role.findByAuthority('ROLE_PUBLISHER') in user.authorities ) {
            redirect(controller: "publisher", action:"list")
            return
        }

        if (Role.findByAuthority('ROLE_EMPLOYEE') in user.authorities && user.authorities.size() == 1) {
            redirect(controller: "homePage", action:"index", params: [newUser: params.newUser])
            return
        }

        if (Role.findByAuthority('ROLE_DEVELOPER') in user.authorities) {
            redirect(controller: "settings")
            return
        }

        if (Role.findByAuthority('ROLE_USER_ADMIN') in user.authorities) {
            redirect(controller: "userManagement")
            return
        }
        redirect(controller: "homePage", action:"index", params: [newUser: params.newUser])
    }
}
