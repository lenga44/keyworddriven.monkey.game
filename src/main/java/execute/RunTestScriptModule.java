package execute;

import common.keywords.app.KeyWordsToComPair;
import report.GenerateReport;

import java.lang.reflect.Method;
import java.util.Map;

public class RunTestScriptModule extends TestScrip{
    public RunTestScriptModule(KeyWordsToComPair keyWord, Method[] method, Map<Class<?>,Method[]> classes){
        super(keyWord, method,classes);
    }
    public static void run(String scopePath,int iTestSuit,int iTotalSuite) throws Exception {
        getLevelFolder(1);
       execute_suites(scopePath,iTestSuit);
       /*GenerateReport.countResultPlan(scopePath,iTotalSuite);*/
    }
}
