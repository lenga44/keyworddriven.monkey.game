package execute;

import common.keywords.KeyWordsToActionCustom;

import java.io.IOException;
import java.lang.reflect.Method;

public class RunTestScriptData extends TestScrip{
    public RunTestScriptData(KeyWordsToActionCustom keyWord, Method method[]){
        super(keyWord, method);
    }
    public static void run(String scopePath, int iTotalFeature) throws IOException {
        //execute(scopePath,iTotalFeature);
    }
}
