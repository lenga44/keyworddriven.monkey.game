package common.keywords;

import common.utility.Constanst;
import common.utility.JsonHandle;
import common.utility.Log;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static io.restassured.RestAssured.given;

public class KeyWordsToActionToVerify extends KeyWordsToAction {
    @NotNull
    public static String isScale(String locator,String second,String expect){
        boolean result = false;
        Response response = request(Constanst.SCENE_URL, "//" + locator+".RectTransform.scale");
        String x = isScaleByCoordinate(response,second,expect,"x");
        String y = isScaleByCoordinate(response,second,expect,"y");
        String z = isScaleByCoordinate(response,second,expect,"z");
        result = (x=="true" && y=="true" && z=="true")?true:false;
        return String.valueOf(result);
    }
    public static String isScale(String locator,String component,String property,String second,String expect){
        boolean result = false;
        Response response = request(Constanst.SCENE_URL, "//" + locator+"."+component+"."+property+".scale");
        String x = isScaleByCoordinate(response,second,expect,"x");
        String y = isScaleByCoordinate(response,second,expect,"y");
        String z = isScaleByCoordinate(response,second,expect,"z");
        result = (x=="true" && y=="true" && z=="true")?true:false;
        return String.valueOf(result);
    }
    public static String isScaleByCoordinate(Response response,String second,String expect,String coordinate){
        boolean result = false;
        String value = convert(response,coordinate);
        ArrayList<String> list = getListInSecond(second,value);
        for (String i:list) {
            result = (Float.valueOf(i)>=0 && Float.valueOf(i)<=Float.valueOf(expect))?true:false;
        }
        return String.valueOf(result);
    }
    public static String elementDisplay(String locator){
        try {
            //waitForObject(locator);
            Response response = request(Constanst.SCENE_URL, "//" + locator);
            return convert(response, "activeInHierarchy");
        }catch (Throwable e){
            return "false";
        }
    }
    public static String elementDisplay(String locator,String second){
        try {
            waitForObject(locator,second);
            Response response = request(Constanst.SCENE_URL, "//" + locator);
            return convert(response, "activeInHierarchy");
        }catch (Throwable e){
            return String.valueOf(false);
        }
    }
    public static String elementNotDisplay(String locator){
        try {
            Response response = request(Constanst.SCENE_URL, "//" + locator);
            return convert(response, "activeInHierarchy");
        }catch (Throwable e){
            return null;
        }
    }
    public static String elementNotDisplay(String locator,String second){
        try {
            waitForObject(locator,second);
            Response response = request(Constanst.SCENE_URL, "//" + locator);
            return convert(response, "activeInHierarchy");
        }catch (Throwable e){
            return null;
        }
    }
    public static String getPropertyValue(String locator, String component, String property){
        waitForObject(locator);
        Response response = request(Constanst.SCENE_URL,"//"+locator+"."+component);
        return convert(response,property);
    }
    public static String getPropertyValue(String locator, String component, String property,String slipStr){
        waitForObject(locator);
        Response response = request(Constanst.SCENE_URL,"//"+locator+"."+component);
        String value = Arrays.stream(convert(response,property).split(slipStr)).toList().get(0).trim();
        Log.info(value);
        return value;
    }
    public static String getImageName(String locator){
        String result =  getPropertyValue(locator,"Image","sprite");
        if(result.contains("(UnityEngine.Sprite)"))
            result = result.replace("(UnityEngine.Sprite)","");
        return result.trim();
    }
    public static String getImageName(String locator,String component){
        String result =  getPropertyValue(locator,component,"sprite");
        if(result.contains("(UnityEngine.Sprite)"))
            result = result.replace("(UnityEngine.Sprite)","");
        return result.trim();
    }
    public static String getImageColor(String locator){
        String result =  getPropertyValue(locator,"Image","color");
        return result.trim();
    }
    public static String getImageNameAndColor(String locator){
        return getImageName(locator)+","+getImageColor(locator);
    }
    public static String getElements(String locator){
        Response response = request(Constanst.SCENE_URL,"//"+locator+"[activeInHierarchy=true]");
        return String.valueOf(response.getBody().jsonPath().getList("name").toArray().length);
    }
    public static String getCurrentScene(String locator){
        waitForObjectNotPresent(locator);
        RequestSpecification request = given();
        request.baseUri(Constanst.STATUS_URL);
        Response response = request.get();
        return response.jsonPath().get("Scene");
    }
    public static String getCurrentScene(){
        RequestSpecification request = given();
        request.baseUri(Constanst.STATUS_URL);
        Response response = request.get();
        return response.jsonPath().get("Scene");
    }
    public static String getText(String locator,String component){
        Response response = request(Constanst.SCENE_URL,"//" +locator+"."+component);
        return convert(response,"text").trim();
    }
    public static String getTextNoColor(String locator,String component,String... StrSplit){

        return null;
    }
    public static String getTextContain(String locator,String component,String contain){
        Response response = request(Constanst.SCENE_URL,"//" +locator+"."+component);
        return String.valueOf(convert(response,"text").trim().contains(contain));
    }
    public static String getSpineState(String locator){
        try {
            return getPropertyValue(locator, "SkeletonGraphic", "AnimationState");
        }catch (Exception e){
            return null;
        }
    }
    @NotNull
    public static String getSpineStates(String locator,String second,String count){
        return getStringConvertFromArrayList(second,count,getPropertyValue(locator, "SkeletonGraphic", "AnimationState"));
    }
    public static String getAudioSource(String locator){
        Log.info("|getAudioSource |");
        return getPropertyValue(locator,"AudioSource","clip","(UnityEngine.AudioClip)");
    }
    public static String getListAudioSource(String locator,String count) {
        String audio = null;
        ArrayList<String> list = null;
        try {
            LocalDateTime time = LocalDateTime.now();
            LocalDateTime time1 = time.plusSeconds(Integer.valueOf(25));
            list = new ArrayList<>();
            do {
                String value = getPropertyValue(locator, "AudioSource", "clip","(UnityEngine.AudioClip)");
                if (!list.contains(value))
                    list.add(value);
                if (list.size() <= Integer.valueOf(count))
                    break;
                Thread.sleep(10);
                time = LocalDateTime.now();
            } while (time.compareTo(time1) <= 0);
        } catch (Throwable e) {
            exception(e);
        }
        return list.toString().replace("[","").replace("]","");
    }
    public static String getListAudioSource(String locator,String count,String audios) {
        String audio = null;
        ArrayList<String> list = null;
        audios = audios.replace("[","").replace("]","");
        List<String> expects = Arrays.stream(audios.split("\\;")).toList();
        try {
            LocalDateTime time = LocalDateTime.now();
            LocalDateTime time1 = time.plusSeconds(25);
            list = new ArrayList<>();
            do {
                String value = getPropertyValue(locator, "AudioSource", "clip");
                if (!list.contains(value))
                    list.add(value);
                if (list.size() <= Integer.valueOf(count))
                    break;
                Thread.sleep(10);
                time = LocalDateTime.now();
            } while (time.compareTo(time1) <= 0);
        } catch (Throwable e) {
            exception(e);
        }
        return String.valueOf(expects.contains(list));
    }
    public static String getPointScreen(String locator, String coordinate){
        Response response = request(Constanst.SCENE_URL,"//"+locator+".RectTransform");
        return convert(response,"position."+coordinate,0,"\\.");
    }
    public static String getPointScreen(Response response, String coordinate){
        return convert(response,"position."+coordinate,0,"\\.");
    }
    public static String isPointInScreen(String locator){
        boolean result = false;
        Response response = request(Constanst.SCENE_URL,"//"+locator+".RectTransform");
        String x = convert(response,"position.x",0,"\\.");
        String y = convert(response,"position.y",0,"\\.");
        String with = getSizeScreen("w");
        String height = getSizeScreen("y");
        if(Float.valueOf(x)<Float.valueOf(with)){
            result = (Float.valueOf(y)<Float.valueOf(height))?true:false;
        }
        Log.info("Element In Screen = " +result);
        return String.valueOf(result);
    }
    public static String getSizeScreen(String key){
        Response response = request(Constanst.SCENE_URL,"//UniumSDK.UniumComponent");
        if(key.equals(Constanst.WITH))
            return convert(response, "Width");
        else
            return convert(response, "Height");
    }
    public static String isMoveLeft(String locator,String second){
        return isMoveType(locator,second,Constanst.X,Constanst.WITH);
    }
    public static String isMoveLeft(String locator){
        return isMoveType(locator,Constanst.X);
    }
    public static String isMoveDown(String locator,String second){
        return isMoveType(locator,second,Constanst.Y,Constanst.HEIGHT);
    }
    public static String isLocationCompare(String locator1,String locator2, String coordinate){
        String x1 = getPointScreen(locator1,coordinate);
        String x2 = getPointScreen(locator2,coordinate);
        boolean result = x1.equals(x2);
        Log.info("|isLocationCompare| "+ x1 +" compare " +x2);
        return String.valueOf(result);
    }
    public static String isRotation(String locator,String coordinate){
        Response response = request(Constanst.SCENE_URL,"//"+locator+".RectTransform");
        String z1 = convert(response,"position."+coordinate,0,"\\.");
        sleep("0.5");
        String z2 = convert(response,"position."+coordinate,0,"\\.");
        Log.info("|isRotation|: " + z1+ " --- "+z2);
        return String.valueOf(z1.equals(z2));
    }
    public static String getVideoName(String locator){
        return getPropertyValue(locator,"VideoPlayer","clip","(UnityEngine.VideoClip)");
    }
    public static String getResultByKey(String locator,String component,String key){
        Response response = request(Constanst.SCENE_URL,"//" +locator+"."+component);
        String value = JsonHandle.getValue(response.getBody().asString(),key);
        return value;

    }
}
