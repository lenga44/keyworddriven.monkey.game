package common.keywords;

import common.utility.Constanst;
import common.utility.ExcelUtils;

public class KeyWordCustomForAISpeak {

    public static void returnChooseTopic(String locator,String path,String sheetName,String row){
        String result = KeyWordsToActionToVerify.elementDisplay(locator);
        ExcelUtils.setExcelFile(path);
        int number = Integer.valueOf(row);
        if(result.equals(Constanst.TRUE)){
            ExcelUtils.setCellData(Constanst.YES,number,Constanst.RUN_MODE_TEST_CASE,sheetName,path);
        }else {
            ExcelUtils.setCellData(Constanst.NO,number,Constanst.RUN_MODE_TEST_CASE,sheetName,path);
        }
    }
}
