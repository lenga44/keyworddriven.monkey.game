package common.keywords;

import com.google.gson.JsonElement;
import common.utility.*;
import execute.TestScrip;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;

public class KeyWordsToAction {
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
            Thread.sleep((long) (Float.valueOf(second) * 1000));
            Log.info("Sleep: " +second);
        }catch (InterruptedException e){
            exception(e);
        }
    }
    public static void sleep(int second)  {
        try {
            Thread.sleep (second * 1000);
            Log.info("Sleep: " +second);
        }catch (InterruptedException e){
            exception(e);
        }
    }
    public static void sleep()  {
        try {
            Thread.sleep((long) (2 * 1000));
        }catch (InterruptedException e){
            exception(e);
        }
    }
    public static void click(String locator, String property){
        waitForObject(locator);
        request(Constanst.SCENE_URL,"//"+locator+"."+property);
    }
    public static void click(String locator,String component, String property){
        waitForObject(locator);
        request(Constanst.SCENE_URL,"//"+locator+"."+component+"."+property);
    }
    public static void click(String locator,String index,String component, String property){
        waitForObject(locator);
        request(Constanst.SCENE_URL,"//"+locator+"[" +index+"]"+"."+component+"."+property);
    }
    public static void clickWhichObjectEnable(String locator,String index,String component, String property){
        request(Constanst.SCENE_URL,"//"+locator+"[" +index+"]"+"."+component+"."+property);
    }
    public static void clickLocatorChild(String locator, String component, String property,String key){
        String locatorChild = FileHelpers.getValueConfig(FileHelpers.getRootFolder()+Constanst.VARIABLE_PATH_FILE,key)+"/"+locator;
        waitForObject(locatorChild);
        request(Constanst.SCENE_URL,"//"+locatorChild+"."+component+"."+property);
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
    @Deprecated
    public static void returnPath(String locator, String component,String key,String expected) throws IOException {
        String path = FileHelpers.getRootFolder()+FileHelpers.convertPath(Constanst.VARIABLE_PATH_FILE);
        waitForObject(locator);
        int index = 0;
        Response response = request(Constanst.SCENE_URL,"//"+locator+"."+component);
        ResponseBody body = response.getBody();
        String json = body.asString();
        for (JsonElement element: JsonHandle.getJsonArray(json)) {
            if(JsonHandle.getValue(element.toString(),"$."+key).equals(expected))
                break;
            index++;
        }
        Response response1 = request(Constanst.SCENE_URL,"//"+locator);
        String value = getAbsolutePath(response1,String.valueOf(index));
        String result = JsonHandle.setValueInJsonObject(path,Constanst.PATH_GAME_OBJECT,value);
        FileHelpers.writeFile(result,path);
        ExcelUtils.closeFile(path);
    }
    public static void returnIndex(){

    }
    public static void press(String locator){
        //waitForObject(locator);
        request(Constanst.POINTER_URL,".Press("+getAbsolutePath(locator,"0")+")");
    }

    public static void press(String locator,String index){
        waitForObject(locator);
        String absolutePath = getAbsolutePath(locator,"0");
        if(absolutePath.contains(":"))
            absolutePath = absolutePath.replace(":","!_!");
        request(Constanst.POINTER_URL,".Press("+absolutePath+","+index+")");
    }
    /*public static void swipeToLeft(String number){
        for(int i = 0; i<Integer.valueOf(number);i++){
            request(Constanst.SIMULATE_URL,Constanst.DRAG_ACTION + "(1000,500,100,500,0.5)");
        }
    }*/
    public static void swipe(String x1, String x2, String y){
        request(Constanst.SIMULATE_URL,Constanst.DRAG_ACTION + "("+x1+","+y+","+x2+","+y+",0.5)");
    }
    /*public static void swipeToRight(String number){
        for(int i = 0; i<Integer.valueOf(number);i++){
            request(Constanst.SIMULATE_URL,Constanst.DRAG_ACTION + "(500,750,500,800,0.5)");
        }
    }*/
    public static void swipeToRight(String x1, String x2, String y){
        request(Constanst.SIMULATE_URL,Constanst.DRAG_ACTION + "("+x2+","+y+","+x1+","+y+",0.5)");
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
    public static void sendKey(String locator,String component, String property,String name){
        request(Constanst.SCENE_URL,"//"+locator+"."+component+"."+property+"="+name);
    }
    public static void sendKey(String locator,String component,String name){
        request(Constanst.SCENE_URL,"//"+locator+"."+component+".text="+name);
    }
    //endregion ACTION

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
                if(!locator.contains(convert(response, "name"))||convert(response,"activeInHierarchy")=="false"){
                    break;
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
                if(response!=null) {
                    JsonPath json = response.jsonPath();
                    List name = (List) json.get("name");
                    if (json != null && !name.isEmpty()) {
                        break;
                    }
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
            Log.info("waitForObjectContain :" + locator);
            LocalDateTime time = LocalDateTime.now();
            LocalDateTime time1 = time.plusSeconds(30);
            Response response = null;
            String value= null;
            do {
                response = request(Constanst.SCENE_URL, "//" + locator);
                if(response!=null) {
                    JsonPath json = response.jsonPath();
                    Log.info("waitForObjectContain :" + json.prettyPrint());
                    if (json != null && json.toString() != "") {
                        value = convert(response, key);
                        if (value != null) {
                            if (value.contains(content))
                                break;
                        }
                        Thread.sleep(500);
                    }
                }
                time = LocalDateTime.now();
            } while (time.compareTo(time1) <= 0);
            Assert.assertTrue(value.contains(content));
        }catch (Throwable e){
            exception(e);
        }
    }
    public static void waitForObjectContain(String locator,String component, String property,String content){
        try {
            LocalDateTime time = LocalDateTime.now();
            LocalDateTime time1 = time.plusSeconds(30);
            Response response = null;
            String value = null;
            do {
                response = request(Constanst.SCENE_URL, "//" + locator+"."+component);
                if(response!=null) {
                    JsonPath json = response.jsonPath();
                    if (json != null && json.toString() != "") {
                        value = convert(response, property);
                        if(value!=null) {
                            if (value.contains(content))
                                break;
                        }
                        Thread.sleep(500);
                    }
                }
                time = LocalDateTime.now();
            } while (time.compareTo(time1) <= 0);
            Assert.assertTrue(value.contains(content));
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
            float with = Float.valueOf(KeyWordsToActionToVerify.getSizeScreen("w"));
            float height = Float.valueOf(KeyWordsToActionToVerify.getSizeScreen("h"));
            do {
                response = request(Constanst.SCENE_URL,"//"+locator+".RectTransform");
                JsonPath json = response.jsonPath();
                List name = (List)json.get("name");
                if (json != null && !name.isEmpty()) {
                    float x = Float.valueOf(KeyWordsToActionToVerify.getPointScreen(response,"x"));
                    float y = Float.valueOf(KeyWordsToActionToVerify.getPointScreen(response,"y"));
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
            float with = Float.valueOf(KeyWordsToActionToVerify.getSizeScreen("w"));
            float height = Float.valueOf(KeyWordsToActionToVerify.getSizeScreen("h"));
            do {
                response = request(Constanst.SCENE_URL,"//"+locator+".RectTransform");
                JsonPath json = response.jsonPath();
                List name = (List)json.get("name");
                if (json != null && !name.isEmpty()) {
                    float x = Float.valueOf(KeyWordsToActionToVerify.getPointScreen(response,"x"));
                    float y = Float.valueOf(KeyWordsToActionToVerify.getPointScreen(response,"y"));
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
            float with = Float.valueOf(KeyWordsToActionToVerify.getSizeScreen(size));
            do {
                response = request(Constanst.SCENE_URL,"//"+locator+".RectTransform");
                JsonPath json = response.jsonPath();
                List name = (List)json.get("name");
                if (json != null && !name.isEmpty()) {
                    float x = Float.valueOf(KeyWordsToActionToVerify.getPointScreen(response,coordinate));
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
            TestScrip.onFail("| request | "+ e.getMessage());
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
        TestScrip.result = Constanst.PASS;
        TestScrip.error = "";
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
    private static String getAbsolutePath(Response response, String index){
        String absolutePath = convert(response,"path");
        if(absolutePath.contains(":"))
            absolutePath = absolutePath.replace(":","!_!");
        return absolutePath;
    }
    public static String convert(Response response,String key){
        try {
            String result = String.valueOf(response.getBody().jsonPath().getList(key).get(0));
            Log.info("|convert 2 param |: "+result);
            if(result.contains("\n")) {
                result = result.replace("\n", "");
            }
            Log.info("|convert 2 param |: "+result);
            return result;
        }catch (Throwable e){
            Log.info(response.prettyPrint());
            exception(e);
            return null;
        }
    }
    public static String convert(Response response,String key,int index,String splitStr){
        String result =  String.valueOf(response.getBody().jsonPath().getList(key).get(0));
        String[] a = result.split(splitStr);
        return Arrays.stream(a).toList().get(index);
    }

    public static void exception(Throwable e){
        TestScrip.error = "Exception | " +e.getMessage();
        Log.error(TestScrip.error);
        TestScrip.onFail( TestScrip.error);
    }
    public static String isMoveType(String locator,String second, String type,String size){
        float x1 = Float.valueOf(KeyWordsToActionToVerify.getPointScreen(locator,type));
        float with = Float.valueOf(KeyWordsToActionToVerify.getSizeScreen(size));
        sleep(second);
        float x2 = Float.valueOf(KeyWordsToActionToVerify.getPointScreen(locator,type));
        Log.info("|isMoveType| "+type + ": |Before : " +x1 +"| -- |AFTER : " +x2+ " |");
        if(x2<with)
            return String.valueOf(x1<x2);
        return null;
    }
    public static String isMoveType(String locator, String type){
        float x1 = Float.valueOf(KeyWordsToActionToVerify.getPointScreen(locator,type));
        sleep("0.5");
        float x2 = Float.valueOf(KeyWordsToActionToVerify.getPointScreen(locator,type));
        Log.info("|isMoveType| "+type + ": |Before : " +x1 +"| -- |AFTER : " +x2+ " |");
        return String.valueOf(x1<x2);

    }
    //endregion

    //region TAKE PHOTO
    public static byte[] takePhoto(){
        Response response = request(Constanst.TAKE_PHOTO,"");
        return response.asByteArray();
    }
    public static String getStringConvertFromArrayList(String second,String count,String value){
        ArrayList<String> list = null;
        try {
            LocalDateTime time = LocalDateTime.now();
            LocalDateTime time1 = time.plusSeconds(Integer.valueOf(second));
            list = new ArrayList<>();
            do {
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
        String result = list.toString();
        StringBuffer sb = new StringBuffer(result);
        return sb.delete(0,1).delete(result.length() - 1, result.length()).toString();
    }
    public static ArrayList<String> getListInSecond(String second,String value){
        ArrayList<String> list = null;
        try {
            LocalDateTime time = LocalDateTime.now();
            LocalDateTime time1 = time.plusSeconds(Integer.valueOf(second));
            list = new ArrayList<>();
            do {
                list.add(value);
                Thread.sleep(10);
                time = LocalDateTime.now();
            } while (time.compareTo(time1) <= 0);
        } catch (Throwable e) {
            exception(e);
        }
        return list;
    }
    //endregion
}
