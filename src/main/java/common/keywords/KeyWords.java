package common.keywords;

import common.utility.Constanst;
import common.utility.Log;
import execute.RunTestScript;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.response.Response;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;

public class KeyWords {
    public static AppiumDriver driver;
    public static String scroll;

    //region KEYWORD_EXCEL
    public static AppiumDriver openApp(){
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("appium:udid","7cbc1b6a");
        caps.setCapability("platformName","android");
        caps.setCapability("appium:automationName","uiautomator2");
        caps.setCapability("appium:appPackage","com.earlystart.android.monkeyjunior");
        caps.setCapability("appium:appActivity","com.earlystart.android.monkeyjunior.MainActivity");
        caps.setCapability("appium:newCommandTimeout","144000");
        caps.setCapability("appium:enableMultiWindows","true");

        URL url = null;
        try {
            url = new URL("http://127.0.0.1:4723");
        }catch (Exception e){

        }
        if(url == null)
            throw new RuntimeException("Can't conect to server url @http://127.0.0.1:4723");
        driver = new AndroidDriver(url,caps);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;
    }

    public static void waitingForCourseListDisplay(){
        System.out.println("=======================");
        System.out.println("| waitingForCourseListDisplay |Bạn đang đứng ở Course List");
        System.out.println("=======================");
    }

    //region ACTION
    public static void sleep(String second)  {
        try {
            Thread.sleep(Integer.valueOf(second) * 1000);
            Log.info("Sleep: " +second);
        }catch (InterruptedException e){
            Log.error("Sleep: " +e.getMessage());
        }
    }
    public static void click(String locator, String property){
        waitForObject(locator);
        request(Constanst.SCENE_URL,"//"+locator+"."+property);
    }
    public static void clickDownAndUp(String locator){
        waitForObject(locator);
        request(Constanst.POINTER_URL,".DownToUp("+getAbsolutePath(locator,"0")+")");
    }
    public static void clickDownAndUp(String locator,String index){
        waitForObject(locator);
        String absolutePath = getAbsolutePath(locator,"0");
        if(absolutePath.contains(":"))
            absolutePath = absolutePath.replace(":","!_!");
        request(Constanst.POINTER_URL,".DownToUp("+absolutePath+","+index+")");
    }
    public static void press(String locator){
        waitForObject(locator);
        request(Constanst.POINTER_URL,".Press("+getAbsolutePath(locator,"0")+")");
    }
    public static void press(String locator,String index){
        waitForObject(locator);
        String absolutePath = getAbsolutePath(locator,"0");
        if(absolutePath.contains(":"))
            absolutePath = absolutePath.replace(":","!_!");
        request(Constanst.POINTER_URL,".Press("+absolutePath+","+index+")");
    }
    public static void swipeToLeft(String number){
        for(int i = 0; i<Integer.valueOf(number);i++){
            request(Constanst.SIMULATE_URL,Constanst.DRAG_ACTION + "(1000,500,100,500,0.5)");
        }
    }
    public static void swipeToRight(String number){
        for(int i = 0; i<Integer.valueOf(number);i++){
            request(Constanst.SIMULATE_URL,Constanst.DRAG_ACTION + "(500,750,500,800,0.5)");
        }
    }
    public static void simulateClick(String locator){
        waitForObject(locator);
        Response response = request(Constanst.SCENE_URL,"//"+locator+".RectTransform");
        String x = convert(response,"position.x",0,"\\.");
        String y = convert(response,"position.y",0,"\\.");
        request(Constanst.SIMULATE_URL,".click("+x+","+y+")");
    }
    public static void swipeToDown(String number){
        for(int i = 0; i<Integer.valueOf(number);i++){
            request(Constanst.SIMULATE_URL,Constanst.DRAG_ACTION + "(400,500,100,100,0.5)");
            sleep("1");
        }
    }
    public static void move(String locator1, String locator2){
        waitForObject(locator1);
        waitForObject(locator2);
        String absolutePath1 = getAbsolutePath(locator1,"0");
        String absolutePath2 = getAbsolutePath(locator2,"0");
        request(Constanst.POINTER_URL, Constanst.MOVE_ACTION + "(" + absolutePath1 + "," + absolutePath2 + ")");
        sleep("1");
    }
    public static void moveByCoordinates(String locator1, String number){
        waitForObject(locator1);
        String absolutePath1 = getAbsolutePath(locator1,"0");
        request(Constanst.POINTER_URL, Constanst.MOVE_COORDINATE + "(" + absolutePath1 + "," + number + ")");
        sleep("1");
    }
    //endregion ACTION

