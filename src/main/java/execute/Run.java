package execute;

import common.utility.*;

import java.io.IOException;
import java.lang.reflect.Method;

public class Run {

    public static void main(String[] args) throws Exception {
        StartTestScript.logging();
        //StartTestScript.sendMessageStartTest();

        scopePath = FileHelpers.getRootFolder() + FileHelpers.getValueConfig(Constanst.SCOPE_FILE_PATH);
        Log.info("SCOPE_PATH: " + scopePath);

        ExcelUtils.setExcelFile(scopePath);
        method = StartTestScript.getMethods();

        returnFlowScrip();

        int iTotalSuite = ExcelUtils.getRowCount(Constanst.SCOPE_SHEET);
        Log.info("Total scope : " + iTotalSuite);

        returnSizeTestSuit(iTotalSuite - 1);

        runOneTime(iOnceTimeSetUp);
        if (isModuleFlow == true) {
            runTestScriptModule = new RunTestScriptModule(keyWord, method);
            runModuleFlow(iFirstTestSuit, iLastTestSuit);
        }
        if (isDataFlow == true) {
            runTestScriptData = new RunTestScriptData(keyWord, method);
            runDataFlow(iFirstTestSuit, iLastTestSuit);
        }
        runOneTime(iOnceTimeTearDown);
        //EndTestScript.sendMessTelegramEndScrip();
    }
    private static void runOneTime(int iOnceTime) throws Exception {
        Log.info("runOneTime " +iOnceTime);
        if(iOnceTime>0){
            TestScrip.execute_suites(scopePath,iOnceTime);
        }
    }
    private static void returnSizeTestSuit(int iTotalSuite) throws IOException {
        Log.info("returnSizeTestSuit with total("+iTotalSuite+")");
        iOnceTimeSetUp = TestScrip.onceTimeScrip(1);
        iOnceTimeTearDown = TestScrip.onceTimeScrip(iTotalSuite);
        if(iOnceTimeSetUp>0){
            if(iOnceTimeTearDown==0) {
                Log.info("Run once time set up");
                iFirstTestSuit = 2;
                iLastTestSuit = iTotalSuite;
            }else {
                Log.info("Run all once time set up and tear down");
                iFirstTestSuit = 2;
                iLastTestSuit = iTotalSuite-1;
            }
        }
        if(iOnceTimeSetUp==0){
            if(iOnceTimeTearDown > 0) {
                Log.info("Run once time tear down");
                iFirstTestSuit = 1;
                iLastTestSuit = iTotalSuite - 1;
            }else {
                Log.info("Run only test suit");
                iFirstTestSuit = 1;
                iLastTestSuit = iTotalSuite;
            }
        }
        Log.info("iFirstTestSuit: "+iFirstTestSuit);
        Log.info("iLastTestSuit: "+iLastTestSuit);
    }
    private static void runModuleFlow(int iTestSuit,int iTotalSuite) throws Exception {
        RunTestScriptModule.run(scopePath,iTestSuit,iTotalSuite);
    }
    @Deprecated
    private static void runDataFlow(int iTestSuit,int iTotalSuite) throws Exception {
        RunTestScriptData.run(scopePath,iTestSuit,iTotalSuite);
    }
    private static void returnFlowScrip(){
        String flow = ExcelUtils.getStringValueInCell(1,Constanst.FLOW_COLUM,Constanst.PLAN_SHEET);
        Log.info("FLOW: " +flow);
        if(flow.equals(Constanst.MODULE_FLOW))
            isModuleFlow = true;
        else if(flow.equals(Constanst.DATA_FLOW))
            isDataFlow = true;
    }

    //region KEY
    public static String scopePath;
    private static Class<?> keyWord;
    private static Type type;
    private static Method method[];
    private static RunTestScriptModule runTestScriptModule;
    private static RunTestScriptData runTestScriptData;
    public static boolean isModuleFlow;
    public static boolean isDataFlow;
    public static int iOnceTimeSetUp;
    public static int iOnceTimeTearDown;
    public static int iFirstTestSuit;
    public static int iLastTestSuit;
    //endregion
}
