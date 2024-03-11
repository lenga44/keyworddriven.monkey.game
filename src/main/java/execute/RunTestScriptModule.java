package execute;

import common.keywords.KeyWordsToActionPocoSDK;
import report.GenerateReport;

import java.io.IOException;
import java.lang.reflect.Method;

public class RunTestScriptModule extends TestScrip{
    public RunTestScriptModule(KeyWordsToActionPocoSDK keyWord, Method method[]){
        super(keyWord, method);
    }
    public static void run(String scopePath,int iTotalSuite) throws IOException {
       execute(scopePath,iTotalSuite);
       GenerateReport.countResultPlan(scopePath,iTotalSuite);
    }
}