    //region VERIFY
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
            return "false";
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
    public static String getImageName(String locator){
       String result =  getPropertyValue(locator,"Image","sprite");
       if(result.contains("(UnityEngine.Sprite)"))
           result = result.replace("(UnityEngine.Sprite)","");
       return result.trim();
    }
    public static String getImageColor(String locator){
        String result =  getPropertyValue(locator,"Image","color");
        return result.trim();
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
    public static String getText(String locator,String component){
        Response response = request(Constanst.SCENE_URL,"//" +locator+"."+component);
        return convert(response,"text").trim();
    }
    public static String getSpineState(String locator){
        try {
            return getPropertyValue(locator, "SkeletonGraphic", "AnimationState");
        }catch (Exception e){
            return null;
        }
    }
    public static String getAudioSource(String locator){
        return getPropertyValue(locator,"AudioSource","clip");
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

    //endregion VERIFY

    //region WAIT
    public static void waitForObject(String locator){
        try {
            LocalDateTime time = LocalDateTime.now();
            LocalDateTime time1 = time.plusSeconds(10);
            Response response = null;
            do {
                response = request(Constanst.SCENE_URL, "//" + locator);
                    JsonPath json = response.jsonPath();
                    List name = (List)json.get("name");
                    if (json != null && !name.isEmpty()) {
                        if(convert(response,"activeInHierarchy")=="true")
                        break;
                    }
                Thread.sleep(500);
                time = LocalDateTime.now();
            } while (time.compareTo(time1) <= 0);
            Assert.assertTrue(locator.contains(convert(response, "name")));
        }catch (Throwable e){
            exception(e);
        }
        Log.info("waitForObject :" + locator);
    }
    public static void waitForObjectNotPresent(String locator){
        try {
            LocalDateTime time = LocalDateTime.now();
            LocalDateTime time1 = time.plusSeconds(10);
            Response response = null;
            do {
                response = request(Constanst.SCENE_URL, "//" + locator);
                JsonPath json = response.jsonPath();
                List name = (List)json.get("name");
                if (json != null && !name.isEmpty()) {
                    if(!locator.contains(convert(response, "name"))){
                        break;
                    }
                }
                Thread.sleep(500);
                time = LocalDateTime.now();
            } while (time.compareTo(time1) <= 0);
        }catch (Throwable e){
            exception(e);
        }
    }
    public static void waitForObject(String locator,String second){
        try {
            LocalDateTime time = LocalDateTime.now();
            LocalDateTime time1 = time.plusSeconds(Integer.valueOf(second));
            Response response = null;
            do {
                response = request(Constanst.SCENE_URL, "//" + locator);
                JsonPath json = response.jsonPath();
                List name = (List)json.get("name");
                if (json != null && !name.isEmpty()) {
                    break;
                }
                Thread.sleep(500);
                time = LocalDateTime.now();
            } while (time.compareTo(time1) <= 0);
            Assert.assertTrue(locator.contains(convert(response, "name")));
        }catch (Throwable e){
            exception(e);
        }
    }
    public static void waitForObjectContain(String locator, String key,String content){
        try {
            LocalDateTime time = LocalDateTime.now();
            LocalDateTime time1 = time.plusSeconds(10);
            Response response = null;
            do {
                response = request(Constanst.SCENE_URL, "//" + locator);
                JsonPath json = response.jsonPath();
                List name = (List)json.get("name");
                if (json != null && !name.isEmpty()) {
                    if(convert(response,key).contains(content))
                        break;
                }
                Thread.sleep(500);
                time = LocalDateTime.now();
            } while (time.compareTo(time1) <= 0);
        }catch (Throwable e){
            exception(e);
        }
        Log.info("waitForObjectContain :" + locator);
    }
    public static void waitForObjectInScreen(String locator){
        try {
            LocalDateTime time = LocalDateTime.now();
            LocalDateTime time1 = time.plusSeconds(10);
            Response response = null;
            float with = Float.valueOf(getSizeScreen("w"));
            float height = Float.valueOf(getSizeScreen("h"));
            do {
                response = request(Constanst.SCENE_URL,"//"+locator+".RectTransform");
                JsonPath json = response.jsonPath();
                List name = (List)json.get("name");
                if (json != null && !name.isEmpty()) {
                    float x = Float.valueOf(getPointScreen(response,"x"));
                    float y = Float.valueOf(getPointScreen(response,"y"));
                    if(x<= with && y <= height)
                        break;
                }
                Thread.sleep(500);
                time = LocalDateTime.now();
            } while (time.compareTo(time1) <= 0);
        }catch (Throwable e){
            exception(e);
        }
        Log.info("waitForObjectInScreen :" + locator);
    }
    public static void waitForObjectInScreen(String locator,String second){
        try {
            LocalDateTime time = LocalDateTime.now();
            LocalDateTime time1 = time.plusSeconds(Integer.valueOf(second));
            Response response = null;
            float with = Float.valueOf(getSizeScreen("w"));
            float height = Float.valueOf(getSizeScreen("h"));
            do {
                response = request(Constanst.SCENE_URL,"//"+locator+".RectTransform");
                JsonPath json = response.jsonPath();
                List name = (List)json.get("name");
                if (json != null && !name.isEmpty()) {
                    float x = Float.valueOf(getPointScreen(response,"x"));
                    float y = Float.valueOf(getPointScreen(response,"y"));
                    if(x<= with && y <= height)
                        break;
                }
                Thread.sleep(500);
                time = LocalDateTime.now();
            } while (time.compareTo(time1) <= 0);
        }catch (Throwable e){
            exception(e);
        }
        Log.info("waitForObjectInScreen :" + locator);
    }
    public static void waitForObjectNotInScreen(String locator,String second,String size,String coordinate){
        try {
            LocalDateTime time = LocalDateTime.now();
            LocalDateTime time1 = time.plusSeconds(Integer.valueOf(second));
            Response response = null;
            float with = Float.valueOf(getSizeScreen(size));
            do {
                response = request(Constanst.SCENE_URL,"//"+locator+".RectTransform");
                JsonPath json = response.jsonPath();
                List name = (List)json.get("name");
                if (json != null && !name.isEmpty()) {
                    float x = Float.valueOf(getPointScreen(response,coordinate));
                    if(x> with)
                        break;
                }
                Thread.sleep(500);
                time = LocalDateTime.now();
            } while (time.compareTo(time1) <= 0);
        }catch (Throwable e){
            exception(e);
        }
        Log.info("waitForObjectNotInScreen :" + locator);
    }
    //endregion

    //endregion KEYWORD_EXCEL

    //region OTHER
    public static void connectUnity(){
        String baseUri = Constanst.SCENE_URL;
        RequestSpecification request = given();
        request.baseUri(baseUri);
        request.basePath("//HomeButton.Button.onClick()");
        Response response = request.get();
        //Response response = request.get("/1");
        response.prettyPrint();
    }
    public static Response request(String baseUri,String basePath){
        try {
            Log.info("request: "+ baseUri+basePath);
            RequestSpecification request = given();
            request.baseUri(baseUri);
            request.basePath(basePath);
            return request.get();
        }catch (Throwable e){
            RunTestScript.onFail("| request | "+ e.getMessage());
            return null;
        }
    }
    private static Response request(String baseUri,String basePath,int number){
        RequestSpecification request = given();
        request.baseUri(baseUri);
        request.basePath(basePath);
        return request.get("/"+number);
    }
    public static void check(String actual,String expect){
        RunTestScript.result = Constanst.PASS;
        RunTestScript.error = "";
        try{
            Assert.assertEquals(actual,expect);
        }catch (Throwable e){
            exception(e);
        }
    }
    private static String getAbsolutePath(String locator, String index){
        Response response = request(Constanst.SCENE_URL,"//"+locator + "["+Integer.valueOf(index)+"]");
        String absolutePath = convert(response,"path");
        if(absolutePath.contains(":"))
            absolutePath = absolutePath.replace(":","!_!");
        return absolutePath;
    }
    private static String convert(Response response,String key){
        try {
            Log.info(String.valueOf(response.getBody().jsonPath().getList(key).get(0)));
            return String.valueOf(response.getBody().jsonPath().getList(key).get(0));
        }catch (Throwable e){
            Log.info(response.prettyPrint());
            exception(e);
            return null;
        }
    }
    private static String convert(Response response,String key,int index,String splitStr){
        String result =  String.valueOf(response.getBody().jsonPath().getList(key).get(0));
        String[] a = result.split(splitStr);
        return Arrays.stream(a).toList().get(index);
    }
    private static void exception(Throwable e){
        RunTestScript.error = "Verify | " +e.getMessage();
        Log.error(RunTestScript.error);
        RunTestScript.onFail( RunTestScript.error);
    }
    public static String isMoveType(String locator,String second, String type,String size){
        float x1 = Float.valueOf(getPointScreen(locator,type));
        float with = Float.valueOf(getSizeScreen(size));
        sleep(second);
        float x2 = Float.valueOf(getPointScreen(locator,type));
        Log.info("|isMoveType| "+type + ": |Before : " +x1 +"| -- |AFTER : " +x2+ " |");
        if(x2<with)
            return String.valueOf(x1<x2);
        return null;
    }
    public static String isMoveType(String locator, String type){
        float x1 = Float.valueOf(getPointScreen(locator,type));
        sleep("0.5");
        float x2 = Float.valueOf(getPointScreen(locator,type));
        Log.info("|isMoveType| "+type + ": |Before : " +x1 +"| -- |AFTER : " +x2+ " |");
        return String.valueOf(x1<x2);

    }
    //endregion

    //region TAKE PHOTO
    public static byte[] takePhoto(){
        Response response = request(Constanst.TAKE_PHOTO,"");
        return response.asByteArray();
    }
    //endregion
}
