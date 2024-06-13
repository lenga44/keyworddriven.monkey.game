package common.keywords.app.action;

import common.keywords.app.Convert;
import common.keywords.app.RequestEx;
import common.utility.Constanst;
import common.utility.FileHelpers;
import io.restassured.response.Response;

public class Click {
    public static void click(String locator, String property){
        RequestEx.request(Constanst.SCENE_URL,"//"+locator+"."+property);
    }
    public static void click(String locator,String component, String property){
        RequestEx.request(Constanst.SCENE_URL,"//"+locator+"."+component+"."+property);
    }
    public static void click(String locator,String component, String property,String index){
        RequestEx.request(Constanst.SCENE_URL,"//"+locator+"[" +index+"]"+"."+component+"."+property);
    }
    public static void clickWhichObjectEnable(String locator,String index,String component, String property){
        RequestEx.request(Constanst.SCENE_URL,"//"+locator+"[" +index+"]"+"."+component+"."+property);
    }
    public static void clickLocatorByVarFile(String generate,String locator, String component, String property,String key){
        String locatorChild = FileHelpers.getValueConfig(Constanst.VARIABLE_PATH_FILE,key)+generate+locator;
        Wait.waitForObject(locatorChild);
        RequestEx.request(Constanst.SCENE_URL,"//"+locatorChild+"."+component+"."+property);
    }
    public static void clickDownAndUp(String locator){
        RequestEx.request(Constanst.POINTER_URL,".DownToUp("+GetAbsolutePath.getAbsolutePath(locator)+")");
    }
    public static void clickDownAndUp(String locator,String index){
        String absolutePath = GetAbsolutePath.getAbsolutePath(locator,"0");
        /*if(absolutePath.contains(":"))
            absolutePath = absolutePath.replace(":","!_!");*/
        RequestEx.request(Constanst.POINTER_URL,".DownToUp("+absolutePath+","+index+")");
    }
    public static void simulateClick(String locator){
        Wait.waitForObject(locator);
        Response response = RequestEx.request(Constanst.SCENE_URL,"//"+locator+".RectTransform");
        String x = Convert.convert(response,"position.x",0,"\\.");
        String y = Convert.convert(response,"position.y",0,"\\.");
        RequestEx.request(Constanst.SIMULATE_URL,".click("+x+","+y+")");
    }
}
