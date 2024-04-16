package common.keywords;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import common.utility.Constanst;
import common.utility.ExcelUtils;
import common.utility.JsonHandle;
import execute.RunTestScriptData;
import execute.TestScrip;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class KeyWordCustomForAISpeak {

    public static void returnChooseTopic(String sheetName, String from, String to, String part) throws IOException {
        try {
            String result = Constanst.FAIL;
            /*try {
                result  = KeyWordsToActionToVerify.isElementDisplay(locator);
            }catch (Throwable e){
                result = "false";
            }*/
            if(part.equals("1")){
                System.out.println("returnChooseTopic");
                result = Constanst.TRUE;
            }
            String path = TestScrip.reportPath;
            for (int i = Integer.valueOf(from); i <= Integer.valueOf(to); i++) {
                if (result.equals(Constanst.TRUE)) {
                    ExcelUtils.setCellData(Constanst.YES, i, Constanst.RUN_MODE_TEST_CASE, sheetName, TestScrip.reportPath);
                } else {
                    ExcelUtils.setCellData(Constanst.NO, i, Constanst.RUN_MODE_TEST_CASE, sheetName, TestScrip.reportPath);
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
