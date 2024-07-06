package common.keywords.app.action;

import common.keywords.app.Convert;
import common.keywords.app.ExceptionEx;
import common.keywords.app.RequestEx;
import common.keywords.app.verify.GetElement;
import common.keywords.app.verify.IsElement;
import common.utility.Constanst;
import common.utility.FileHelpers;
import common.utility.Log;
import common.utility.LogicHandle;
import io.restassured.response.Response;
import io.restassured.response.ResponseBodyData;

import java.io.File;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.util.List;

public class Click {
    public static void click(String locator, String property){
        RequestEx.request(Constanst.SCENE_URL_UNIUM,"//"+locator+".Button."+property);
    }
    public static void clickWhichDisplay(String locator,String component, String property){
        if(IsElement.elementDisplay(locator)){
            RequestEx.request(Constanst.SCENE_URL_UNIUM,"//"+locator+"."+component+"."+property);
        }
    }
    public static void click(String locator,String component, String property){
        RequestEx.request(Constanst.SCENE_URL_UNIUM,"//"+locator+"."+component+"."+property);
    }
    public static void click(String locator,String component, String property,String index){
        RequestEx.request(Constanst.SCENE_URL_UNIUM,"//"+locator+"[" +index+"]"+"."+component+"."+property);
    }
    public static void clickWhichObjectEnable(String locator,String index,String component, String property){
        RequestEx.request(Constanst.SCENE_URL_UNIUM,"//"+locator+"[" +index+"]"+"."+component+"."+property);
    }
    public static void clickLocatorByVarFile(String generate,String locator, String component, String property,String key){
        String locatorChild = FileHelpers.getValueConfig(Constanst.VARIABLE_PATH_FILE,key)+generate+locator;
        Wait.waitForObject(locatorChild);
        RequestEx.request(Constanst.SCENE_URL_UNIUM,"//"+locatorChild+"."+component+"."+property);
    }
    public static void clickDownAndUp(String locator){
        RequestEx.request(Constanst.POINTER_URL_UNIUM,".DownToUp("+GetAbsolutePath.getAbsolutePath(locator)+")");
    }
    public static void clickDownAndUpChild(String locator,String index){
        Response response = RequestEx.request(Constanst.SCENE_URL_UNIUM,"//"+locator);
        List<String> list = LogicHandle.convertStringToList(Convert.convert(response,"children"));
        locator = locator + "/"+list.get(0);
        System.out.println(locator);
        RequestEx.request(Constanst.POINTER_URL_UNIUM,".DownToUp("+GetAbsolutePath.getAbsolutePath(locator)+","+index+")");
    }
    public static void clickDownAndUp(String locator,String index){
        String absolutePath = GetAbsolutePath.getAbsolutePath(locator,"0");
        /*if(absolutePath.contains(":"))
            absolutePath = absolutePath.replace(":","!_!");*/
        RequestEx.request(Constanst.POINTER_URL_UNIUM,".DownToUp("+absolutePath+","+index+")");
    }
    public static void simulateClick(String locator){
        Wait.waitForObject(locator);
        Response response = RequestEx.request(Constanst.SCENE_URL_UNIUM,"//"+locator+".RectTransform");
        String x = Convert.convert(response,"position.x",0,"\\.");
        String y = Convert.convert(response,"position.y",0,"\\.");
        RequestEx.request(Constanst.SIMULATE_URL_UNIUM,".click("+x+","+y+")");
    }
    public static void clickImage(String path){
        Response response = RequestEx.request(Constanst.URL_POCO,Constanst.CLICK_IMAGE_ACTION+path);
        assert response != null;
        Log.info(response.body().toString());
    }
    public static void clickImage(String folder, String name,String subFolder){
        String path = folder + '\\' + subFolder + "\\" + name;
        try {
            RequestEx.requestFile(Constanst.URL_POCO +Constanst.CLICK_IMAGE_ACTION,path);
        }catch (Exception e){
            Log.info("Path: "+path);
            ExceptionEx.exception(e.getMessage());
        }
    }
    public static void clickByPoco(String locator){
        try {
            RequestEx.GET(Constanst.URL_POCO ,Constanst.CLICK_ACTION+"?element="+ URLEncoder.encode(locator));
        }catch (Exception e){
            ExceptionEx.exception(e.getMessage());
        }
    }
    public static void clickByPositionPoco(String locator){
        try {
            RequestEx.GET(Constanst.URL_POCO ,Constanst.CLICK_POSITION_ACTION+"?element="+URLEncoder.encode(locator));
        }catch (Exception e){
            ExceptionEx.exception(e.getMessage());
        }
    }
}
