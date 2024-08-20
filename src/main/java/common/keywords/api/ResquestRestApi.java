package common.keywords.api;

import common.keywords.app.RequestEx;
import common.utility.Constanst;
import common.utility.ExcelUtils;
import common.utility.JsonHandle;
import common.utility.LogicHandle;
import io.restassured.response.Response;

import java.io.IOException;

import static common.utility.FileHelpers.getValueConfig;

public class ResquestRestApi {
    public static String body;
    public static String getRequestInFile(String key) {
        return getValueConfig(Constanst.REQUEST_PATH_FILE,key);
    }
    public static void setParamInVariable(String url,String method,String index,String key,String variable) throws IOException {
        String value = HandleAPI.getProperty(url,method,index,key);
        JsonHandle.setValueInJsonObject(Constanst.VARIABLE_PATH_FILE, variable, value);
        ExcelUtils.closeFile(Constanst.VARIABLE_PATH_FILE);
    }
    public static void call(String url,String method,String index) throws IOException {
        String json = Body.getJsonBody(index);
        LogicHandle.replaceStr(json,"\"");
        String domain = ResquestRestApi.getRequestInFile(url);
        String sub = ResquestRestApi.getRequestInFile(method);
        Response response = RequestEx.POST_MULTIPART(domain, sub, json);
    }
}
