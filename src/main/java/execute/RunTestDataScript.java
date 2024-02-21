package execute;

import common.keywords.KeyWordsToAction;
import common.keywords.KeyWordsToActionToVerify;
import common.utility.Constanst;
import common.utility.ExcelUtils;
import common.utility.Log;

import java.io.IOException;
import java.lang.reflect.Method;

public class RunTestDataScript {

    //region RUN_CONFIG

    //region PLAN_SHEET
    public static String getMode(int row){
        String mode = ExcelUtils.getCellData(row,Constanst.RUN_MODE_PLAN_COLUM,Constanst.PLAN_SHEET);
        Log.info("MODE: " +mode);
        return mode;
    }
    public static void getLeveL(int row){
         level = ExcelUtils.getCellData(row,Constanst.LEVEL_COLUM,Constanst.PLAN_SHEET);
        Log.info("LEVEL: " +level);
    }
    public static void getNumberInLevel(int row){
        getLeveL(row);
        getStart(row);
        endLesson =(getMode(row).equals(Constanst.DATA_MODE))?Integer.valueOf(ExcelUtils.getCellData(row,Constanst.END_INDEX_COLUM,Constanst.PLAN_SHEET)):1;
        Log.info("END LESSON: " + endLesson);
        numberLesson = endLesson -lesson;
        Log.info("NUMBER: " +numberLesson);
    }
    public static void getStart(int row){
        lesson = Integer.valueOf(ExcelUtils.getCellData(row,Constanst.BEGIN_INDEX_COLUM,Constanst.PLAN_SHEET));
        Log.info("START LESSON: " +lesson);
    }
    //endregion


    //endregion

    //region KEY
    public static KeyWordsToAction keyWord;
    public static Method method[];
    public static int lesson;
    public static String level;
    public static int endLesson;
    public static int startLesson;
    public static int numberLesson;
    //endregion
}
