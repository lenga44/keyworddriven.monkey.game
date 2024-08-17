package common.keywords.app.verify;

import common.keywords.app.Convert;
import common.keywords.app.RequestEx;
import common.utility.Constanst;
import io.restassured.response.Response;

public class SizeScreen {
    public static String getSizeScreen(String key){
        Response response = RequestEx.request(Constanst.SCENE_URL_UNIUM,"//UniumSDK.UniumComponent");
        if(key.equals(Constanst.WITH))
            return Convert.convert(response, "Width");
        else
            return Convert.convert(response, "Height");
    }
}
