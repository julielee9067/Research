package ca.georgebrown.gbcresearch

import grails.plugin.springsecurity.SpringSecurityUtils
import org.springframework.security.core.context.SecurityContextHolder
import javax.servlet.http.HttpSession


class LogoutController {

    def settingService

    def index() {
        if (settingService.get(Setting.CODE.PUBLIC_ACCESS)) {
          logout()
          redirect controller: 'homePage', action: 'publicView'
          return
        }
        redirect uri: SpringSecurityUtils.securityConfig.logout.filterProcessesUrl // '/j_spring_security_logout'
    }

    def logout() {
        SecurityContextHolder.clearContext();
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate()
        }
    }
}
