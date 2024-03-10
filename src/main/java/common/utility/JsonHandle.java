package common.utility;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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

public class JsonHandle {

    public static String getValue(Object json,String jsonPath){
        //$.Page[0].Id
        Object id = JsonPath.read(json, jsonPath);
        return id.toString();
    }
    @Deprecated
    public static JSONObject getObjectInJsonData(int index) throws IOException, ParseException {
        String objects = FileHelpers.getAllData(Constanst.DATA_FILE_PATH);
        JSONArray jsonArr = new JSONArray(objects);
        for(int n = 0; n < jsonArr.length(); n++)
        {
            JSONObject object = jsonArr.getJSONObject(n);
            System.out.println(object);
        }
        return jsonArr.getJSONObject(index);
    }
    public static Object getValueInJsonObject(String path,String key) throws IOException{
        String json = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        return jsonObject.get(key);
    }

    public static JsonObject getJsonObject(String path) throws IOException{
        String json = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
        return  JsonParser.parseString(json.toString()).getAsJsonObject();
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
}
