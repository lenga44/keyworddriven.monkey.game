package execute;

import common.keywords.KeyWordsToActionCustom;
import common.keywords.KeyWordsToAction;
import common.utility.Constanst;
import common.utility.ExcelUtils;
import common.utility.FileHelperUtils;
import common.utility.Log;
import report.GenerateReport;

import java.io.*;
import java.util.ArrayList;

public class RunTestScript extends RunTestDataScript{

    /*public static void main(String[] args) throws IOException {
        keyWord = new KeyWordsToActionCustom();
        method = keyWord.getClass().getMethods();

        Log.resetFileLog();
        scopePath = openScopeFile(Constanst.SCOPE_FILE_PATH);
        int iTotalPlanRow = ExcelUtils.getRowCount(Constanst.PLAN_SHEET);

        for (int i =1;i<iTotalPlanRow;i++) {

            getNumberInLevel(i);
            currentLesson = lesson + i;
            ExcelUtils.setCellData(String.valueOf(currentLesson), i, Constanst.CURRENT_INDEX_COLUM, Constanst.PLAN_SHEET, scopePath);
            getLevelFolder(i);

            for(int row =0;row<numberLesson;row++) {
                execute();
                GenerateReport.genReport(row,levelFolder);
                resetStatusTCFile();
            }

        }
    }*/
    //region SCOPE
    private static void cleanContextInCell(){
        if(isMarkTest==true) {
            if (markTest == "1") {
                String markResult = ExcelUtils.getStringValueInCell(markRow, Constanst.TESTCASE_STATUS, Constanst.TESTCASE_SHEET);
                String status = (numberLesson > 1 && markResult == Constanst.PASS) ? Constanst.YES : Constanst.NO;
                ExcelUtils.setCellData(status, markRow, Constanst.RUN_MODE_SCOPE, Constanst.SCOPE_SHEET, scopePath);
            }
        }
    }
    public static void execute() throws IOException {
        int iTotalFeature = ExcelUtils.getRowCount(Constanst.SCOPE_SHEET);
        Log.info("Total scope : "+iTotalFeature);

        for (int i = 1; i<iTotalFeature;i++){

            markTest = "";
            sRunMode = ExcelUtils.getStringValueInCell(i,Constanst.RUN_MODE_SCOPE,Constanst.SCOPE_SHEET);
            Log.info("Mode in scope: "+sRunMode);

            if(sRunMode.equals(Constanst.YES)) {

                tcName = ExcelUtils.getStringValueInCell(i, Constanst.TEST_SUITE_FILE_NAME, Constanst.SCOPE_SHEET);
                Log.info("TCS name: "+tcName);
                getTCPath();

                execute_testcases();
                cleanContextInCell();

                ExcelUtils.setExcelFile(scopePath);
                ExcelUtils.setCellData(tcResult, i, Constanst.STATUS_SUITE, Constanst.SCOPE_SHEET, scopePath);
            }
        }
    }
    //endregion

    public static void getLevelFolder(int row)throws IOException{
        courseFolder = FileHelperUtils.getRootFolder() + Constanst.REPORT_FILE_PATH + ExcelUtils.getStringValueInCell(row, Constanst.COURSE_COLUM, Constanst.PLAN_SHEET);
        levelFolder = courseFolder +"//" + RunTestScript.level;
        Log.info("levelFolder: "+levelFolder);
        Log.info("Folder path report course: " +FileHelperUtils.convertPath(levelFolder));

        FileHelperUtils.genFolderReport(courseFolder);
        Log.info("Folder path report level: " +FileHelperUtils.convertPath(levelFolder));
    }

