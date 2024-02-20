package execute;

import common.utility.Constanst;
import common.utility.ExcelUtils;
import report.GenerateReport;

import java.io.IOException;

public class RunTestDataScript extends RunTestScript {
     public static void main(String[] args) throws IOException {
         scopePath = openScopeFile(Constanst.SCOPE_FILE_PATH);
         getNumberInLevel();
         startLesson = Integer.valueOf(ExcelUtils.getCellData(1,Constanst.BEGIN_INDEX_COLUM,Constanst.PLAN_SHEET));
         for (int i =0;i<numberLesson;i++) {
             currentLesson = startLesson + i;
             ExcelUtils.setCellData(String.valueOf(currentLesson),1,Constanst.CURRENT_INDEX_COLUM,Constanst.PLAN_SHEET,scopePath);
             execute();
             GenerateReport.genReport(i);
         }
    }

    //region RUN_CONFIG

    //region PLAN_SHEET
    private static int getMode(){
        return Integer.valueOf(ExcelUtils.getCellData(1,Constanst.RUN_MODE_PLAN_COLUM,Constanst.PLAN_SHEET));
    }
    private static void getLeveL(){
         level = ExcelUtils.getCellData(1,Constanst.LEVEL_COLUM,Constanst.PLAN_SHEET);
    }
    private static void getNumberInLevel(){
        total =(getMode()==Constanst.DATA_MODE)?Integer.valueOf(ExcelUtils.getCellData(1,Constanst.NUMBER_COLUM,Constanst.PLAN_SHEET)):1;
        numberLesson = total -startLesson +1;
        if(numberLesson>1){
            getLeveL();
            getStart();
        }
    }
    private static void getStart(){
        lesson = Integer.valueOf(ExcelUtils.getCellData(1,Constanst.BEGIN_INDEX_COLUM,Constanst.PLAN_SHEET));
    }
    //endregion


    //endregion
}
