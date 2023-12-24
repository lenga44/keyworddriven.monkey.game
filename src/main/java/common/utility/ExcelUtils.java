package common.utility;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;

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
            System.out.println("Class Utils | Method setExcelFile | Exception desc : " + e.getMessage());
        }
    }
    public static int getRowCount(String sheetName){
        int iMumber = 0;
        try{
            ExcelSheet = ExcelBook.getSheet(sheetName);
            iMumber = ExcelSheet.getLastRowNum() +1;
        }catch (Exception e){

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
            System.out.println("Class Utils | Method getRowContains | Exception desc : " + e.getMessage());
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
            return 0;
        }
    }
}
