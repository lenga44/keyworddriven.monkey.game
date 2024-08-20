package common.keywords.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import common.utility.*;

import java.io.IOException;

public class Body {
    public static void setBodyFileByString(String key,String value) throws IOException {
        JsonHandle.setValueInJsonObject(Constanst.BODY_PATH_FILE, key, value);
        ExcelUtils.closeFile(Constanst.BODY_PATH_FILE);
    }
    public static void setBodyFileByNumber(String key,String value) throws IOException {
        JsonHandle.setValueInJsonObject(Constanst.BODY_PATH_FILE, key, Integer.parseInt(value));
        ExcelUtils.closeFile(Constanst.BODY_PATH_FILE);
    }
    public static String getJsonBody(String index) throws IOException {
        String array = FileHelpers.readFile(Constanst.BODY_PATH_FILE);
        return JsonHandle.converStringToJsonArray(array).get(Integer.parseInt(index)).toString();
    }
    public static void setBodyFile(String key,String value) throws IOException {
        String json = FileHelpers.readFile(Constanst.BODY_PATH_FILE);
        JsonArray array = new JsonArray();
        for (JsonElement element: JsonHandle.converStringToJsonArray(json)){
            JsonHandle.setValueInJsonObject(element.getAsJsonObject(),key,value);
            array.add(element);
        }
        FileHelpers.writeFile(array.toString(),Constanst.BODY_PATH_FILE);
        ExcelUtils.closeFile(Constanst.BODY_PATH_FILE);
    }
    public static void setBodyFileNumber(String key,String value) {
        String json = FileHelpers.readFile(Constanst.BODY_PATH_FILE);
        for (JsonElement element: JsonHandle.converStringToJsonArray(json)){
            JsonHandle.setValueInJsonObject(element.getAsJsonObject(),key,Integer.parseInt(value));
        }
    }
    public static void setBodyFile(String key,String index,String value) throws IOException {
        String json = FileHelpers.readFile(Constanst.BODY_PATH_FILE);
        JsonArray array = JsonHandle.converStringToJsonArray(json);
        int i = Integer.parseInt(index);
        JsonElement element = array.get(i);
        JsonHandle.setValueInJsonObject(element.getAsJsonObject(),key,value);
        array.set(i,element);
        FileHelpers.writeFile(array.toString(),Constanst.BODY_PATH_FILE);
        ExcelUtils.closeFile(Constanst.BODY_PATH_FILE);
    }
    /*public static void setBodyFile(String key,String value,String index) {
        String json = FileHelpers.readFile(Constanst.BODY_PATH_FILE);
        JsonElement element = JsonHandle.converStringToJsonArray(json).get(Integer.parseInt(index));
        JsonHandle.setValueInJsonObject(element.getAsJsonObject(),key,value);
    }*/
}
