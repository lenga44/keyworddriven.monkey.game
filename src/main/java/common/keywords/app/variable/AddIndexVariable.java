package common.keywords.app.variable;

import common.utility.Constanst;
import common.utility.JsonHandle;
import common.utility.Log;

import java.io.IOException;

public class AddIndexVariable {
    public static void addIndexVariableFile(String add) throws IOException {
        Log.info("addIndexVariableFile");
        String  value = JsonHandle.getValueInJsonObject(Constanst.VARIABLE_PATH_FILE,"index").toString();
        int index = Integer.parseInt(value)+Integer.parseInt(add);
        JsonHandle.setValueInJsonObject(Constanst.VARIABLE_PATH_FILE,Constanst.INDEX_GAME_OBJECT,index);
    }
}
