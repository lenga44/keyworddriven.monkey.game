package common.keywords;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import common.utility.Constanst;
import common.utility.ExcelUtils;
import common.utility.FileHelpers;
import common.utility.JsonHandle;
import execute.RunTestScriptData;
import execute.TestScrip;
import io.basc.framework.lang.Nullable;
import io.restassured.response.Response;
import org.apache.commons.lang3.ObjectUtils;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class KeyWordCustomForAISpeak {

    public static void returnChooseTopic(String locator,String sheetName,String from, String to) throws IOException {
        try {
            String result = "";
            try {
                result  = KeyWordsToActionToVerify.isElementDisplay(locator);
                System.out.println("ressult1  " +result);
            }catch (Throwable e){
                result = "false";
            }
            String path = TestScrip.reportPath;
            for (int i = Integer.valueOf(from); i <= Integer.valueOf(to); i++) {
                if (result.equals(Constanst.TRUE)) {
                    ExcelUtils.setCellData(Constanst.NO, i, Constanst.RUN_MODE_TEST_CASE, sheetName, TestScrip.reportPath);
                } else {
                    ExcelUtils.setCellData(Constanst.YES, i, Constanst.RUN_MODE_TEST_CASE, sheetName, TestScrip.reportPath);
                }
            }
            ExcelUtils.closeFile(path);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    public static void deFindModeRunTestCase(String key,String sheetName,String from, String to)  {
        String result = Constanst.NO;
        try {
            JsonObject jsonObject = JsonHandle.getObject(RunTestScriptData.json);
            Set<Map.Entry<String, JsonElement>> entries = jsonObject.entrySet();
            for (Map.Entry<String, JsonElement> entry: entries) {
                if(entry.getKey().equals(key)){
                    result = Constanst.YES;
                    break;
                }
            }
        }catch (Exception e){
            result = Constanst.NO;
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        for (int i = Integer.valueOf(from); i <= Integer.valueOf(to); i++) {
            ExcelUtils.setCellData(result, i, Constanst.RUN_MODE_TEST_CASE, sheetName, TestScrip.reportPath);
        }
    }
}
