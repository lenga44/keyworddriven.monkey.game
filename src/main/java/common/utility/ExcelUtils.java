package common.utility;

import execute.TestScrip;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.NumberFormat;
import java.util.List;

public class ExcelUtils {
    public static Sheet ExcelSheet;
    public static Workbook ExcelBook;
    public static Cell Cell;
    public static Row Row;

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
    public static int getRow(String sheetName,int i){
        ExcelSheet = ExcelBook.getSheet(sheetName);
        return ExcelSheet.getRow(i).getLastCellNum();
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
    public static double getFormulaValueInCell(int rowNumber, int columnNumber, String sheetName){
        try {
            ExcelSheet = ExcelBook.getSheet(sheetName);
            Cell = ExcelSheet.getRow(rowNumber).getCell(columnNumber);
            return Cell.getNumericCellValue();
        } catch (Throwable e) {
            Log.info("Method getFormulaValueInCell: rowNumber[" + rowNumber+"], columnNumber["+columnNumber+"], sheetName["+sheetName+"]");
            Log.error("Method getFormulaValueInCell | Exception desc : " + e.getMessage());
            onTestCaseFail("Method getFormulaValueInCell | Exception desc : " + e.getMessage());
            return 0;
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
            Log.info("Method getTestStepCount: sTestCaseID[" + sTestCaseID+"], startTestStep["+startTestStep+"], sheetName["+sheetName+"]");
            Log.error("Method getTestStepCount | Exception desc : " + e.getMessage());
            onTestCaseFail("Method getTestStepCount | Exception desc : " + e.getMessage());
            return 0;
        }
    }
    public static int getValueCount(String sheetName, String value, int start,int colum) {
        try{
            for (int i = start;i< ExcelUtils.getRowCount(sheetName);i++){
                if(!value.equals(ExcelUtils.getStringValueInCell(i, colum,sheetName))){
                    int number = i;
                    return number;
                }
            }
            ExcelSheet = ExcelBook.getSheet(sheetName);
            int number = ExcelSheet.getLastRowNum() + 1;
            return number;
        }catch (Throwable e){
            Log.info("Method getValueCount: sTestCaseID[" + value+"], startTestStep["+start+"], sheetName["+sheetName+"]");
            Log.error("Method getTestStepCount | Exception desc : " + e.getMessage());
            onTestCaseFail("Method getTestStepCount | Exception desc : " + e.getMessage());
            return 0;
        }
    }
    public static int getLastByContain(String sheetName, String value, int start,int colum) {
        int number = 0;
        try{
            for (int i = start;i< ExcelUtils.getRowCount(sheetName);i++){
                if(value.equals(ExcelUtils.getStringValueInCell(i, colum,sheetName))){
                    number = i;
                }
            }
            /*ExcelSheet = ExcelBook.getSheet(sheetName);
            int number = ExcelSheet.getLastRowNum() + 1;*/
            return number;
        }catch (Throwable e){
            Log.info("Method getLastByContain: sTestCaseID[" + value+"], startTestStep["+start+"], sheetName["+sheetName+"]");
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
            Log.info("Method setCellData: result[" + result+"], rowNumber["+rowNumber+"], columnNumber["+columnNumber+"], sheetName["+sheetName+"], path["+path+"]");
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
            Log.info("Method setCellData: result[" + result+"], rowNumber["+rowNumber+"], columnNumber["+columnNumber+"], sheetName["+sheetName+"], path["+path+"]");
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
        }catch (Exception e) {
            Log.error("|copyFile|: " +e.getMessage());
        }finally {
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
    public static void saveFile(String path) throws IOException {
        FileOutputStream fileOut = new FileOutputStream(path);
        ExcelBook.write(fileOut);
        fileOut.close();
        ExcelBook = new XSSFWorkbook(new FileInputStream(path));
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
    public static void createRowLastest(int number,String sheetName,String path){
        try {
            com.aspose.cells.Workbook workbook = new com.aspose.cells.Workbook(path);
            com.aspose.cells.Worksheet worksheet = workbook.getWorksheets().get(sheetName);
            worksheet.getCells().insertRows(number,1);
            worksheet.getCells().get(number,0).setValue("END");
            deleteDefaultSheetAspose(workbook,path);
            closeFile(path);
            setExcelFile(path);
        }catch (Exception e){
            Log.error("Method createRow | Exception desc : " + e.getMessage());
            onTestCaseFail("Method createRow | Exception desc : " + e.getMessage());
            e.printStackTrace();
        }
    }
    public static void insertRow(int start){
        try {
            ExcelSheet.shiftRows(start, ExcelSheet.getLastRowNum(), 1,true,true);
            ExcelSheet.createRow(start);
        }catch (Exception e){
            Log.error("Method insertRow | Exception desc : " + e.getMessage());
            e.printStackTrace();
        }
    }
    public static void insertRow(int start,String sheetName){
        try {
            ExcelSheet = ExcelBook.getSheet(sheetName);
            ExcelSheet.shiftRows(start, ExcelSheet.getLastRowNum(), 1,true,true);
            ExcelSheet.createRow(start);
        }catch (Exception e){
            Log.error("Method insertRow | Exception desc : " + e.getMessage());
            e.printStackTrace();
        }
    }
    public static void insertCell(int row,int cell,String sheetName){
        try {
            ExcelSheet = ExcelBook.getSheet(sheetName);
            ExcelSheet.getRow(row).createCell(cell);
        }catch (Exception e){
            Log.error("Method insertRow | Exception desc : " + e.getMessage());
            e.printStackTrace();
        }
    }
    public static void copyRow(String path,String sheetName,int from, int to,int totalCellInRow){
        try {
            ExcelSheet = ExcelBook.getSheet(sheetName);
            insertRow(to);
            for(int i =1;i<=totalCellInRow;i++) {
                String value = getStringValueInCell(from,i-1,sheetName);
                setCellData(value,to,i-1,sheetName,path);
            }
            FileOutputStream outFile = new FileOutputStream(new File(path));
            ExcelBook.write(outFile);
            outFile.close();
        }catch (Exception e){
            Log.error("Method copyRow | Exception desc : " + e.getMessage());
            onTestCaseFail("Method copyRow | Exception desc : " + e.getMessage());
        }
    }
    public static void copyRow(String path, String sheetName, int to, List<String> values){
        try {
            ExcelSheet = ExcelBook.getSheet(sheetName);
            for(int i =0;i<values.size();i++) {
                setCellData(values.get(i),to,i,sheetName,path);
            }
            FileOutputStream outFile = new FileOutputStream(new File(path));
            ExcelBook.write(outFile);
            outFile.close();
        }catch (Exception e){
            Log.error("Method copyRow | Exception desc : " + e.getMessage());
            onTestCaseFail("Method copyRow | Exception desc : " + e.getMessage());
        }
    }
    public static void deleteRow(int number,String sheetName){
        try {
            ExcelSheet = ExcelBook.getSheet(sheetName);
            Row row = ExcelSheet.getRow(number);
            ExcelSheet.removeRow(row);
        }catch (Exception e){
            Log.error("|deleteRow|: " +e.getMessage());
        }
    }
    private static void deleteDefaultSheetAspose(com.aspose.cells.Workbook workbook,String path) throws Exception {
        workbook.getWorksheets().removeAt("Evaluation Warning");
        workbook.save(path);
        workbook.dispose();
    }
    public static void createSheet(String sheetName){
        try{
            ExcelBook.createSheet(sheetName);
        }catch (Exception e){
            Log.error("|createSheet|: " +e.getMessage());
        }
    }
    public static Sheet cloneSheet(String sheetName,String path){
        try{
            int index = ExcelBook.getSheetIndex(sheetName);
            ExcelSheet = ExcelBook.cloneSheet(index);
            FileOutputStream fos = new FileOutputStream(path);
            ExcelBook.write(fos);
            // Close streams
            fos.close();
            return ExcelSheet;
        }catch (Exception e){
            Log.error("|cloneSheet|: " +e.getMessage());
        }
        return null;
    }
    public static void deleteSheet(String sheetName,String path){
        try {
            ExcelBook.removeSheetAt(ExcelBook.getSheetIndex(sheetName));
            FileOutputStream outFile =new FileOutputStream(new File(path));
            ExcelBook.write(outFile);
            outFile.close();
        }catch (Exception e){
            Log.error("|deleteSheet|: "+e.getMessage());
        }
    }
}
