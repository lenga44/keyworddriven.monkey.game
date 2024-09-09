package common.keywords.app.verify;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import common.keywords.app.RequestEx;
import common.keywords.app.variable.ReturnPath;
import common.utility.Constanst;
import common.utility.JsonHandle;
import common.utility.Log;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

public class Video {
    public static String getVideoName(String locator){
        return Common.getPropertyValue(locator,"VideoPlayer","clip","(UnityEngine.VideoClip)")+".mp4";
    }
    public static String isVideoplay(String locator){
        String time = Common.getPropertyValue(locator,"VideoPlayer","time");
        if(Float.parseFloat(time)>0){
            return String.valueOf(true);
        }else {
            Log.info("isVideoplay "+Float.parseFloat(time));
            return  String.valueOf(false);
        }
    }
    public static String getVideoName(String locator,String strSplit,String indexSplit){
        return Common.getPropertyValue(locator,"VideoPlayer","clip",strSplit,indexSplit);
    }
    public static String getVideoUrl(String locator,String strSplit,String contain){
        return Common.getPropertyValue(locator,"VideoPlayer","url",strSplit,contain);
    }
    public static String getVideoUrl(String locator, String component,String key,String expected){
        return getVideoURls(locator,component,key,expected);
    }
    public static String getVideoUrl(String locator, String component,String key1,String key2,String expected){
        return getVideoURls(locator,component,key1,key2,expected);
    }
    public static String getVideoUrl(String locator, String component,String key,String strSplit,String contain,String expected){
        String url = Common.getPropertyValue(ReturnPath.getPath(locator,component,key,expected),"VideoPlayer","url",strSplit,contain);
        if(url.equals(expected)){
            return Constanst.TRUE;
        }else {
            Log.error("Found ["+url+"]");
            return Constanst.FALSE;
        }
    }
    public static String getVideoURls(String locator, String component,String key,String expected)  {
        String s = "";
        String path = "";
        try {
            Response response1 = RequestEx.request(Constanst.SCENE_URL_UNIUM, "//" + locator + "." + component);
            ResponseBody body1 = response1.getBody();
            JsonArray array = JsonHandle.getJsonArray(body1.asString());
            if(array.size()>0) {
                for (int i = 0; i < array.size(); i++) {
                    String json1 = body1.asString();
                    for (JsonElement element : JsonHandle.getJsonArray(json1)) {
                        s = JsonHandle.getValue(element.toString(), "$." + key);
                        if (!s.equals("")) {
                            if (s.toLowerCase().contains(expected.toLowerCase())) {
                                System.out.println("s1: " + s);
                                path =s;
                                break;
                            }
                        }
                    }
                    if(!path.equals("")){
                        break;
                    }
                }
            }
        }catch (Exception e){
            Log.error(e.getMessage());
            e.printStackTrace();
        }
        return path;
    }
    public static String getVideoURls(String locator, String component,String key1,String key2,String expected)  {
        String s = "";
        String path = "";
        try {
            Response response1 = RequestEx.request(Constanst.SCENE_URL_UNIUM, "//" + locator + "." + component);
            ResponseBody body1 = response1.getBody();
            JsonArray array = JsonHandle.getJsonArray(body1.asString());
            if(array.size()>0) {
                for (int i = 0; i < array.size(); i++) {
                    String json1 = body1.asString();
                    for (JsonElement element : JsonHandle.getJsonArray(json1)) {
                        s = JsonHandle.getValue(element.toString(), "$." + key1);
                        if (!s.equals("")) {
                            String actual = JsonHandle.getValue(s,"$."+key2);
                            System.out.println(actual);
                            if (actual.toLowerCase().contains(expected.toLowerCase())) {
                                path =actual;
                                break;
                            }
                        }
                    }
                    if(!path.equals("")){
                        break;
                    }
                }
            }
        }catch (Exception e){
            Log.error(e.getMessage());
            e.printStackTrace();
        }
        return path;
    }
}
