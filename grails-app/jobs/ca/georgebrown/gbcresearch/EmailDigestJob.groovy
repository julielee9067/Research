package ca.georgebrown.gbcresearch

import grails.util.Environment


class EmailDigestJob {
    def mailerService

    static triggers = {
        switch (Environment.current) {
            case Environment.PRODUCTION:
//                cron name: 'emailDigestJob', cronExpression: '0 0 23 * * ?' // daily @ 11PM
                break;
            case Environment.DEVELOPMENT:
                cron name: 'emailDigestJob', cronExpression: '0 0 23 * * ?' // daily @ 11PM
//                cron name: 'emailDigestJob', cronExpression: '0 * * * * ?' // every minute
                break;
            case Environment.TEST:
               cron name: 'emailDigestJob', cronExpression: '0 0 23 * * ?' // daily @ 11PM
//                cron name: 'emailDigestJob', cronExpression: '0 * * * * ?' // every minute
                break;
        }
    }

    def execute() {
        mailerService.sendEmailDigest()
    }
}
