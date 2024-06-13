package common.keywords.app.verify;

import common.keywords.app.Convert;
import common.keywords.app.RequestEx;
import common.utility.Constanst;
import common.utility.Log;
import common.utility.LogicHandle;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class IsElement {
    public static boolean isElementDisplay(String locator){
        try {
            Response response = RequestEx.request(Constanst.SCENE_URL, "//" + locator);
            ResponseBody body = response.getBody();
            List actvies = Convert.convertToList(response,"activeInHierarchy");
            boolean value = false;
            if(actvies.size()>0){
                String result = Convert.convert(response,"activeInHierarchy");
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
    public static String isElementDisplay(String strSplit, String locator){
        try {
            if(locator.contains(strSplit)){
                locator = Arrays.stream(locator.split(strSplit)).toList().get(0);
            }
            Response response = RequestEx.request(Constanst.SCENE_URL, "//" + locator);
            ResponseBody body = response.getBody();
            List actvies = Convert.convertToList(response,"activeInHierarchy");
            boolean value = false;
            if(actvies.size()>0){
                String result = Convert.convert(response,"activeInHierarchy");
                if(result.equals("true")){
                    value =true;
                }
            }
            return String.valueOf(value);
        }catch (Exception e){
            Log.info("No Such element " +locator);
            return String.valueOf(false);
        }
    }
    public static String isElementsDisplay(String strSplit, String locator) throws InterruptedException {
        return isElementsDisplay("5",strSplit,locator);
    }
    public static String isElementsDisplay(String second,String strSplit, String locator) throws InterruptedException {
        System.out.println(locator);
        boolean enable = false;
        LocalDateTime time = LocalDateTime.now();
        LocalDateTime time1 = time.plusSeconds(Integer.parseInt(second));
        while (!time.isAfter(time1)) {
            if (locator.contains("[") && locator.contains(",")) {
                locator = LogicHandle.replaceStr(locator, "[");
                locator = LogicHandle.replaceStr(locator, "]");
                List<String> locators = LogicHandle.convertStringToList(locator);
                for (String item : locators) {
                    System.out.println(item);
                    enable = isElementDisplay(LogicHandle.replaceStr(item, strSplit));
                    if (enable) {
                        //System.out.println(item);
                        break;
                    }
                }
            }else {
                enable = isElementDisplay(LogicHandle.replaceStr(locator, strSplit));
                if (enable) {
                    //System.out.println(item);
                    break;
                }
            }
            if (enable){
                break;
            }
            Thread.sleep(500);
            time = LocalDateTime.now();
        }
        return String.valueOf(enable);
    }
}
