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
        String absolutePath = getAbsolutePath(locator,"0");
        if(absolutePath.contains(":"))
            absolutePath = absolutePath.replace(":","!_!");
        request(Constanst.POINTER_URL,".DownToUp("+absolutePath+")");
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
        String absolutePath = getAbsolutePath(locator,"0");
        if(absolutePath.contains(":"))
            absolutePath = absolutePath.replace(":","!_!");
        request(Constanst.POINTER_URL,".Press("+absolutePath+")");
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
    //endregion ACTION

    //region VERIFY
    public static String elementDisplay(String locator){
        try {
            waitForObject(locator);
            Response response = request(Constanst.SCENE_URL, "//" + locator);
            return convert(response, "activeInHierarchy");
        }catch (Throwable e){
            return "false";
        }
    }

    public static String elementDisplay(String locator, String index){
        return "";
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

    //endregion VERIFY
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
    //region TAKE PHOTO
    public static byte[] takePhoto(){
        Response response = request(Constanst.TAKE_PHOTO,"");
        return response.asByteArray();
    }
    //endregion

    //endregion KEYWORD_EXCEL

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
        return convert(response,"path");
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
        return Arrays.stream(a).toList().get(0);
    }
    private static void exception(Throwable e){
        RunTestScript.error = "Verify | " +e.getMessage();
        Log.error(RunTestScript.error);
        RunTestScript.onFail( RunTestScript.error);
    }
}
