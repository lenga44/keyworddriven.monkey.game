package common.keywords;

import common.utility.Constanst;
import execute.RunTestScript;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.restassured.internal.common.assertion.Assertion;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.response.Response;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.http.HttpClient;
import org.testng.Assert;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;

public class KeyWords {
    public static AppiumDriver driver;
    public static HttpClient client;

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
    public static void click(String locator, String property){
        waitForObject(locator);
        request(Constanst.SCENE_URL,"//"+locator+"."+property);
    }
    public static void clickDownAndUp(String locator){
        String absolutePath = getAbsolutePath(locator,"0");
        request(Constanst.POINTER_URL,".DownToUp("+absolutePath+")");
    }
    public static void clickDownAndUp(String locator,String index){
        waitForObject(locator);
        String absolutePath = getAbsolutePath(locator,"0");
        request(Constanst.POINTER_URL,".DownToUp("+absolutePath+","+index+")");
    }
    public static void horizontalSwipe(String number){
        for(int i = 0; i<Integer.valueOf(number);i++){
            request(Constanst.SIMULATE_URL,Constanst.DRAG_ACTION + "(1000,500,100,500,0.5)");
        }
    }
    public static void simulateClick(String locator){
        waitForObject(locator);
        Response response = request(Constanst.SCENE_URL,"//"+locator+".RectTransform");
        String x = convert(response,"position.x",0,"\\.");
        String y = convert(response,"position.y",0,"\\.");
        request(Constanst.SIMULATE_URL,".click("+x+","+y+")");
    }
    //endregion ACTION

    public static String elementDisplay(String locator){
        boolean output = true;
        System.out.println("=======================");
        System.out.println("| u_elementDisplay |Element display " +output);
        System.out.println("=======================");
        return String.valueOf(output);
    }

    public static String elementDisplay(String locator, String index){
        boolean output = true;
        System.out.println("=======================");
        System.out.println("| u_elementDisplay |Element display " +output);
        System.out.println("| u_elementDisplay |Element display " +index);
        System.out.println("=======================");
        return String.valueOf(output);
    }

    public static void waitForObject(String locator){
        LocalDateTime time = LocalDateTime.now();
        LocalDateTime time1 = time.plusSeconds(15);
        Response response = null;
        do {
            response = request(Constanst.SCENE_URL, "//" + locator);
            JsonPath json = response.getBody().jsonPath();
            if (json != null) {
                break;
            }
            time = LocalDateTime.now();
        } while (time.compareTo(time1) <= 0);
        Assert.assertTrue(locator.contains(convert(response,"name")));
    }
    public static void waitForObject(String locator,String second){
        Response response = request(Constanst.SCENE_URL,"//"+locator);
        response.prettyPrint();
    }
    //endregion KEYWORD_EXCEL
    public static void main(String[] args) {
        clickDownAndUp("Item 0: On Unlock Lesson");
    }
    public static void connectUnity(){
        String baseUri = Constanst.SCENE_URL;
        RequestSpecification request = given();
        request.baseUri(baseUri);
        request.basePath("//HomeButton.Button.onClick()");
        Response response = request.get();
        //Response response = request.get("/1");
        response.prettyPrint();
    }
    private static Response request(String baseUri,String basePath){
        RequestSpecification request = given();
        request.baseUri(baseUri);
        request.basePath(basePath);
        return request.get();
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
            RunTestScript.result = Constanst.FAIL;
            RunTestScript.error = "| Verify | " +e.getMessage();
        }
    }
    private static String getAbsolutePath(String locator, String index){
        Response response = request(Constanst.SCENE_URL,"//"+locator + "["+Integer.valueOf(index)+"]");
        return convert(response,"path");
    }
    private static String convert(Response response,String key){
        return String.valueOf(response.getBody().jsonPath().getList(key).get(0));
    }
    private static String convert(Response response,String key,int index,String splitStr){
        String result =  String.valueOf(response.getBody().jsonPath().getList(key).get(0));
        String[] a = result.split(splitStr);
        return Arrays.stream(a).toList().get(0);
    }
}
