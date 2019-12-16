package ca.georgebrown.gbcresearch.iago

class IagoImportStats {
    static final STATUS = [SUCCESS:'success', FAIL:'fail']
    Date lastImportDate
    int updateRecCnt = 0
    int insertRecCnt = 0
    String status = STATUS.SUCCESS
    String errorMsg
    int runtime = 0
    static constraints = {
        errorMsg maxSize: 4000, nullable: true
    }
    static mapping = {
        version false
        runtime defaultValue: '0'
    }
}
