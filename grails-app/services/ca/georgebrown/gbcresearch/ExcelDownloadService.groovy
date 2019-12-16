package ca.georgebrown.gbcresearch

import grails.transaction.Transactional
import groovy.sql.GroovyRowResult
import org.codehaus.groovy.grails.web.util.WebUtils
import javax.servlet.http.HttpServletResponse
import jxl.Workbook
import jxl.write.*


@Transactional
class ExcelDownloadService {

    static int PROPERTY = 0
    static int FUNCTION = 1

    def downloadWorkbook(String name, def wksData) {
        def utils = WebUtils.retrieveGrailsWebRequest()
        HttpServletResponse response = utils.getCurrentResponse()
        response.setContentType('application/vnd.ms-excel')
        response.setHeader('Content-Disposition', 'Attachment;Filename="' + name + '.xls"')
        WritableWorkbook workbook = Workbook.createWorkbook(response.outputStream)
        WritableSheet sheet = null
        def sheetNo = 1

        wksData.each { k, v ->
            def rownum = 0
            sheet = workbook.createSheet(k, sheetNo)

            if (v[0] instanceof java.util.ArrayList) {
                v.each { rec ->
                    def colnum = 0
                    rec.each { col ->
                        addCell(sheet, colnum, rownum, col)
                        colnum++
                    }
                    rownum++
                }
            }

            if (v[0] instanceof java.util.LinkedHashMap) {
                v.each { rec ->
                    def colnum = 0
                    rec.each { colname, colvalue ->
                        def val = colvalue
                        if (rownum == 0) val = colname
                        addCell(sheet, colnum, rownum, val)
                        colnum++
                    }
                    rownum++
                }
            }
            sheetNo++
        }
        workbook.write()
        workbook.close()
    }

    def addCell(WritableSheet sheet, int colnum, int rownum, def val) {
        if (val instanceof String || val == null) {
            sheet.addCell(new Label(colnum, rownum, val))
        }
        else if (val instanceof Date) {
            sheet.addCell(new DateTime(colnum, rownum, val))
        }
        else if (val instanceof Float) {
            WritableCellFormat f = new WritableCellFormat(NumberFormats.FORMAT4)
            sheet.addCell(new Number(colnum, rownum, val, f))
        }
        else {
            sheet.addCell(new Number(colnum, rownum, val))
        }
    }

    def qResultToExcelArray(def report, def rptMap = null) {
        def result = []
        def resultRec = []
        if (rptMap == null) {
            rptMap = [:]
            if (report[0] instanceof GroovyRowResult) {
                report[0].each { k, v ->
                    rptMap.put(k, [PROPERTY, k])
                }
            }
            else {
                report[0].getProperties().each { k, v ->
                    rptMap.put(k, [PROPERTY, k])
                }
            }
        }

        rptMap.each { k, v ->
            resultRec.add(k)
        }

        result.add(resultRec)
        report.each { rec ->
            resultRec = []
            rptMap.each { k, v ->
                def sz = v.size()
                def target = v[1]
                def val = (sz == 2 && v[0] == FUNCTION) ? rec."$target"() : rec."$target"

                for (def i = 2; i < sz; i++) {
                    target = v[i]
                    val = (sz == (i + 1) && v[0] == FUNCTION) ? val?."$target"() : val?."$target"
                }

                if (val && !val.class.toString().startsWith('class java')) {
                    def id = "id"
                    val = val."$id"
                }

                resultRec.add(val)
            }
            result.add(resultRec)
        }
        return result
    }

    def colsAndTableToExcelArray(def cols, def table) {
        def result = []
        result << cols
        table.each { result << it }
        return result
    }
}