    public static void getTCPath()throws IOException{
        switch (tcName){
            case  "Report_OnceTimeSetUp":
                tcPath = openScopeFile( Constanst.ONCE_TIME_SETUP_FILE_PATH);
                isMarkTest = true;
                break;
            case  "Report_SetUp":
                tcPath = openScopeFile( Constanst.SETUP_FILE_PATH);
                isMarkTest = true;
                break;
            case  "Report_TearDown":
                tcPath = openScopeFile( Constanst.TEARDOWN_FILE_PATH);
                break;
            case  "Report_OnceTimeTearDown":
                tcPath = openScopeFile( Constanst.ONCE_TIME_TEARDOWN_SETUP_FILE_PATH);
                break;
            default:
                tcPath = openScopeFile( Constanst.TESTCASE_FILE_PATH, tcName + ".xlsx");
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

    //region TESTCASE
    private static void execute_testcases() throws IOException {
        int iTotalTestCase = ExcelUtils.getRowCount(Constanst.TESTCASE_SHEET);
        Log.info("Total TC: " + iTotalTestCase);
        for(int i =0; i<iTotalTestCase;i++) {

            sTestCaseID = ExcelUtils.getStringValueInCell(i, Constanst.TESTCASE_ID, Constanst.TESTCASE_SHEET);
            Log.info("TCID: " + sTestCaseID);
            runMode = ExcelUtils.getStringValueInCell(i,Constanst.RUN_MODE_TEST_STEP,Constanst.TESTCASE_SHEET);
            Log.info("Run mode in TC: " + runMode);

            if(runMode.equals(Constanst.YES)) {
                if(isMarkTest==true) {
                    if (markTest == "1")
                            markRow = i;
                }

                rangeStepByTestCase();
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
            process = ExcelUtils.getStringValueInCell(iTestStep, Constanst.PROCEED, Constanst.TEST_STEP_SHEET);
            Log.info("Process TS: "+process);
            if(process.equals(Constanst.PROCESS_YES)) {

                sActionKeyword = ExcelUtils.getStringValueInCell(iTestStep, Constanst.KEYWORD, Constanst.TEST_STEP_SHEET);
                params = ExcelUtils.getStringValueInCell(iTestStep, Constanst.PARAMS, Constanst.TEST_STEP_SHEET);
                dataSet = ExcelUtils.getStringValueInCell(iTestStep, Constanst.DATA_SET, Constanst.TEST_STEP_SHEET);
                description = ExcelUtils.getStringValueInCell(iTestStep, Constanst.DESCRIPTION, Constanst.TEST_STEP_SHEET);
                testStep = ExcelUtils.getStringValueInCell(iTestStep, Constanst.TEST_STEP, Constanst.TEST_STEP_SHEET);

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
                    Log.info("type: "+type);
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
        sActionKeyword = ExcelUtils.getStringValueInCell(numberStep, Constanst.VERIFY_STEP, Constanst.TEST_STEP_SHEET);
        params = "";

        if(!sActionKeyword.equals("")){
            if(result == Constanst.PASS) {
                expected = ExcelUtils.getStringValueInCell(numberStep,Constanst.EXPECTED,Constanst.TEST_STEP_SHEET);
                description = "Check - " +description;
                execute_action(numberStep, "");
            }
        }
    }
    // endregion verify result after each step

    //region RESULT
    private static void onResultStep(String status, String message, int rowNumber ){
        if(status == Constanst.FAIL) {
            ExcelUtils.setCellData("",rowNumber,Constanst.IMAGE,Constanst.TEST_STEP_SHEET,tcPath);
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

    public static void resetStatusTCFile(){
        if(isMarkTest==true && markTest.equals("1")) {
            ExcelUtils.setExcelFile(tcPath);
            ExcelUtils.cleanContextInRange(Constanst.RESULT, Constanst.TEST_STEP_SHEET, tcPath);
            ExcelUtils.cleanContextInRange(Constanst.TESTCASE_STATUS, Constanst.TESTCASE_SHEET, tcPath);
        }
    }
    //region KEY

    //region Test Step key
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
    //endregion

    //region Scope key
    public static String sRunMode;
    public static String scopePath;
    public static String sTestCaseID;
    public static int currentLesson;
    //endregion

    //region Testcase key
    public static String tcResult;
    public static String tcPath;
    public static String runMode;
    public static String tcName;
    //endregion

    //region SetUp key
    public static String markTest;
    public static int markRow;
    public static boolean isMarkTest = false;
    //endregion
    public static String courseFolder;
    public static String levelFolder;
    //endregion
}
