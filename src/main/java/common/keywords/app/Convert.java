package common.keywords.app;

import common.utility.Log;
import io.restassured.response.Response;

import java.util.Arrays;
import java.util.List;

public class Convert {
    public static String convert(Response response, String key){
        try {
            String result = String.valueOf(response.getBody().jsonPath().getList(key).get(0));
            if(result.contains("\n")) {
                result = result.replace("\n", "");
            }
            return result;
        }catch (Throwable e){
            Log.error(response.prettyPrint());
            ExceptionEx.exception(e);
            return null;
        }
    }
    public static String convertNotNull(Response response,String key){
        String result = "";
        try {
            result = String.valueOf(response.getBody().jsonPath().getList(key).get(0));
            if(result.contains("\n")) {
                result = result.replace("\n", "");
            }
            return result;
        }catch (Throwable e){
        }
        return result;
    }
    public static String convert(Response response,String key,String oldStr,String newStr){
        return convert(response,key).replace(oldStr,newStr);
    }
    public static String convert(Response response,String key,int index,String splitStr){
        String result =  String.valueOf(response.getBody().jsonPath().getList(key).get(0));
        String[] a = result.split(splitStr);
        return Arrays.stream(a).toList().get(index);
    }
    public static List<String> convertToList(Response response, String key){
        return response.jsonPath().getList(key);
    }
}
