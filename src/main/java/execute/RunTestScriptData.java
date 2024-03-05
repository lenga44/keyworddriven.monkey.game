package execute;

import common.keywords.KeyWordsToActionCustom;
import common.utility.Constanst;
import common.utility.ExcelUtils;
import common.utility.FileHelperUtils;
import common.utility.Log;
import report.GenerateReport;

import java.io.IOException;
import java.lang.reflect.Method;

public class RunTestScriptData extends TestScrip{
    public RunTestScriptData(KeyWordsToActionCustom keyWord, Method method[]){
        super(keyWord, method);
    }

    public static void run(String scopePath, int iTotalSuite) throws IOException {

        isDataFlow = true;
        int begin = ExcelUtils.getNumberValueInCell(1, Constanst.BEGIN_INDEX_COLUM,Constanst.PLAN_SHEET)-1;
        int end = ExcelUtils.getNumberValueInCell(1,Constanst.END_INDEX_COLUM,Constanst.PLAN_SHEET);
        Log.info("Run data from "+(begin+1)+" to "+end);

        for(int index = begin;index<end;index++){
            getLevelFolder(begin);
            int start = begin+1;

            execute(scopePath,iTotalSuite);
            ExcelUtils.setCellData(start,1,Constanst.CURRENT_INDEX_COLUM,Constanst.PLAN_SHEET,scopePath);

            GenerateReport.genReport(start,levelFolder);
            GenerateReport.countResultPlan(scopePath,iTotalSuite);
        }
    }
    private static void getLevelFolder(int row)throws IOException{
        String courseFolder = FileHelperUtils.getRootFolder() + Constanst.REPORT_FILE_PATH + ExcelUtils.getStringValueInCell(row, Constanst.COURSE_COLUM, Constanst.PLAN_SHEET);
        levelFolder = courseFolder +"//" + RunTestScript.level;
        Log.info("levelFolder: "+levelFolder);
        Log.info("Folder path report course: " +FileHelperUtils.convertPath(levelFolder));

        FileHelperUtils.genFolderReport(courseFolder);
        Log.info("Folder path report level: " +FileHelperUtils.convertPath(levelFolder));
    }

    private static String levelFolder;
}
