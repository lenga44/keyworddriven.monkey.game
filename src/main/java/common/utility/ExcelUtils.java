package common.utility;

import execute.RunTestScript;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ExcelUtils {
    private static XSSFSheet ExcelSheet;
    private static XSSFWorkbook ExcelBook;
    private static org.apache.poi.ss.usermodel.Cell Cell;
    private static XSSFRow Row;

    public static void setExcelFile(String path) {
        try{
            FileInputStream ExcelFile = new FileInputStream(path);
            ExcelBook = new XSSFWorkbook(ExcelFile);
        }catch (Throwable e){
            Log.info("Method setExcelFile: " +path);
            Log.error("Method setExcelFile | Exception desc : " + e.getMessage());
            onTestCaseFail("Method setExcelFile | Exception desc : " + e.getMessage());
        }
    }

    public static int getRowCount(String sheetName){
        int iMumber = 0;
        try{
            ExcelSheet = ExcelBook.getSheet(sheetName);
            iMumber = ExcelSheet.getLastRowNum() +1;
        }catch (Throwable e){
            Log.info("Method getRowCount: " + sheetName);
            Log.error("Method getRowCount | Exception desc : " + e.getMessage());
            onTestCaseFail("Method getRowCount | Exception desc : " + e.getMessage());
        }
        return iMumber;
    }

    public static String getCellData(int rowNumber, int columnNumber, String sheetName){
        try {
            ExcelSheet = ExcelBook.getSheet(sheetName);
            Cell = ExcelSheet.getRow(rowNumber).getCell(columnNumber);
            String cellData = Cell.getStringCellValue();
            return cellData;
        } catch (Throwable e) {
            Log.info("Method getCellData: rowNumber[" + rowNumber+"], columnNumber["+columnNumber+"], sheetName["+sheetName+"]");
            Log.error("Method getCellData | Exception desc : " + e.getMessage());
            onTestCaseFail("Method getCellData | Exception desc : " + e.getMessage());
            return "";
        }
    }

    public static int getRowContains(String sTestCaseName, int colNum, String sheetName)  {
        int iRowNum = 0;
        try {
            int rowCount = ExcelUtils.getRowCount(sheetName);
            for (; iRowNum < rowCount; iRowNum++) {
                if (ExcelUtils.getCellData(iRowNum, colNum, sheetName).equalsIgnoreCase(sTestCaseName)) {
                    break;
                }
            }
        } catch (Throwable e) {
            Log.info("Method getRowContains: sTestCaseName[" + sTestCaseName+"], colNum["+colNum+"], sheetName["+sheetName+"]");
            Log.error("Method getRowContains | Exception desc : " + e.getMessage());
            onTestCaseFail("Method getRowContains | Exception desc : " + e.getMessage());
        }
        return iRowNum;
    }

    public static int getTestStepCount(String sheetName, String sTestCaseID, int startTestStep) {
        try{
            for (int i = startTestStep;i< ExcelUtils.getRowCount(sheetName);i++){
                if(!sTestCaseID.equals(ExcelUtils.getCellData(i,Constanst.TESTCASE_ID,sheetName))){
                    int number = i;
                    return number;
                }
            }
            ExcelSheet = ExcelBook.getSheet(sheetName);
            int number = ExcelSheet.getLastRowNum() + 1;
            return number;
        }catch (Throwable e){
            Log.info("Method getRowContains: sTestCaseID[" + sTestCaseID+"], startTestStep["+startTestStep+"], sheetName["+sheetName+"]");
            Log.error("Method getTestStepCount | Exception desc : " + e.getMessage());
            onTestCaseFail("Method getTestStepCount | Exception desc : " + e.getMessage());
            return 0;
        }
    }

    @SuppressWarnings("static-access")
    public static void setCellData(String result, int rowNumber, int columnNumber, String sheetName,String path) {
        try{
            ExcelSheet = ExcelBook.getSheet(sheetName);
            Row = ExcelSheet.getRow(rowNumber);
            Cell = Row.getCell(columnNumber, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            if(Cell == null){
                Cell = Row.createCell(columnNumber);
            }
            Cell.setCellValue(result);
            FileOutputStream fileOut = new FileOutputStream(path);
            ExcelBook.write(fileOut);
            fileOut.close();
            ExcelBook = new XSSFWorkbook(new FileInputStream(path));
        }catch (Exception e){
            Log.info("Method getRowContains: result[" + result+"], rowNumber["+rowNumber+"], columnNumber["+columnNumber+"], sheetName["+sheetName+"], path["+path+"]");
            Log.error("Method setCellData | Exception desc : " + e.getMessage());
            RunTestScript.result = Constanst.FAIL;
        }
    }

    private static void onTestCaseFail(String message){
        RunTestScript.result = Constanst.SKIP;
        RunTestScript.error = message;
    }
}
