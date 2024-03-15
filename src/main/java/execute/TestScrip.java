package execute;

import common.keywords.KeyWordsToAction;
import common.keywords.KeyWordsToActionPocoSDK;
import common.keywords.KeyWordsToActionToVerify;
import common.utility.*;
import report.GenerateReport;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static common.keywords.KeyWordsToAction.exception;

public class TestScrip {
    public TestScrip(KeyWordsToActionPocoSDK keyWord, Method method[]){
        this.keyWord = keyWord;
        this.method = method;
    }
    //region SCOPE
    public static void execute_suites(String scopePath,int iTestSuite, int iTotalSuite) throws IOException {
        Log.info("execute_suites");
        ExcelUtils.setExcelFile(scopePath);
        for (;iTestSuite<=iTotalSuite;iTestSuite++){

            ExcelUtils.setCellData("",iTestSuite,Constanst.STATUS_SUITE,Constanst.SCOPE_SHEET,scopePath);
            String sRunMode = ExcelUtils.getStringValueInCell(iTestSuite,Constanst.RUN_MODE_SCOPE,Constanst.SCOPE_SHEET);
            Log.info("Mode in scope: "+sRunMode);

            if(sRunMode.equals(Constanst.YES)) {

                tcName = ExcelUtils.getStringValueInCell(iTestSuite, Constanst.TEST_SUITE_FILE_NAME, Constanst.SCOPE_SHEET);
                if(tcName.equals(Constanst.TEST_CASE_GAME_NAME_IN_FLOW))
                {
                    KeyWordsToAction.sleep("1");
                    String game = KeyWordsToActionToVerify.getCurrentScene();
                    tcName = "Report_"+game;
                    ExcelUtils.setCellData(tcName,iTestSuite,Constanst.TEST_SUITE_FILE_NAME,Constanst.SCOPE_SHEET,scopePath);
                }

                Log.info("TCS name: "+tcName);
                tcPath = FileHelpers.getRootFolder() + FileHelpers.getValueConfig(Constanst.TESTCASE_FILE_PATH)+ tcName + ".xlsx";
                reportPath = GenerateReport.genReport(levelFolder,tcName+currentDataName);
                openScopeFile(reportPath);

                execute_testcases();
                ExcelUtils.setExcelFile(scopePath);
                ExcelUtils.setCellData(tcResult, iTestSuite, Constanst.STATUS_SUITE, Constanst.SCOPE_SHEET, scopePath);
            }
        }
    }
    public static String openScopeFile(String fileName) throws IOException{
        Log.info("fileName "+fileName);
        String path = /*FileHelperUtils.getRootFolder() +*/ FileHelpers.getValueConfig(fileName);
        Log.info("==PATH:== "+path);
        ExcelUtils.setExcelFile(path);
        return path;
    }
    public static String openScopeFile(String filePath,String fileName) throws IOException{
        Log.info("filePath "+filePath);
        Log.info("fileName "+fileName);
        String path = FileHelpers.getRootFolder() + FileHelpers.getValueConfig(filePath)+fileName;
        Log.info("==PATH:== "+path);
        ExcelUtils.setExcelFile(path);
        return path;
    }
    //endregion

