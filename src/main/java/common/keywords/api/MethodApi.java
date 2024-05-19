package common.keywords.api;

import common.utility.Log;
import execute.TestScrip;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class MethodApi {
    public static Response GET(String baseUri, String basePath){
        try {
            Log.info("GET: "+ baseUri+basePath);
            RequestSpecification request = given();
            request.baseUri(baseUri);
            request.basePath(basePath);
            return request.get();
        }catch (Throwable e){
            TestScrip.onFail("| GET | "+ e.getMessage());
            return null;
        }
    }
    public static Response POST(String baseUri,String basePath,String payload){
        try {
            Log.info("POST: ");
            RequestSpecification request = given();
            request.baseUri(baseUri);
            request.body(payload);
            return request.post(basePath);
        }catch (Throwable e){
            Log.error(baseUri);
            Log.error(basePath);
            Log.error(payload);
            TestScrip.onFail("| POST | "+ e.getMessage());
            return null;
        }
    }
}
