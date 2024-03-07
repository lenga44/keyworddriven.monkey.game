package common.utility;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jayway.jsonpath.JsonPath;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.io.IOException;

public class JsonHandle {

    public static String getValue(Object json,String jsonPath){
        //$.Page[0].Id
        Object id = JsonPath.read(json, jsonPath);
        return id.toString();
    }
    public static Object getObjectInJsonData(int index) throws IOException, ParseException {
        String objects = FileHelpers.getAllData(Constanst.DATA_FILE_PATH);
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(objects);
        return jsonArray.get(index);
    }
}
