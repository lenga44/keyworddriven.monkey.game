package common.keywords;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import sun.net.www.http.HttpClient;

import java.net.URL;
import java.util.concurrent.TimeUnit;

public class KeyWords {
    public static AppiumDriver driver;
    public static HttpClient client;
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
}
