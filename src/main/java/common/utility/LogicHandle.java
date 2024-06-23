package common.utility;

import common.keywords.app.KeyWordsToAction;
import common.keywords.app.KeyWordsToActionToVerify;
import io.restassured.response.Response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static common.keywords.app.KeyWordsToAction.*;
import static common.keywords.app.KeyWordsToActionToVerify.isElementDisplay;

public class LogicHandle {
    public static List<Integer> convertToArrayListInt(String str) {
        try {
            String replace = str;
            if (replace.contains(",") && replace.contains("[")) {
                replace = str.replaceAll("^\\[|]$", "");
                List<String> myList = new ArrayList<String>(Arrays.asList(replace.split(",")));
                List<Integer> list = new ArrayList<>();
                for (String s : myList) {
                    s = LogicHandle.removeString(s,"\"");
                    list.add(Integer.valueOf(s));
                }
                return list;
            }
        }catch (Exception e){
            Log.error("|convertToArrayListInt| "+e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    public static List<String> convertToArrayListString(String str){
        String replace = str;
        if (replace.contains(",")&& replace.contains("[")) {
            replace = str.replaceAll("^\\[|]|\"$", "");
            List<String> myList = new ArrayList<String>(Arrays.asList(replace.split(",")));
            return myList;
        }
        return null;
    }
    public static List<String> convertToArrayListString(String str,String slipStr){
        String replace = str;
        if (replace.contains(",")&& replace.contains("[")) {
            replace = str.replaceAll("^\\[|]|\"$", "");
            replace = removeString(replace,slipStr);
            List<String> myList = new ArrayList();
            for (String text: replace.split("\\,")){
                myList.add(text);
        }
            return myList;
        }
        return null;
    }
    public static String removeString(String str,String slipStr){
        if (str.contains(slipStr)) {
            str = str.replace(slipStr,"");
        }
        return str;
    }
    public static String convertListToString(List<String> list){
        StringBuilder resultBuilder = new StringBuilder();
        for (String element : list) {
            if (!element.contains("([")) {
                resultBuilder.append(element).append("|");
            }
        }
        return resultBuilder.toString().trim();
    }
    public static List<String> convertStringToList(String inputString){
        inputString = splitString(inputString);
        List<String> resultList = new ArrayList<>();
        for (String spitStr:Constanst.splits) {
            if(inputString.contains(spitStr)){
                resultList = Arrays.asList(inputString.split(spitStr));
                break;
            }
        }
        return resultList;
    }
    private static String splitString(String str){
        String result = str;
        if(str.startsWith("[") && str.endsWith("]")){
            result = str.replace("[","").replace("]","");
        }
        return result;
    }
    public static String getProValuesByTime(String locator,String component,String property,String second,String expect){
        String str= KeyWordsToActionToVerify.getPropertyValue(locator,component,property);
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
                    Response response = request(Constanst.SCENE_URL_UNIUM, "//" + locator + "." + component);
                    List<String> texts = getListProValue(locator, component,property);
                    if (texts.size() > 0) {
                        String result = KeyWordsToAction.convert(response, property).trim();
                        if (!results.contains(result)) {
                            results.add(result);
                            if (!result.equals("")) {
                                if (text.equals("")) {
                                    text = text + result.trim();
                                } else {
                                    text = text + "|" + result.trim();
                                }
                            }
                        }
                    }
                }else {
                    break;
                }
                time = LocalDateTime.now();
                //sleep(0.1f);
            }
            return text.trim();
        }catch (Exception e){
            Log.error("getTextsError "+e.getMessage());
            exception(e.getMessage());
            return "";
        }
    }
    public static String getProValues(String locator,String component,String property,String expect){
        return getProValuesByTime(locator,component,property,"15",expect);
    }
    private static List<String> getListProValue(String locator,String component,String property){
        Response response = request(Constanst.SCENE_URL_UNIUM, "//" + locator + "." + component);
        return KeyWordsToAction.convertToList(response,property);
    }
    public static String getProValueByLocator(String locator,String component,String property,String locator2,String expect){
        LocalDateTime time = LocalDateTime.now();
        LocalDateTime time1 = time.plusSeconds(30);
        List<String> results = new ArrayList<>();
        List<String > texts = getListProValue(locator,component,property);
        Response response = null;
        boolean stop = false;
        try {
            String text ="";
            while (time.compareTo(time1) <= 0) {
                if(texts.size()>0) {
                    response = request(Constanst.SCENE_URL_UNIUM, "//" + locator + "." + component);
                    String result = convert(response, property).trim();
                    if(!results.contains(result)) {
                        results.add(result);
                        if (!result.equals("")) {
                            if(text.equals("")){
                                text = text + result.trim();
                            }else {
                                text = text + "|" + result.trim();
                            }
                        }
                    }
                }else {
                    texts = getListProValue(locator,component,property);
                }
                /*System.out.println("getTexts "+text);
                System.out.println("getTexts "+expect);*/
                if (text.contains(expect)||stop==true) {
                    break;
                }else {
                    stop = isElementDisplay(locator2);
                    time = LocalDateTime.now();
                }
            }
            return text.trim();
        }catch (Exception e){
            Log.error("getTextsError "+e.getMessage());
            exception(e.getMessage());
            return "";
        }
    }
    public static String getProValuesByTime(String locator,String component,String property,String second,String expect,String split){
        expect = replaceStr(expect,split);
        return getProValuesByTime(locator,component,property,second,expect);
    }
    public static String getProValues(String locator,String component,String property,String expect,String split){
        expect = replaceStr(expect,split);
        return getProValues(locator,component,property,expect);
    }
    public static String getProValueByLocator(String locator,String component,String property,String locator2,String expect,String split){
        expect = replaceStr(expect,split);
        return getProValueByLocator(locator,component,property,locator2,expect);
    }
    public static String replaceStr(String str,String replace){
        if(str.contains(replace)){
            return str.replace(replace,"");
        }
        return str;
    }
    public static String replaceStr(String str,String oldStr,String newStr){
        if(str.contains(oldStr)){
            return str.replace(oldStr,newStr);
        }
        return str;
    }
    public static int getIndexInList(List<Object> list,String item){
        return list.indexOf(item);
    }
    public static int getIndexInListEndWith(List<Object> list,String item){
        int index = 0;
        for (int i =0;i<list.size();i++){
            if(item.endsWith(list.get(i).toString())){
                index=i;
                break;
            }
        }
        return index;
    }
    public static String getEndWithTextAlphabet(String text){
        if (!Character.isLetter(text.charAt(text.length() - 1))) {
            text = text.substring(0, text.length() - 1);
        }
        return text;
    }
    public static String getTextAlphabet(String text){
        return text.replaceAll("[^\\p{Alpha}\\p{Digit}\\s]", "");
    }
    public static String getNumber(String text){
        try {
            return text.replaceAll("[^\\p{Digit}\\s]", "");
        }catch (Exception e){
            return "0";
        }
    }
    public static int calculate(String operator,int value,int number){
        int result =0;
        switch (operator){
            case "/":
                result = value / number;
                break;
            case "*":
                result = value * number;
                break;
            case "-":
                result = value - number;
                break;
            case "+":
                result = value + number;
                break;
            default:
                result = value;
                break;
        }
        return result;
    }
}
