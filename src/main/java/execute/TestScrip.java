package execute;

import common.keywords.KeyWordsToAction;
import common.keywords.KeyWordsToActionToVerify;
import common.utility.*;
import report.GenerateReport;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static common.keywords.KeyWordsToAction.exception;

public class TestScrip {
    public TestScrip(KeyWordsToActionToVerify keyWord, Method method[]){
        this.keyWord = keyWord;
        this.method = method;
    }
    //region SCOPE
    public static void execute_suites(String scopePath,int iTestSuite, int iTotalSuite) throws Exception {
        Log.info("execute_suites");
        List<String> flow = new ArrayList<>();
        List<String> reports = new ArrayList<>();
        ExcelUtils.setExcelFile(scopePath);
        for (;iTestSuite<=iTotalSuite;iTestSuite++){
            System.out.println("============= "+iTotalSuite);
            ExcelUtils.setCellData("",iTestSuite,Constanst.STATUS_SUITE,Constanst.SCOPE_SHEET,scopePath);
            String sRunMode = ExcelUtils.getStringValueInCell(iTestSuite,Constanst.RUN_MODE_SCOPE,Constanst.SCOPE_SHEET);
            tcName = ExcelUtils.getStringValueInCell(iTestSuite, Constanst.TEST_SUITE_FILE_NAME, Constanst.SCOPE_SHEET);
            System.out.println("TC name: "+tcName);
            Scope.genFlowLesson(iTestSuite,json);
            iTotalSuite = ExcelUtils.getRowCount(Constanst.SCOPE_SHEET);
            if(sRunMode.equals(Constanst.YES)) {
                Scope.deFindFlowGame(iTestSuite,scopePath);
                flow.add(tcName);
                tcPath = FileHelpers.getRootFolder() + FileHelpers.getValueConfig(Constanst.TESTCASE_FILE_PATH)+ tcName + ".xlsx";
                if(isDataFlow) {
                    reportPath = GenerateReport.genTCReport(levelFolder, reportName);
                }else {
                    reportPath = GenerateReport.genTCReport(levelFolder, "");
                }
                ExcelUtils.setExcelFile(reportPath);
                int iTotalTestCase = ExcelUtils.getRowCount(Constanst.TESTCASE_SHEET);
                System.out.println(iTotalTestCase);
                if(isDataFlow) {
                    int group = GroupInTest.getGroup().size();
                    if (group > 0) {
                        KeyWordsToAction.pause();
                        try {
                            ExcelUtils.createRowLastest(iTotalTestCase, Constanst.TESTCASE_SHEET, reportPath);
                            GroupInTest.genTestCaseWhichGroupContain(json, reportPath);
                            GroupInTest.genTestStepFollowTestCase(reportPath);
                        }catch (Exception ignored){
                        }
                        KeyWordsToAction.resume();
                    }
                }
                reports.add(reportPath);
                iTotalTestCase = ExcelUtils.getRowCount(Constanst.TESTCASE_SHEET);
                execute_testcases(iTotalTestCase);
                ExcelUtils.setExcelFile(scopePath);
                ExcelUtils.setCellData(tcResult, iTestSuite, Constanst.STATUS_SUITE, Constanst.SCOPE_SHEET, scopePath);
            }
        }
        FileHelpers.writeFile(flow.toString(),RunTestScriptData.reportPath.replace(".xlsx",".txt"));
    }
    public static String openScopeFile(String fileName) throws IOException{
        Log.info("fileName "+fileName);
        String path =  FileHelpers.getValueConfig(fileName);
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
    private static void execute_testcases(int iTotalTestCase) throws IOException {
        Log.info("Total TC: " + iTotalTestCase);
        for(int i =1; i<iTotalTestCase;i++) {
            tcResults = new ArrayList<>();
            ExcelUtils.setCellData("",i,Constanst.TESTCASE_STATUS,Constanst.TESTCASE_SHEET,reportPath);
            String runMode = ExcelUtils.getStringValueInCell(i,Constanst.RUN_MODE_TEST_CASE,Constanst.TESTCASE_SHEET);
            if(runMode.equals(Constanst.YES)) {
            String sTestCaseID = ExcelUtils.getStringValueInCell(i, Constanst.TESTCASE_ID, Constanst.TESTCASE_SHEET);
            Log.info("TCID: " + sTestCaseID);
            rangeStepByTestCase(sTestCaseID);
            Log.info("result: "+result);
                if (!result.equals(Constanst.SKIP)) {
                    tcResult = Constanst.PASS;
                    execute_steps();

                } else
                    tcResult = Constanst.SKIP;
                if(tcResults.contains(Constanst.FAIL)){
                    onResultTestcase(Constanst.FAIL, error, i);
                }else {
                    onResultTestcase(Constanst.PASS, "", i);
                }
            }
        }
        markFailTest(iTotalTestCase);
        markSkipTest(iTotalTestCase);
        System.out.println("============ "+tcResult);
    }
    private static void markFailTest(int iTotalTestCase){
        for(int i =1; i<iTotalTestCase;i++) {
            String runMode = ExcelUtils.getStringValueInCell(i,Constanst.RUN_MODE_TEST_CASE,Constanst.TESTCASE_SHEET);
            if(runMode.equals(Constanst.YES)){
                String tcStatus = ExcelUtils.getStringValueInCell(i, Constanst.TESTCASE_STATUS,Constanst.TESTCASE_SHEET);
                if(tcStatus.equals("")){
                    ExcelUtils.setCellData(Constanst.FAIL,i,Constanst.TESTCASE_STATUS,Constanst.TESTCASE_SHEET,reportPath);
                    ExcelUtils.setCellData("Not run",i,Constanst.TESTCASE_ERROR,Constanst.TESTCASE_SHEET,reportPath);
                }
            }
        }
    }
    private static void markSkipTest(int iTotalTestCase){
        for(int i =1; i<iTotalTestCase;i++) {
            String runMode = ExcelUtils.getStringValueInCell(i,Constanst.RUN_MODE_TEST_CASE,Constanst.TESTCASE_SHEET);
            if(runMode.equals(Constanst.NO)){
                String tcStatus = ExcelUtils.getStringValueInCell(i, Constanst.TESTCASE_STATUS,Constanst.TESTCASE_SHEET);
                if(tcStatus.equals("")){
                    ExcelUtils.setCellData(Constanst.SKIP,i,Constanst.TESTCASE_STATUS,Constanst.TESTCASE_SHEET,reportPath);
                }
            }
        }
    }
    private static void onResultTestcase(String status, String message, int rowNumber) {
        ExcelUtils.setCellData(status, rowNumber, Constanst.TESTCASE_STATUS, Constanst.TESTCASE_SHEET, reportPath);
        ExcelUtils.setCellData(message,  rowNumber, Constanst.TESTCASE_ERROR, Constanst.TESTCASE_SHEET, reportPath);
    }
    private static Object[] getParam(String params, String data,int row,int colum)  {
        if(params.contains("$.path")){
            String locator = FileHelpers.getValueVariableFile("path");
            params = params.replace("$.path",locator);
            ExcelUtils.setCellData(params,row,colum,Constanst.TEST_STEP_SHEET,reportPath);
        }
        if(params.contains("$.index")){
            String index = FileHelpers.getValueVariableFile("index");
            params = params.replace("$.index",index);
            ExcelUtils.setCellData(params,row,colum,Constanst.TEST_STEP_SHEET,reportPath);
        }
        List<Object> list = new ArrayList<>();
        if (!params.equals("")) {
            if (params.contains(",")) {
                list.addAll(Arrays.asList(params.split(",")));
            } else {
                list.add(params);
            }
            if(!data.equals("")){
                list.add(data);
            }
            return list.toArray();
        } else {
            return null;
        }
    }
    /*private static String DataSetActual(String data){
        String actual = getDataSet(data);
    }*/
    //endregion

    //region TEST STEP
    private static void rangeStepByTestCase(String sTestCaseID){
        iTestStep = ExcelUtils.getRowContains(sTestCaseID,Constanst.TESTCASE_ID,Constanst.TEST_STEP_SHEET);
        lastTestStep = ExcelUtils.getTestStepCount(Constanst.TEST_STEP_SHEET,sTestCaseID,iTestStep);
    }

    public static String getDataSet(int row){
        String key = ExcelUtils.getStringValueInCell(row, Constanst.DATA_SET, Constanst.TEST_STEP_SHEET);
        String value = "";
        try {
            if (key.contains("$") && !json.equals(null)) {
                value = JsonHandle.getValue(json, key);
                FileHelpers.setJsonVariable(key, value);
                ExcelUtils.setCellData(value, row, Constanst.DATA_SET, Constanst.TEST_STEP_SHEET, reportPath);
                return value;
            } else
                return key;
        }catch (Exception e){
            Log.error("getDataSet "+e.getMessage());
            return "";
        }
    }
    public static String getDataSet(String key){
        String value = "";
        if(key.contains("$")&& !json.equals(null)) {
            value = JsonHandle.getValue(json, key);
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
                        execute_action(dataSet,sActionKeyword,iTestStep,Constanst.PARAMS);
                    }
                    verifyStep(iTestStep);

                }
                onResultStep(result, error, iTestStep);

            }
        }
        if (result == Constanst.FAIL)
            tcResult = Constanst.FAIL;
    }
    private static void onResultStep(String status, String message, int rowNumber ){
        ExcelUtils.setCellData(status, rowNumber, Constanst.RESULT, Constanst.TEST_STEP_SHEET, reportPath);
        ExcelUtils.setCellData(message,  rowNumber, Constanst.ERROR, Constanst.TEST_STEP_SHEET, reportPath);
        tcResults.add(status);
        if(status == Constanst.FAIL) {
            byte[] bytes = KeyWordsToAction.takePhoto();
            ExcelUtils.addPictureInCell(rowNumber, bytes, reportPath);
        }else {
            ExcelUtils.setCellData("", rowNumber, Constanst.IMAGE, Constanst.TEST_STEP_SHEET, reportPath);
        }
    }
    private static void execute_action(String data,String sActionKeyword,int row,int colum){
        String testStep = ExcelUtils.getStringValueInCell(iTestStep, Constanst.TEST_STEP, Constanst.TEST_STEP_SHEET);
        result = Constanst.PASS;
        String name=null;
        try {
            param = getParam(params,data,row,colum);
            int paramCount = (param == null) ? 0: param.length;
            for (int i = 0; i < method.length; i++) {
                String a = method[i].getName();
                if (a.equals(sActionKeyword) && method[i].getParameterCount() == paramCount) {
                    name = method[i].getName();
                    Log.info(testStep +":  "+description);
                    if (paramCount == 0) {
                        param = null;
                    }
                    String type = String.valueOf(method[i].getReturnType());
                    if (!type.equals("void")) {
                        String actual = (String) method[i].invoke(keyWord, param);
                        Log.info(description);
                        if(expected.contains(Constanst.CHECK_CONTAIN)){
                            expected = expected.replace(Constanst.CHECK_CONTAIN,"");
                            KeyWordsToAction.checkContain(actual,expected);
                        }else {
                            KeyWordsToAction.check(actual, expected);
                        }
                    } else {
                        method[i].invoke(keyWord, param);
                    }
                    break;
                }
            }
        }catch (Throwable e) {
            Log.error(name);
            onFail(e.getMessage());
        }
        //onResultStep(result,error,numberStep);
    }

    public static void onFail(String message) {
        //Log.info(message);
        result = Constanst.FAIL;
        error = message;
        Log.error(message);
    }
    // region verify result after each step
    private static void verifyStep(int numberStep) throws IOException {
        String sActionKeyword = ExcelUtils.getStringValueInCell(numberStep, Constanst.VERIFY_STEP, Constanst.TEST_STEP_SHEET);
        if(!sActionKeyword.equals("")) {
            String dataSetActual = ExcelUtils.getStringValueInCell(numberStep, Constanst.DATA_SET_ACTUAL, Constanst.TEST_STEP_SHEET);
            String data = getDataSet(dataSetActual);
            if (!data.equals("")) {
                params = ExcelUtils.getStringValueInCell(numberStep, Constanst.PARAM_VERIFY_STEP, Constanst.TEST_STEP_SHEET) + "," + data;
            } else {
                params = ExcelUtils.getStringValueInCell(numberStep, Constanst.PARAM_VERIFY_STEP, Constanst.TEST_STEP_SHEET);
            }
            ExcelUtils.setCellData(data, numberStep, Constanst.DATA_SET_ACTUAL, Constanst.TEST_STEP_SHEET, reportPath);
            if(result == Constanst.PASS) {
                expected = getExpectedWithKey(numberStep);
                description = "Check - " +description;
                execute_action("",sActionKeyword,numberStep,Constanst.PARAM_VERIFY_STEP);
            }
        }
    }
    private static String getExpectedWithKey(int numberStep){
        String ex = ExcelUtils.getStringValueInCell(numberStep,Constanst.EXPECTED,Constanst.TEST_STEP_SHEET);
        if(ex.contains(Constanst.CHECK_CONTAIN)){
            ex = ex.replace(Constanst.CHECK_CONTAIN,"");
        }
        if(isDataFlow ==true && ex.contains("$")) {
            ex = getVariableValue(ex,"$.index",numberStep);
            ex = getVariableValue(ex,"$.activity",numberStep);
            String value = JsonHandle.getValue(json, ex);
            ExcelUtils.setCellData(value,numberStep,Constanst.EXPECTED,Constanst.TEST_STEP_SHEET,reportPath);
            return value+Constanst.CHECK_CONTAIN;
        }
        else
            return ex+Constanst.CHECK_CONTAIN;
    }
    private static String getVariableValue(String ex, String key,int row){
        if(ex.contains(key)){
            String variable = FileHelpers.readFile(Constanst.VARIABLE_PATH_FILE);
            String index = JsonHandle.getValue(variable,key);
            ex = ex.replace(key,index);
            ExcelUtils.setCellData(ex,row,Constanst.EXPECTED,Constanst.TEST_STEP_SHEET,reportPath);
        }
        return ex;
    }
    // endregion verify result after each step
    public static void getLevelFolder(int row)throws IOException{
        String courseFolder = FileHelpers.getRootFolder() + Constanst.REPORT_FILE_PATH;
        String level = ExcelUtils.getStringValueInCell(1,Constanst.LEVEL_COLUM,Constanst.PLAN_SHEET);
        System.out.println(level);
        levelFolder =(level.contains("$."))? courseFolder +"//" +JsonHandle.getValue(json,level): courseFolder +"//" + level;
        Log.info("levelFolder: "+levelFolder);
        Log.info("Folder path report course: " + FileHelpers.convertPath(levelFolder));

        FileHelpers.genFolderReport(courseFolder);
        Log.info("Folder path report level: " + FileHelpers.convertPath(levelFolder));
    }
    //endregion


    //region KEY

    //region Testcase key
    public static String tcResult = Constanst.PASS;
    public static List<String> tcResults;
    public static String tcPath;
    public static boolean isDataFlow;
    public static String json;
    public static String tcName;
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
    public static String levelFolder;
    public static String reportName="";
    public static String reportPath;
    //end region

    //endregion

    //endregion
}
