package ca.georgebrown.gbcresearch

import ca.georgebrown.gbcresearch.security.Appuser
import org.springframework.security.access.annotation.Secured


@Secured('permitAll')
class UploadFileController {

    def fileUploadService
    def springSecurityService

    def download() {
        return fileUploadService.file(response, params)
    }

    def upload() {
        Appuser appuser = springSecurityService.currentUser
        def msg=''
        def anchor = "fileAnchor"

        if (params.containsKey("send")) {
            UploadedFile uploadedFile = fileUploadService.uploadFile(params, request, appuser)

            if (!uploadedFile) {
                msg = "File failed to upload"
                [msg: msg]
            }
            else msg = "File successfully uploaded"
        }
        def contr = getParams().returnController
        def actn = getParams().returnAction
        def srId = getParams().sourceId

        redirect (controller:contr , action:actn , id:srId, params:[msg:msg, anchor: anchor, createNew: params?.createNew ?: 'false', title: params?.title, summary: params?.summary])
    }
}
