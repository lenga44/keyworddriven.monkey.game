package common.keywords.app.variable;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import common.keywords.app.Convert;
import common.keywords.app.RequestEx;
import common.keywords.app.action.Wait;
import common.utility.*;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

import java.io.IOException;

public class SetVariable {
    public static void setVariableFile(Object key,Object value)  {
        try {
            String expect = value.toString();
            if(expect.matches("\\{Digit}")) {
                JsonHandle.setValueInJsonObject(Constanst.VARIABLE_PATH_FILE, key.toString(), Integer.parseInt(value.toString()));
                Log.info("setIndexVariableFile _INT_ " + value);
            }else {
                JsonHandle.setValueInJsonObject(Constanst.VARIABLE_PATH_FILE, key.toString(), value.toString());
                Log.info("setIndexVariableFile _STRING_ " + value);
            }
        }catch (Exception e){
            Log.error(e.getMessage());
            e.printStackTrace();
        }
    }
    public static void setVariableFileWhichCondition(String key,String preLocator, String locator, String component,String property,String expected) throws IOException {
        try {
            String ex = LogicHandle.getNumber(expected).trim();
            System.out.println("====== " + ex);
            int index = 0;
            Response response = RequestEx.request(Constanst.SCENE_URL_UNIUM, "//" + preLocator + locator);
            ResponseBody body = response.getBody();
            String json = body.asString();
            JsonArray array = JsonHandle.getJsonArray(json);
            for (int i = 0; i < array.size(); i++) {
                Response response1 = RequestEx.request(Constanst.SCENE_URL_UNIUM, "//" + preLocator + "[" + i + "]" + locator + "." + component);
                String result = "";
                try{
                    result = Convert.convert(response1, property);
                }catch (Exception e){
                    result = "ERROR";
                }
                if (result.equals(ex)) {
                    index = i;
                    break;
                }
            }
            JsonHandle.setValueInJsonObject(Constanst.VARIABLE_PATH_FILE, key, index);
            ExcelUtils.closeFile(Constanst.VARIABLE_PATH_FILE);
        }catch (Exception e){
            Log.error(e.getMessage());
            e.printStackTrace();
        }
    }
    public static void setVariableFile(Object key,Object strSplit,Object value)  {
        try {
            String expect = value.toString();
            if(expect.matches("\\{Digit}")) {
                JsonHandle.setValueInJsonObject(Constanst.VARIABLE_PATH_FILE, key.toString(), Integer.parseInt(expect));
                Log.info("setIndexVariableFile _INT_ " + value);
            }else {
                JsonHandle.setValueInJsonObject(Constanst.VARIABLE_PATH_FILE, key.toString(), LogicHandle.removeString(expect,strSplit.toString()));
                Log.info("setIndexVariableFile _STRING_ " + value);
            }
        }catch (Exception e){
            Log.error(e.getMessage());
            e.printStackTrace();
        }
    }
    public static void setVariableTypeOfStringFile(String key,String value) throws IOException {
        try {
            JsonHandle.setValueInJsonObject(Constanst.VARIABLE_PATH_FILE, key, value);
            Log.info("setVariableTypeOfStringFile " + value);
        }catch (Exception e){
            Log.error(e.getMessage());
            e.printStackTrace();
        }
    }
    public static void setVariableTypeOfIntFile(String key,String value) throws IOException {
        try {
            JsonHandle.setValueInJsonObject(Constanst.VARIABLE_PATH_FILE, key, Integer.valueOf(value));
            Log.info("setVariableTypeOfStringFile " + value);
        }catch (Exception e){
            Log.error(e.getMessage());
            e.printStackTrace();
        }
    }
    public static void setVariableTypeOfStringFile(String key,Object value) throws IOException {
        try {
            JsonHandle.setValueInJsonObject(Constanst.VARIABLE_PATH_FILE, key, Integer.parseInt(value.toString()));
            Log.info("setVariableTypeOfObjectFile " + value);
        }catch (Exception e){
            Log.error(e.getMessage());
            e.printStackTrace();
        }
    }
    public static void setVariableTypeOfStringFile(Object key,Object value) throws IOException {
        try {
            JsonHandle.setValueInJsonObject(Constanst.VARIABLE_PATH_FILE, String.valueOf(key), Integer.parseInt(value.toString()));
            Log.info("setVariableTypeOfObjectFile " + value);
        }catch (Exception e){
            Log.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
