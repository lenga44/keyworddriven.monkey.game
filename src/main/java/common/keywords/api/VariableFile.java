package common.keywords.api;

import common.keywords.app.variable.AddIndexVariable;
import common.keywords.app.variable.AddVariable;
import common.keywords.app.variable.SetVariable;
import common.utility.*;

import java.io.IOException;

public class VariableFile {
    public static void setVariableApi(String varKey,String jsonKey) throws IOException {
        Log.info(ResquestRestApi.body);
        String value = JsonHandle.getValue(ResquestRestApi.body,"$."+jsonKey);
        Log.info("value: "+value);
        SetVariable.setVariableFile(varKey,value);
    }
    public static void setVariableFile(String key,String value) throws IOException {
        SetVariable.setVariableFile(key,value);
    }
    public static void addVariableFile(String key,String add) throws IOException {
        try {
            Log.info("addVariableFile");
            String json= FileHelpers.readFile(Constanst.VARIABLE_PATH_FILE);
            String value = JsonHandle.getValue(json, "$."+key);
            int index = Integer.valueOf(LogicHandle.removeString(value,"\"")) + Integer.parseInt(add.toString());
            JsonHandle.setValueInJsonObject(Constanst.VARIABLE_PATH_FILE, key, index);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
