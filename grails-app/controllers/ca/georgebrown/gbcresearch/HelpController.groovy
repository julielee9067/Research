package ca.georgebrown.gbcresearch

import ca.georgebrown.gbcresearch.security.Appuser
import org.springframework.security.access.annotation.Secured


@Secured('ROLE_DEVELOPER')
class HelpController {

    def helpService
    def springSecurityService

    def index() {
        Appuser appuser = springSecurityService.currentUser
        def helpContent = helpService.getHelpContent()
        [helpContent: helpContent, appuser: appuser]
    }

    def list() {
        Appuser appuser = springSecurityService.currentUser
        Appuser adminUser = Appuser.findById(6)
        def fileList = UploadedFile.findAllByUploadedBy(adminUser)
        return [appuser: appuser, fileList: fileList]
    }

    def downloadFile() {
        def absoluteDir = grailsApplication.config.file.absolute.path?:''
        File file = new File("${absoluteDir}${params?.pathToFile}")

        if (file.exists()) {
            response.setContentType("application/octet-stream")
            response.setHeader("Content-disposition", "filename=\"${file.name}\"")
            response.outputStream << file.bytes
        }
    }

    def uploadHelpDocument() {
        if (params.containsKey("send")) UploadedFile uploadedFile = helpService.uploadFile(params, request)
        redirect (controller: 'help', action: 'list')
    }
}
