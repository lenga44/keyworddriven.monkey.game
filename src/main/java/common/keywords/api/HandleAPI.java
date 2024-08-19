package common.keywords.api;

import common.keywords.app.RequestEx;
import common.utility.Log;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

import java.io.IOException;

public class HandleAPI {
    public static String getProperty(String url,String method,String index,String key) throws IOException {
        String json = Body.getJsonBody(index);
        String domain = Request.getRequestInFile(url);
        String sub = Request.getRequestInFile(method);
        try {
            Response response = RequestEx.POST(domain, sub, json);
            System.out.println(response.prettyPrint());
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
}
