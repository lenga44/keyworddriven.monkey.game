package common.keywords.app;

import com.google.gson.Gson;
import common.utility.*;
import execute.TestScrip;
import io.restassured.RestAssured;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.MultiPartSpecification;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static common.keywords.api.HandleAPI.getMap;
import static common.keywords.api.ResquestRestApi.body;
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
    public static void GET(String domain,String filePath){
        try {
            String urlString = domain+ filePath;
            URL url = new URL(urlString);
            Log.info("GET: "+urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            Log.info("URL: "+urlString);
            Log.info("Status code: "+connection.getResponseCode());
        }catch (Throwable e){
            TestScrip.onFail("| request | "+ e.getMessage());
        }
    }
    public static void requestFile(String domain,String filePath){
        try {
            String encodedFilePath = URLEncoder.encode(filePath, StandardCharsets.UTF_8);
            String urlString = domain+ encodedFilePath;
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            Log.info("URL: "+urlString);
            Log.info("Status code: "+connection.getResponseCode());
        }catch (Throwable e){
            TestScrip.onFail("| request | "+ e.getMessage());
        }
    }
    public static Response POST(String url, String method, String json){
        Response response = given()
                .baseUri(url)
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .post(method)
                .then()
                .extract().response();

        //Thực hiện phương thức post() để gửi dữ liệu đi
        return response;
    }
    public static Response POST_MULTIPART(String url, String method, String json){
        try {
            Log.info(json);
            Response response = postMethod(multiParts(url,json),method);
            response.then().statusCode(200);
            body = response.getBody().asString();
            Log.info(body);
            return response;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    private static RequestSpecification givenRestApi(String url){
         return given()
                .baseUri(url)
                .contentType("multipart/form-data");
    }
    private static Response postMethod(RequestSpecification requestSpecification, String method){
        return requestSpecification.when()
                .post(method)
                .then()
                .extract().response();
    }
    private static RequestSpecification multiPart(RequestSpecification requestSpecification,String s1, String s2){
        return requestSpecification.multiPart(s1,s2);
    }
    private static RequestSpecification multiParts(String url,String json){
        List<String> list = JsonHandle.getAllKeyInJsonString(json);
        RequestSpecification requestSpecification = givenRestApi(url);
        for (String item:list){
            requestSpecification = multiPart(requestSpecification,item,JsonHandle.getValue(json,"$."+item));
        }
        return requestSpecification;
    }

}
