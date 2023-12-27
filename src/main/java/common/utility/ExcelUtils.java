package common.utility;

import execute.RunTestScript;
import org.apache.poi.ss.usermodel.Row;
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

    public static void setExcelFile(String path)throws Exception {
        try{
            FileInputStream ExcelFile = new FileInputStream(path);
            ExcelBook = new XSSFWorkbook(ExcelFile);
        }catch (Exception e){
            onTestCaseFail("Method setExcelFile | Exception desc : " + e.getMessage());
        }
    }

    public static int getRowCount(String sheetName){
        int iMumber = 0;
        try{
            ExcelSheet = ExcelBook.getSheet(sheetName);
            iMumber = ExcelSheet.getLastRowNum() +1;
        }catch (Exception e){
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
        } catch (Exception e) {
            onTestCaseFail("Method getCellData | Exception desc : " + e.getMessage());
            e.printStackTrace();
            return "";
        }
    }

    public static int getRowContains(String sTestCaseName, int colNum, String sheeetName) throws Exception {
        int iRowNum = 0;
        try {
            int rowCount = ExcelUtils.getRowCount(sheeetName);
            for (; iRowNum < rowCount; iRowNum++) {
                if (ExcelUtils.getCellData(iRowNum, colNum, sheeetName).equalsIgnoreCase(sTestCaseName)) {
                    break;
                }
            }
        } catch (Exception e) {
            onTestCaseFail("Method getRowContains | Exception desc : " + e.getMessage());
        }
        return iRowNum;
    }

    public static int getTestStepCount(String sheetName, String sTestCaseID, int startTestStep) {
        try{
            for (int i = startTestStep;i<= ExcelUtils.getRowCount(sheetName);i++){
                if(!sTestCaseID.equals(ExcelUtils.getCellData(i,Constanst.TESTCASE_ID,sheetName))){
                    int number = i;
                    return number;
                }
            }
            ExcelSheet = ExcelBook.getSheet(sheetName);
            int number = ExcelSheet.getLastRowNum() + 1;
            return number;
        }catch (Exception e){
            onTestCaseFail("Method getTestStepCount | Exception desc : " + e.getMessage());
            return 0;
        }
    }

    @SuppressWarnings("static-access")
    public static void setCellData(String result, int rowNumber, int columnNumber, String sheetName,String path) throws Exception{
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
            RunTestScript.result = Constanst.FAIL;
        }
    }

    private static void onTestCaseFail(String message){
        RunTestScript.result = Constanst.SKIP;
        RunTestScript.error = message;
    }
}
