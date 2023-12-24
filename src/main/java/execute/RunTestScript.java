package execute;

import common.utility.Constanst;
import common.utility.ExcelUtils;

public class RunTestScript {
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
                System.out.println("Test step: " +iTestStep);
                lastTestStep = ExcelUtils.getTestStepCount(Constanst.TEST_STEP_SHEET,sTestCaseID,iTestStep);
                System.out.println("Last test step: " +lastTestStep);
            }
        }
    }
    public static String sRunMode;
    public static int iTestStep;
    public static int lastTestStep;
    public static String sTestCaseID;
}
