package execute;

import common.keywords.KeyWordsToActionPocoSDK;
import common.keywords.KeyWordsToActionToVerify;
import common.keywords.KeyWordsToComPair;
import report.GenerateReport;

import java.io.IOException;
import java.lang.reflect.Method;

public class RunTestScriptModule extends TestScrip{
    public RunTestScriptModule(KeyWordsToComPair keyWord, Method method[]){
        super(keyWord, method);
    }
    public static void run(String scopePath,int iTestSuit,int iTotalSuite) throws Exception {
        getLevelFolder(1);
       execute_suites(scopePath,iTestSuit);
       GenerateReport.countResultPlan(scopePath,iTotalSuite);
    }
}
