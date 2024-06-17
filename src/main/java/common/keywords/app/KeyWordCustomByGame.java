package common.keywords.app;

import common.utility.*;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;

import java.io.IOException;
import java.time.LocalDateTime;

import static common.keywords.app.KeyWordsToAction.*;

public class KeyWordCustomByGame {
    //region Dien the
    public static void deFindAnswer(String locator,String component,String property,String expect,String strReplace,String strAdd,String locator1) throws IOException {
        Log.info("Dien the choose image or text");
        Response response = request(Constanst.SCENE_URL,"//"+locator+"."+component);
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

    //endregion
}
