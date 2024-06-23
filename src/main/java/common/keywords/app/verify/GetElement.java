package common.keywords.app.verify;

import common.keywords.app.Convert;
import common.keywords.app.ExceptionEx;
import common.keywords.app.RequestEx;
import common.utility.Constanst;
import common.utility.LogicHandle;
import io.restassured.response.Response;

import java.util.List;

public class GetElement {
    public static String getElements(String locator){
        Response response = RequestEx.request(Constanst.SCENE_URL_UNIUM,"//"+locator+"[activeInHierarchy=true]");
        return String.valueOf(response.getBody().jsonPath().getList("name").toArray().length);
    }
    public static String getElementDisplayInScene(String strAdd,String expect){
        String result = null;
        try {
            List<String> list = Scene.getListScene();
            List<String> expects = LogicHandle.convertStringToList(expect);
            for (String item : list) {
                result = item.trim() + strAdd;
                if (expects.contains(result)) {
                    break;
                }
            }
        }catch (Exception e){
            ExceptionEx.exception("getElementDisplayInScene " +e.getMessage());
        }
        return result;
    }
    public static Response getElement(String locator){
        return RequestEx.request(Constanst.SCENE_URL_UNIUM,"//"+locator+"[activeInHierarchy=true]");
    }
}
