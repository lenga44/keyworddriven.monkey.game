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


        if (isModuleFlow == true) {
            runTestScriptModule = new RunTestScriptModule(keyWord, method);
            runModuleFlow(iTotalSuite);
        }
        if(isDataFlow == true){
            runTestScriptData = new RunTestScriptData(keyWord,method);
            runDataFlow(iTotalSuite);
        }
    }


    private static void runModuleFlow(int iTotalSuite) throws IOException {
        runTestScriptModule.run(scopePath,iTotalSuite);
    }
    @Deprecated
    private static void runDataFlow(int iTotalSuite) throws IOException, ParseException {
        runTestScriptData.run(scopePath,iTotalSuite);
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
    //endregion
}
