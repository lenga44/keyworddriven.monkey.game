package common.utility;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jayway.jsonpath.JsonPath;



import java.io.IOException;

public class JsonHandle {

    public static String getValue(String json,String jsonPath){
        //$.Page[0].Id
        Object id = JsonPath.read(json, jsonPath);
        return id.toString();
    }
    public static String getObjectInJsonData(int index) throws IOException {
        String objects = FileHelpers.getAllData(Constanst.DATA_FILE_PATH);
        return objects;
    }
}
