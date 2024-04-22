package common.keywords;

import common.utility.Constanst;
import common.utility.FileHelpers;
import common.utility.JsonHandle;
import common.utility.Log;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

import java.time.LocalDateTime;
import java.util.*;

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
    public static boolean isElementDisplay(String locator){
        try {
            Response response = request(Constanst.SCENE_URL, "//" + locator);
            ResponseBody body = response.getBody();
            List actvies = convertToList(response,"activeInHierarchy");
            boolean value = false;
            if(actvies.size()>0){
                String result = convert(response,"activeInHierarchy");
                if(result.equals("true")){
                    value =true;
                }
            }
            return value;
        }catch (Exception e){
            Log.info("No Such element " +locator);
            return false;
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
    public static String getPropertyValue(String locator, String component, String property,String slipStr,String contain){
        //waitForObject(locator);
        Response response = request(Constanst.SCENE_URL,"//"+locator+"."+component);
        String result = convert(response,property);
        List<String> list = Arrays.stream(result.split(slipStr)).toList();
        String value = "";
        for (String str: list){
            if(str.contains(contain)){
                value = str;
                break;
            }
        }
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
    public static String getImageNameVariable(String generate,String locator,String key){
        String result =  getPropertyValue(FileHelpers.getValueConfig(Constanst.VARIABLE_PATH_FILE,key)+generate+locator,"Image","sprite");
        if(result.contains("(UnityEngine.Sprite)"))
            result = result.replace("(UnityEngine.Sprite)","");
        return result.trim();
    }
    public static String getImageNameVariable(String generate,String locator,String component,String key){
        String result =  getPropertyValue(FileHelpers.getValueConfig(Constanst.VARIABLE_PATH_FILE,key)+generate+locator,component,"sprite");
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
    public static String getAllScene(){
        RequestSpecification request = given();
        request.baseUri(Constanst.ALL_SCENE);
        Response response = request.get();
        return response.jsonPath().get("name").toString().replace("[","").replace("]","");
    }
    public static String getText(String locator,String component){
        Response response = request(Constanst.SCENE_URL,"//" +locator+"."+component);
        return convert(response,"text").trim();
    }
    public static String getTextsByTime(String locator,String component,String second,String expect){
        Response response = request(Constanst.SCENE_URL, "//" + locator + "." + component);
        List texts = convertToList(response,"text");
        String result= "";
        if(texts.size()>0){
            result = convert(response, "text").trim();
        }
        LocalDateTime time = LocalDateTime.now();
        LocalDateTime time1 = time.plusSeconds(Integer.valueOf(second));
        List<String> results = new ArrayList<>();
        try {
            String text ="";
            while (time.compareTo(time1) <= 0) {
                if (!result.equals("")) {
                    if (!results.contains(result)) {
                        results.add(result);
                        if (text.equals("")) {
                            text = text + result.trim();
                        } else {
                            text = text + " " + result.trim();
                        }
                    }
                }
                if (text.contains(expect)) {
                    break;
                } else {
                    time = LocalDateTime.now();
                }
                response = request(Constanst.SCENE_URL, "//" + locator + "." + component);
                texts = convertToList(response,"text");
                if(texts.size()>0){
                    result = convert(response, "text").trim();
                }else {
                    result = "";
                }
            }
            System.out.println("getTexts " + text);
            System.out.println("getTexts " + expect);
            return text.trim();
        }catch (Exception e){
            Log.error("getTextsError "+e.getMessage());
            exception(e.getMessage());
            return "";
        }
    }
    public static String getTexts(String locator,String component,String expect){
        return getTextsByTime(locator,component,"15",expect);
    }
    private static List<String> getListTexts(String locator,String component){
        Response response = request(Constanst.SCENE_URL, "//" + locator + "." + component);
        return convertToList(response,"text");
    }
    public static String getTextsByLocator(String locator,String component,String locator2,String expect){
        LocalDateTime time = LocalDateTime.now();
        LocalDateTime time1 = time.plusSeconds(30);
        List<String> results = new ArrayList<>();
        List<String > texts = getListTexts(locator,component);
        Response response = null;
        boolean stop = false;
        try {
            String text ="";
            while (time.compareTo(time1) <= 0) {
                if(texts.size()>0) {
                response = request(Constanst.SCENE_URL, "//" + locator + "." + component);
                    String result = convert(response, "text").trim();
                    if(!results.contains(result)) {
                        results.add(result);
                        if (!result.equals("")) {
                            if(text.equals("")){
                                text = text + result.trim();
                            }else {
                                text = text + " " + result.trim();
                            }
                        }
                    }
                }else {
                    texts = getListTexts(locator,component);
                }
                System.out.println("getTexts "+text);
                System.out.println("getTexts "+expect);
                if (text.contains(expect)||stop==true) {
                    break;
                }else {
                    stop = isElementDisplay(locator2);
                    time = LocalDateTime.now();
                }
            }
            return text.trim();
        }catch (Exception e){
            Log.error("getTextsError "+e.getMessage());
            exception(e.getMessage());
            return "";
        }
    }
    public static String getTextNoColor(String locator,String component,String... StrSplit){
        Response response = request(Constanst.SCENE_URL,"//" +locator+"."+component);
        String values = convert(response,"text").trim();
        String result = null;
        for (String value:values.split(" ")) {
            for (String str:StrSplit) {
                if(value.contains(str))
                    result += value.replace(str,"")+" ";
            }
        }
        return result.trim();
    }
    public static String getTextLocatorChild(String locator, String component, String key,String... StrSplit){
        String locatorChild = FileHelpers.getValueConfig(FileHelpers.getRootFolder()+Constanst.VARIABLE_PATH_FILE,key)+"/"+locator;
        waitForObject(locatorChild);
        return  getTextNoColor(locatorChild,component,StrSplit);
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
        return getPropertyValue(locator,"AudioSource","clip"," (UnityEngine.AudioClip)");
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
    public static String getVideoName(String locator,String strSplit,String indexSplit){
        return getPropertyValue(locator,"VideoPlayer","clip",strSplit,indexSplit);
    }
    public static String getVideoUrl(String locator,String strSplit,String contain){
        return getPropertyValue(locator,"VideoPlayer","url",strSplit,contain);
    }
    public static String getVideoUrl(String locator, String component,String key,String strSplit,String contain,String expected){
        String url = getPropertyValue(KeyWordsToAction.getPath(locator,component,key,expected),"VideoPlayer","url",strSplit,contain);
        if(url.equals(expected)){
            return Constanst.TRUE;
        }else {
            Log.info("Found ["+url+"]");
            return Constanst.FALSE;
        }
    }
    public static String getResultByKey(String locator,String component,String key){
        Response response = request(Constanst.SCENE_URL,"//" +locator+"."+component);
        String value = JsonHandle.getValue(response.getBody().asString(),key);
        return value;

    }
    public static String getSentenceByText(String locators,String component){
        String sentence = null;
        Response response = request(Constanst.SCENE_URL,"//" +locators+"."+component);
        List<String> list = convertToList(response,"text");
        for (String text: list) {
           if(text.matches("^[a-z0-9A-Z]{2,25}$")){
               sentence = sentence + text +" ";
           }else {
               sentence = sentence + text +" ";
           }
        }
        return sentence;
    }
    public static String getSentenceByText(String locators,String component,String strSplit){
        String sentence = null;
        Response response = request(Constanst.SCENE_URL,"//" +locators+"."+component);
        List<String> list = convertToList(response,"text");
        for (String text: list) {
            text = getTextNoColor(locators,component,strSplit);
            if(text.matches("^[a-z0-9A-Z]{2,25}$")){
                sentence = sentence + text +" ";
            }else {
                sentence = sentence + text +" ";
            }
        }
        return sentence;
    }
}
