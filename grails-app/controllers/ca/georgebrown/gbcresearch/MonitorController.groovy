package ca.georgebrown.gbcresearch

import grails.converters.JSON
import groovy.sql.Sql
import org.springframework.security.access.annotation.Secured


@Secured('permitAll')
class MonitorController {

    def dataSource
    static final STATUS = [SUCCESS:"Success",ERRORS:'Errors']

    def index() {
        def status = [status:STATUS.SUCCESS,message:"OK"]
        try {
            Sql sql = Sql.newInstance(dataSource)
            if (sql) {
                sql.withStatement {
                    stmt -> stmt.queryTimeout = 30
                }
                sql.eachRow("select count(*) from project", {})
                sql.close()
            }
        }
        catch (Exception e) {
            status.status = STATUS.ERRORS
            status.message = "SQL SERVER error: ${e.message}"
        }
        render(status as JSON)
    }
}
