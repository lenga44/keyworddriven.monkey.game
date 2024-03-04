package execute;

import common.keywords.KeyWordsToAction;
import common.keywords.KeyWordsToActionCustom;

import java.io.IOException;
import java.lang.reflect.Method;

public class RunTestScriptModule extends TestScrip{
    public RunTestScriptModule(KeyWordsToActionCustom keyWord, Method method[]){
        super(keyWord, method);
    }
    public static void run(String scopePath,int iTotalSuite) throws IOException {
        //onceTimeScrip(1);
        execute(scopePath,2,iTotalSuite);
        //onceTimeScrip(iTotalSuite);
    }
}
