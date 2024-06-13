package common.keywords.app.verify;

import common.keywords.app.Convert;
import common.keywords.app.RequestEx;
import common.utility.Constanst;
import common.utility.Log;
import io.restassured.response.Response;

public class PointInScreen {
    public static String isPointInScreen(String locator){
        boolean result = false;
        Response response = RequestEx.request(Constanst.SCENE_URL,"//"+locator+".RectTransform");
        String x = Convert.convert(response,"position.x",0,"\\.");
        String y = Convert.convert(response,"position.y",0,"\\.");
        String with = SizeScreen.getSizeScreen("w");
        String height = SizeScreen.getSizeScreen("y");
        if(Float.valueOf(x)<Float.valueOf(with)){
            result = (Float.valueOf(y)<Float.valueOf(height))?true:false;
        }
        Log.info("Element In Screen = " +result);
        return String.valueOf(result);
    }
}
