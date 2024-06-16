package common.keywords.app.action;

import common.keywords.app.ExceptionEx;
import common.keywords.app.Convert;
import common.keywords.app.RequestEx;
import common.keywords.app.verify.PointScreen;
import common.keywords.app.verify.SizeScreen;
import common.utility.Constanst;
import common.utility.Log;
import common.utility.LogicHandle;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static common.keywords.app.KeyWordsToAction.*;
import static common.keywords.app.KeyWordsToAction.exception;

public class Wait {
    public static void waitForObject(String locator){
        try {
            LocalDateTime time = LocalDateTime.now();
            LocalDateTime time1 = time.plusSeconds(10);
            Response response = null;
            do {
                response = RequestEx.request(Constanst.SCENE_URL, "//" + locator);
                JsonPath json = response.jsonPath();
                List name = (List)json.get("name");
                if (json != null && name.size()>0) {
                    if(Convert.convert(response,"activeInHierarchy")=="true")
                        break;
                }
                Thread.sleep(500);
                time = LocalDateTime.now();
            } while (time.compareTo(time1) <= 0);
            //Assert.assertTrue(locator.contains(convert(response, "name")));
        }catch (Throwable e){
            ExceptionEx.exception("No such element "+ locator);
        }
        Log.info("waitForObject :" + locator);
    }
    public static void waitForObject(String locator,String second){
        try {
            LocalDateTime time = LocalDateTime.now();
            LocalDateTime time1 = time.plusSeconds(Integer.valueOf(second));
            Response response = null;
            do {
                response = RequestEx.request(Constanst.SCENE_URL, "//" + locator);
                JsonPath json = response.jsonPath();
                List name = (List)json.get("name");
                if (json != null && name.size()>0) {
                    if(Convert.convert(response,"activeInHierarchy")=="true")
                        break;
                }
                Thread.sleep(500);
                time = LocalDateTime.now();
            } while (time.compareTo(time1) <= 0);
            //Assert.assertTrue(locator.contains(convert(response, "name")));
        }catch (Throwable e){
            ExceptionEx.exception("No such element "+ locator);
            //e.printStackTrace();
        }
        Log.info("waitForObject :" + locator);
    }
    public static void waitForObject(String second,String splitStr,String locator){
        try {
            if(!locator.contains("[")&&!locator.contains("]")){
                waitForObjectString(second,splitStr,locator);
            }else{
                waitForObjectStrings(second,splitStr, LogicHandle.convertToArrayListString(locator, "\"").toArray(new String[0]));
            }
        }
        catch (Exception e){
            ExceptionEx.exception("Not exit method wait object " + locator);
        }
    }
    public static void waitForObjectString(String second,String splitStr,String locator){
        try {
            if(locator.contains(splitStr)){
                locator = locator.replace(splitStr,"");
            }
            Response response = RequestEx.request(Constanst.SCENE_URL, "//" + locator);
            LocalDateTime time = LocalDateTime.now();
            LocalDateTime time1 = time.plusSeconds(Integer.parseInt(second));
            do {
                JsonPath json = response.jsonPath();
                List name = (List)json.get("name");
                if (json != null && !name.isEmpty()) {
                    if(Convert.convert(response,"activeInHierarchy")=="true")
                        break;
                }
                response = RequestEx.request(Constanst.SCENE_URL, "//" + locator);
                Thread.sleep(500);
                time = LocalDateTime.now();
            } while (time.compareTo(time1) <= 0);
            Assert.assertTrue(locator.contains(Convert.convert(response, "name")));
        }catch (Throwable e){
            ExceptionEx.exception("No such element "+ locator);
        }
        Log.info("waitForObject :" + locator);
    }
    public static void waitForObjectStrings(String second, String splitStr, String... locators){
        try {
            LocalDateTime time = LocalDateTime.now();
            LocalDateTime time1 = time.plusSeconds(Integer.parseInt(second));
            boolean correct = false;
            do {
                List<Response> responses = new ArrayList<>();
                for (String locator : locators) {

                    if (locator.contains(splitStr)) {
                        locator = locator.replace(splitStr, "");
                    }
                    Response response = RequestEx.request(Constanst.SCENE_URL, "//" + locator);
                    System.out.println(locator);
                    responses.add(response);
                }
                for (Response response : responses  ){
                    JsonPath json = response.jsonPath();
                    List names = (List)json.get("name");
                    System.out.println(names);
                    if(!names.isEmpty()){
                        if(Convert.convert(response,"activeInHierarchy").equals("true")){
                            correct = true;
                            break;
                        }
                    }
                }
                if (correct == true){
                    break;
                }
                Thread.sleep(500);
                time = LocalDateTime.now();
            }while (time.compareTo(time1) <= 0);

        }catch (Throwable e){
            ExceptionEx.exception("No such element "+ locators);
        }
        Log.info("waitForObject :" + locators);

    }

