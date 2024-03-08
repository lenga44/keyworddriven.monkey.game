package report;

import common.utility.*;
import execute.Run;
import execute.TestScrip;

import java.io.File;
import java.io.IOException;

public class GenerateReport{
    //region REPORT
    private static void genTCReportFile(String folder,int row,String subFolder,String reportName) throws IOException{
        File f = new File(folder);
        File dest =null;
        try {
            if (f.exists()) {
                File source = new File(TestScrip.tcPath);
                String tcCopyPath = subFolder + FileHelpers.convertPath("//" + TestScrip.tcName +"_"+reportName+"_"+ row+".xlsx");
                Log.info("Path report TC current: " + tcCopyPath);
                dest = new File(tcCopyPath);
                ExcelUtils.copyFile(source, dest);
                if(dest.exists()) {
                    replaceValueInReport(tcCopyPath);
                    ExcelUtils.closeFile(dest);
                }else
                    Log.error(tcCopyPath.toUpperCase() +"not exist!!");
            }
        }catch (Exception e){
            Log.error("Copy file status: " + dest.exists());
        }
    }
    public static void genReport(int row,String folderName,String reportName)throws IOException{
        FileHelpers.genFolderReport(FileHelpers.convertPath(folderName+"//"+reportName));
        genTCReportFile(FileHelpers.convertPath(folderName),row,folderName,reportName);
        ExcelUtils.setCellData(Constanst.YES, row, Constanst.RUN_MODE_SCOPE, Constanst.SCOPE_SHEET, Run.scopePath);
    }
    public static void countResultPlan(String path,int totalSuite){
        ExcelUtils.setExcelFile(path);
        int pass = Integer.valueOf(ExcelUtils.getStringValueInCell(1,Constanst.PASS_PLAN_COLLUM,Constanst.PLAN_SHEET));
        int fail =0;
        for (int i = 0;i<totalSuite;i++){
            if(ExcelUtils.getStringValueInCell(i,Constanst.STATUS_SUITE,Constanst.SCOPE_SHEET)==Constanst.FAIL){
                fail = 1;
                break;
            }
        }
        pass = (fail==0)?pass+1:pass;
        fail += Integer.valueOf(ExcelUtils.getStringValueInCell(1,Constanst.FAIL_PLAN_COLLUM,Constanst.PLAN_SHEET));
        ExcelUtils.setCellData(pass,1,Constanst.PASS_PLAN_COLLUM,Constanst.PLAN_SHEET,path);
        ExcelUtils.setCellData(fail,1,Constanst.FAIL_PLAN_COLLUM,Constanst.PLAN_SHEET,path);
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
