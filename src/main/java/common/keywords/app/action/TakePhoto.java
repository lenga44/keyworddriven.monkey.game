package common.keywords.app.action;

import common.utility.Constanst;
import io.restassured.response.Response;

import static common.keywords.app.KeyWordsToAction.request;

public class TakePhoto {
    public static byte[] takePhoto(){
        Response response = request(Constanst.TAKE_PHOTO,"");
        return response.asByteArray();
    }
}
