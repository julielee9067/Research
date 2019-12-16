package ca.georgebrown.gbcresearch

import ca.georgebrown.gbcresearch.security.Appuser

class UploadedFile {
    def grailsApplication

    Appuser uploadedBy = null
    Date dateCreated
    String dir
    String originalFilename
    long fileSize
    String source
    long objId
    String newFilename
    FileContentType contentType
    String description
    boolean saved

    static constraints = {
        uploadedBy nullable: true
        source nullable: true
        uploadedBy nullable:  true
        dir nullable: true
        fileSize nullable: true
        description nullable: true
        saved nullable: false
    }
    static mapping = {
        objId defaultValue:'0'
        saved defaultValue: false
    }

    def getFilePath() {
        if (!dir) dir = grailsApplication.config.file.upload.directory ?: '/tmp'
        return "${dir}/${newFilename}"
    }


}
