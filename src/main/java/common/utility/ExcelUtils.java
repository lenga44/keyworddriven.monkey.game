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

    public static void main(String[] args) {

    }
    public static void setExcelFile(String path){
        try{
            FileInputStream ExcelFile = new FileInputStream(path);
            ExcelBook = new XSSFWorkbook(ExcelFile);
        }catch (Exception e){

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
}
