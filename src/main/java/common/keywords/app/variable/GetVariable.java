package common.keywords.app.variable;

import common.utility.Constanst;
import common.utility.FileHelpers;

public class GetVariable {
    public static String getValueOfVariable(String key){
        return FileHelpers.getValueConfig(Constanst.VARIABLE_PATH_FILE,key);
    }
}
