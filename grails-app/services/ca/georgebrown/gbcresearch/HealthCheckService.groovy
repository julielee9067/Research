package ca.georgebrown.gbcresearch

import grails.transaction.Transactional


@Transactional
class HealthCheckService {

    def grailsApplication
    def mailerService
    def userService

    // This function checks if there is enough space in the disk.
    def checkDiskspace() {
        def root = grailsApplication.config.file.upload.directory
        def threshold = grailsApplication.config.healthCheck.diskThreshold
        def freeSpace = Math.ceil(dir.getFreeSpace() /1024/1024)
        def dir = new File(root)
        def diskSize = Math.ceil(dir.getTotalSpace() /1024/1024)
        def diskUsed = diskSize - freeSpace
        def percentUsed = (diskUsed*100.00/diskSize)
        def percentageFree = 100.00 - percentUsed
        def status = "Disk Free: ${freeSpace} MB, Percentage Free: ${String.format("%.2f",percentageFree)}%"
        log.info(status)

        if (threshold > percentageFree) {
            log.warn("Free disk space is low")
            log.info(mailerService.sendEmailUsingTemplate("HEALTH_CHECK",userService.getEmailsWithRole("ROLE_DEVELOPER"),[issue:status]))
        }
    }
}
