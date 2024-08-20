package common.keywords.api;

import common.keywords.app.variable.AddIndexVariable;
import common.keywords.app.variable.AddVariable;
import common.keywords.app.variable.SetVariable;
import common.utility.JsonHandle;

import java.io.IOException;

public class VariableFile {
    public static void setVariableApi(String varKey,String jsonKey) throws IOException {
        String value = JsonHandle.getValue(ResquestRestApi.body,"$."+jsonKey);
        SetVariable.setVariableFile(varKey,value);
    }
    public static void setVariableFile(String key,String value) throws IOException {
        SetVariable.setVariableFile(key,value);
    }
}
