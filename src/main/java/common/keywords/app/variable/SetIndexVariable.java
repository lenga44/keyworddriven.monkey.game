package common.keywords.app.variable;

import com.google.gson.JsonElement;
import common.keywords.app.RequestEx;
import common.keywords.app.action.Wait;
import common.utility.*;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

import java.io.IOException;

public class SetIndexVariable {
    public static void setIndexVariableFile(String locator, String component,String key,String expected) throws IOException {
        Wait.waitForObject(locator);
        int index = 0;
        Response response = RequestEx.request(Constanst.SCENE_URL_UNIUM,"//"+locator+"."+component);
        ResponseBody body = response.getBody();
        String json = body.asString();
        for (JsonElement element: JsonHandle.getJsonArray(json)) {
            if(JsonHandle.getValue(element.toString(),"$."+key).toLowerCase().contains(expected.toLowerCase()))
                break;
            index++;
        }
        JsonHandle.setValueInJsonObject(Constanst.VARIABLE_PATH_FILE,Constanst.INDEX_GAME_OBJECT,index);
        ExcelUtils.closeFile(Constanst.VARIABLE_PATH_FILE);
    }
    public static void setIndexVariableFile(String locator, String component,String key,String removeStr,String expected) throws IOException {
        Wait.waitForObject(locator);
        int index = 0;
        Response response = RequestEx.request(Constanst.SCENE_URL_UNIUM,"//"+locator+"."+component);
        ResponseBody body = response.getBody();
        String json = body.asString();
        for (JsonElement element: JsonHandle.getJsonArray(json)) {
            if(expected.toLowerCase().contains(LogicHandle.removeString(JsonHandle.getValue(element.toString(),"$."+key).toLowerCase(),removeStr))){
                break;
            }
            index++;
        }
        JsonHandle.setValueInJsonObject(Constanst.VARIABLE_PATH_FILE,Constanst.INDEX_GAME_OBJECT,index);
        ExcelUtils.closeFile(Constanst.VARIABLE_PATH_FILE);
    }
    
    public static void setIndexVariableFile(String value) throws IOException {
        JsonHandle.setValueInJsonObject(Constanst.VARIABLE_PATH_FILE,Constanst.INDEX_GAME_OBJECT,Integer.parseInt(value));
        Log.info("setIndexVariableFile "+value);
    }
    public static void setIndexVariableFile(int value) throws IOException {
        JsonHandle.setValueInJsonObject(Constanst.VARIABLE_PATH_FILE,Constanst.INDEX_GAME_OBJECT,value);
        Log.info("setIndexVariableFile "+value);
    }
    public static void setIndexVariableFile() throws IOException {
        JsonHandle.setValueInJsonObject(Constanst.VARIABLE_PATH_FILE,Constanst.INDEX_GAME_OBJECT,0);
        Log.info("setIndexVariableFile ");
    }
}
