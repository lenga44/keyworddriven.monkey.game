package common.keywords.app.action;

import common.keywords.app.RequestEx;
import common.utility.Constanst;
import common.utility.FileHelpers;

public class Press {
    public static void pressLocatorByVarFile(String locator,String key){
        if(key.equals(Constanst.PATH_GAME_OBJECT)){
            String locatorChild = FileHelpers.getValueConfig(Constanst.VARIABLE_PATH_FILE,key)+locator;
            press(locatorChild);
        }else {
            press(locator,FileHelpers.getValueConfig(Constanst.VARIABLE_PATH_FILE,key));
        }
    }
    public static void press(String locator){
        RequestEx.request(Constanst.POINTER_URL_UNIUM,".Press("+GetAbsolutePath.getAbsolutePath(locator,"0")+")");
    }
    public static void press(String preLocator,String index,String locator){
        locator = preLocator+locator;
        RequestEx.request(Constanst.POINTER_URL_UNIUM,".Press("+GetAbsolutePath.getAbsolutePath(locator,index)+")");
    }
    public static void pressWithTag(String tagNew,String tagOld){
        RequestEx.request(Constanst.POINTER_URL_UNIUM,".PressWithTag("+tagNew +","+tagOld+")");
    }
    public static void press(String locator,String index){
        Wait.waitForObject(locator);
        String absolutePath = GetAbsolutePath.getAbsolutePath(locator,"0");
        if(absolutePath.contains(":"))
            absolutePath = absolutePath.replace(":","!_!");
        RequestEx.request(Constanst.POINTER_URL_UNIUM,".Press("+absolutePath+","+index+")");
    }
}
