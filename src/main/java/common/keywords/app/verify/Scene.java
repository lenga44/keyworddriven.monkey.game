package common.keywords.app.verify;

import common.keywords.app.Convert;
import common.keywords.app.action.Wait;
import common.utility.Constanst;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.List;

import static io.restassured.RestAssured.given;

public class Scene {
    public static String getCurrentScene(String locator){
        Wait.waitForObjectNotPresent(locator);
        RequestSpecification request = given();
        request.baseUri(Constanst.STATUS_URL_UNIUM);
        Response response = request.get();
        return response.jsonPath().get("Scene");
    }
    public static String getCurrentScene(){
        RequestSpecification request = given();
        request.baseUri(Constanst.STATUS_URL_UNIUM);
        Response response = request.get();
        return Convert.convert(response,"Scene");
    }
    public static String getAllScene(){
        RequestSpecification request = given();
        request.baseUri(Constanst.ALL_SCENE_UNIUM);
        Response response = request.get();
        return Convert.convert(response,"name");
    }
    public static List<String> getListScene(){
        RequestSpecification request = given();
        request.baseUri(Constanst.ALL_SCENE_UNIUM);
        Response response = request.get();
        return Convert.convertToList(response,"name");
    }
}
