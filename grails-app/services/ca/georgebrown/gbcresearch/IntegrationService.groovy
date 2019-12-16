package ca.georgebrown.gbcresearch

import ca.georgebrown.gbcresearch.security.Appuser
import ca.georgebrown.gbcresearch.security.AppuserRole
import ca.georgebrown.gbcresearch.security.Role
import grails.transaction.Transactional


@Transactional
class IntegrationService {

    def bannerService
    def ldapService
    def profileService

    // This function imports the active employees.
    def importActiveEmployees() {
        Role employeeRole = Role.findByAuthority('ROLE_EMPLOYEE')
        def instructorBannerIdList = bannerService.getActiveEmployeeList()
        def result = [instructorCnt: instructorBannerIdList.size(), addCnt: 0, disableCnt: 0, errorCnt: 0]

        if (result.instructorCnt == 0) return result

        def usersWithEmployeeOnlyRole = getUsersWithEmployeeOnlyRole()
        instructorBannerIdList.each() {bannerId ->
            Appuser user = Appuser.findByUsername(bannerId)
            usersWithEmployeeOnlyRole.removeElement(user)

            if (!user) {
                def ldapInfo = ldapService.getAttributes(bannerId)
                if (ldapInfo?.sn) {
                    user = new Appuser(username: bannerId, authenticationType: 'LDAP', source: Appuser.SOURCE.BANNER,
                            firstName: ldapInfo.givenName, lastName: ldapInfo.sn, email: ldapInfo.mail)
                    user.save(failOnError: true, flush: true)
                    profileService.addProfile(user)
                    profileService.defaultSettings(user)
                    result.addCnt++
                }
                else {
                    result.errorCnt++
                    return
                }
            }
            user.enabled = true

            if (!(Role.findByAuthority('ROLE_EMPLOYEE') in user.authorities)) {
                AppuserRole.create user, employeeRole
            }

            user.save(failOnError: true, flush:true)
        }
        result.disableCnt = usersWithEmployeeOnlyRole.size()
        usersWithEmployeeOnlyRole.each() { Appuser u->
            u.enabled = false
            u.save(flush:true)
        }
        return result
    }

    // This function gets the users with employee role.
    def getUsersWithEmployeeOnlyRole() {
        def userList = []
        Appuser.findAllBySourceAndEnabled(Appuser.SOURCE.BANNER,true).each { Appuser user->
            if (Role.findByAuthority('ROLE_EMPLOYEE') in user.authorities && user.authorities.size() == 1) {
                userList.add(user)
            }
        }
        return userList
    }

    // This function adds a profile about a user.
    def makeProfiles() {
        Appuser.list().each() { user ->
            profileService.addProfile(user)
            profileService.defaultSettings(user)
        }
    }
}
