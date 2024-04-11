package common.keywords;

import com.google.gson.JsonObject;
import common.utility.Constanst;
import common.utility.ExcelUtils;
import common.utility.FileHelpers;
import common.utility.JsonHandle;
import execute.RunTestScriptData;

import java.io.IOException;

public class KeyWordCustomForAISpeak {

    public static void returnChooseTopic(String locator,String fileName,String sheetName,String row){
        String result = KeyWordsToActionToVerify.elementDisplay(locator);
        String path = FileHelpers.getRootFolder() + FileHelpers.getValueConfig(Constanst.TESTCASE_FILE_PATH) +fileName +".xlsx";
        ExcelUtils.setExcelFile(path);
        int number = Integer.valueOf(row);
        if(result.equals(Constanst.TRUE)){
            ExcelUtils.setCellData(Constanst.YES,number,Constanst.RUN_MODE_TEST_CASE,sheetName,path);
        }else {
            ExcelUtils.setCellData(Constanst.NO,number,Constanst.RUN_MODE_TEST_CASE,sheetName,path);
        }
    }
    public static void deFindModeRunTestCase(String key,String fileName,String sheetName,String from, String to)  {
        JsonObject jsonObject = JsonHandle.getObject(RunTestScriptData.json);
        String path = FileHelpers.getRootFolder() + FileHelpers.getValueConfig(Constanst.TESTCASE_FILE_PATH) +fileName +".xlsx";
        ExcelUtils.setExcelFile(path);
        String result = Constanst.NO;
        for (String k:jsonObject.keySet()){
            if (k.equals(key)){
                result = Constanst.YES;
                break;
            }
        }
        for(int i = Integer.valueOf(from) ; i<= Integer.valueOf(to);i++){
            ExcelUtils.setCellData(result,i,Constanst.RUN_MODE_TEST_CASE,sheetName,path);
        }
    }
}
