package execute;

import common.utility.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

public class RunTestScriptData extends TestScrip{
    public RunTestScriptData( Method method[]){
        super(method);
    }

    @Deprecated
    public static void run(String scopePath, int iTestSuit, int iTotalSuite) throws Exception {
        try {
            isDataFlow = true;
            //calculator loop
            int begin = ExcelUtils.getNumberValueInCell(1, Constanst.BEGIN_INDEX_COLUM, Constanst.PLAN_SHEET);
            int end = ExcelUtils.getNumberValueInCell(1, Constanst.END_INDEX_COLUM, Constanst.PLAN_SHEET);
            Log.info("Run data from " + begin + " to " + end);
            String copyFile = FileHelpers.getRootFolder() + FileHelpers.getValueConfig(Constanst.SCOPE_COPY_FILE_PATH);
            String scopeReport = FileHelpers.getRootFolder()+FileHelpers.getValueConfig(Constanst.SCOPE_REPORT_FILE_PATH)+"Scope";
            for (int index = begin; index <= end; index++) {
                //get node need check
                json = JsonHandle.getObjectInJsonData(index - 1);
                ExcelUtils.setExcelFile(scopePath);
                getLevelFolder(1);
                FileHelpers.copyFile(scopePath,copyFile);
                try {
                    ExcelUtils.setCellData(index, 1, Constanst.CURRENT_INDEX_COLUM, Constanst.PLAN_SHEET, scopePath);
                    String key = ExcelUtils.getStringValueInCell(1, Constanst.LESSON_PLAN_COLUM, Constanst.PLAN_SHEET);
                    reportName = LogicHandle.getTextAlphabet(Scope.genReportName(key));
                    //execute tc
                    execute_suites(scopePath, iTestSuit);
                    Scope.ResetScopeFile(scopePath,copyFile,scopeReport,index);
                    ExcelUtils.closeFile(reportPath);
                    ExcelUtils.closeFile(tcPath);
                }catch (Exception e){
                    Log.error("|run| "+e.getMessage());
                    e.printStackTrace();
                }
                reportName ="";
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
