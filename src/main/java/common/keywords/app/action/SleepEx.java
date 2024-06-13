package common.keywords.app.action;

import common.keywords.app.ExceptionEx;
import common.utility.Log;

public class SleepEx {
    public static void sleep(String second)  {
        try {
            Thread.sleep((Integer.parseInt(second) * 1000));
            Log.info("Sleep: " +second);
        }catch (Exception e){
            ExceptionEx.exception("|sleep String| "+e.getMessage());
        }
    }
    public static void sleep(String text,String second)  {
        try {
            Thread.sleep((Integer.parseInt(second) * 1000L));
            Log.info("Sleep: " +second);
        }catch (Exception e){
            ExceptionEx.exception("|sleep String| "+e.getMessage());
        }
    }
    public static void sleep(Object second)  {
        try {
            Thread.sleep((Integer.parseInt((String) second) * 1000L));
            Log.info("Sleep: " +second);
        }catch (Exception e){
            ExceptionEx.exception("|sleep String| "+e.getMessage());
        }
    }
    public static void sleep(int second)  {
        try {
            Thread.sleep (second * 1000L);
            Log.info("Sleep: " +second);
        }catch (Exception e){
            ExceptionEx.exception("|sleep int| "+e.getMessage());
        }
    }
    public static void sleep(float second)  {
        try {
            Thread.sleep ((long) (second * 1000));
            Log.info("Sleep: " +second);
        }catch (Exception e){
            ExceptionEx.exception("|sleep float| "+e.getMessage());
        }
    }
    public static void sleep()  {
        try {
            Thread.sleep((long) (2 * 1000));
        }catch (Exception e){
            ExceptionEx.exception("|sleep| "+e.getMessage());
        }
    }
}
