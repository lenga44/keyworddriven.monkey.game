package execute;

import common.keywords.KeyWordsToAction;
import common.keywords.KeyWordsToActionCustom;
import common.keywords.KeyWordsToActionToVerify;
import common.utility.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static common.keywords.KeyWordsToAction.exception;

public class TestScrip {
    public TestScrip(KeyWordsToActionCustom keyWord,Method method[]){
        this.keyWord = keyWord;
        this.method = method;
    }
    //region SCOPE
    public static void execute(String scopePath, int iTotalSuite) throws IOException {
        int iFirstSuite = onceTimeScrip(1);
        int iLastSuite = onceTimeScrip(iTotalSuite-1);

        if(iFirstSuite>0 && iLastSuite==0){
            Log.info("Run once time set up");
            execute_suites(scopePath,1,2);
            execute_suites(scopePath,2,iTotalSuite);
        }
        if(iLastSuite>0 && iFirstSuite==0){
            Log.info("Run once time set up and tear down");
            execute_suites(scopePath,1,iTotalSuite-1);
            execute_suites(scopePath,iTotalSuite-1,iTotalSuite);
        }
        if (iFirstSuite>0 && iLastSuite>0){
            Log.info("Run once time set up and tear down");
            execute_suites(scopePath,1,2);
            execute_suites(scopePath,2,iTotalSuite-1);
            execute_suites(scopePath,iTotalSuite-1,iTotalSuite);
        }
        if(iFirstSuite == 0 && iLastSuite ==0){
            Log.info("Run only testcase");
            execute_suites(scopePath,1,iTotalSuite);
        }
    }
    public static void execute_suites(String scopePath,int iTestSuite, int iTotalSuite) throws IOException {
        for (;iTestSuite<iTotalSuite;iTestSuite++){

            ExcelUtils.setCellData("",iTestSuite,Constanst.STATUS_SUITE,Constanst.SCOPE_SHEET,scopePath);
            String sRunMode = ExcelUtils.getStringValueInCell(iTestSuite,Constanst.RUN_MODE_SCOPE,Constanst.SCOPE_SHEET);
            Log.info("Mode in scope: "+sRunMode);

            if(sRunMode.equals(Constanst.YES)) {

                String tcName = ExcelUtils.getStringValueInCell(iTestSuite, Constanst.TEST_SUITE_FILE_NAME, Constanst.SCOPE_SHEET);
                if(tcName.equals(Constanst.TEST_CASE_GAME_NAME_IN_FLOW))
                {
                    KeyWordsToAction.sleep("1");
                    String game = KeyWordsToActionToVerify.getCurrentScene();
                    tcName = "Report_"+game;
                    ExcelUtils.setCellData(tcName,iTestSuite,Constanst.TEST_SUITE_FILE_NAME,Constanst.SCOPE_SHEET,scopePath);
                }
                Log.info("TCS name: "+tcName);
                tcPath = openScopeFile(Constanst.TESTCASE_FILE_PATH, tcName + ".xlsx");

                execute_testcases();
                ExcelUtils.setExcelFile(scopePath);
                ExcelUtils.setCellData(tcResult, iTestSuite, Constanst.STATUS_SUITE, Constanst.SCOPE_SHEET, scopePath);
            }
        }
    }
    public static String openScopeFile(String fileName) throws IOException{
        Log.info("fileName "+fileName);
        String path = /*FileHelperUtils.getRootFolder() +*/ FileHelpers.getPathConfig(fileName);
        Log.info("==PATH:== "+path);
        ExcelUtils.setExcelFile(path);
        return path;
    }
    public static String openScopeFile(String filePath,String fileName) throws IOException{
        Log.info("filePath "+filePath);
        Log.info("fileName "+fileName);
        String path = FileHelpers.getRootFolder() + FileHelpers.getPathConfig(filePath)+fileName;
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
        for(int i =1; i<iTotalTestCase;i++) {

            ExcelUtils.setCellData("",i,Constanst.TESTCASE_STATUS,Constanst.TESTCASE_SHEET,tcPath);
            String sTestCaseID = ExcelUtils.getStringValueInCell(i, Constanst.TESTCASE_ID, Constanst.TESTCASE_SHEET);
            Log.info("TCID: " + sTestCaseID);

            String runMode = ExcelUtils.getStringValueInCell(i,Constanst.RUN_MODE_TEST_STEP,Constanst.TESTCASE_SHEET);
            Log.info("Run mode in TC: " + runMode);

            if(runMode.equals(Constanst.YES)) {
                rangeStepByTestCase(sTestCaseID);
                Log.info("result: "+result);
                if (result != Constanst.SKIP) {
                    tcResult = Constanst.PASS;
                    execute_steps();
                    onResultTestcase(tcResult, "", i);

                } else
                    onResultTestcase(Constanst.SKIP, error, i);
            }
        }
    }
    private static void onResultTestcase(String status, String message, int rowNumber) {
        ExcelUtils.setCellData(status, rowNumber, Constanst.TESTCASE_STATUS, Constanst.TESTCASE_SHEET, tcPath);
        ExcelUtils.setCellData(message,  rowNumber, Constanst.TESTCASE_ERROR, Constanst.TESTCASE_SHEET, tcPath);
    }
    private static Object[] getParam(String params, String data){
        Log.info("Params: "+params);
        Log.info("Data: "+data);
        ArrayList<Object> objs = new ArrayList<>();
        if (!params.equals("")) {
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

    private static String getDataSet(int row){
        String data = ExcelUtils.getStringValueInCell(row, Constanst.DATA_SET, Constanst.TEST_STEP_SHEET);
        if(data.contains("$")&& !json.equals(null)){
            data = JsonHandle.getValue(json,data);
        }
        return data;
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
        params = "";

        if(!sActionKeyword.equals("")){
            if(result == Constanst.PASS) {
                expected = ExcelUtils.getStringValueInCell(numberStep,Constanst.EXPECTED,Constanst.TEST_STEP_SHEET);
                description = "Check - " +description;
                execute_action("",sActionKeyword);
            }
        }
    }
    // endregion verify result after each step

    //endregion

    //region KEY

    //region Testcase key
    public static String tcResult;
    public static String tcPath;
    public static boolean isDataFlow;
    public static String json;
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
    //endregion

    //endregion
}
