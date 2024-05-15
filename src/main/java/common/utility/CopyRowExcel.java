package common.utility;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class CopyRowExcel {
    private static Sheet ExcelSheet;
    private static Workbook ExcelBook;
    private static org.apache.poi.ss.usermodel.Cell Cell;
    private static org.apache.poi.ss.usermodel.Row Row;
    public static void test(String[] args) throws Exception {
        try {
            String path = "E:\\tool_test_game\\config\\report\\All Level\\Report_TestStory_My Family.xlsx";
            FileInputStream ExcelFile = new FileInputStream(path);
            ExcelBook = new XSSFWorkbook(ExcelFile);
            ExcelSheet = ExcelBook.getSheet("TestCase");
            int iNumber = ExcelSheet.getLastRowNum();
            ExcelSheet.shiftRows(2,iNumber,1);
            ExcelSheet.createRow(2);
            FileOutputStream outFile = new FileOutputStream(new File(path));
            ExcelBook.write(outFile);
            outFile.close();
            ExcelBook.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
