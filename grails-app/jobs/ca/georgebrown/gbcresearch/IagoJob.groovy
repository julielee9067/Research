package ca.georgebrown.gbcresearch

import grails.util.Environment


class IagoJob {
    def iagoService
    static triggers = {
        switch (Environment.current) {
            case Environment.PRODUCTION:
                cron name: 'iagoJob', cronExpression: '0 * * * * ?' // every minute
                break;
            case Environment.DEVELOPMENT:
                cron name: 'iagoJob', cronExpression: '0 * * * * ?' // every minute
                break;
            case Environment.TEST:
                cron name: 'iagoJob', cronExpression: '0 * * * * ?' // every minute
                break;
        }
    }

    def execute() {
     def result = iagoService.importIagoProjects()
     log.info("${result.status}:updated:${result.updateRecCnt}:inserted:${result.insertRecCnt}:runtime:${result.runtime}")
    }
}
