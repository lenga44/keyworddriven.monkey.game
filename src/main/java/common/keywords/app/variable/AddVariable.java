package common.keywords.app.variable;

import common.utility.Constanst;
import common.utility.JsonHandle;
import common.utility.Log;
import common.utility.LogicHandle;

import java.io.IOException;

public class AddVariable {
    public static void addVariableFile(String key,Object add) throws IOException {
        try {
            Log.info("addVariableFile");
            String value = JsonHandle.getValueInJsonObject(Constanst.VARIABLE_PATH_FILE, key).toString();
            int index = Integer.valueOf(LogicHandle.removeString(value,"\"")) + Integer.parseInt(add.toString());
            JsonHandle.setValueInJsonObject(Constanst.VARIABLE_PATH_FILE, key, index);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
