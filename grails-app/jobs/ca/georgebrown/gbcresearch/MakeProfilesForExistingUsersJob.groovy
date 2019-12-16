package ca.georgebrown.gbcresearch

import ca.georgebrown.gbcresearch.security.Appuser
import grails.util.Environment


class MakeProfilesForExistingUsersJob {
    def integrationService

    static triggers = {
        switch (Environment.current) {
            case Environment.PRODUCTION:
                //WHEN BUILT FOR FIRST TIME
                break;
            case Environment.DEVELOPMENT:
                //WHEN BUILT FOR FIRST TIME
                break;
            case Environment.TEST:

                break;
        }
    }

    def execute() {
        integrationService.makeProfiles()
    }
}
