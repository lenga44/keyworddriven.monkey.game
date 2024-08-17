package common.keywords.app.verify;

import common.keywords.app.Convert;
import common.keywords.app.RequestEx;
import common.utility.Constanst;
import io.restassured.response.Response;

public class GetPosition {
    public static String getPointScreen(String locator, String coordinate){
        try {
            Response response = RequestEx.request(Constanst.SCENE_URL_UNIUM, "//" + locator + ".RectTransform");
            return Convert.convert(response, "position." + coordinate, 0, "\\.");
        }catch (Exception e){
            return "0";
        }
    }
    public static String getPointScreen(Response response, String coordinate){
        return Convert.convert(response,"position."+coordinate,0,"\\.");
    }
}