    /*public static void main(String[] args) {
        String expected = "[\"o3LzQwkycORfToRiQmDHQ4JB2wD4MUoe\",\"r9HQLsmmG2AYGYZpqY2gfSjprZASh1Fa\"]";
        String[] itemsList = LogicHandle.convertToArrayListString(expected, "\"").toArray(new String[0]);
       waitForObject(String.valueOf(15), ".mp3",itemsList);
    }*/
    public static void waitForObjectNoReturn(String locator,String second){
        try {
            LocalDateTime time = LocalDateTime.now();
            LocalDateTime time1 = time.plusSeconds(Integer.valueOf(second));
            Response response = null;
            do {
                response = RequestEx.request(Constanst.SCENE_URL, "//" + locator);
                JsonPath json = response.jsonPath();
                List name = (List)json.get("name");
                if (json != null && !name.isEmpty()) {
                    if(Convert.convert(response,"activeInHierarchy")=="true")
                        break;
                }
                Thread.sleep(500);
                time = LocalDateTime.now();
            } while (time.compareTo(time1) <= 0);
        }catch (Throwable e){
        }
        Log.info("waitForObjectNoReturn :" + locator);
    }
    public static void waitForObjectNotPresent(String locator){
        try {
            LocalDateTime time = LocalDateTime.now();
            LocalDateTime time1 = time.plusSeconds(10);
            Response response = null;
            do {
                response = RequestEx.request(Constanst.SCENE_URL, "//" + locator);
                JsonPath json = response.jsonPath();
                List name = (List)json.get("name");
                if(!locator.contains(Convert.convert(response, "name"))
                        || Convert.convert(response,"activeInHierarchy")=="false"
                        ||name.size()==0){
                    break;
                }
                Thread.sleep(500);
                time = LocalDateTime.now();
            } while (time.compareTo(time1) <= 0);
        }catch (Throwable e){
            ExceptionEx.exception(e);
            e.printStackTrace();
        }
    }
    public static void waitForObjectNotPresent(String locator,String second){
        try {
            LocalDateTime time = LocalDateTime.now();
            LocalDateTime time1 = time.plusSeconds(Integer.valueOf(second));
            Response response = null;
            System.out.println("waitForObjectNotPresent"+locator);
            do {
                response = RequestEx.request(Constanst.SCENE_URL, "//" + locator);
                JsonPath json = response.jsonPath();
                List name = (List)json.get("name");
                System.out.println(name);
                if(name.size()!=0){
                    List activeInHierarchy = (List)json.get("activeInHierarchy");
                    if(activeInHierarchy.contains("false") || activeInHierarchy.size()==0){
                        System.out.println("waitForObjectNotPresent"+name.size());
                        break;
                    }
                }else {
                    break;
                }
                Thread.sleep(500);
                time = LocalDateTime.now();
            } while (time.compareTo(time1) <= 0);
        }catch (Throwable e){
            ExceptionEx.exception(e);
            e.printStackTrace();
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
                response = RequestEx.request(Constanst.SCENE_URL, "//" + locator);
                if(response!=null) {
                    if (Convert.convert(response, "activeInHierarchy") == "true") {
                        JsonPath json = response.jsonPath();
                        if (json != null && json.toString() != "") {
                            value = Convert.convert(response, key);
                            if(value != null) {
                                if (value.toLowerCase().contains(content.toLowerCase()))
                                    break;
                            }
                        }
                    }
                    Thread.sleep(500);
                }
                time = LocalDateTime.now();
            } while (time.compareTo(time1) <= 0);
            Assert.assertTrue(value.contains(content));
        }catch (Throwable e){
            ExceptionEx.exception(e);
        }
    }
    public static void waitForObjectContainAddStr(String locator, String key,String strAdd,String second,String content){
        try {
            Log.info("waitForObjectContain :" + locator);
            LocalDateTime time = LocalDateTime.now();
            LocalDateTime time1 = time.plusSeconds(Integer.valueOf(second));
            Response response = null;
            String value= null;
            do {
                response = RequestEx.request(Constanst.SCENE_URL, "//" + locator);
                if(response!=null) {
                    if (Convert.convert(response, "activeInHierarchy") == "true") {
                        JsonPath json = response.jsonPath();
                        if (json != null && json.toString() != "") {
                            value = Convert.convert(response, key).trim()+strAdd;
                            if(value.length()>=content.length()) {
                                if (value.toLowerCase().contains(content.toLowerCase()))
                                    break;
                            }else {
                                if (content.toLowerCase().contains(value.toLowerCase()))
                                    break;
                            }
                        }
                    }
                    Thread.sleep(500);
                }
                time = LocalDateTime.now();
            } while (time.compareTo(time1) <= 0);
            Assert.assertTrue(value.contains(content));
        }catch (Throwable e){
            ExceptionEx.exception(e);
        }
    }
    public static void waitForObjectContain(String locator,String component, String property,String content){
        waitForObjectContain(locator,component,property,"30",content);
    }
    public static void waitForObjectContain(String locator,String component, String property,String second,String content){
        try {
            LocalDateTime time = LocalDateTime.now();
            LocalDateTime time1 = time.plusSeconds(Integer.valueOf(second));
            Response response = null;
            String value = null;
            do {
                response = RequestEx.request(Constanst.SCENE_URL, "//" + locator+"."+component);
                if(response!=null) {
                    if (!Convert.convertToList(response, property).isEmpty()) {
                        JsonPath json = response.jsonPath();
                        if (json != null && json.toString() != "") {
                            value = Convert.convert(response, property);
                            if (value != null) {
                                System.out.println(value);
                                System.out.println(content);
                                if (value.contains(content))
                                    break;
                            }
                            Thread.sleep(500);
                        }
                    }
                }
                time = LocalDateTime.now();
            } while (time.compareTo(time1) <= 0);
            Assert.assertTrue(value.contains(content));
        }catch (Throwable e){
            ExceptionEx.exception(e);
        }
        Log.info("waitForObjectContain :" + locator);
    }
    public static void waitForObjectContainNotAble(String locator,String component, String property,String strRemove,String content){
        try {
            LocalDateTime time = LocalDateTime.now();
            LocalDateTime time1 = time.plusSeconds(30);
            Response response = null;
            String value = null;
            do {
                response = RequestEx.request(Constanst.SCENE_URL, "//" + locator+"."+component);
                if(response!=null) {
                    if (Convert.convertToList(response, property).size()>0) {
                        JsonPath json = response.jsonPath();
                        if (json != null && json.toString() != "") {
                            value = LogicHandle.replaceStr(Convert.convert(response, property),strRemove);
                            if (value != null) {
                                if (!value.contains(content)) {
                                    System.out.println(value);
                                    System.out.println(content);
                                    break;
                                }
                            }
                            Thread.sleep(500);
                        }
                    }
                }
                time = LocalDateTime.now();
            } while (time.compareTo(time1) <= 0);
        }catch (Throwable e){
            ExceptionEx.exception(e);
        }
        Log.info("waitForObjectContain :" + locator);
    }
    public static void waitForObjectContainNotAble(String locator,String component, String property,String content){
        waitForObjectContainNotAble(locator,component,property,"",content);
    }
    public static void waitForObjectInScreen(String locator){
        try {
            LocalDateTime time = LocalDateTime.now();
            LocalDateTime time1 = time.plusSeconds(10);
            Response response = null;
            float with = Float.valueOf(SizeScreen.getSizeScreen("w"));
            float height = Float.valueOf(SizeScreen.getSizeScreen("h"));
            do {
                response = RequestEx.request(Constanst.SCENE_URL,"//"+locator+".RectTransform");
                JsonPath json = response.jsonPath();
                List name = (List)json.get("name");
                if (json != null && !name.isEmpty()) {
                    float x = Float.valueOf(PointScreen.getPointScreen(response,"x"));
                    float y = Float.valueOf(PointScreen.getPointScreen(response,"y"));
                    if(x<= with && y <= height)
                        break;
                }
                Thread.sleep(500);
                time = LocalDateTime.now();
            } while (time.compareTo(time1) <= 0);
        }catch (Throwable e){
            ExceptionEx.exception(e);
        }
        Log.info("waitForObjectInScreen :" + locator);
    }
    public static void waitForObjectInScreen(String locator,String second){
        try {
            LocalDateTime time = LocalDateTime.now();
            LocalDateTime time1 = time.plusSeconds(Integer.valueOf(second));
            Response response = null;
            float with = Float.valueOf(SizeScreen.getSizeScreen("w"));
            float height = Float.valueOf(SizeScreen.getSizeScreen("h"));
            do {
                response = RequestEx.request(Constanst.SCENE_URL,"//"+locator+".RectTransform");
                JsonPath json = response.jsonPath();
                List name = (List)json.get("name");
                if (json != null && !name.isEmpty()) {
                    float x = Float.valueOf(PointScreen.getPointScreen(response,"x"));
                    float y = Float.valueOf(PointScreen.getPointScreen(response,"y"));
                    if(x<= with && y <= height)
                        break;
                }
                Thread.sleep(500);
                time = LocalDateTime.now();
            } while (time.compareTo(time1) <= 0);
        }catch (Throwable e){
            ExceptionEx.exception(e);
        }
        Log.info("waitForObjectInScreen :" + locator);
    }
    public static void waitForObjectNotInScreen(String locator,String second,String size,String coordinate){
        try {
            LocalDateTime time = LocalDateTime.now();
            LocalDateTime time1 = time.plusSeconds(Integer.valueOf(second));
            Response response = null;
            float with = Float.valueOf(SizeScreen.getSizeScreen(size));
            do {
                response = RequestEx.request(Constanst.SCENE_URL,"//"+locator+".RectTransform");
                JsonPath json = response.jsonPath();
                List name = (List)json.get("name");
                if (json != null && !name.isEmpty()) {
                    float x = Float.valueOf(PointScreen.getPointScreen(response,coordinate));
                    if(x> with)
                        break;
                }
                Thread.sleep(500);
                time = LocalDateTime.now();
            } while (time.compareTo(time1) <= 0);
        }catch (Throwable e){
            ExceptionEx.exception(e);
        }
        Log.info("waitForObjectNotInScreen :" + locator);
    }

    public static void waitForNumber(String locator,String component,String prop,String second,String expected){
        try {
            LocalDateTime time = LocalDateTime.now();
            LocalDateTime time1 = time.plusSeconds(Integer.parseInt(second));
            int number = Integer.parseInt(expected);
            Response response = null;
            do {
                response = RequestEx.request(Constanst.SCENE_URL, "//" + locator+"."+component);
                List list =Convert.convertToList(response,prop);
                if(list.size()>0){
                    if(Integer.parseInt(convert(response,prop))>number){
                        break;
                    }
                }
                Thread.sleep(1000);
                time = LocalDateTime.now();
            } while (time.compareTo(time1) <= 0);
            //Assert.assertTrue(locator.contains(convert(response, "name")));
        }catch (Throwable e){
            ExceptionEx.exception("Not found number "+ locator);
        }
        Log.info("waitForNumber :" + locator);
    }
}
