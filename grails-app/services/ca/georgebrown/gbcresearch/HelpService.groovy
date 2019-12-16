package ca.georgebrown.gbcresearch

import ca.georgebrown.gbcresearch.security.Appuser
import grails.transaction.Transactional
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.MultipartHttpServletRequest


@Transactional
class HelpService {

    def grailsApplication

    // This function returns the content of the help category.
    def getHelpContent() {
        def helpContent=[]
        Help.list(sort:"code").each() { def c ->
            helpContent.add(['topic':c.topic])
        }
    }

    // This function uploads the file to research/help
    // Note that if you make any modification to the help document, you would need to upload it manually on the website.
    // Changing and commiting to the subversion would not automatically fix it in the website.
    def uploadFile(def params, def request) {
        switch(request.method) {
            case "GET":
                break
            case "POST":
                if (request instanceof MultipartHttpServletRequest) {
                    for(filename in request.getFileNames()) {
                        MultipartFile file = request.getFile(filename)
                        String originalFileExtension = file.originalFilename.substring(file.originalFilename.lastIndexOf(".")+1)

                        String storageDirectory =  grailsApplication.config.file.upload.directory

                        if (params?.storageDir) storageDirectory = params?.storageDir

                        FileContentType contentType = FileContentType.findByExt('.' + originalFileExtension)
                        File newFile = new File("$storageDirectory/$file.originalFilename")
                        file.transferTo(newFile)

                        if(UploadedFile.findByOriginalFilename(newFile.name)) {
                            def originalFiles = UploadedFile.findAllByOriginalFilename(newFile.name)
                            originalFiles.each() { UploadedFile originalFile ->
                                originalFile.delete(flush: true)
                            }
                        }

                        UploadedFile ufile = new UploadedFile(
                                originalFilename: file.originalFilename,
                                newFilename: file.originalFilename,
                                dir: storageDirectory,
                                source: params?.source,
                                objId: params?.sourceId ?: 0,
                                uploadedBy: Appuser.findById(6),
                                contentType: contentType,
                                fileSize: file.size
                        )
                        ufile.save(flush: true, failOnError: true)
                        return ufile
                    }
                }
                break
            default: println("error")
        }
        return null
    }
}