    //region TESTCASE
    public static int onceTimeScrip(int row) throws IOException {
        String testSuiteName = ExcelUtils.getStringValueInCell(row,Constanst.TEST_SUITE_FILE_NAME,Constanst.SCOPE_SHEET);
        if(testSuiteName.contains(Constanst.ONCE_TIME_KEY)){
            return row;
        }
        return 0;
    }
    private static void execute_testcases() throws IOException {
        int iTotalTestCase = ExcelUtils.getRowCount(Constanst.TESTCASE_SHEET);
        Log.info("Total TC: " + iTotalTestCase);
        for(int iTestCase =1; iTestCase<iTotalTestCase;iTestCase++) {

            String sTestCaseID = ExcelUtils.getStringValueInCell(iTestCase, Constanst.TESTCASE_ID, Constanst.TESTCASE_SHEET);
            Log.info("TCID: " + sTestCaseID);

            String runMode = ExcelUtils.getStringValueInCell(iTestCase,Constanst.RUN_MODE_TEST_CASE,Constanst.TESTCASE_SHEET);
            Log.info(sTestCaseID+ " - Run mode in TC: " + runMode);

            if(runMode.equals(Constanst.YES)) {
                rangeStepByTestCase(sTestCaseID);
                Log.info("result: "+result);
                if (result != Constanst.SKIP) {
                    tcResult = Constanst.PASS;
                    execute_steps();
                    onResultTestcase(tcResult, "", iTestCase);

                } else
                    onResultTestcase(Constanst.SKIP, error, iTestCase);
            }
        }
    }
    private static void executeTCSByGroup(int iTestCase){
        groupIDS = new HashSet<>();
    }
    private static void onResultTestcase(String status, String message, int rowNumber) throws IOException {
        ExcelUtils.setCellData(status, rowNumber, Constanst.TESTCASE_STATUS, Constanst.TESTCASE_SHEET, reportPath);
        ExcelUtils.setCellData(message,  rowNumber, Constanst.TESTCASE_ERROR, Constanst.TESTCASE_SHEET, reportPath);
        ExcelUtils.closeFile(reportPath);
    }
    private static Object[] getParam(String params, String data){
        Log.info("Params: "+params);
        Log.info("Data: "+data);
        ArrayList<Object> objs = new ArrayList<>();
        if (!params.equals("")&& !params.equals(null)) {
            if (params.contains(",")) {
                for (String value : params.split(",")) {
                    objs.add(value);
                }
            } else {
                objs.add(params);
            }
            if(!data.equals("")){
                objs.add(data);
            }
            return objs.toArray();
        } else {
            return null;
        }
    }
    //endregion

    //region TEST STEP
    private static void rangeStepByTestCase(String sTestCaseID){
        iTestStep = ExcelUtils.getRowContains(sTestCaseID,Constanst.TESTCASE_ID,Constanst.TEST_STEP_SHEET);
        lastTestStep = ExcelUtils.getTestStepCount(Constanst.TEST_STEP_SHEET,sTestCaseID,iTestStep);
    }
    private static void rangeByID(String id,int collum,String sheetName,int startID){
        iTestStep = ExcelUtils.getRowContains(id,collum,sheetName);
        lastTestStep = ExcelUtils.getTestStepCount(sheetName,id,startID);
    }

    public static String getDataSet(int row){
        String key = ExcelUtils.getStringValueInCell(row, Constanst.DATA_SET, Constanst.TEST_STEP_SHEET);
        String value = null;
        if(key.contains("$")&& !json.equals(null)) {
            value = JsonHandle.getValue(json, key);
            FileHelpers.setJsonVariable(key, value);
            map_key_data_set.put(row,key);
            ExcelUtils.setCellData(value,row, Constanst.DATA_SET, Constanst.TEST_STEP_SHEET,tcPath);
            return value;
        }else
            return key;
    }
    public static String getDataSet(String key){
        String value = null;
        if(key.contains("$")&& !json.equals(null)) {
            value = JsonHandle.getValue(json, key);
            //FileHelpers.setJsonVariable(key, value);
        }else
            value = key;
        return value;
    }
    public static String getDataSet(String key,int rowNumber, int columnNumber){
        String value = null;
        if(!json.equals(null)){
            value = JsonHandle.getValue(json,ExcelUtils.getStringValueInCell(rowNumber,columnNumber));
        }
        FileHelpers.setJsonVariable(key,value);
        return value;
    }
    public static void execute_steps() throws IOException {
        for (; iTestStep < lastTestStep; iTestStep++) {
            result = Constanst.PASS;
            error = "";
            String process = ExcelUtils.getStringValueInCell(iTestStep, Constanst.PROCEED, Constanst.TEST_STEP_SHEET);
            Log.info("Process TS: "+process);
            if(process.equals(Constanst.PROCESS_YES)) {

                String sActionKeyword = ExcelUtils.getStringValueInCell(iTestStep, Constanst.KEYWORD, Constanst.TEST_STEP_SHEET);

                params = ExcelUtils.getStringValueInCell(iTestStep, Constanst.PARAMS, Constanst.TEST_STEP_SHEET);
                String dataSet = getDataSet(iTestStep);

                description = ExcelUtils.getStringValueInCell(iTestStep, Constanst.DESCRIPTION, Constanst.TEST_STEP_SHEET);
                Log.info(description);

                if (result != Constanst.SKIP) {
                    if(sActionKeyword != "") {
                        execute_action(dataSet,sActionKeyword);
                    }
                    verifyStep(iTestStep);

                }
                if(result == "" && result==null){
                    result = Constanst.SKIP;
                }
                onResultStep(result, error, iTestStep);

                if (result == Constanst.FAIL)
                    tcResult = Constanst.FAIL;
            }
        }
    }
    private static void onResultStep(String status, String message, int rowNumber ){
        if(status == Constanst.FAIL) {
            byte[] bytes = KeyWordsToAction.takePhoto();
            ExcelUtils.addPictureInCell(rowNumber, bytes, tcPath);
        }else {
            ExcelUtils.setCellData("", rowNumber, Constanst.IMAGE, Constanst.TEST_STEP_SHEET, tcPath);
        }
        ExcelUtils.setCellData(status, rowNumber, Constanst.RESULT, Constanst.TEST_STEP_SHEET, tcPath);
        ExcelUtils.setCellData(message,  rowNumber, Constanst.ERROR, Constanst.TEST_STEP_SHEET, tcPath);
    }
    private static void execute_action(String data,String sActionKeyword){
        String testStep = ExcelUtils.getStringValueInCell(iTestStep, Constanst.TEST_STEP, Constanst.TEST_STEP_SHEET);
        result = Constanst.PASS;
        try {
            param = getParam(params,data);
            int paramCount = (param == null) ? 0: param.length;
            for (int i = 0; i < method.length; i++) {
                if (method[i].getName().equals(sActionKeyword) && method[i].getParameterCount() == paramCount) {
                    Log.info(testStep +":  "+description);
                    if (paramCount == 0) {
                        param = null;
                    }
                    String type = String.valueOf(method[i].getReturnType());
                    Log.info("type: "+type);
                    if (!type.equals("void")) {
                        String actual = (String) method[i].invoke(keyWord, param);
                        Log.info(description);
                        keyWord.check(actual, expected);
                    } else {
                        method[i].invoke(keyWord, param);
                    }
                    break;
                }
            }
        }catch (Throwable e) {
            exception(e);
        }
        //onResultStep(result,error,numberStep);
    }

