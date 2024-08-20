package common.keywords.api;

import common.keywords.app.RequestEx;
import common.utility.JsonHandle;
import common.utility.Log;
import common.utility.LogicHandle;
import io.restassured.response.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static common.keywords.api.ResquestRestApi.body;

public class HandleAPI {
    public static String getProperty(String url, String method, String index, String key) throws IOException {
        String json = Body.getJsonBody(index);
        LogicHandle.replaceStr(json,"\"");
        String domain = ResquestRestApi.getRequestInFile(url);
        String sub = ResquestRestApi.getRequestInFile(method);
        try {
            Response response = RequestEx.POST_MULTIPART(domain, sub, json);
            return response.jsonPath().get(key);
        }catch (Exception e){
            Log.info(json);
            Log.info(domain);
            Log.info(sub);
            Log.error("|getProperty| "+e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    public static String getProperty(String key) throws IOException {
            return JsonHandle.getValue(body,"$."+key);
    }
    public static Map<String, String> getMap(String json) throws IOException {
        Map<String, String> map = new HashMap<>();
        List<String> list = JsonHandle.getAllKeyInJsonString(json);
        for (String item:list){
            map.put(item,JsonHandle.getValue(json,"$."+item));
        }
        return map;
    }

}
