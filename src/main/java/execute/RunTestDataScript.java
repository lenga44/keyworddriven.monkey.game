package execute;

import common.utility.Constanst;
import common.utility.ExcelUtils;
import common.utility.FileHelperUtils;
import common.utility.Log;
import report.GenerateReport;

import java.io.IOException;

public class RunTestDataScript extends RunTestScript {
     public static void main(String[] args) throws IOException {
         openScopeFile();
         getNumberInLevel();
         startLesson = Integer.valueOf(ExcelUtils.getCellData(1,Constanst.BEGIN_INDEX_COLLUM,Constanst.PLAN_SHEET));
         for (int i =0;i<numberLesson;i++) {
             currentLesson = startLesson + i;
             ExcelUtils.setCellData(String.valueOf(currentLesson),1,Constanst.CURRENT_INDEX_COLLUM,Constanst.PLAN_SHEET,scopePath);
             execute();
             GenerateReport.genReport(i);
         }
    }

    //region RUN_CONFIG

    private static void openScopeFile() throws IOException{
        Log.resetFileLog();
        scopePath = FileHelperUtils.getRootFolder() + FileHelperUtils.getPathConfig(Constanst.SCOPE_FILE_PATH);
        Log.info("SCOPE_PATH: "+scopePath);
        ExcelUtils.setExcelFile(scopePath);
    }

    //region PLAN_SHEET
    private static int getMode(){
        return Integer.valueOf(ExcelUtils.getCellData(1,Constanst.RUN_MODE_PLAN_COLLUM,Constanst.PLAN_SHEET));
    }
    private static void getLeveL(){
         level = ExcelUtils.getCellData(1,Constanst.LEVEL_COLLUM,Constanst.PLAN_SHEET);
    }
    private static void getNumberInLevel(){
        total =(getMode()==Constanst.DATA_MODE)?Integer.valueOf(ExcelUtils.getCellData(1,Constanst.NUMBER_COLLUM,Constanst.PLAN_SHEET)):1;
        numberLesson = total -startLesson +1;
        if(numberLesson>1){
            getLeveL();
            getStart();
        }
    }
    private static void getStart(){
        lesson = Integer.valueOf(ExcelUtils.getCellData(1,Constanst.BEGIN_INDEX_COLLUM,Constanst.PLAN_SHEET));
    }
    //endregion


    //endregion
}
