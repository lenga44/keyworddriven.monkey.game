package common.keywords.app.action;

import common.keywords.app.RequestEx;
import common.utility.Constanst;
import common.utility.Log;
import io.restassured.response.Response;

public class SendKey {
    public static void sendKey(String locator,String component, String property,String name){
        Log.info("sendKey " +name);
        RequestEx.request(Constanst.SCENE_URL,"//"+locator+"."+component+"."+property+"="+name);
    }
    public static void sendKey(String locator,String component,String name){
        Log.info("sendKey " +name);
        RequestEx.request(Constanst.SCENE_URL,"//"+locator+"."+component+".text="+name);
    }
    public static void sendKey(String locator,String component){
        Log.info("sendKey trống");
        Response response = RequestEx.request(Constanst.SCENE_URL,"//"+locator+"."+component+".text=");
    }
}
