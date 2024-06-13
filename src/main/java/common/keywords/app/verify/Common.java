package common.keywords.app.verify;

import common.keywords.app.Convert;
import common.keywords.app.RequestEx;
import common.keywords.app.action.Wait;
import common.utility.Constanst;
import common.utility.Log;
import io.restassured.response.Response;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Common {
    public static String getPropertyValue(String locator, String component, String property){
        //waitForObject(locator);
        Response response = RequestEx.request(Constanst.SCENE_URL,"//"+locator+"."+component);
        return Convert.convert(response,property);
    }
    public static String getPropertyValue(String locator, String component, String property,String slipStr){
        Wait.waitForObject(locator);
        Response response = RequestEx.request(Constanst.SCENE_URL,"//"+locator+"."+component);
        String value = Arrays.stream(Objects.requireNonNull(Convert.convert(response, property)).split(slipStr)).toList().get(0).trim();
        if(value.contains("(")){
            value = value.replace("(","").trim();
        }
        Log.info(value);
        return value;
    }
    public static String getPropertyValue(String locator, String component, String property,String slipStr,String contain){
        //waitForObject(locator);
        Response response = RequestEx.request(Constanst.SCENE_URL,"//"+locator+"."+component);
        String result = Convert.convert(response,property);
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
}
