package common.keywords.app;

import common.utility.Log;
import execute.TestScrip;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class RequestEx {
    public static Response request(String baseUri, String basePath){
        try {
            RequestSpecification request = given();
            request.baseUri(baseUri);
            request.basePath(basePath);
            Log.info("request: "+ baseUri+basePath);
            return request.get();
        }catch (Throwable e){
            TestScrip.onFail("| request | "+ e.getMessage());
            return null;
        }
    }
    private static Response request(String baseUri,String basePath,int number){
        RequestSpecification request = given();
        request.baseUri(baseUri);
        request.basePath(basePath);
        return request.get("/"+number);
    }
}
