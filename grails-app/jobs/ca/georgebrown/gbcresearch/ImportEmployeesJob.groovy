package ca.georgebrown.gbcresearch

import grails.util.Environment


class ImportEmployeesJob {
    def integrationService
    def settingService
    static triggers = {
        switch (Environment.current) {
            case Environment.PRODUCTION:
                cron name: 'importEmployeesJob', cronExpression: '0 0 2 * * ?' // daily @ 2AM
                break;
            case Environment.DEVELOPMENT:
                break;
            case Environment.TEST:
                break;
        }
    }

    def execute() {

        def result_import

        result_import = integrationService.importActiveEmployees()
        log.info(result_import)

        result_import

    }
}
