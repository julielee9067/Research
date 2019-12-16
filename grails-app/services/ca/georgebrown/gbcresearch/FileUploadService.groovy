package ca.georgebrown.gbcresearch

import ca.georgebrown.gbcresearch.security.Appuser
import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.MultipartHttpServletRequest


@Transactional
class FileUploadService {

    def grailsApplication

    // This function uploads a file.
    def uploadFile(GrailsParameterMap params, def request, Appuser currentUser) {
        def maxSize = grailsApplication.config.file.upload.maxSize
        def allowedExtensions = grailsApplication.config.file.upload.allowedExtensions

        switch(request.method) {
            case "GET":
                break
            case "POST":
                if (request instanceof MultipartHttpServletRequest) {
                    for(filename in request.getFileNames()) {
                        MultipartFile file = request.getFile(filename)
                        String newFilenameBase = UUID.randomUUID().toString()
                        String originalFileExtension = file.originalFilename.substring(file.originalFilename.lastIndexOf(".")+1)

                        if (!(originalFileExtension in allowedExtensions)) {
                            // extension not allowed error should be returned here
                            return
                        }

                        if (file.size > maxSize) {
                            // file too big error should be returned here
                            return
                        }

                        String newFilename = newFilenameBase + "." + originalFileExtension
                        String storageDirectory =  grailsApplication.config.file.upload.directory

                        if (params?.source == 'Profile') {
                            checkForExisting(currentUser)
                        }

                        if (params?.storageDir) storageDirectory = params?.storageDir

                        FileContentType contentType = FileContentType.findByExt(originalFileExtension)?:FileContentType.findByExt('.bin')
                        File newFile = new File("$storageDirectory/$newFilename")
                        file.transferTo(newFile)

                        UploadedFile ufile = new UploadedFile(
                                originalFilename: file.originalFilename,
                                newFilename: newFilename,
                                dir: storageDirectory,
                                source: params?.source,
                                objId: params?.sourceId ?: 0,
                                uploadedBy: currentUser,
                                contentType: contentType,
                                fileSize: file.size
                        )

                        if (params.description) {
                            ufile.description = params.description
                        }

                        ufile.save(flush: true, failOnError: true)
                        return ufile
                    }
                }
                break
            default: println("error")
        }
        return null
    }

    // This function checks if the profile photo already exists or not. If it does, then it deletes the current one.
    def checkForExisting(currentUser) {
        def currentProfilePhoto = UploadedFile.findBySourceAndUploadedBy('Profile', currentUser)
        if (currentProfilePhoto)  {
            delete(currentProfilePhoto)
        }
        return
    }

    def file(response, params) {
        def uf = UploadedFile.get(params.id)
        def dir = uf.dir

        if (!dir) dir = grailsApplication.config.file.upload.directory?:'/tmp'

        File uFile = new File("${dir}/${uf.newFilename}")
        response.setHeader("Content-disposition", "filename=\"${uf.originalFilename}\"")
        response.contentType = uf.contentType.mimeType

        try {
            response.outputStream << new FileInputStream(uFile)
            response.outputStream.flush()
        }
        catch (Exception e) {
            response.outputStream << e.message
            response.outputStream.flush()
        }
    }

    def delete(params) {
        def uf = UploadedFile.get(params.id)
        def dir = uf.dir

        if (!dir) dir = grailsApplication.config.file.upload.directory?:'/tmp'

        File uFile = new File("${dir}/${uf.newFilename}")
        uFile.delete()
        uf.delete()
        def result = [success: true]
        return result
    }
}

