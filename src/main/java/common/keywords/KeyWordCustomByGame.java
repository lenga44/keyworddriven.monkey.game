package common.keywords;

import common.utility.Constanst;
import common.utility.FileHelpers;
import common.utility.Log;
import io.restassured.response.Response;

public class KeyWordCustomByGame {
    //region Dien the
    public static void deFindAnswer(String locator,String component,String property,String expect,String strReplace,String strAdd,String locator1){
        Log.info("Dien the choose image or text");
        Response response = KeyWordsToAction.request(Constanst.SCENE_URL,"//"+locator+"."+component);
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
            FileHelpers.setJsonVariable("path",locator);
            FileHelpers.setJsonVariable("type_dien_the","image");
        }else{
            FileHelpers.setJsonVariable("path",locator1);
            FileHelpers.setJsonVariable("type_dien_the","text");
        }

    }

    //endregion
}