    public static void onFail(String message) {
        //Log.info(message);
        result = Constanst.FAIL;
        error = message;
    }
    // region verify result after each step
    private static void verifyStep(int numberStep) throws IOException {
        String sActionKeyword = ExcelUtils.getStringValueInCell(numberStep, Constanst.VERIFY_STEP, Constanst.TEST_STEP_SHEET);
        String dataSetActual =ExcelUtils.getStringValueInCell(numberStep, Constanst.DATA_SET_ACTUAL, Constanst.TEST_STEP_SHEET);
        getActualWithKey(iTestStep,dataSetActual);
        params = ExcelUtils.getStringValueInCell(numberStep, Constanst.PARAM_VERIFY_STEP, Constanst.TEST_STEP_SHEET)+ dataSetActual ;

        if(!sActionKeyword.equals("")){
            if(result == Constanst.PASS) {
                expected = getExpectedWithKey(numberStep);
                description = "Check - " +description;
                execute_action("",sActionKeyword);
            }
        }
    }
    private static String getExpectedWithKey(int numberStep){
        String ex = ExcelUtils.getStringValueInCell(numberStep,Constanst.EXPECTED,Constanst.TEST_STEP_SHEET);
        if(isDataFlow ==true && ex.contains("$")) {
            String value = JsonHandle.getValue(json, ex);
            map_key_expected.put(numberStep, ex);
            ExcelUtils.setCellData(value,numberStep,Constanst.EXPECTED,Constanst.TEST_STEP_SHEET,tcPath);
            return value;
        }
        else
            return ex;
    }
    private static void getActualWithKey(int numberStep,String value){
        if(isDataFlow ==true && params.contains("$")) {
            map_key_actual.put(numberStep, value);
        }
    }

    // endregion verify result after each step

    //endregion

    //region KEY

    //region Testcase key
    public static String tcResult = Constanst.PASS;
    public static String tcPath;
    public static boolean isDataFlow;
    public static String json;
    public static String tcName;
    public static Set<String> groupIDS;
    public static int iFirstSuite;
    public static int iLastSuite;
    //endregion

    //region Test Step key
    public static int iTestStep;
    public static int lastTestStep;
    public static String result = Constanst.PASS;
    public static String error;
    public static String params;
    public static String description;
    public static Object[]  param;
    private static Method method[];
    protected static KeyWordsToAction keyWord;
    protected static String expected;
    protected static Map<Integer, String> map_key_expected;
    protected static Map<Integer, String> map_key_actual;
    protected static Map<Integer, String> map_key_data_set;
    //endregion

    //region report key
    protected static String reportPath;
    public static String levelFolder;
    public static String currentDataName;
    //endregion

    //endregion
}
