package common.keywords.app.verify;

import common.keywords.app.Convert;
import common.keywords.app.ExceptionEx;
import common.keywords.app.RequestEx;
import common.keywords.app.action.SleepEx;
import common.keywords.app.action.Wait;
import common.utility.Constanst;
import common.utility.FileHelpers;
import common.utility.Log;
import common.utility.LogicHandle;
import io.restassured.response.Response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class GetText {
    public static String getTextChildElement(String locator,String locator2,String component){
        List<String> texts = new ArrayList<>();
        Response response = RequestEx.request(Constanst.SCENE_URL_UNIUM,"//" +locator);
        System.out.println(Convert.convert(response,"children"));
        List<String> childs = LogicHandle.convertStringToList(Convert.convert(response,"children"));
        for(String child: childs){
            Response response1 = RequestEx.request(Constanst.SCENE_URL_UNIUM,"//" +child);
            String components = Convert.convert(response1,"components");
            if(components.contains(component)){
                texts.add(child);
            }else {
                texts.add(locator2);
            }
        }
        String result = "";
        for(String text:texts){
            String t = getText(text,component);
            if(!result.equals("")){
                if(!t.matches("[^\\p{Alpha}\\p{Digit}\\s]")) {
                    result = result +" "+t;
                }else {
                    result+=t;
                }
            }else {
                result+=t;
            }
        }
        return result;
    }
    public static String getText(String locator,String component){
        try {
            Response response = RequestEx.request(Constanst.SCENE_URL_UNIUM, "//" + locator.trim() + "." + component);
            return LogicHandle.replaceStr(Convert.convert(response, "text").trim(),"\n");
        }catch (Exception e){
            Log.error("getText "+e.getMessage());
            return "";
        }
    }
    public static String getTextAlphabet(String locator,String component){
        Log.info("getTextAlphabet");
        String text = getText(locator,component);
        text = LogicHandle.getEndWithTextAlphabet(text);
        return text;
    }
    public static String getTextsByTime(String locator,String component,String second,String expect){
        String str= getText(locator,component);
        LocalDateTime time = LocalDateTime.now();
        LocalDateTime time1 = time.plusSeconds(Integer.valueOf(second));
        List<String> results = new ArrayList<>();
        String text ="";
        if(!str.equals("")){
            text = str;
            results.add(text);
        }
        try {
            while (time.compareTo(time1) <= 0) {
                if(!text.contains(expect)){
                    Response response = RequestEx.request(Constanst.SCENE_URL_UNIUM, "//" + locator + "." + component);
                    List<String> texts = getListTexts(locator, component);
                    if (texts.size() > 0) {
                        String result = Convert.convert(response, "text").trim();
                        if (!results.contains(result)) {
                            results.add(result);
                            if (!result.equals("")) {
                                if (text.equals("")) {
                                    text = text + result.trim();
                                } else {
                                    text = text + " " + result.trim();
                                }
                            }
                        }
                    }
                }else {
                    break;
                }
                time = LocalDateTime.now();
                SleepEx.sleep(0.2f);
            }
            return LogicHandle.replaceStr(text.trim(),"\n");
        }catch (Exception e){
            Log.error("getTextsError "+e.getMessage());
            ExceptionEx.exception(e.getMessage());
            return "";
        }
    }
    public static List<String> getListTexts(String locator, String component){
        Response response = RequestEx.request(Constanst.SCENE_URL_UNIUM, "//" + locator + "." + component);
        return Convert.convertToList(response,"text");
    }
    public static String getTexts(String locator,String component,String expect){
        return getTextsByTime(locator,component,"15",expect);
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
                    response = RequestEx.request(Constanst.SCENE_URL_UNIUM, "//" + locator + "." + component);
                    String result = Convert.convert(response, "text").trim();
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
                /*System.out.println("getTexts "+text);
                System.out.println("getTexts "+expect);*/
                if (text.contains(expect)||stop==true) {
                    break;
                }else {
                    stop = IsElement.elementDisplay(locator2);
                    time = LocalDateTime.now();
                }
            }
            return LogicHandle.replaceStr(text.trim(),"\n");
        }catch (Exception e){
            Log.error("getTextsError "+e.getMessage());
            ExceptionEx.exception(e.getMessage());
            return "";
        }
    }
    public static String getTextNoColor(String locator,String component,String... StrSplit){
        Response response = RequestEx.request(Constanst.SCENE_URL_UNIUM,"//" +locator+"."+component);
        String values = Convert.convert(response,"text").trim();
        String result = null;
        for (String value:values.split(" ")) {
            for (String str:StrSplit) {
                if(value.contains(str))
                    result += value.replace(str,"")+" ";
            }
        }
        return LogicHandle.replaceStr(result.trim(),"\n");
    }
    public static String getTextLocatorChild(String locator, String component, String key,String... StrSplit){
        String locatorChild = FileHelpers.getValueConfig(FileHelpers.getRootFolder()+Constanst.VARIABLE_PATH_FILE,key)+"/"+locator;
        Wait.waitForObject(locatorChild);
        return  LogicHandle.replaceStr(getTextNoColor(locatorChild,component,StrSplit),"\n");
    }
    public static String getTextContain(String locator,String component,String contain){
        Response response = RequestEx.request(Constanst.SCENE_URL_UNIUM,"//" +locator+"."+component);
        return LogicHandle.replaceStr(String.valueOf(Convert.convert(response,"text").trim().contains(contain)),"\n");
    }
}
