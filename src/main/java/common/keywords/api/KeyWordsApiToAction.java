package common.keywords.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import common.keywords.ui.KeyWordCustomByGame;
import common.keywords.ui.KeyWordCustomForAISpeak;
import common.keywords.ui.KeyWordsToActionPocoSDK;
import common.keywords.ui.KeyWordsToActionToVerify;
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

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class KeyWordsApiToAction {

}
