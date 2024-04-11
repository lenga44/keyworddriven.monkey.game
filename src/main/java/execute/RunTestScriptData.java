package execute;

import common.keywords.KeyWordsToActionPocoSDK;
import common.keywords.KeyWordsToActionToVerify;
import common.utility.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RunTestScriptData extends TestScrip{
    public RunTestScriptData(KeyWordsToActionToVerify keyWord, Method method[]){
        super(keyWord, method);
    }

    @Deprecated
    public static void run(String scopePath, int iTestSuit, int iTotalSuite) throws Exception {

        //reset total Pass Fail
        setPassAndFail(0,0,scopePath);
        isDataFlow = true;

        //calculator loop
        int begin = ExcelUtils.getNumberValueInCell(1, Constanst.BEGIN_INDEX_COLUM,Constanst.PLAN_SHEET);
        int end = ExcelUtils.getNumberValueInCell(1,Constanst.END_INDEX_COLUM,Constanst.PLAN_SHEET);
        Log.info("Run data from "+begin+" to "+end);
        getLevelFolder(1);

        for(int index = begin;index<=end;index++){

            //init map key by cell
            map_key_expected = new HashMap<>();
            map_key_actual = new HashMap<>();
            map_key_data_set =new HashMap<>();

            //get node need check
            json = JsonHandle.getObjectInJsonData(index-1);
            ExcelUtils.setCellData(begin,1,Constanst.CURRENT_INDEX_COLUM,Constanst.PLAN_SHEET,scopePath);
            String key = ExcelUtils.getStringValueInCell(1,Constanst.DATA_PLAN_COLUM,Constanst.PLAN_SHEET);
            reportName = getDataSet(key);
            //execute tc
            execute_suites(scopePath,iTestSuit,iTotalSuite);
            break;
            //gen report
            /*GenerateReport.genReport(begin,levelFolder,reportName);
            //sum pass fail
            GenerateReport.countResultPlan(scopePath,iTotalSuite);
            ExcelUtils.closeFile(scopePath);

            ExcelUtils.setExcelFile(tcPath);

            Log.info("Reset key");
            resetKey(map_key_expected,Constanst.EXPECTED,Constanst.TEST_STEP_SHEET,tcPath);
            resetKey(map_key_actual,Constanst.DATA_SET_ACTUAL,Constanst.TEST_STEP_SHEET,tcPath);
            resetKey(map_key_data_set,Constanst.DATA_SET,Constanst.TEST_STEP_SHEET,tcPath);

            Log.info("Reset status,image, error");

            ExcelUtils.closeFile(tcPath);*/
        }
    }
    private static void resetKey(Map<Integer,String> map,int collum,String sheetName,String path) throws IOException {
        if(!map.isEmpty()) {
            Set<Integer> set = map.keySet();
            for (Integer key : set) {
                ExcelUtils.setCellData(map.get(key), key, collum, sheetName, path);
            }
        }
    }
    private static void setPassAndFail(int pass, int fail, String path) throws IOException {
        ExcelUtils.setCellData(pass, 1, Constanst.PASS_PLAN_COLUM, Constanst.PLAN_SHEET, path);
        ExcelUtils.setCellData(fail, 1, Constanst.FAIL_PLAN_COLUM, Constanst.PLAN_SHEET, path);

    }


    //endregion
}
