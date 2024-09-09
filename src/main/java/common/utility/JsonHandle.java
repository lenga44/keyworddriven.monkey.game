package common.utility;

import com.google.gson.*;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class JsonHandle {

    public static String getValue(String json,String jsonPath){
        //$.Page[0].Id
        Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);
        Object result = JsonPath.read(document, jsonPath);
        Gson gson = new Gson();
        try {
            return gson.toJsonTree(result).getAsString();
        }catch (Exception e){
            return result.toString();
        }
    }
    public static String checkValueIsNull(String result){
        //$.Page[0].Id
        try {
            List<String> value = LogicHandle.convertStringsToList(result);
            for (String v: value){
                System.out.println(v);
            }
             if(value.size()>0 ){
                 return Constanst.YES;
             }
        }catch (Exception e){
        }
        return Constanst.NO;
    }
    @Deprecated
    public static String getObjectInJsonData(int index) throws IOException, ParseException {
        String objects = FileHelpers.getAllData(Constanst.DATA_FILE_PATH);
        JSONArray jsonArr = new JSONArray(objects);
        return jsonArr.getJSONObject(index).toString();
    }
    public static Object getValueInJsonObject(String path,String key) throws IOException{
        String json = Files.readString(Paths.get(path));
        json = LogicHandle.replaceStr(json,"\"");
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        return jsonObject.get(key);
    }
    public static void setValueInJsonObject(String path,String key,String property) throws IOException{
        String json = Files.readString(Paths.get(path));
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        jsonObject.addProperty(key,property);
        FileHelpers.writeFile(jsonObject.toString(),path);
    }
    public static void setValueInJsonObject(JsonObject jsonObject,String key,String property) {
        jsonObject.addProperty(key,property);
    }
    public static void setValueInJsonObject(JsonObject jsonObject,String key,int property) {
        jsonObject.addProperty(key,property);
    }
    public static void setValueInJsonObject(String path,String key,JSONArray property) throws IOException{
        Gson gson = new Gson();
        String json = Files.readString(Paths.get(path));
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        jsonObject.addProperty(key,gson.toJson(property));
        FileHelpers.writeFile(jsonObject.toString(),path);
    }
    public static void setValueInJsonObject(String path,String key,int property) throws IOException{
        String json = Files.readString(Paths.get(path));
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        jsonObject.addProperty(key,property);
        FileHelpers.writeFile(jsonObject.toString(),path);
    }

    public static JsonObject getJsonObject(String path) throws IOException{
        String json = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
        return  JsonParser.parseString(json.toString()).getAsJsonObject();
    }
    public static JsonObject getObject(String json) {
        return JsonParser.parseString(json).getAsJsonObject();
    }
    @Deprecated
    public static JsonArray getJsonArray(String json) throws IOException{
        JsonParser parser = new JsonParser();
        JsonElement tradeElement = parser.parse(json);
        return  tradeElement.getAsJsonArray();
    }
    @Deprecated
    public static List<String> getAllKeyInJsonObject() throws IOException {
        String json = FileHelpers.getAllData(Constanst.DATA_FILE_PATH);
        JsonParser parser = new JsonParser();
        JsonObject jObj = (JsonObject) parser.parse(json);

        List<String> keys = jObj.entrySet()
                .stream()
                .map(i -> i.getKey())
                .collect(Collectors.toCollection(ArrayList::new));
        return keys;
    }
    public static List<String> getAllKeyInJsonString(String json) {
        JsonParser parser = new JsonParser();
        JsonObject jObj = (JsonObject) parser.parse(json);

        List<String> keys = jObj.entrySet()
                .stream()
                .map(i -> i.getKey())
                .collect(Collectors.toCollection(ArrayList::new));
        return keys;
    }
    public static List<String> getAllKeyInJsonObject(String path) throws IOException {
        String json = FileHelpers.readFile(path);
        JsonParser parser = new JsonParser();
        JsonObject jObj = (JsonObject) parser.parse(json);

        List<String> keys = jObj.entrySet()
                .stream()
                .map(i -> i.getKey())
                .collect(Collectors.toCollection(ArrayList::new));
        return keys;
    }
    public static JSONArray getJsonArray(String json,String jsonPath){
        String array = getValue(json,jsonPath);
        if(array.contains("[")) {
            return new JSONArray(array);
        }else {
            JSONArray array1 = new JSONArray();
            array1.put(array);
            return array1;
        }
    }
    public static JsonArray converStringToJsonArray(String json){
        JsonElement jsonElement = JsonParser.parseString(json);
        return jsonElement.getAsJsonArray();
    }
}
