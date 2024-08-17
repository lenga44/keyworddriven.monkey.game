package common.keywords.app.custom;

import common.keywords.app.KeyWordsToAction;
import common.utility.Constanst;
import common.utility.ExcelUtils;
import common.utility.JsonHandle;
import common.utility.Log;
import io.restassured.response.Response;

import java.io.IOException;

import static common.keywords.app.KeyWordsToAction.request;

public class DeFindAnswer {
    public static void deFindAnswer(String locator,String component,String property,String expect,String strReplace,String strAdd,String locator1) throws IOException {
        Log.info("Dien the choose image or text");
        Response response = request(Constanst.SCENE_URL_UNIUM,"//"+locator+"."+component);
        String value = KeyWordsToAction.convertNotNull(response,property);
        if(!strReplace.equals("")){
            if(value.contains(strReplace)){
                value = value.replace(strReplace,"");
            }
        }
        if(!strAdd.equals("")){
            value = value+strAdd;
        }
        if(expect.contains(value)&& !value.equals("")){
            JsonHandle.setValueInJsonObject(Constanst.VARIABLE_PATH_FILE,"path_type_dien_the",locator);
            JsonHandle.setValueInJsonObject(Constanst.VARIABLE_PATH_FILE,"type_dien_the","image");
        }else{
            JsonHandle.setValueInJsonObject(Constanst.VARIABLE_PATH_FILE,"path_type_dien_the",locator1);
            JsonHandle.setValueInJsonObject(Constanst.VARIABLE_PATH_FILE,"type_dien_the","text");
        }
        ExcelUtils.closeFile(Constanst.VARIABLE_PATH_FILE);
    }
}
