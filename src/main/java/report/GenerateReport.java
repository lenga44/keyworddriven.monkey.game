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
    private static void genFolderReport(String folderName) throws IOException {
        File f = new File(folderName);
        if (!f.exists() && f.isDirectory())
            f.mkdirs();
    }
    private static void genTCReportFile(String folder) throws IOException{
        File f = new File(folder);
        File dest =null;
        try {
            if (f.exists()) {
                File source = new File(RunTestScript.tcPath);
                String tcCopyPath = levelFolder + FileHelperUtils.getPathConfig("//" + RunTestScript.tcName + RunTestScript.numberLesson + "_" + ".xlsx");
                Log.info("Path report TC current: " + tcCopyPath);
                dest = new File(tcCopyPath);
                ExcelUtils.copyFile(source, dest);
                Log.error("Copy file status: " + dest.exists());
            }
        }catch (Exception e){
            Log.error("Copy file status: " + dest.exists());
        }
    }
    public static void genReport(int row)throws IOException{
        if(RunTestScript.numberLesson>1) {
            ExcelUtils.setCellData(String.valueOf(RunTestScript.numberLesson), row, Constanst.END_INDEX_COLLUM, Constanst.PLAN_SHEET, RunTestScript.scopePath);
            courseFolder = FileHelperUtils.getRootFolder() + FileHelperUtils.getPathConfig(Constanst.REPORT_FILE_PATH + ExcelUtils.getCellData(row, Constanst.COURSE_COLLUM, Constanst.PLAN_SHEET));
            levelFolder = courseFolder + FileHelperUtils.getPathConfig("//" + RunTestScript.level);
            Log.info("Folder path report course: " +courseFolder);
            genFolderReport(courseFolder);
            Log.info("Folder path report level: " +levelFolder);
            genFolderReport(levelFolder);
            genTCReportFile(levelFolder);
        }
    }
    public static String courseFolder;
    public static String levelFolder;
    //endregion
}
