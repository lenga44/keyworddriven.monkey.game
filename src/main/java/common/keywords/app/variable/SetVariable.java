package common.keywords.app.variable;

import common.utility.Constanst;
import common.utility.JsonHandle;
import common.utility.Log;
import common.utility.LogicHandle;

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
