package common.keywords.app.verify;

import common.utility.Constanst;
import common.utility.FileHelpers;

public class GetImage {
    public static String getImageName(String locator){
        String result =  Common.getPropertyValue(locator,"Image","sprite");
        if(result.contains("(UnityEngine.Sprite)"))
            result = result.replace("(UnityEngine.Sprite)","");
        return result.trim()+".png";
    }
    public static String getImageName(String locator,String component){
        String result =  Common.getPropertyValue(locator,component,"sprite");
        if(result.contains("(UnityEngine.Sprite)"))
            result = result.replace("(UnityEngine.Sprite)","");
        return result.trim()+".png";
    }
    public static String getImageNameVariable(String generate,String locator,String key){
        String result =  Common.getPropertyValue(FileHelpers.getValueConfig(Constanst.VARIABLE_PATH_FILE,key)+generate+locator,"Image","sprite");
        if(result.contains("(UnityEngine.Sprite)"))
            result = result.replace("(UnityEngine.Sprite)","");
        return result.trim()+".png";
    }
    public static String getImageNameVariable(String generate,String locator,String component,String key){
        String result =  Common.getPropertyValue(FileHelpers.getValueConfig(Constanst.VARIABLE_PATH_FILE,key)+generate+locator,component,"sprite");
        if(result.contains("(UnityEngine.Sprite)"))
            result = result.replace("(UnityEngine.Sprite)","");
        return result.trim()+".png";
    }
    public static String getImageColor(String locator){
        String result =  Common.getPropertyValue(locator,"Image","color");
        return result.trim();
    }
    public static String getImageNameAndColor(String locator){
        return getImageName(locator)+","+getImageColor(locator);
    }
}
