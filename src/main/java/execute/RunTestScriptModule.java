package execute;

import common.keywords.KeyWordsToAction;
import common.keywords.KeyWordsToActionCustom;
import common.utility.Log;
import report.GenerateReport;

import java.io.IOException;
import java.lang.reflect.Method;

public class RunTestScriptModule extends TestScrip{
    public RunTestScriptModule(KeyWordsToActionCustom keyWord, Method method[]){
        super(keyWord, method);
    }
    public static void run(String scopePath,int iTotalSuite) throws IOException {
       execute(scopePath,iTotalSuite);
       GenerateReport.countResultPlan(scopePath,iTotalSuite);
    }
}
