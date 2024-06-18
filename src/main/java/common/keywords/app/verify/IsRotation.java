package common.keywords.app.verify;

import common.keywords.app.Convert;
import common.keywords.app.RequestEx;
import common.keywords.app.action.SleepEx;
import common.utility.Constanst;
import common.utility.Log;
import io.restassured.response.Response;

public class IsRotation {
    public static String isRotation(String locator,String coordinate){
        Response response = RequestEx.request(Constanst.SCENE_URL_UNIUM,"//"+locator+".RectTransform");
        String z1 = Convert.convert(response,"position."+coordinate,0,"\\.");
        SleepEx.sleep("0.5");
        String z2 = Convert.convert(response,"position."+coordinate,0,"\\.");
        Log.info("|isRotation|: " + z1+ " --- "+z2);
        return String.valueOf(z1.equals(z2));
    }
}
