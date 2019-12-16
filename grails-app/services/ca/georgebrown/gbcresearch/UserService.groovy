package ca.georgebrown.gbcresearch

import ca.georgebrown.gbcresearch.security.AppuserRole
import ca.georgebrown.gbcresearch.security.Role
import grails.transaction.Transactional


@Transactional
class UserService {

    def getUsersWithRole(String roleName) {
        Role role = Role.findByAuthority(roleName)
        return getUsersWithRole(role)
    }

    def getUsersWithRole(Role role) {
        return AppuserRole.findAllByRole(role)*.appuser.findAll{it.enabled}
    }

    def getEmailsWithRole(def role) {
        return getUsersWithRole(role).email
    }
}
