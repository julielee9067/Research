package ca.georgebrown.gbcresearch

import grails.util.Environment

class DeleteInitiatedJob {

    def submissionService

    static triggers = {
        switch (Environment.current) {
            case Environment.PRODUCTION:
                cron name: 'deleteInitiatedJob', cronExpression: '0 0 * * * ?' // every hour
                break;
            case Environment.DEVELOPMENT:
                cron name: 'deleteInitiatedJob', cronExpression: '0 0 * * * ?' // every hour
                break;
            case Environment.TEST:
                cron name: 'deleteInitiatedJob', cronExpression: '0 0 * * * ?' // every hour
                break;
        }
    }

    def execute() {
        submissionService.deleteInitiated()
    }
}
