package execute;

import common.keywords.KeyWordsToActionToVerify;
import common.utility.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class RunTestScriptData extends TestScrip{
    public RunTestScriptData(KeyWordsToActionToVerify keyWord, Method method[]){
        super(keyWord, method);
    }

    @Deprecated
    public static void run(String scopePath, int iTestSuit, int iTotalSuite) throws Exception {
        try {
            isDataFlow = true;
            //calculator loop
            int begin = ExcelUtils.getNumberValueInCell(1, Constanst.BEGIN_INDEX_COLUM, Constanst.PLAN_SHEET);
            int end = ExcelUtils.getNumberValueInCell(1, Constanst.END_INDEX_COLUM, Constanst.PLAN_SHEET);
            Log.info("Run data from " + begin + " to " + end);

            for (int index = begin; index <= end; index++) {
                //get node need check
                json = JsonHandle.getObjectInJsonData(index - 1);
                getLevelFolder(1);
                ExcelUtils.setExcelFile(scopePath);
                ExcelUtils.copySheet(Constanst.SCOPE_SHEET,Constanst.SCOPE_COPY_SHEET);
                ExcelUtils.setCellData(index, 1, Constanst.CURRENT_INDEX_COLUM, Constanst.PLAN_SHEET, scopePath);
                String key = ExcelUtils.getStringValueInCell(1, Constanst.DATA_PLAN_COLUM, Constanst.PLAN_SHEET);
                if(key.contains(",")){
                    for (String k: Arrays.stream(key.split(",")).toList()){
                        reportName += getDataSet(k);
                    }
                }else {
                    reportName = getDataSet(key);
                }
                //execute tc
                execute_suites(scopePath, iTestSuit, iTotalSuite);
                EndTestScript.saveReportToFailListFile(reportPath,scopePath);
                ExcelUtils.deleteSheet(Constanst.SCOPE_COPY_SHEET,scopePath);
                ExcelUtils.closeFile(reportPath);
                ExcelUtils.closeFile(tcPath);
                reportName ="";
                ExcelUtils.copySheet(Constanst.SCOPE_COPY_SHEET,Constanst.SCOPE_SHEET);
            }
            ExcelUtils.closeFile(scopePath);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
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
