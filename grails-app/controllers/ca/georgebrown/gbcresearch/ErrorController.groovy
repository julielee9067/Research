package ca.georgebrown.gbcresearch

import org.springframework.security.access.annotation.Secured


@Secured('permitAll')
class ErrorController {

    def notFound() {
        println "Not found"
    }
}
