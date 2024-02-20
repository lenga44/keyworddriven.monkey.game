package execute;

import common.keywords.KeyWdsToActionCustom;
import common.keywords.KeyWordsToAction;
import common.keywords.KeyWordsToActionToVerify;
import common.utility.Constanst;
import common.utility.ExcelUtils;
import common.utility.FileHelperUtils;
import common.utility.Log;

import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class RunTestScript {

    public RunTestScript() {
        keyWord = new KeyWdsToActionCustom();
        method = keyWord.getClass().getMethods();
    }

    //region SCOPE
    private static void cleanContextInCell(){
        if(markTest == "1") {
            String markResult = ExcelUtils.getCellData(markRow, Constanst.TESTCASE_STATUS, Constanst.TESTCASE_SHEET);
            String status = (numberLesson > 1 && markResult == Constanst.PASS)? Constanst.YES:Constanst.NO;
            ExcelUtils.setCellData(status, markRow, Constanst.RUN_MODE_SCOPE, Constanst.SCOPE_SHEET, scopePath);
        }
    }
    protected static void execute() throws IOException {
        int iTotalFeature = ExcelUtils.getRowCount(Constanst.SCOPE_SHEET);
        for (int i = 0; i<iTotalFeature;i++){
            markTest = "";
            sRunMode = ExcelUtils.getCellData(i,Constanst.RUN_MODE_TEST_STEP,Constanst.SCOPE_SHEET);
            if(sRunMode.equals(Constanst.YES)) {
                tcName = ExcelUtils.getCellData(i, Constanst.TESTCASE_FILE_NAME, Constanst.SCOPE_SHEET);
                getTCPath();
                execute_testcases();
                cleanContextInCell();
                ExcelUtils.setExcelFile(scopePath);
                ExcelUtils.setCellData(tcResult, i, Constanst.STATUS_GAME, Constanst.SCOPE_SHEET, scopePath);
            }
        }
    }
    //endregion

    private static void getTCPath()throws IOException{
        String path = null;
        switch (tcName){
            case  "Report_OnceTimeSetUp":
                path = Constanst.ONCE_TIME_SETUP_FILE_PATH;
                break;
            case  "Report_SetUp":
                path = Constanst.SETUP_FILE_PATH;
                break;
            case  "Report_TearDown":
                path = Constanst.TEARDOWN_FILE_PATH;
                break;
            case  "Report_OnceTimeTearDown":
                path = Constanst.ONCE_TIME_TEARDOWN_SETUP_FILE_PATH;
                break;
            default:
                path = Constanst.TESTCASE_FILE_PATH + tcName + ".xlsx";
                break;
        }
        tcPath = openScopeFile(path);
    }
    protected static String openScopeFile(String fileName) throws IOException{
        Log.resetFileLog();
        String path = FileHelperUtils.getRootFolder() + FileHelperUtils.getPathConfig(fileName);
        Log.info("==PATH:== "+path);
        ExcelUtils.setExcelFile(path);
        return path;
    }

    //region TESTCASE
    private static void execute_testcases() throws IOException {
        int iTotalTestCase = ExcelUtils.getRowCount(Constanst.TESTCASE_SHEET);
        for(int i =0; i<iTotalTestCase;i++) {

            sTestCaseID = ExcelUtils.getCellData(i, Constanst.TESTCASE_ID, Constanst.TESTCASE_SHEET);
            runMode = ExcelUtils.getCellData(i,Constanst.RUN_MODE_TEST_STEP,Constanst.TESTCASE_SHEET);

            if(runMode.equals(Constanst.YES)) {
                Log.info("TC: " + sTestCaseID);
                if(markTest!="1") {
                    markTest = ExcelUtils.getCellData(i, Constanst.MARK_IS_PASS_COLUM, Constanst.TESTCASE_SHEET);
                    if(markTest == "1")
                        markRow = i;
                }

                rangeStepByTestCase();

                if (result != Constanst.SKIP) {

                    tcResult = Constanst.PASS;
                    execute_steps();
                    onResultTestcase(tcResult, "", i);

                } else
                    onResultTestcase(Constanst.SKIP, error, i);
            }
        }
    }

    private static Object[] getParam(String params, String data){
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

    private static void onResultTestcase(String status, String message, int rowNumber) {
        ExcelUtils.setCellData(status, rowNumber, Constanst.TESTCASE_STATUS, Constanst.TESTCASE_SHEET, tcPath);
        ExcelUtils.setCellData(message,  rowNumber, Constanst.TESTCASE_ERROR, Constanst.TESTCASE_SHEET, tcPath);
    }
    //endregion TESTCASE

    //region TEST STEP

    private static void rangeStepByTestCase(){
        iTestStep = ExcelUtils.getRowContains(sTestCaseID,Constanst.TESTCASE_ID,Constanst.TEST_STEP_SHEET);
        lastTestStep = ExcelUtils.getTestStepCount(Constanst.TEST_STEP_SHEET,sTestCaseID,iTestStep);
    }

    private static void execute_steps() throws IOException {
        for (; iTestStep < lastTestStep; iTestStep++) {
            result = Constanst.PASS;
            process = ExcelUtils.getCellData(iTestStep, Constanst.PROCEED, Constanst.TEST_STEP_SHEET);
            if(process.equals(Constanst.PROCESS_YES)) {

                sActionKeyword = ExcelUtils.getCellData(iTestStep, Constanst.KEYWORD, Constanst.TEST_STEP_SHEET);
                params = ExcelUtils.getCellData(iTestStep, Constanst.PARAMS, Constanst.TEST_STEP_SHEET);
                dataSet = ExcelUtils.getCellData(iTestStep, Constanst.DATA_SET, Constanst.TEST_STEP_SHEET);
                description = ExcelUtils.getCellData(iTestStep, Constanst.DESCRIPTION, Constanst.TEST_STEP_SHEET);
                testStep = ExcelUtils.getCellData(iTestStep, Constanst.TEST_STEP, Constanst.TEST_STEP_SHEET);

                if (result != Constanst.SKIP) {
                    if(sActionKeyword != "") {
                        execute_action(iTestStep, dataSet);
                    }
                    verifyStep(iTestStep);
                }
                if(result == "" && result==null){
                    result = Constanst.SKIP;
                }
                onResultStep(result, error, iTestStep);
                error = "";

                if (result == Constanst.FAIL)
                    tcResult = Constanst.FAIL;
            }
        }
    }

    private static void execute_action(int numberStep,String data){
        result = Constanst.PASS;
        try {
            param = getParam(params,data);
            paramCount = (param == null) ? 0: param.length;

            for (int i = 0; i < method.length; i++) {
                if (method[i].getName().equals(sActionKeyword) && method[i].getParameterCount() == paramCount) {
                    Log.info(testStep +":  "+description);
                    if (paramCount == 0) {
                        param = null;
                    }
                    String type = String.valueOf(method[i].getReturnType());
                    if (!type.equals("void")) {
                        RunTestScript.actual = (String) method[i].invoke(keyWord, param);
                        Log.info(description);
                        keyWord.check(actual, expected);
                    } else {
                        method[i].invoke(keyWord, param);
                    }
                    break;
                }
            }
        }catch (Throwable e) {
            Log.error("Method execute_action | Exception desc : " + e.getMessage());
            onFail(error);
        }
        //onResultStep(result,error,numberStep);
    }

    // region verify result after each step
    private static void verifyStep(int numberStep) throws IOException {
        sActionKeyword = ExcelUtils.getCellData(numberStep, Constanst.VERIFY_STEP, Constanst.TEST_STEP_SHEET);
        params = "";

        if(!sActionKeyword.equals("")){
            if(result == Constanst.PASS) {
                expected = ExcelUtils.getCellData(numberStep,Constanst.EXPECTED,Constanst.TEST_STEP_SHEET);
                description = "Check - " +description;
                execute_action(numberStep, "");
            }
        }
    }
    // endregion verify result after each step

    //region RESULT
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
    public static void onFail(String message) {
        //Log.info(message);
        result = Constanst.FAIL;
        error = message;
    }

    // endregion RESULT

    //endregion TEST STEP


    // Test Step
    public static int iTestStep;
    public static int lastTestStep;
    public static String sActionKeyword;
    public static int  paramCount;
    public static Object[]  param;
    public static String params;
    public static String result = Constanst.PASS;
    public static String error;
    public static String dataSet;
    public static String expected;
    public static String actual;
    public static String process;
    public static String description;
    public static String testStep;

    //Class
    public static KeyWordsToAction keyWord;
    public static Method method[];

    //Scope
    public static String sRunMode;
    public static String scopePath;
    public static String sTestCaseID;
    public static int lesson;
    public static String level;
    public static int total;
    public static int startLesson;
    public static int numberLesson;
    public static int currentLesson;

    //Testcase
    public static String tcResult;
    public static String tcPath;
    public static String runMode;
    public static String tcName;

    //SetUp
    public static String markTest;
    public static int markRow;
}
