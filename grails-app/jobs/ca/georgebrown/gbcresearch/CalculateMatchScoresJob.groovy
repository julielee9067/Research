package ca.georgebrown.gbcresearch

import ca.georgebrown.gbcresearch.Setting
import grails.util.Environment


class CalculateMatchScoresJob {

    def userMatchService

    static triggers = {
        switch (Environment.current) {
            case Environment.PRODUCTION:
//                cron name: 'calculateMatchScoresJob', cronExpression: '0 0 3 * * ?' // daily @ 3AM
                break;
            case Environment.DEVELOPMENT:
                cron name: 'calculateMatchScoresJob', cronExpression: '0 0 3 * * ?' // daily @ 3AM
                break;
            case Environment.TEST:
                cron name: 'calculateMatchScoresJob', cronExpression: '0 0 3 * * ?' // daily @ 3AM
                break;
        }
    }

    def execute() {
          userMatchService.updateUserListForAll(false, 0, null)
    }
}
