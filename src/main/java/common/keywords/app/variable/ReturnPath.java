package common.keywords.app.variable;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import common.keywords.app.Convert;
import common.keywords.app.RequestEx;
import common.keywords.app.action.GetAbsolutePath;
import common.keywords.app.action.Wait;
import common.utility.*;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReturnPath {
    public static void returnPath(String locator, String component,String key,String expected) throws IOException {
        Wait.waitForObject(locator);
        int index = 0;
        Response response = RequestEx.request(Constanst.SCENE_URL_UNIUM,"//"+locator+"."+component);
        ResponseBody body = response.getBody();
        String json = body.asString();
        for (JsonElement element: JsonHandle.getJsonArray(json)) {
            if(JsonHandle.getValue(element.toString(),"$."+key).toLowerCase().equals(expected.toLowerCase()))
                break;
            index++;
        }
        Response response1 = RequestEx.request(Constanst.SCENE_URL_UNIUM,"//"+locator);
        String value = GetAbsolutePath.getAbsolutePath(response1,String.valueOf(index));
        JsonHandle.setValueInJsonObject(Constanst.VARIABLE_PATH_FILE,"path",value);
        ExcelUtils.closeFile(Constanst.VARIABLE_PATH_FILE);
    }
    public static String returnPathValue(String locator, String component,String key,String expected) throws IOException {
        Wait.waitForObject(locator);
        int index = 0;
        Response response = RequestEx.request(Constanst.SCENE_URL_UNIUM,"//"+locator+"."+component);
        ResponseBody body = response.getBody();
        String json = body.asString();
        for (JsonElement element: JsonHandle.getJsonArray(json)) {
            if(JsonHandle.getValue(element.toString(),"$."+key).toLowerCase().equals(expected.toLowerCase()))
                break;
            index++;
        }
        Response response1 = RequestEx.request(Constanst.SCENE_URL_UNIUM,"//"+locator);
        return GetAbsolutePath.getAbsolutePath(response1,String.valueOf(index));
    }

    public static String returnPath(String locator, String component,String key,String index,String expected) throws IOException {
        Wait.waitForObject(locator);
        Response response = RequestEx.request(Constanst.SCENE_URL_UNIUM,"//"+locator+"."+component);
        ResponseBody body = response.getBody();
        String json = body.asString();
        String path = null;
        int i =0;
        int j =0;
        List<String> paths = new ArrayList<>();
        for (JsonElement element: JsonHandle.getJsonArray(json)) {
            if(JsonHandle.getValue(element.toString(),"$."+key).toLowerCase().equals(expected.toLowerCase()))
            {
                if(Integer.valueOf(index)==i){
                    Response response1 = RequestEx.request(Constanst.SCENE_URL_UNIUM,"//"+locator);
                    path = Convert.convertToList(response1,"path").get(j);
                    paths.add(path);
                    break;
                }else {
                    i++;
                }
            }
            j++;
        }
        return path;
    }
    public static void returnPathReplaceVariable(String replaceStr,String expect) throws IOException {
        String value = expect.replace(replaceStr,"");
        JsonHandle.setValueInJsonObject(Constanst.VARIABLE_PATH_FILE,Constanst.PATH_GAME_OBJECT,value);
        ExcelUtils.closeFile(Constanst.VARIABLE_PATH_FILE);
    }
    public static void returnPathFullName(String locator,String key) throws IOException {
        Wait.waitForObject(locator);
        Response response = RequestEx.request(Constanst.SCENE_URL_UNIUM,"//"+locator);
        String name = Convert.convert(response,key);
        JsonHandle.setValueInJsonObject(Constanst.VARIABLE_PATH_FILE,"path",name);
        ExcelUtils.closeFile(Constanst.VARIABLE_PATH_FILE);
    }
    public static void returnPathFullName(String locator,String key,String strReplace) throws IOException {
        Wait.waitForObject(locator);
        Response response = RequestEx.request(Constanst.SCENE_URL_UNIUM,"//"+locator);
        String name = Convert.convert(response,key);
        name = LogicHandle.replaceStr(name,strReplace);
        JsonHandle.setValueInJsonObject(Constanst.VARIABLE_PATH_FILE,"path",name);
        ExcelUtils.closeFile(Constanst.VARIABLE_PATH_FILE);
    }
    public static void returnPathFullName(String locator) throws IOException {
        Wait.waitForObject(locator);
        Response response = RequestEx.request(Constanst.SCENE_URL_UNIUM,"//"+locator);
        String name = Convert.convert(response,"name");
        JsonHandle.setValueInJsonObject(Constanst.VARIABLE_PATH_FILE,"path",name);
        ExcelUtils.closeFile(Constanst.VARIABLE_PATH_FILE);
    }

    public static void returnPathFullPath(String locator) throws IOException {
        Wait.waitForObject(locator);
        Response response = RequestEx.request(Constanst.SCENE_URL_UNIUM,"//"+locator);
        String name = Convert.convert(response,"path");
        JsonHandle.setValueInJsonObject(Constanst.VARIABLE_PATH_FILE,"path",name);
        ExcelUtils.closeFile(Constanst.VARIABLE_PATH_FILE);
    }

    public static void returnPath(String locator,String index) throws IOException {
        Wait.waitForObject(locator);
        Response response = RequestEx.request(Constanst.SCENE_URL_UNIUM,"//"+locator);
        String name = LogicHandle.replaceStr(Convert.convertToList(response,"path").get(Integer.valueOf(index)),".","<_>");
        JsonHandle.setValueInJsonObject(Constanst.VARIABLE_PATH_FILE,"path",name);
        ExcelUtils.closeFile(Constanst.VARIABLE_PATH_FILE);
    }
    public static void returnPathContain(String locator, String component,String key,String expected){
        try {
            String path = getPath(locator,component,key,expected);
/*            FileHelpers.writeFile("",Constanst.VARIABLE_PATH_FILE);
            ExcelUtils.closeFile(Constanst.VARIABLE_PATH_FILE);*/
            JsonHandle.setValueInJsonObject(Constanst.VARIABLE_PATH_FILE,"path",path);
            ExcelUtils.closeFile(Constanst.VARIABLE_PATH_FILE);
        }catch (Exception e){
            Log.error(e.getMessage());
            e.printStackTrace();
        }
    }
    public static void returnPath(String locator,String groupID,String plusStr) throws IOException {
        Wait.waitForObject(locator);
        String index = FileHelpers.getValueConfig(Constanst.VARIABLE_PATH_FILE,groupID);
        JsonHandle.setValueInJsonObject(Constanst.VARIABLE_PATH_FILE,Constanst.PATH_GAME_OBJECT,locator+index+plusStr);
        ExcelUtils.closeFile(Constanst.VARIABLE_PATH_FILE);
    }
    public static String getPath(String locator, String component,String key,String expected)  {
        String path = "";
        try {
            Response response = RequestEx.request(Constanst.SCENE_URL_UNIUM, "//" + locator);
            ResponseBody body = response.getBody();
            JsonArray array = JsonHandle.getJsonArray(body.asString());
            for (int i = 0; i < array.size(); i++) {
                String value = JsonHandle.getValue(array.get(i).toString(), "$.components");
                String name = JsonHandle.getValue(array.get(i).toString(), "$.path");
                if (value.contains(component)) {
                    Response response1 = RequestEx.request(Constanst.SCENE_URL_UNIUM, "//" + name + "." + component);
                    ResponseBody body1 = response1.getBody();
                    String json1 = body1.asString();
                    for (JsonElement element : JsonHandle.getJsonArray(json1)) {
                        path = "";
                        String s = JsonHandle.getValue(element.toString(), "$." + key);
                        if (!s.equals("")) {
                            if (s.toLowerCase().contains(expected.toLowerCase())) {
                                path = name;
                                break;
                            }
                        }
                    }
                }
                if (!path.equals("")) {
                    break;
                }
            }
        }catch (Exception e){
            Log.error(e.getMessage());
            e.printStackTrace();
        }
        return path;
    }
    public static void returnPathChild(String locator,String index) throws IOException {
        Log.info("returnPathChild");
        Response response = RequestEx.request(Constanst.SCENE_URL_UNIUM,"//"+locator);
        String child = Convert.convert(response,"children");
        List<String> children = LogicHandle.convertStringToList(child);
        response = RequestEx.request(Constanst.SCENE_URL_UNIUM,"//"+locator+"/"+children.get(Integer.parseInt(index)).trim());
        String name = Objects.requireNonNull(Convert.convert(response, "name")).replaceAll("^\\s+|\\s+$", "");
        JsonHandle.setValueInJsonObject(Constanst.VARIABLE_PATH_FILE,"path",name);
        ExcelUtils.closeFile(Constanst.VARIABLE_PATH_FILE);
    }
    public static void returnPathParent(String locator,String index) throws IOException {
        Response response = RequestEx.request(Constanst.SCENE_URL_UNIUM,"//"+locator);
        List<String> names = Convert.convertToList(response,"path");
        String path = names.get(Integer.valueOf(index));
        List<String> parent = List.of(path.split("/"));
        path = LogicHandle.replaceStr(path.replace("/"+parent.get(parent.size()-1),""),".","<_>");
        JsonHandle.setValueInJsonObject(Constanst.VARIABLE_PATH_FILE,"path",path);
        ExcelUtils.closeFile(Constanst.VARIABLE_PATH_FILE);
    }
                                                                                                                                                                        public static void getPathStartWith(String startWith,String locator, String component,String key,String index,String expected) throws IOException {
        String paths = returnPath(locator,component,key,index,expected);
        for (String path: paths.split("/")) {
            if(path.startsWith(startWith)){
                JsonHandle.setValueInJsonObject(Constanst.VARIABLE_PATH_FILE,Constanst.PATH_GAME_OBJECT,path);
                ExcelUtils.closeFile(Constanst.VARIABLE_PATH_FILE);
                break;
            }
        }
    }
    public static void getPathStartWith(String startWith,String locator, String component,String key,String expected) throws IOException {
        int ex = Integer.valueOf(LogicHandle.getNumber(expected).trim());
        Wait.waitForObject(locator);
        int index = 0;
        Response response = RequestEx.request(Constanst.SCENE_URL_UNIUM,"//"+locator+"."+component);
        ResponseBody body = response.getBody();
        String json = body.asString();
        for (JsonElement element: JsonHandle.getJsonArray(json)) {
            int ac = Integer.valueOf(JsonHandle.getValue(element.toString(),"$."+key));
            if(ac==ex){
                System.out.println("___________ "+index);
                break;
            }
            index++;
        }
        Response response1 = RequestEx.request(Constanst.SCENE_URL_UNIUM,"//"+locator+"[components="+component+"]");
        String paths = Convert.convertToList(response1,"path").get(index);
        System.out.println(paths);
        for (String path: paths.split("/")) {
            if(path.startsWith(startWith)){
                JsonHandle.setValueInJsonObject(Constanst.VARIABLE_PATH_FILE,Constanst.PATH_GAME_OBJECT,path);
                ExcelUtils.closeFile(Constanst.VARIABLE_PATH_FILE);
                break;
            }
        }
    }

    public static void getPathStartWith(String startWith,String index,String expected) throws IOException {
        Response response = RequestEx.request(Constanst.SCENE_URL_UNIUM,"//" +startWith+expected+"*[activeInHierarchy=true]");
        List<String> paths = Convert.convertToList(response,"path");
        JsonHandle.setValueInJsonObject(Constanst.VARIABLE_PATH_FILE,Constanst.PATH_GAME_OBJECT,GetAbsolutePath.getAbsoluteLocator(paths.get(Integer.parseInt(index))));
        ExcelUtils.closeFile(Constanst.VARIABLE_PATH_FILE);
    }
}
