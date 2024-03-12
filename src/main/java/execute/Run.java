package execute;

import common.keywords.KeyWordsToActionPocoSDK;
import common.utility.Constanst;
import common.utility.ExcelUtils;
import common.utility.FileHelpers;
import common.utility.Log;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.lang.reflect.Method;

public class Run {

    public static void main(String[] args) throws IOException, ParseException {

        keyWord = new KeyWordsToActionPocoSDK();
        method = keyWord.getClass().getMethods();

        Log.resetFileLog();

        scopePath = FileHelpers.getRootFolder() + FileHelpers.getValueConfig(Constanst.SCOPE_FILE_PATH);
        Log.info("SCOPE_PATH: "+scopePath);

        ExcelUtils.setExcelFile(scopePath);
        returnFlowScrip();

        int iTotalSuite = ExcelUtils.getRowCount(Constanst.SCOPE_SHEET);
        Log.info("Total scope : "+iTotalSuite);

        returnSizeTestSuit(iTotalSuite-1);

        runOneTime(iOnceTimeSetUp);
        if (isModuleFlow == true) {
            runTestScriptModule = new RunTestScriptModule(keyWord, method);
            runModuleFlow(iFirstTestSuit,iLastTestSuit);
        }
        if(isDataFlow == true){
            runTestScriptData = new RunTestScriptData(keyWord,method);
            runDataFlow(iFirstTestSuit,iLastTestSuit);
        }
        runOneTime(iOnceTimeTearDown);
    }
    private static void runOneTime(int iOnceTime) throws IOException {
        Log.info("runOneTime " +iOnceTime);
        if(iOnceTime>0){
            TestScrip.execute_suites(scopePath,iOnceTime,iOnceTime);
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
    private static void runModuleFlow(int iTestSuit,int iTotalSuite) throws IOException {
        runTestScriptModule.run(scopePath,iTestSuit,iTotalSuite);
    }
    @Deprecated
    private static void runDataFlow(int iTestSuit,int iTotalSuite) throws IOException, ParseException {
        runTestScriptData.run(scopePath,iTestSuit,iTotalSuite);
    }
    private static void returnFlowScrip(){
        String flow = ExcelUtils.getStringValueInCell(1,Constanst.FLOW_COLLUM,Constanst.PLAN_SHEET);
        Log.info("FLOW: " +flow);
        if(flow.equals(Constanst.MODULE_FLOW))
            isModuleFlow = true;
        else if(flow.equals(Constanst.DATA_FLOW))
            isDataFlow = true;
    }
    //region KEY

    public static String scopePath;
    private static KeyWordsToActionPocoSDK keyWord;
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
