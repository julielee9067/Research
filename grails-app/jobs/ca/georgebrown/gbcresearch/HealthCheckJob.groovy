package ca.georgebrown.gbcresearch

import grails.util.Environment


class HealthCheckJob {
    def healthCheckService
    static triggers = {
        if (Environment.current == Environment.PRODUCTION) {
             cron name: 'healthCheckJobTrigger', cronExpression: '0 0 2 * * ?' // 2am each night
        }
        if (Environment.current == Environment.TEST) {
          //  cron name: 'healthCheckJobTrigger', cronExpression: '0 0 2 * * ?' // 2am each night
        }
        if (Environment.current == Environment.DEVELOPMENT) {
          cron name: 'healthCheckJobTrigger', cronExpression: '0 40 10 * * ?' // 2am each night
        }
    }

    def execute() {
        healthCheckService.checkDiskspace()
    }
}
