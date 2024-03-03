package execute;

import common.keywords.KeyWordsToAction;
import common.keywords.KeyWordsToActionCustom;
import common.utility.Constanst;
import common.utility.ExcelUtils;
import common.utility.FileHelperUtils;
import common.utility.Log;

import java.io.IOException;
import java.lang.reflect.Method;

public class Run {
    public static String scopePath;
    public static KeyWordsToActionCustom keyWord;
    public static Method method[];
    public static void main(String[] args) throws IOException {
        keyWord = new KeyWordsToActionCustom();
        method = keyWord.getClass().getMethods();

        Log.resetFileLog();

        scopePath = FileHelperUtils.getRootFolder() + FileHelperUtils.getPathConfig(Constanst.SCOPE_FILE_PATH);
        Log.info("SCOPE_PATH: "+scopePath);

        ExcelUtils.setExcelFile(scopePath);
        RunTestScriptModul runTestScriptModul = new RunTestScriptModul(keyWord,method);
        runTestScriptModul.execute(scopePath);
    }
}
