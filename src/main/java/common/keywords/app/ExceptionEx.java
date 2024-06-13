package common.keywords.app;

import common.utility.Log;
import execute.TestScrip;

public class ExceptionEx {
    public static void exception(String message){
        TestScrip.error = "Exception | " +message;
        Log.error(TestScrip.error);
        TestScrip.onFail( TestScrip.error);
    }
    public static void exception(Throwable e){
        TestScrip.error = "Exception | " +e.getMessage();
        Log.error(TestScrip.error);
        TestScrip.onFail( TestScrip.error);
    }
}
