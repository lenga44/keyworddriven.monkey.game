package common.keywords.api;

import common.utility.Constanst;
import common.utility.ExcelUtils;
import common.utility.JsonHandle;

import java.io.IOException;

import static common.utility.FileHelpers.getValueConfig;

public class Request {
    public static String body;
    public static String getRequestInFile(String key) {
        return getValueConfig(Constanst.REQUEST_PATH_FILE,key);
    }
    public static void setParamInVariable(String url,String method,String index,String key) throws IOException {
        String value = HandleAPI.getProperty(url,method,index,key);
        JsonHandle.setValueInJsonObject(Constanst.VARIABLE_PATH_FILE, key, value);
        ExcelUtils.closeFile(Constanst.VARIABLE_PATH_FILE);
    }
}
