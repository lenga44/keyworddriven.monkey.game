package execute;

import common.keywords.KeyWordsToActionCustom;
import common.utility.Constanst;
import common.utility.ExcelUtils;
import common.utility.FileHelperUtils;
import common.utility.Log;

import java.io.IOException;
import java.lang.reflect.Method;

public class Run {

    public static void main(String[] args) throws IOException {
        keyWord = new KeyWordsToActionCustom();
        method = keyWord.getClass().getMethods();

        Log.resetFileLog();

        scopePath = /*FileHelperUtils.getRootFolder() + */FileHelperUtils.getPathConfig(Constanst.SCOPE_FILE_PATH);
        Log.info("SCOPE_PATH: "+scopePath);

        ExcelUtils.setExcelFile(scopePath);
        returnFlowScrip();

        int iTotalSuite = ExcelUtils.getRowCount(Constanst.SCOPE_SHEET);
        Log.info("Total scope : "+iTotalSuite);

        runTestScriptModule = new RunTestScriptModule(keyWord,method);
        if(isModuleFlow==true)
            runModule(iTotalSuite);

    }


    private static void runModule(int iTotalSuite) throws IOException {
        runTestScriptModule.run(scopePath,iTotalSuite);
    }
    private static void returnFlowScrip(){
        String flow = ExcelUtils.getCellData(1,Constanst.FLOW_COLLUM,Constanst.PLAN_SHEET);
        if(flow.equals(Constanst.MODULE_FLOW))
            isModuleFlow = true;
        else if(flow.equals(Constanst.DATA_FLOW))
            isDataFlow = true;
    }
    //region KEY

    private static String scopePath;
    private static KeyWordsToActionCustom keyWord;
    private static Method method[];
    private static RunTestScriptModule runTestScriptModule;
    public static boolean isModuleFlow;
    public static boolean isDataFlow;
    //endregion
}
