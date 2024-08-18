package execute;

import com.aspose.cells.DateTime;
import common.facade.Adapter;
import common.keywords.app.KeyWordsToComPair;
import common.utility.*;
import org.apache.poi.ss.formula.FormulaParser;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Run {

    public static void main(String[] args) throws Exception {
        keyWord = new KeyWordsToComPair();
        method = keyWord.getClass().getMethods();

        Logger formulaParserLogger = Logger.getLogger(FormulaParser.class.getName());
        formulaParserLogger.setLevel(Level.OFF);
        Log.resetFileLog();

        scopePath = FileHelpers.getRootFolder() + FileHelpers.getValueConfig(Constanst.SCOPE_FILE_PATH);
        Log.info("SCOPE_PATH: " + scopePath);
        ExcelUtils.setExcelFile(scopePath);

        String type = ExcelUtils.getStringValueInCell(1,9,Constanst.PLAN_SHEET);
        classes = Adapter.callClass(type);
        returnFlowScrip();
        resetSumarryStatus();

        String start = DateTime.getNow().toString();
        //TelegramBot.sendMessTele("Start__M_GO: " + start);
        FileHelpers.writeFile("", Constanst.LIST_FAIL_PATH_FILE );

        int iTotalSuite = ExcelUtils.getRowCount(Constanst.SCOPE_SHEET);
        Log.info("Total scope : " + iTotalSuite);

        returnSizeTestSuit(iTotalSuite - 1);

        iOnceTimeSetUp = runOneTime(iOnceTimeSetUp);
        runTest();
        iOnceTimeTearDown = runOneTime(iOnceTimeTearDown);
        //EndTestScript.sendMessTelegramEndScrip();
    }
    private static int runOneTime(int iOnceTime) throws Exception {
        Log.info("runOneTime " +iOnceTime);
        if(iOnceTime>0){
            if (isModuleFlow == true) {
                runTestScriptModule = new RunTestScriptModule(keyWord, method,classes);
                runModuleFlow(iOnceTime, iOnceTime);
            }
            if (isDataFlow == true) {
                runTestScriptData = new RunTestScriptData(keyWord, method,classes);
                RunTestScriptData.runOnceTime(scopePath,iOnceTime);
            }
        }
        return 0;
    }
    private static void runTest() throws Exception {
        if (isModuleFlow == true) {
            runTestScriptModule = new RunTestScriptModule(keyWord, method,classes);
            runModuleFlow(iFirstTestSuit, iLastTestSuit);
        }
        if (isDataFlow == true) {
            runTestScriptData = new RunTestScriptData(keyWord, method,classes);
            runDataFlow(iFirstTestSuit, iLastTestSuit);
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
                System.out.println(iLastTestSuit);
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
    private static void resetSumarryStatus(){
        Log.info("Reset pass number");
        ExcelUtils.setCellData(0,1,Constanst.PASS_PLAN_COLUM,Constanst.PLAN_SHEET,scopePath);
        Log.info("Reset fail number");
        ExcelUtils.setCellData(0,1,Constanst.FAIL_PLAN_COLUM,Constanst.PLAN_SHEET,scopePath);
    }

    //region KEY
    public static String scopePath;
    private static KeyWordsToComPair keyWord;
    private static Method method[];
    private static RunTestScriptModule runTestScriptModule;
    private static RunTestScriptData runTestScriptData;
    public static boolean isModuleFlow;
    public static boolean isDataFlow;
    public static int iOnceTimeSetUp;
    public static int iOnceTimeTearDown;
    public static int iFirstTestSuit;
    public static int iLastTestSuit;
    public static Map<Class<?>,Method[]> classes;
    //endregion
}
