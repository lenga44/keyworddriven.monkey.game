package execute;

import common.facade.Adapter;
import common.keywords.app.ExceptionEx;
import common.keywords.app.KeyWordsToAction;
import common.keywords.app.KeyWordsToComPair;
import common.keywords.app.action.TakePhoto;
import common.keywords.app.verify.Check;
import common.utility.*;
import report.GenerateReport;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static common.keywords.app.KeyWordsToAction.exception;
import static execute.Run.iOnceTimeTearDown;
import static execute.Scope.genReportName;

public class TestScrip {
    public TestScrip(KeyWordsToComPair keyWord, Method method[],Map<Class<?>,Method[]> classes){
        this.keyWord = keyWord;
        this.method = method;
        this.classes = classes;
    }
    private static int getTotalTestSuit(int total){
        System.out.println(iOnceTimeTearDown);
        if(iOnceTimeTearDown>0){
            total = total -1;
        }
        return total;
    }
    //region SCOPE
    public static void execute_suites(String scopePath,int iTestSuite) throws Exception {
        Log.info("execute_suites");
        List<String> reports = new ArrayList<>();
        flow = new ArrayList<>();
        ExcelUtils.setExcelFile(scopePath);
        int iTotalSuite =getTotalTestSuit(ExcelUtils.getRowCount(Constanst.SCOPE_SHEET));
        Scope.genFlowLesson(json,iTotalSuite,scopePath);
        iTotalSuite = getTotalTestSuit(ExcelUtils.getRowCount(Constanst.SCOPE_SHEET));
        for (;iTestSuite<=iTotalSuite-1;iTestSuite++){
            scopeResult = new ArrayList<>();
            tcName = ExcelUtils.getStringValueInCell(iTestSuite, Constanst.TEST_SUITE_FILE_NAME, Constanst.SCOPE_SHEET);
            if (!tcName.equals("")) {
            ExcelUtils.setCellData("", iTestSuite, Constanst.STATUS_SUITE, Constanst.SCOPE_SHEET, scopePath);
            String sRunMode = ExcelUtils.getStringValueInCell(iTestSuite, Constanst.RUN_MODE_SCOPE, Constanst.SCOPE_SHEET);
                try {
                    if (sRunMode.equals(Constanst.YES)) {
                        reports.add(reportPath);
                        Scope.deFindFlowGame(iTestSuite, scopePath);
                        genTestcaseReport();
                        genTestCaseWithGroup();
                        ;
                        int iTotalTestCase = ExcelUtils.getRowCount(Constanst.TESTCASE_SHEET);
                        execute_testcases(iTotalTestCase);
                        ExcelUtils.setExcelFile(scopePath);
                        if (scopeResult.contains(Constanst.FAIL)) {
                            ExcelUtils.setCellData(Constanst.FAIL, iTestSuite, Constanst.STATUS_SUITE, Constanst.SCOPE_SHEET, scopePath);
                        } else {
                            ExcelUtils.setCellData(Constanst.PASS, iTestSuite, Constanst.STATUS_SUITE, Constanst.SCOPE_SHEET, scopePath);
                        }
                    }
                }catch (Exception e){
                    Log.error(e.getMessage());
                }
            }
            GroupInTest.index =1;
            EndTestScript.saveListFail(scopeResult,"L"+level+"_"+topic+"_"+lesson+"_"+tcName);
        }
    }
    public static void execute_suite(String scopePath,int iTestSuite) throws Exception {
        Log.info("execute_suites");
        List<String> reports = new ArrayList<>();
        ExcelUtils.setExcelFile(scopePath);
        int iTotalSuite =ExcelUtils.getRowCount(Constanst.SCOPE_SHEET);
        Scope.genFlowLesson(json,iTotalSuite,scopePath);
            scopeResult = new ArrayList<>();
            tcName = ExcelUtils.getStringValueInCell(iTestSuite, Constanst.TEST_SUITE_FILE_NAME, Constanst.SCOPE_SHEET);
            if (!tcName.equals("")) {
                ExcelUtils.setCellData("", iTestSuite, Constanst.STATUS_SUITE, Constanst.SCOPE_SHEET, scopePath);
                String sRunMode = ExcelUtils.getStringValueInCell(iTestSuite, Constanst.RUN_MODE_SCOPE, Constanst.SCOPE_SHEET);
                if (sRunMode.equals(Constanst.YES)) {
                    reports.add(reportPath);
                    Scope.deFindFlowGame(iTestSuite, scopePath);
                    genTestcaseReport();
                    genTestCaseWithGroup();;
                    int iTotalTestCase = ExcelUtils.getRowCount(Constanst.TESTCASE_SHEET);
                    execute_testcases(iTotalTestCase);
                    ExcelUtils.setExcelFile(scopePath);
                    if(scopeResult.contains(Constanst.FAIL)) {
                        ExcelUtils.setCellData(Constanst.FAIL, iTestSuite, Constanst.STATUS_SUITE, Constanst.SCOPE_SHEET, scopePath);
                    }else {
                        ExcelUtils.setCellData(Constanst.PASS, iTestSuite, Constanst.STATUS_SUITE, Constanst.SCOPE_SHEET, scopePath);
                    }
                }
            GroupInTest.index =1;
            EndTestScript.saveListFail(scopeResult,"L"+level+"_"+topic+"_"+lesson+"_"+tcName);
        }
        //FileHelpers.deleteAllFileInFolder(reports,levelFolder);
    }
    private static void genTestcaseReport() throws IOException {
        tcPath = FileHelpers.getRootFolder() + FileHelpers.getValueConfig(Constanst.TESTCASE_FILE_PATH)+ tcName + ".xlsx";
        if(isDataFlow) {
            reportPath = GenerateReport.genTCReport(levelFolder, reportName);
        }else {
            reportPath = GenerateReport.genTCReport(levelFolder, "");
        }
        ExcelUtils.setExcelFile(reportPath);
    }
    private static void genTestCaseWithGroup() throws Exception {
        KeyWordsToAction.pause();
        ExcelUtils.setExcelFile(reportPath);
        int iTotalTestCase = ExcelUtils.getRowCount(Constanst.TESTCASE_SHEET);
        if(isDataFlow) {
            int group = GroupInTest.getGroup().size();
            if (group > 0) {
                ExcelUtils.setExcelFile(reportPath);
                ExcelUtils.createRowLastest(iTotalTestCase, Constanst.TESTCASE_SHEET, reportPath);
                GroupInTest.genTestCaseWhichGroupContain(json, reportPath);
                GroupInTest.genTestStepFollowTestCase(reportPath);
            }
        }
        KeyWordsToAction.resume();
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
        try {
            Log.info("Total TC: " + iTotalTestCase);
            for (int i = 1; i < iTotalTestCase; i++) {
                tcResults = new ArrayList<>();
                try {
                    ExcelUtils.setCellData("", i, Constanst.TESTCASE_STATUS, Constanst.TESTCASE_SHEET, reportPath);
                    String runMode = ExcelUtils.getStringValueInCell(i, Constanst.RUN_MODE_TEST_CASE, Constanst.TESTCASE_SHEET);
                    if (runMode.equals(Constanst.YES)) {
                        String sTestCaseID = ExcelUtils.getStringValueInCell(i, Constanst.TESTCASE_ID, Constanst.TESTCASE_SHEET);
                        Log.info("TC ID: " + sTestCaseID);
                        rangeStepByTestCase(sTestCaseID);
                        Log.info("result: " + result);
                        execute_steps();
                    }else {
                        result =Constanst.PASS;
                        error = "";
                    }
                }catch (Exception e){
                    error = "Step fail: "+ e.getMessage();
                }
                onResultTestcase(error, i);
            }
            markFailTest(iTotalTestCase);
            markSkipTest(iTotalTestCase);
        }catch (Exception e){
            Log.info("|execute_testcases | " + e.getMessage());
        }
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
    private static void onResultTestcase(String message, int rowNumber) {
        if(tcResults.contains(Constanst.FAIL) ||tcResults.contains(Constanst.SKIP)) {
            scopeResult.add(Constanst.FAIL);
            ExcelUtils.setCellData(Constanst.FAIL, rowNumber, Constanst.TESTCASE_STATUS, Constanst.TESTCASE_SHEET, reportPath);
            ExcelUtils.setCellData(message,  rowNumber, Constanst.TESTCASE_ERROR, Constanst.TESTCASE_SHEET, reportPath);
        }else {
            ExcelUtils.setCellData(Constanst.PASS, rowNumber, Constanst.TESTCASE_STATUS, Constanst.TESTCASE_SHEET, reportPath);
        }
        //ExcelUtils.setCellData(message,  rowNumber, Constanst.TESTCASE_ERROR, Constanst.TESTCASE_SHEET, reportPath);
    }
    private static String getValueVariable(String value,String key){
        if(value.contains("$."+key)){
            String locator = FileHelpers.getValueVariableFile(key);
            value = value.replace("$."+key,locator);
        }
        return value;
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
        if(params.contains("$.order")){
            String locator = FileHelpers.getValueVariableFile("order");
            params = params.replace("$.order",locator);
            ExcelUtils.setCellData(params,row,colum,Constanst.TEST_STEP_SHEET,reportPath);
        }
        List<String> list = new ArrayList<>();
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
                key = getVariableValue(key,"$.index",row);
                key = getVariableValue(key,"$.activity",row);
                key = getVariableValue(key,"$.order",row);
                value = JsonHandle.getValue(json, key);
                FileHelpers.setJsonVariable(key, value);
                ExcelUtils.setCellData(value, row, Constanst.DATA_SET, Constanst.TEST_STEP_SHEET, reportPath);
            } else
                return key;
        }catch (Exception e){
            Log.error("getDataSet "+e.getMessage());
        }
        return value;
    }
    public static String getDataSet(String key){
        key = getValueInVariableFile(key);
        String value = "";
        if(key.contains("$")&& json != null) {
            value = JsonHandle.getValue(json, key);
        }else
            value = key;
        return value;
    }
    private static String getValueInVariableFile(String key){
        String value = key;
        if(key.contains("$.path")){
            String locator = FileHelpers.getValueVariableFile("path");
            value = value.replace("$.path",locator);
        }
        if(key.contains("$.index")){
            String index = FileHelpers.getValueVariableFile("index");
            value = value.replace("$.index",index);
        }
        value = getValueVariable(value,"order");
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
                        getMethods(sActionKeyword);
                        execute_action(dataSet,sActionKeyword,iTestStep,Constanst.PARAMS);
                    }
                    verifyStep(iTestStep);

                }
                onResultStep(result, error, iTestStep);

            }
        }
    }
    private static void getMethods(String sActionKeyword){
        try {
            boolean isMethod = false;
            for (Class name : classes.keySet()) {
                for (Method m : classes.get(name)) {
                    if (m.getName().equals(sActionKeyword)) {
                        method = classes.get(name);
                        isMethod = true;
                        break;
                    }
                }
                if (isMethod == true) {
                    break;
                }
            }
        }catch (Exception e){
            ExceptionEx.exception("getMethods| "+e.getMessage());
        }
    }
    private static void onResultStep(String status, String message, int rowNumber ){
        ExcelUtils.setCellData(status, rowNumber, Constanst.RESULT, Constanst.TEST_STEP_SHEET, reportPath);
        ExcelUtils.setCellData(message,  rowNumber, Constanst.ERROR, Constanst.TEST_STEP_SHEET, reportPath);
        if(status == Constanst.FAIL) {
            tcResults.add(status);
            byte[] bytes = TakePhoto.takePhoto();
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
                        Log.info(testStep + ":  " + description);
                        if (paramCount == 0) {
                            param = null;
                        }
                        String type = String.valueOf(method[i].getReturnType());
                        if (!type.equals("void")) {
                            result = Constanst.PASS;
                            String actual = (String) method[i].invoke(keyWord, param);
                            Log.info(description);
                            if (expected.contains(Constanst.CHECK_CONTAIN)||expected.contains(Constanst.CHECK_SKIP)) {
                                if(expected.contains(Constanst.CHECK_SKIP)) {
                                    if(actual.equals("")){
                                        expected ="";
                                    }else {
                                        expected = expected.replace(Constanst.CHECK_SKIP, "");
                                    }
                                }else {
                                    expected = expected.replace(Constanst.CHECK_CONTAIN, "");
                                }
                                Check.checkContain(actual.trim(), expected);
                            } else {
                                Check.check(actual.trim(), expected);
                            }
                        } else {
                        /*for(int z=0;z<paramCount;z++){
                            System.out.println(param[z].);
                        }*/
                            method[i].invoke(keyWord, param);
                        }
                        break;
                    }
                }
        }catch (Throwable e) {
            Log.error(name);
            Log.error(params);
            for (int z=0;z<param.length;z++) {
                Log.info(param[z].getClass().getTypeName());
                Log.info(param[z].getClass().getName());
                Log.error(param[z]);
            }
            onFail("action "+e.getMessage());
        }
        //onResultStep(result,error,row);
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
            getMethods(sActionKeyword);
            String data = getDataSet(dataSetActual);
            System.out.println("dataSetActual "+data);
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
        String key = getKey(ex);
        ex = LogicHandle.removeString(ex,key);
        if(isDataFlow ==true && ex.contains("$")) {
            ex = getVariableValue(ex,"$.index",numberStep);
            ex = getVariableValue(ex,"$.activity",numberStep);
            ex = getVariableValue(ex,"$.order",numberStep);
            String value = JsonHandle.getValue(json, ex);
            ExcelUtils.setCellData(value,numberStep,Constanst.EXPECTED,Constanst.TEST_STEP_SHEET,reportPath);
            return LogicHandle.replaceStr(value+key,"null");
        }
        else
            return LogicHandle.replaceStr(ex+key,"null");
    }
    private static String getKey(String value){
        String key = null;
        if(value.endsWith(Constanst.CHECK_SKIP)){
            key = Constanst.CHECK_SKIP;
        }
        if (value.endsWith(Constanst.CHECK_CONTAIN)){
            key=Constanst.CHECK_CONTAIN;
        }
        return key;
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

        level = getDataSet(ExcelUtils.getStringValueInCell(1,Constanst.LEVEL_COLUM,Constanst.PLAN_SHEET));
        levelFolder =courseFolder +"//" + level;

        topic = ExcelUtils.getStringValueInCell(1,Constanst.TOPIC_PLAN_COLUM,Constanst.PLAN_SHEET);
        lesson = ExcelUtils.getStringValueInCell(1,Constanst.LESSON_PLAN_COLUM,Constanst.PLAN_SHEET);
        if(!topic.isEmpty()){
            topic = LogicHandle.getTextAlphabet(genReportName(topic));
            levelFolder += "//"+topic;
        }
        if(!lesson.isEmpty()) {
            lesson = LogicHandle.getTextAlphabet(genReportName(lesson));
            levelFolder += "//"+lesson;
        }

        Log.info("levelFolder: "+levelFolder);
        Log.info("Folder path report course: " + levelFolder);

        FileHelpers.genFolderReport(courseFolder);
        Log.info("Folder path report level: " + courseFolder);
    }
    //endregion


    //region KEY

    //region Testcase key
    //public static String tcResult = Constanst.PASS;
    public static List<String> tcResults;
    public static List<String> scopeResult;
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
    protected static KeyWordsToComPair keyWord;
    protected static Map<Class<?>,Method[]> classes;
    protected static String expected;
    public static List<String> flow;
    //endregion

    //region report key
    public static String levelFolder;
    public static String reportName="";
    public static String reportPath;
    public static String topic;
    public static String level;
    public static String lesson;
    //end region

    //endregion

    //endregion
}
