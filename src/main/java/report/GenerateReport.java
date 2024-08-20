package report;

import com.aspose.cells.DateTime;
import common.utility.*;
import execute.Run;
import execute.TestScrip;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GenerateReport{
    //region REPORT
    public static String genTCReportFile(String folder,String subFolder,String reportName) throws IOException{
        File f = new File(folder);
        File dest =null;
        String file = FileHelpers.readFile(Constanst.VARIABLE_PATH_FILE);
        String order = JsonHandle.getValue(file,"$.order");
        try {
            if (f.exists()) {
                File source = new File(TestScrip.tcPath);
                ExcelUtils.setExcelFile(Run.scopePath);
                int number = ExcelUtils.getNumberValueInCell(1,Constanst.CURRENT_INDEX_COLUM,Constanst.PLAN_SHEET);
                String tcCopyPath = subFolder + FileHelpers.convertPath("\\"+TestScrip.tcName+order+"_"+reportName+"_"+number+".xlsx");
                if(TestScrip.tcName.equals(null)){
                    tcCopyPath = FileHelpers.convertPath("\\"+TestScrip.tcName+""+order+"_"+reportName+"_"+number+".xlsx");
                }
                Log.info("Path report TC current: " + tcCopyPath);
                dest = new File(tcCopyPath);
                if(dest.exists()) {
                    dest.delete();
                }
                Path newFilePath = Paths.get(tcCopyPath);
                Files.createFile(newFilePath);
                ExcelUtils.copyFile(source, dest);
                ExcelUtils.closeFile(dest);
            }
        }catch (Exception e){
            Log.error("Copy file status: " + e.getMessage());
            Log.error("Copy file status: " + dest.exists());
        }
        return dest.getAbsolutePath();
    }
    public static String genTCReport(String folderName,String reportName) throws IOException {
        FileHelpers.genFolderReport(FileHelpers.convertPath(folderName));
        return genTCReportFile(FileHelpers.convertPath(folderName),folderName,reportName);
    }
    /*public static void genReport(int row,String folderName,String reportName)throws IOException{
        FileHelpers.genFolderReport(FileHelpers.convertPath(folderName));
        genTCReportFile(FileHelpers.convertPath(folderName),folderName,reportName);
        ExcelUtils.setCellData(Constanst.YES, row, Constanst.RUN_MODE_SCOPE, Constanst.SCOPE_SHEET, Run.scopePath);
    }*/
    public static void countResultPlan(String path,int totalSuite){
        try {
            ExcelUtils.setExcelFile(path);
            int pass = ExcelUtils.getNumberValueInCell(1, Constanst.PASS_PLAN_COLUM, Constanst.PLAN_SHEET);
            int fail = 0;
            for (int i = 0; i < totalSuite; i++) {
                if (ExcelUtils.getStringValueInCell(i, Constanst.STATUS_SUITE, Constanst.SCOPE_SHEET) == Constanst.FAIL) {
                    fail = 1;
                    break;
                }
            }
            pass = (fail == 0) ? pass + 1 : pass;
            fail += ExcelUtils.getNumberValueInCell(1, Constanst.FAIL_PLAN_COLUM, Constanst.PLAN_SHEET);
            ExcelUtils.setCellData(pass, 1, Constanst.PASS_PLAN_COLUM, Constanst.PLAN_SHEET, path);
            ExcelUtils.setCellData(fail, 1, Constanst.FAIL_PLAN_COLUM, Constanst.PLAN_SHEET, path);
        }catch (Exception e){
            Log.error("countResultPlan || "+ e.getMessage());
            e.printStackTrace();
        }
    }
    @Deprecated
    private static void replaceValueInReport(String path) throws IOException {
        for (String key:JsonHandle.getAllKeyInJsonObject()) {
            String value = JsonHandle.getValueInJsonObject(path,key).toString();
            ExcelUtils.setExcelFile(path);
            ExcelUtils.replaceValueInAnyCell(value,key);
        }
    }
    private static void replaceValueInReport(String path,String key) throws IOException {
        String value = JsonHandle.getValueInJsonObject(path,key).toString();
        ExcelUtils.setExcelFile(path);
        ExcelUtils.replaceValueInAnyCell(value,key);
    }
    //endregion
}
