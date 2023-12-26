package execute;

import common.keywords.KeyWords;
import common.utility.Constanst;
import common.utility.ExcelUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class RunTestScript {
    public RunTestScript() throws NoSuchMethodException, SecurityException{
        keyWord = new KeyWords();
        method = keyWord.getClass().getMethods();
    }
    public static void main(String[] args) throws Exception {
        ExcelUtils.setExcelFile(Constanst.PROJECT_PATH +Constanst.SCOPE_FILE_PATH);
        RunTestScript runTestScript = new RunTestScript();
        runTestScript.execute();
    }

    private void execute() throws Exception{
        int iTotalFeature = ExcelUtils.getRowCount(Constanst.SCOPE_SHEET);
        for (int i = 0; i<iTotalFeature;i++){
            sRunMode = ExcelUtils.getCellData(i,Constanst.RUN_MODE,Constanst.SCOPE_SHEET);
            if(sRunMode.equals(Constanst.YES)) {
                execute_testcases(ExcelUtils.getCellData(i,Constanst.TESTCASE_FILE_NAME,Constanst.SCOPE_SHEET));
            }
        }
    }

    //region TESTCASE
    private void execute_testcases(String fileName) throws Exception{
        tcPath = Constanst.PROJECT_PATH +Constanst.TESTCASE_FILE_PATH + fileName +".xlsx";
        ExcelUtils.setExcelFile(tcPath);
        int iTotalTestCase = ExcelUtils.getRowCount(Constanst.TESTCASE_SHEET);
        for(int i =0; i<iTotalTestCase;i++){

            sRunMode = ExcelUtils.getCellData(i,Constanst.RUN_MODE,Constanst.TESTCASE_SHEET);
            sTestCaseID = ExcelUtils.getCellData(i,Constanst.TESTCASE_ID,Constanst.TEST_STEP_SHEET);

            if(sRunMode.equals(Constanst.YES)){
                rangeStepByTestCase();
            }
            if(result != Constanst.SKIP)
                execute_steps();
            else
                onResultTestcase(Constanst.SKIP,error,i);
        }
    }

    private Object[] getParam(String params, String data){
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

    private void onResultTestcase(String status, String message, int rowNumber) throws Exception{
        ExcelUtils.setCellData(status, rowNumber, Constanst.TESTCASE_STATUS, Constanst.TESTCASE_SHEET, tcPath);
        ExcelUtils.setCellData(message,  rowNumber, Constanst.TESTCASE_ERROR, Constanst.TESTCASE_SHEET, tcPath);
    }
    //endregion TESTCASE

    //region TEST STEP

    private void rangeStepByTestCase()throws Exception{
        iTestStep = ExcelUtils.getRowContains(sTestCaseID,Constanst.TESTCASE_ID,Constanst.TEST_STEP_SHEET);
        lastTestStep = ExcelUtils.getTestStepCount(Constanst.TEST_STEP_SHEET,sTestCaseID,iTestStep);
    }

    private void execute_steps()throws Exception{
        result = Constanst.PASS;
        for (; iTestStep < lastTestStep; iTestStep++) {

            sActionKeyword = ExcelUtils.getCellData(iTestStep, Constanst.KEYWORD, Constanst.TEST_STEP_SHEET);
            params = ExcelUtils.getCellData(iTestStep, Constanst.PARAMS, Constanst.TEST_STEP_SHEET);
            dataSet = ExcelUtils.getCellData(iTestStep,Constanst.DATA_SET,Constanst.TEST_STEP_SHEET);

            if(result !=Constanst.SKIP) {
                execute_action(iTestStep,dataSet);
                verifyStep(iTestStep);
            }else {
                onResultStep(Constanst.SKIP,error,iTestStep);
            }

        }
    }

    private void execute_action(int numberStep,String data)throws Exception{
        try {
            result = Constanst.PASS;
            param = getParam(params,data);
            paramCount = (param == null) ? 0: param.length;

            for (int i = 0; i < method.length; i++) {

                if (method[i].getName().equals(sActionKeyword) && method[i].getParameterCount() == paramCount) {

                    if (paramCount > 0) {
                        if (!method[i].getReturnType().equals("void")) {
                            RunTestScript.actual = (String) method[i].invoke(keyWord, param);
                            keyWord.check(actual, expected);
                        } else
                            method[i].invoke(keyWord, param);
                    } else
                        method[i].invoke(keyWord, null);
                }
            }
        }catch (Exception e) {
            onFail( "Method execute_action | Exception desc : " + e.getMessage());
            onResultStep(Constanst.FAIL,error,numberStep);
        }
    }

    // region verify result after each step
    private void verifyStep(int numberStep)throws Exception{
        try{
            sActionKeyword = ExcelUtils.getCellData(iTestStep, Constanst.VERIFY_STEP, Constanst.TEST_STEP_SHEET);
            params = ExcelUtils.getCellData(iTestStep, Constanst.PARAM_VERIFY_STEP, Constanst.TEST_STEP_SHEET);

            if(!sActionKeyword.equals("")){
                if(result == Constanst.PASS) {
                    error = "";
                    execute_action(numberStep, "");
                }
            }
        }catch (Exception e){
            onFail( "Method verify | Exception desc : " + e.getMessage());
        }
        onResultStep(Constanst.FAIL,error,numberStep);
    }
    // endregion verify result after each step

    //region RESULT
    private void onResultStep(String status, String message, int rowNumber ) throws Exception{
        ExcelUtils.setCellData(status, rowNumber, Constanst.RESULT, Constanst.TEST_STEP_SHEET, tcPath);
        ExcelUtils.setCellData(message,  rowNumber, Constanst.ERROR, Constanst.TEST_STEP_SHEET, tcPath);
    }
    private void onFail(String message) throws Exception{
        result = Constanst.FAIL;
        error = message;
    }

    // endregion RESULT

    //endregion TEST STEP



    public static String sRunMode;
    public static int iTestStep;
    public static int lastTestStep;
    public static String sTestCaseID;
    public static String sActionKeyword;
    public static int  paramCount;
    public static Object[]  param;
    public static String params;
    public static String result = Constanst.PASS;
    public static String error;
    public static String tcPath;
    public static String dataSet;
    public static String expected;
    public static String actual;

    public static KeyWords keyWord;
    public static Method method[];
}
