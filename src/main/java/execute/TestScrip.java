package execute;

import common.keywords.KeyWordsToAction;
import common.utility.Constanst;
import common.utility.ExcelUtils;
import common.utility.FileHelperUtils;
import common.utility.Log;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class TestScrip {

    //region SCOPE
    public static void execute(String scopePath) throws IOException {
        int iTotalFeature = ExcelUtils.getRowCount(Constanst.SCOPE_SHEET);
        Log.info("Total scope : "+iTotalFeature);

        for (int i = 1; i<iTotalFeature;i++){

            String sRunMode = ExcelUtils.getCellData(i,Constanst.RUN_MODE_SCOPE,Constanst.SCOPE_SHEET);
            Log.info("Mode in scope: "+sRunMode);

            if(sRunMode.equals(Constanst.YES)) {

                String tcName = ExcelUtils.getCellData(i, Constanst.TESTCASE_FILE_NAME, Constanst.SCOPE_SHEET);
                Log.info("TCS name: "+tcName);
                getTCPath(tcName);

                execute_testcases();
                //cleanContextInCell();

                ExcelUtils.setExcelFile(scopePath);
                ExcelUtils.setCellData(tcResult, i, Constanst.STATUS_GAME, Constanst.SCOPE_SHEET, scopePath);
            }
        }
    }
    public static void getTCPath(String tcName)throws IOException{
        switch (tcName){
            case  "Report_OnceTimeSetUp":
                tcPath = openScopeFile( Constanst.ONCE_TIME_SETUP_FILE_PATH);
                break;
            case  "Report_OnceTimeTearDown":
                tcPath = openScopeFile( Constanst.ONCE_TIME_TEARDOWN_SETUP_FILE_PATH);
                break;
            default:
                tcPath = openScopeFile(Constanst.TESTCASE_FILE_PATH, tcName + ".xlsx");
                break;
        }
    }
    public static String openScopeFile(String fileName) throws IOException{
        Log.info("fileName "+fileName);
        String path = FileHelperUtils.getRootFolder() + FileHelperUtils.getPathConfig(fileName);
        Log.info("==PATH:== "+path);
        ExcelUtils.setExcelFile(path);
        return path;
    }
    public static String openScopeFile(String filePath,String fileName) throws IOException{
        Log.info("filePath "+filePath);
        Log.info("fileName "+fileName);
        String path = FileHelperUtils.getRootFolder() + FileHelperUtils.getPathConfig(filePath)+fileName;
        Log.info("==PATH:== "+path);
        ExcelUtils.setExcelFile(path);
        return path;
    }
    //endregion

    //region TESTCASE
    private static void execute_testcases() throws IOException {
        int iTotalTestCase = ExcelUtils.getRowCount(Constanst.TESTCASE_SHEET);
        Log.info("Total TC: " + iTotalTestCase);
        for(int i =0; i<iTotalTestCase;i++) {

            String sTestCaseID = ExcelUtils.getCellData(i, Constanst.TESTCASE_ID, Constanst.TESTCASE_SHEET);
            Log.info("TCID: " + sTestCaseID);
            String runMode = ExcelUtils.getCellData(i,Constanst.RUN_MODE_TEST_STEP,Constanst.TESTCASE_SHEET);
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
    private static void execute_steps() throws IOException {
        for (; iTestStep < lastTestStep; iTestStep++) {
            result = Constanst.PASS;
            String process = ExcelUtils.getCellData(iTestStep, Constanst.PROCEED, Constanst.TEST_STEP_SHEET);
            Log.info("Process TS: "+process);
            if(process.equals(Constanst.PROCESS_YES)) {

                String sActionKeyword = ExcelUtils.getCellData(iTestStep, Constanst.KEYWORD, Constanst.TEST_STEP_SHEET);
                params = ExcelUtils.getCellData(iTestStep, Constanst.PARAMS, Constanst.TEST_STEP_SHEET);
                String dataSet = ExcelUtils.getCellData(iTestStep, Constanst.DATA_SET, Constanst.TEST_STEP_SHEET);
                description = ExcelUtils.getCellData(iTestStep, Constanst.DESCRIPTION, Constanst.TEST_STEP_SHEET);

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
                error = "";

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
        String testStep = ExcelUtils.getCellData(iTestStep, Constanst.TEST_STEP, Constanst.TEST_STEP_SHEET);
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
            Log.error("Method execute_action | Exception desc : " + e.getMessage());
            onFail(error);
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
        String sActionKeyword = ExcelUtils.getCellData(numberStep, Constanst.VERIFY_STEP, Constanst.TEST_STEP_SHEET);
        params = "";

        if(!sActionKeyword.equals("")){
            if(result == Constanst.PASS) {
                expected = ExcelUtils.getCellData(numberStep,Constanst.EXPECTED,Constanst.TEST_STEP_SHEET);
                description = "Check - " +description;
                execute_action("",sActionKeyword);
            }
        }
    }
    // endregion verify result after each step

    //endregion

    /*public static void getTCPath()throws IOException{
        switch (tcName){
            case  "Report_OnceTimeSetUp":
                tcPath = openScopeFile( Constanst.ONCE_TIME_SETUP_FILE_PATH);
                break;
            case  "Report_OnceTimeTearDown":
                tcPath = openScopeFile( Constanst.ONCE_TIME_TEARDOWN_SETUP_FILE_PATH);
                break;
            default:
                tcPath = openScopeFile( Constanst.TESTCASE_FILE_PATH, tcName + ".xlsx");
                break;
        }
    }*/

    //region KEY

    //region Testcase key
    public static String tcResult;
    public static String tcPath;
    //endregion

    //region Test Step key
    public static int iTestStep;
    public static int lastTestStep;
    public static String result = Constanst.PASS;
    public static String error;
    public static String params;
    public static String description;
    public static Object[]  param;
    public static Method method[];
    public static KeyWordsToAction keyWord;
    public static String expected;
    //endregion

    //endregion
}
