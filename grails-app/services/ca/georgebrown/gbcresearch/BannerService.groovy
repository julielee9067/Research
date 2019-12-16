package ca.georgebrown.gbcresearch

import grails.transaction.Transactional
import groovy.sql.Sql


@Transactional
class BannerService {

    def dataSource_banner
    def gbcActiveEmployeeQuery = """
                select distinct unique_id as SPRIDEN_ID 
                        from pwtidmempf_audit x
                    where status = 'A'
                            and seq_no = (select max(seq_no) from pwtidmempf_audit
                                            where unique_id = x.unique_id)
                            and (termination_date is null
                            or termination_date >= last_activity_date)
             """


    // This function gets the list of active employees.
    def getActiveEmployeeList() {
        def instructorsList = []
        Sql sql = null

        try {
            sql = Sql.newInstance(dataSource_banner)
            sql.eachRow(gbcActiveEmployeeQuery) {
                instructorsList.add(it['SPRIDEN_ID'])
            }
        }
        catch (Exception e) {
            log.error(e.message)
        }
        finally {
            if (sql) sql.close()
        }

        return instructorsList
    }

    // This function returns the banner ID based on the given email.
    def getBannerIdUsingEmail(def email) {
        Sql sql = null
        def spridenId = null
        def query = """select distinct spriden_id from goremal join spriden on spriden_pidm = goremal_pidm and spriden_change_ind is null 
                   where goremal_email_address = :email"""

        try {
            sql = Sql.newInstance(dataSource_banner)
            sql.eachRow(query,[email:email])  { row->
                spridenId = row.spriden_id
            }
        }
        catch (Exception e) {
            log.error(e.message)
        }
        finally {
            if (sql) sql.close()
        }

        return spridenId
    }
}
