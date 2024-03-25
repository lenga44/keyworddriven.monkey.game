package execute;

import common.keywords.KeyWordsToActionPocoSDK;
import report.GenerateReport;

import java.io.IOException;
import java.lang.reflect.Method;

public class RunTestScriptModule extends TestScrip{
    public RunTestScriptModule(KeyWordsToActionPocoSDK keyWord, Method method[]){
        super(keyWord, method);
    }
    public static void run(String scopePath,int iTestSuit,int iTotalSuite) throws Exception {
        getLevelFolder(1);
        reportName = tcName;
       execute_suites(scopePath,iTestSuit,iTotalSuite);
       GenerateReport.countResultPlan(scopePath,iTotalSuite);
    }
}
