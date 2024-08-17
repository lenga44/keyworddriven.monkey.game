package execute;

import common.keywords.app.KeyWordsToComPair;
import common.utility.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

public class RunTestScriptData extends TestScrip{
    public RunTestScriptData(KeyWordsToComPair keyWord, Method[] method,Map<Class<?>,Method[]> classes){
        super(keyWord, method,classes);
    }
    @Deprecated
    public static void run(String scopePath, int iTestSuit, int iTotalSuite) throws Exception {
        try {
            isDataFlow = true;
            //calculator loop
            int begin = ExcelUtils.getNumberValueInCell(1, Constanst.BEGIN_INDEX_COLUM, Constanst.PLAN_SHEET);
            int end = ExcelUtils.getNumberValueInCell(1, Constanst.END_INDEX_COLUM, Constanst.PLAN_SHEET);
            Log.info("Run data from " + begin + " to " + end);
            String scopeData = Constanst.DATA_FOLDER_PATH + "Scope.xlsx";
            String copyFile = FileHelpers.getRootFolder() + FileHelpers.getValueConfig(Constanst.SCOPE_COPY_FILE_PATH);
            String scopeReport = FileHelpers.getRootFolder()+FileHelpers.getValueConfig(Constanst.SCOPE_REPORT_FILE_PATH)+"Scope";
            for (int index = begin; index <= end; index++) {
                //get node need check
                /*FileHelpers.copyFile(scopeData,scopePath);*/
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
                    //FileHelpers.copyFile(scopeData,scopePath);
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
    public static void runOnceTime(String scopePath, int iTestSuit) throws Exception {
        try {
            isDataFlow = true;
            //calculator loop
            Log.info("Run data from " + iTestSuit + " to " + iTestSuit);
            String copyFile = FileHelpers.getRootFolder() + FileHelpers.getValueConfig(Constanst.SCOPE_COPY_FILE_PATH);
            String scopeReport = FileHelpers.getRootFolder()+FileHelpers.getValueConfig(Constanst.SCOPE_REPORT_FILE_PATH)+"Scope";
                //get node need check
                json = JsonHandle.getObjectInJsonData(iTestSuit - 1);
                ExcelUtils.setExcelFile(scopePath);
                levelFolder = FileHelpers.getRootFolder() +Constanst.REPORT_FILE_PATH;
                FileHelpers.copyFile(scopePath,copyFile);
                try {
                    ExcelUtils.setCellData(iTestSuit, 1, Constanst.CURRENT_INDEX_COLUM, Constanst.PLAN_SHEET, scopePath);
                    String key = ExcelUtils.getStringValueInCell(1, Constanst.LESSON_PLAN_COLUM, Constanst.PLAN_SHEET);
                    reportName = LogicHandle.getTextAlphabet(Scope.genReportName(key));
                    //execute tc
                    execute_suite(scopePath, iTestSuit);
                    //Scope.ResetScopeFile(scopePath,copyFile,scopeReport,iTestSuit);
                    ExcelUtils.closeFile(reportPath);
                    ExcelUtils.closeFile(tcPath);
                }catch (Exception e){
                    Log.error("|run| "+e.getMessage());
                    e.printStackTrace();
                }
                reportName ="";
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
