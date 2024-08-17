package common.keywords.app.action;

import common.keywords.app.ExceptionEx;
import common.keywords.app.RequestEx;
import common.keywords.app.verify.GetElement;
import common.keywords.app.verify.GetPosition;
import common.keywords.app.verify.IsElement;
import common.utility.Constanst;
import common.utility.Log;
import common.utility.LogicHandle;
import io.restassured.response.Response;

import java.time.LocalDateTime;
import java.util.List;

public class Touch {
    public static void touchByOS(String x,String y){
        String os = Constanst.ANDROID_OS;
        switch (os) {
            case Constanst.ANDROID_OS:
                touchAndroid(x, y);
                break;
            default:
                Log.error("No defind OS");
                break;
        }

    }
    public static void touchForElementDisplay(String x,String y,String locator,String second){
        LocalDateTime time = LocalDateTime.now();
        LocalDateTime time1 = time.plusSeconds(Integer.parseInt(second));
        do {
            boolean display = IsElement.elementDisplay(locator);
            if(!display){
                break;
            }
            touchByOS(x,y);
            time = LocalDateTime.now();
        } while (!time.isAfter(time1));

    }
    public static void touchAndroid(String x,String y){
        try {

            RequestEx.GET(Constanst.URL_POCO ,Constanst.TOUCH_ANDROID+"x="+ LogicHandle.enCode(x) +"&y="+LogicHandle.enCode(y));
        }catch (Exception e){
            ExceptionEx.exception(e.getMessage());
        }
    }
    public static void touchIOS(String x,String y){

    }
    public static void touchWin(String x,String y){

    }
}
