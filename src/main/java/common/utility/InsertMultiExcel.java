package common.utility;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ooxml.POIXMLException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static common.utility.ExcelUtils.*;

public class InsertMultiExcel implements Runnable{
    String result;
    int rowNumber;
    int columnNumber;
    String sheetName;
    String path;

    public InsertMultiExcel(String result, int rowNumber, int columnNumber, String sheetName, String path) {
        this.result = result;
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
        this.sheetName = sheetName;
        this.path = path;
    }

    @Override
    public void run() {
        try{
            ExcelSheet = ExcelBook.getSheet(sheetName);
            Row = ExcelSheet.getRow(rowNumber);
            Cell = Row.getCell(columnNumber, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            if(Cell == null){
                Cell = Row.createCell(columnNumber);
            }
            Cell.setCellValue(result);
        }catch (Exception e) {
            Log.info("Method InsertMultiExcel run: result[" + result+"], rowNumber["+rowNumber+"], columnNumber["+columnNumber+"], sheetName["+sheetName+"], path["+path+"]");
            Log.error("Method InsertMultiExcel run | Exception desc : " + e.getMessage());
        }
    }
}
