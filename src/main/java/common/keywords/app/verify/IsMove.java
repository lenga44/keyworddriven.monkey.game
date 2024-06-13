package common.keywords.app.verify;

import common.keywords.app.KeyWordsToActionToVerify;
import common.keywords.app.action.SleepEx;
import common.utility.Constanst;
import common.utility.Log;

public class IsMove {
    public static String isMoveLeft(String locator,String second){
        return isMoveType(locator,second, Constanst.X,Constanst.WITH);
    }
    public static String isMoveLeft(String locator){
        return isMoveType(locator,Constanst.X);
    }
    public static String isMoveDown(String locator,String second){
        return isMoveType(locator,second,Constanst.Y,Constanst.HEIGHT);
    }
    public static String isMoveType(String locator,String second, String type,String size){
        float x1 = Float.valueOf(KeyWordsToActionToVerify.getPointScreen(locator,type));
        float with = Float.valueOf(KeyWordsToActionToVerify.getSizeScreen(size));
        SleepEx.sleep(second);
        float x2 = Float.valueOf(KeyWordsToActionToVerify.getPointScreen(locator,type));
        Log.info("|isMoveType| "+type + ": |Before : " +x1 +"| -- |AFTER : " +x2+ " |");
        if(x2<with)
            return String.valueOf(x1<x2);
        return null;
    }
    public static String isMoveType(String locator, String type){
        float x1 = Float.valueOf(KeyWordsToActionToVerify.getPointScreen(locator,type));
        SleepEx.sleep("0.5");
        float x2 = Float.valueOf(KeyWordsToActionToVerify.getPointScreen(locator,type));
        Log.info("|isMoveType| "+type + ": |Before : " +x1 +"| -- |AFTER : " +x2+ " |");
        return String.valueOf(x1<x2);

    }
}
