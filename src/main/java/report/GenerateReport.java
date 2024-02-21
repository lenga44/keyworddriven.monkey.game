package report;

import common.utility.Constanst;
import common.utility.ExcelUtils;
import common.utility.FileHelperUtils;
import common.utility.Log;
import execute.RunTestScript;

import java.io.File;
import java.io.IOException;

public class GenerateReport{
    //region REPORT
    private static void genTCReportFile(String folder,int row,String subFolder) throws IOException{
        File f = new File(folder);
        File dest =null;
        try {
            if (f.exists()) {
                File source = new File(RunTestScript.tcPath);
                String tcCopyPath = subFolder + FileHelperUtils.convertPath("//" + RunTestScript.tcName +"_"+(RunTestScript.endLesson-1)+"_"+ row+1+".xlsx");
                Log.info("Path report TC current: " + tcCopyPath);
                dest = new File(tcCopyPath);
                ExcelUtils.copyFile(source, dest);
                Log.error("Copy file status: " + dest.exists());
            }
        }catch (Exception e){
            Log.error("Copy file status: " + dest.exists());
        }
    }
    public static void genReport(int row,String folderName)throws IOException{
        if(RunTestScript.numberLesson>1) {
            FileHelperUtils.genFolderReport(FileHelperUtils.convertPath(folderName));
            genTCReportFile(FileHelperUtils.convertPath(folderName),row,folderName);
            ExcelUtils.setCellData(Constanst.YES, row, Constanst.RUN_MODE_SCOPE, Constanst.SCOPE_SHEET, RunTestScript.scopePath);
        }
    }
    //endregion
}
