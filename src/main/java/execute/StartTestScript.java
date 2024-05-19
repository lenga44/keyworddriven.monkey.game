package execute;

import com.aspose.cells.DateTime;
import common.keywords.api.KeyWordsApiToAction;
import common.keywords.api.KeyWordsApiToAssert;
import common.keywords.app.KeyWordsToComPair;
import common.utility.*;
import org.apache.poi.ss.formula.FormulaParser;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StartTestScript {
    public static void logging() throws IOException {
        Logger formulaParserLogger = Logger.getLogger(FormulaParser.class.getName());
        formulaParserLogger.setLevel(Level.OFF);
        Log.resetFileLog();
    }
    public static void sendMessageStartTest(){
        String start = DateTime.getNow().toString();
        TelegramBot.sendMessTele("Start: " + start);
        FileHelpers.writeFile("", Constanst.LIST_FAIL_PATH_FILE );
    }
    public static Method[] getMethods() throws IOException {
        String typeName = ExcelUtils.getStringValueInCell(1,Constanst.TYPE_SCRIPT_COLUM,Constanst.PLAN_SHEET);
        String className;
        switch (typeName){
            case "app":
                className = new KeyWordsToComPair().getClass().getName();
                break;
            case "api":
                className = new KeyWordsApiToAction().getClass().getName();
                break;
            default:
                throw new RuntimeException("get methods is null");
        }
        Log.info("KEYWORD IS "+className);
        Type type = new Type(typeName,className);
        Class<?> keyWord = type.genType();
        return keyWord.getClass().getMethods();
    }
}
