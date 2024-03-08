package common.utility;

import execute.TestScrip;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class ExcelUtils {
    private static Sheet ExcelSheet;
    private static Workbook ExcelBook;
    private static Cell Cell;
    private static Row Row;

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
    public static String getStringValueInCell(int rowNumber, int columnNumber, String sheetName){
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
    public static String getStringValueInCell(int rowNumber, int columnNumber){
        try {
            Cell cell  = Cell = ExcelSheet.getRow(rowNumber).getCell(columnNumber);
            String cellData = cell.getStringCellValue();
            return cellData;
        } catch (Throwable e) {
            Log.error("Method getCellData | Exception desc : " + e.getMessage());
            onTestCaseFail("Method getCellData | Exception desc : " + e.getMessage());
            return "";
        }
    }
    public static int getNumberValueInCell(int rowNumber, int columnNumber, String sheetName){
        try {
            ExcelSheet = ExcelBook.getSheet(sheetName);
            Cell = ExcelSheet.getRow(rowNumber).getCell(columnNumber);
            int cellData = (int) Cell.getNumericCellValue();
            return cellData;
        } catch (Throwable e) {
            Log.info("Method getCellData: rowNumber[" + rowNumber+"], columnNumber["+columnNumber+"], sheetName["+sheetName+"]");
            Log.error("Method getCellData | Exception desc : " + e.getMessage());
            onTestCaseFail("Method getCellData | Exception desc : " + e.getMessage());
            return 0;
        }
    }

    public static int getRowContains(String sTestCaseName, int colNum, String sheetName)  {
        int iRowNum = 0;
        try {
            int rowCount = ExcelUtils.getRowCount(sheetName);
            for (; iRowNum < rowCount; iRowNum++) {
                if (ExcelUtils.getStringValueInCell(iRowNum, colNum, sheetName).equalsIgnoreCase(sTestCaseName)) {
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
                if(!sTestCaseID.equals(ExcelUtils.getStringValueInCell(i, Constanst.TESTCASE_ID,sheetName))){
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
    public static void addPictureInCell(int row, byte[] imageContent,String path  ) {
        try {
            ExcelSheet = ExcelBook.getSheet(Constanst.TEST_STEP_SHEET);
            int my_picture_id = ExcelBook.addPicture(imageContent, Workbook.PICTURE_TYPE_PNG);
            XSSFDrawing drawing = (XSSFDrawing) ExcelSheet.createDrawingPatriarch();

            XSSFClientAnchor my_anchor = new XSSFClientAnchor();
            my_anchor.setCol1(Constanst.IMAGE);
            my_anchor.setRow1(row);
            my_anchor.setCol2(Constanst.IMAGE + 1);
            my_anchor.setRow2(row+1);

            drawing.createPicture(my_anchor, my_picture_id);
            FileOutputStream fileOut = new FileOutputStream(path);
            ExcelBook.write(fileOut);
            /*fileOut.close();
            ExcelBook.close();*/

        }catch (IOException e) {
            Log.error("addPictureInCell |" + e.getMessage());
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
            TestScrip.result = Constanst.FAIL;
        }
    }
    public static void setCellData(int result, int rowNumber, int columnNumber, String sheetName,String path) {
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
            TestScrip.result = Constanst.FAIL;
        }
    }

    private static void onTestCaseFail(String message){
        TestScrip.result = Constanst.SKIP;
        TestScrip.error = message;
    }
    public static void copyFile(File source, File dest)throws IOException{
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
    }
    public static void cleanContextInRange(int columnNumber, String sheetName,String path){
        for(int i=1;i<getRowCount(sheetName);i++){
            setCellData("",i,columnNumber,sheetName,path);
        }
    }
    public static void closeFile(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        br.close();
        fis.close();
    }
    public static void closeFile(String path) throws IOException {
        File file = new File(path);
        FileInputStream fis = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        br.close();
        fis.close();
    }
    public static void replaceValueInAnyCell(String value,String key) throws IOException {
        ExcelSheet = ExcelBook.getSheet(Constanst.TEST_STEP_SHEET);
        for (Row row:ExcelSheet) {
            for (Cell cell: row){
                if(cell.getStringCellValue().equals(key)){
                    cell.setCellValue(FileHelpers.getValueConfig(value));
                }
            }
        }
    }
}
