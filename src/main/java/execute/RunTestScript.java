package execute;

import common.keywords.KeyWords;
import common.utility.Constanst;
import common.utility.ExcelUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

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

    private void execute_testcases(String fileName) throws Exception{
        ExcelUtils.setExcelFile(Constanst.PROJECT_PATH +Constanst.TESTCASE_FILE_PATH + fileName +".xlsx");
        int iTotalTestCase = ExcelUtils.getRowCount(Constanst.TESTCASE_SHEET);
        for(int i =0; i<iTotalTestCase;i++){
            sRunMode = ExcelUtils.getCellData(i,Constanst.RUN_MODE,Constanst.TESTCASE_SHEET);
            sTestCaseID = ExcelUtils.getCellData(i,Constanst.TESTCASE_ID,Constanst.TEST_STEP_SHEET);

            if(sRunMode.equals(Constanst.YES)){
                iTestStep = ExcelUtils.getRowContains(sTestCaseID,Constanst.TESTCASE_ID,Constanst.TEST_STEP_SHEET);
                lastTestStep = ExcelUtils.getTestStepCount(Constanst.TEST_STEP_SHEET,sTestCaseID,iTestStep);
            }

            for(;iTestStep<lastTestStep;iTestStep++){
                sActionKeyword = ExcelUtils.getCellData(iTestStep,Constanst.ACTION_KEYWORD,Constanst.TEST_STEP_SHEET);
                params = ExcelUtils.getCellData(iTestStep,Constanst.PARAM,Constanst.TEST_STEP_SHEET);
                execute_action();
            }
        }
    }

    private Object[] getParam(String params){
        ArrayList<Object> objs = new ArrayList<>();
        if (!params.equals("")) {
            if (params.contains(",")) {
                for (String value : params.split(",")) {
                    objs.add(value);
                }
            } else {
                objs.add(params);
            }
            return objs.toArray();
        } else {
            return null;
        }
    }

    private void execute_action()throws Exception{
        param = getParam(params);
        paramCount = (param == null) ? 0: param.length;

        for (int i=0; i<method.length;i++){

            if (method[i].getName().equals(sActionKeyword) && method[i].getParameterCount() == paramCount) {

                if(paramCount == 0){
                    method[i].invoke(keyWord, null);
                }else {
                    method[i].invoke(keyWord, param);
                }

            }
        }
    }

    private void onPassStep() throws Exception{

    }
    private void onFailStep() throws Exception{
        
    }

    public static String sRunMode;
    public static int iTestStep;
    public static int lastTestStep;
    public static String sTestCaseID;
    public static String sActionKeyword;
    public static int  paramCount;
    public static Object[]  param;
    public static String params;
    public static String tcResult;
    public static String fResult;

    public static KeyWords keyWord;
    public static Method method[];
}
