package common.keywords.app;

import common.utility.Log;
import execute.TestScrip;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

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
}
