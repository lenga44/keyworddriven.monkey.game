package common.keywords;

import common.utility.Constanst;
import execute.RunTestScript;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.restassured.internal.common.assertion.Assertion;
import io.restassured.specification.RequestSpecification;
import io.restassured.response.Response;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.http.HttpClient;
import org.testng.Assert;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;

public class KeyWords {
    public static AppiumDriver driver;
    public static HttpClient client;
    public static AppiumDriver openApp(){
        System.out.println("=======================");
        System.out.println("| openApp |App đã mở rồi nhé!!!");
        System.out.println("=======================");
        /*DesiredCapabilities caps = new DesiredCapabilities();
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
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);*/
        return driver;
    }
    public static void waitingForCourseListDisplay(){
        System.out.println("=======================");
        System.out.println("| waitingForCourseListDisplay |Bạn đang đứng ở Course List");
        System.out.println("=======================");
    }
    public static void u_click(){
        System.out.println("=======================");
        System.out.println("| u_click |Click click click");
        System.out.println("=======================");
    }
    public static void u_click(String locator){
        System.out.println("=======================");
        System.out.println("| u_click |Click click click " +locator);
        System.out.println("=======================");
    }
    public static void u_click(String locator, String index){
        System.out.println("=======================");
        System.out.println("| u_click |Click click click " +locator);
        System.out.println("| u_click |Click click click " +index);
        System.out.println("=======================");
    }
    public static String u_elementDisplay(String locator){
        boolean output = true;
        System.out.println("=======================");
        System.out.println("| u_elementDisplay |Element display " +output);
        System.out.println("=======================");
        return String.valueOf(output);
    }
    public static String u_elementDisplay(String locator, String index){
        boolean output = true;
        System.out.println("=======================");
        System.out.println("| u_elementDisplay |Element display " +output);
        System.out.println("| u_elementDisplay |Element display " +index);
        System.out.println("=======================");
        return String.valueOf(output);
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
    public static void connectUnity(){
        String baseUri = "http://localhost:8342/q/scene";
        RequestSpecification request = given();
        request.baseUri(baseUri);
        //request.basePath("//Chat//Question(Clone)/Image[activeInHierarchy=true].Button.onClick()");
        Response response = request.get();
        //Response response = request.get("/1");
        response.prettyPrint();
    }
}
