package common.keywords.app.action;

import common.keywords.app.RequestEx;
import common.utility.Constanst;

public class TimeScale {
    public static void timeScale(String second){
        RequestEx.request(Constanst.POINTER_URL_UNIUM,Constanst.TIME_SCALE_ACTION +"("+second+")");
    }
}
