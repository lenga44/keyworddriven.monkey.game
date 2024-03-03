package execute;

import common.keywords.KeyWordsToAction;
import common.keywords.KeyWordsToActionCustom;

import java.io.IOException;
import java.lang.reflect.Method;

public class RunTestScriptModul extends TestScrip{
    public RunTestScriptModul(KeyWordsToActionCustom keyWord, Method method[]){
        super(keyWord, method);
    }
    public static void runNormalFlow(String scopePath) throws IOException {
        execute(scopePath);
    }
}
