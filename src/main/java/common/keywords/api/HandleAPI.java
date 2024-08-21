package common.keywords.api;

import common.keywords.app.KeyWordCustomForAISpeak;
import common.keywords.app.RequestEx;
import common.utility.*;
import execute.TestScrip;
import io.restassured.response.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static common.keywords.api.ResquestRestApi.body;
import static execute.TestScrip.json;

public class HandleAPI {
    public static String getProperty(String url, String method, String index, String key) throws IOException {
        String json = Body.getJsonBody(index);
        LogicHandle.replaceStr(json,"\"");
        String domain = ResquestRestApi.getRequestInFile(url);
        String sub = ResquestRestApi.getRequestInFile(method);
        try {
            Response response = RequestEx.POST_MULTIPART(domain, sub, json);
            return response.jsonPath().get(key);
        }catch (Exception e){
            Log.info(json);
            Log.info(domain);
            Log.info(sub);
            Log.error("|getProperty| "+e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    public static String getProperty(String key) throws IOException {
            return JsonHandle.getValue(body,"$."+key);
    }
    public static Map<String, String> getMap(String json) throws IOException {
        Map<String, String> map = new HashMap<>();
        List<String> list = JsonHandle.getAllKeyInJsonString(json);
        for (String item:list){
            map.put(item,JsonHandle.getValue(json,"$."+item));
        }
        return map;
    }
    public static void deFindModeRunTestCase(String sheetName,String from, String to,String value){
        try{
            ExcelUtils.setExcelFile(TestScrip.reportPath);
            ExecutorService executor = Executors.newFixedThreadPool(Integer.valueOf(to) - Integer.valueOf(from));
            String result = JsonHandle.checkValueIsNull(value);
            for (int i = Integer.valueOf(from); i <= Integer.valueOf(to); i++) {
                Runnable runnable = new InsertMultiExcel(result, i, Constanst.RUN_MODE_TEST_CASE, sheetName, TestScrip.reportPath);
                executor.execute(runnable);
            }
            executor.shutdown();
            ExcelUtils.saveFile(TestScrip.reportPath);
            ExcelUtils.closeFile(TestScrip.reportPath);
        }catch (Exception e){
            Log.error("deFindModeRunTestCase: "+ e.getMessage());
        }
    }
}
