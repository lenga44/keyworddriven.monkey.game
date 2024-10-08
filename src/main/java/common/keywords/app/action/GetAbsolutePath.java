package common.keywords.app.action;

import common.keywords.app.Convert;
import common.keywords.app.RequestEx;
import common.utility.Constanst;
import io.restassured.response.Response;

public class GetAbsolutePath {
    public static String getAbsolutePath(String locator, String index){
        Response response = RequestEx.request(Constanst.SCENE_URL_UNIUM,"//"+locator + "["+Integer.valueOf(index)+"]");
        String absolutePath = Convert.convert(response,"path");
        if(absolutePath.contains(":"))
            absolutePath = absolutePath.replace(":","!_!");
        if(absolutePath.contains(".")){
            absolutePath = absolutePath.replace(".","<_>");
        }
        return absolutePath;
    }
    public static String getAbsolutePath(String locator){
        Response response = RequestEx.request(Constanst.SCENE_URL_UNIUM,"//"+locator);
        String absolutePath = Convert.convert(response,"path");
        if(absolutePath.contains(":"))
            absolutePath = absolutePath.replace(":","!_!");
        if(absolutePath.contains(".")){
            absolutePath = absolutePath.replace(".","<_>");
        }
        return absolutePath;
    }
    public static String getAbsolutePath(Response response, String index){
        String absolutePath = Convert.convert(response,"path");
        if(absolutePath.contains(":"))
            absolutePath = absolutePath.replace(":","!_!");
        return absolutePath;
    }
    public static String getAbsoluteLocator(String locator){
        if(locator.contains(":"))
            locator = locator.replace(":","!_!");
        if(locator.contains(".")){
            locator = locator.replace(".","<_>");
        }
        return locator;
    }
}
