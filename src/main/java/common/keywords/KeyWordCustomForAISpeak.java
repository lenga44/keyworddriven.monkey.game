package common.keywords;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import common.utility.*;
import execute.RunTestScriptData;
import execute.TestScrip;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class KeyWordCustomForAISpeak {

    public static void returnChooseTopic(String sheetName, String from, String to,String exception, String part) throws IOException {
        try {
            String result = Constanst.FAIL;
            /*try {
                result  = KeyWordsToActionToVerify.isElementDisplay(locator);
            }catch (Throwable e){
                result = "false";
            }*/
            if(part.equals("1")){
                result = Constanst.TRUE;
            }
            String path = TestScrip.reportPath;
            if (result.equals(Constanst.TRUE)) {
                ExcelUtils.setCellData(Constanst.NO, Integer.valueOf(exception), Constanst.RUN_MODE_TEST_CASE, sheetName, TestScrip.reportPath);
            } else {
                ExcelUtils.setCellData(Constanst.YES, Integer.valueOf(exception), Constanst.RUN_MODE_TEST_CASE, sheetName, TestScrip.reportPath);
            }
            for (int i = Integer.valueOf(from); i <= Integer.valueOf(to); i++) {
                if (result.equals(Constanst.TRUE)) {
                    ExcelUtils.setCellData(Constanst.YES, i, Constanst.RUN_MODE_TEST_CASE, sheetName, TestScrip.reportPath);
                } else {
                    ExcelUtils.setCellData(Constanst.NO, i, Constanst.RUN_MODE_TEST_CASE, sheetName, TestScrip.reportPath);
                }
            }
            ExcelUtils.closeFile(path);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    public static void returnChooseTopic(String sheetName, String from, String to, String part) throws IOException {
        try {
            String result;
            if(part.equals("1")){
                result = Constanst.TRUE;
            } else {
                result = Constanst.FAIL;
            }
            String path = TestScrip.reportPath;
                try {
                    for (int i = Integer.valueOf(from); i <= Integer.valueOf(to); i++) {
                        if (result.equals(Constanst.TRUE)) {
                            ExcelUtils.setCellData(Constanst.YES, i, Constanst.RUN_MODE_TEST_CASE, sheetName, TestScrip.reportPath);
                        } else {
                            ExcelUtils.setCellData(Constanst.NO, i, Constanst.RUN_MODE_TEST_CASE, sheetName, TestScrip.reportPath);
                        }
                    }
                }catch (Exception e){

                }
            ExcelUtils.closeFile(path);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    public static void returnChooseTopic( String part) {
        try {
            String result = Constanst.FAIL;
            if(part.equals("1")){
                result = Constanst.TRUE;
            }
            String variable = FileHelpers.readFile(Constanst.VARIABLE_PATH_FILE);
            String value = "";
            if(variable.equals("")){
                variable = "{'"+Constanst.RUN_MODE_TC_VAR+"':'"+result+"'}";
            }else {
                variable = variable.replace("}","");
                variable += ",\n'"+Constanst.RUN_MODE_TC_VAR+"':'"+result+"'}";
            }
            FileHelpers.writeFile(variable,Constanst.VARIABLE_PATH_FILE);
            ExcelUtils.closeFile(Constanst.VARIABLE_PATH_FILE);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    public static void setRunModeTC(String from, String to,String sheetName) throws IOException {
        String result = FileHelpers.getValueVariableFile(Constanst.RUN_MODE_TC_VAR);
        for (int i = Integer.valueOf(from); i <= Integer.valueOf(to); i++) {
            if (result.equals(Constanst.TRUE)) {
                ExcelUtils.setCellData(Constanst.YES, i, Constanst.RUN_MODE_TEST_CASE, sheetName, TestScrip.reportPath);
            } else {
                ExcelUtils.setCellData(Constanst.NO, i, Constanst.RUN_MODE_TEST_CASE, sheetName, TestScrip.reportPath);
            }
        }
        ExcelUtils.closeFile(TestScrip.reportPath);
    }
    public static void deFindModeRunTestCase(String key,String sheetName,String from, String to)  {
        try{
            ExcelUtils.setExcelFile(TestScrip.reportPath);
            ExecutorService executor = Executors.newFixedThreadPool(Integer.valueOf(to) - Integer.valueOf(from));
            String result = getDeFind(key);
            for (int i = Integer.valueOf(from); i <= Integer.valueOf(to); i++) {
                    Runnable runnable = new InsertMultiExcel(result, i, Constanst.RUN_MODE_TEST_CASE, sheetName, TestScrip.reportPath);
                    executor.execute(runnable);
            }
            executor.shutdown();
            ExcelUtils.saveFile(TestScrip.reportPath);
            ExcelUtils.closeFile(TestScrip.reportPath);
        }catch (Exception e){
            Log.error("deFindModeRunTestCase: "+ e.getMessage());
        }
    }
   /* public static void deFindModeRunTestCase(String key,String sheetName,String from, String to)  {
        try{
            String result = getDeFind(key);
            for (int i = Integer.valueOf(from); i <= Integer.valueOf(to); i++) {
                ExcelUtils.setCellData(result, i, Constanst.RUN_MODE_TEST_CASE, sheetName, TestScrip.reportPath);
            }
        }catch (Exception e){
            Log.error("deFindModeRunTestCase: "+ e.getMessage());
        }
    }*/
    public static void deFindModeRunTestCase(String key,String sheetName,String from, String to,String exception)  {
        String result = getDeFind(key);
        if(result.equals(Constanst.YES)){
            ExcelUtils.setCellData(Constanst.NO, Integer.valueOf(exception), Constanst.RUN_MODE_TEST_CASE, sheetName, TestScrip.reportPath);
        }else {
            ExcelUtils.setCellData(Constanst.YES, Integer.valueOf(exception), Constanst.RUN_MODE_TEST_CASE, sheetName, TestScrip.reportPath);
        }
        for (int i = Integer.valueOf(from); i <= Integer.valueOf(to); i++) {
            ExcelUtils.setCellData(result, i, Constanst.RUN_MODE_TEST_CASE, sheetName, TestScrip.reportPath);
        }
    }
    public static void deFindModeRunTestCase(String key,String sheetName,String from, String to,String exception,String... exception1)  {
        String result = getDeFind(key);
        if(result.equals(Constanst.YES)){
            ExcelUtils.setCellData(Constanst.NO, Integer.valueOf(exception), Constanst.RUN_MODE_TEST_CASE, sheetName, TestScrip.reportPath);
        }else {
            ExcelUtils.setCellData(Constanst.YES, Integer.valueOf(exception), Constanst.RUN_MODE_TEST_CASE, sheetName, TestScrip.reportPath);
            for (String ex:exception1) {
                ExcelUtils.setCellData(Constanst.NO, Integer.valueOf(ex), Constanst.RUN_MODE_TEST_CASE, sheetName, TestScrip.reportPath);
            }
        }
        for (int i = Integer.valueOf(from); i <= Integer.valueOf(to); i++) {
            ExcelUtils.setCellData(result, i, Constanst.RUN_MODE_TEST_CASE, sheetName, TestScrip.reportPath);
        }
    }
    private static String getDeFind(String key){
        String result = Constanst.NO;
        try {
            JsonObject jsonObject = JsonHandle.getObject(RunTestScriptData.json);
            Set<Map.Entry<String, JsonElement>> entries = jsonObject.entrySet();
            for (Map.Entry<String, JsonElement> entry: entries) {
                if(entry.getKey().equals(key)){
                    result = Constanst.YES;
                    break;
                }
            }
        }catch (Exception e){
            result = Constanst.NO;
            Log.error("getDeFind "+e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
    public static void returnModeTC(String sheetName, String to, String expected, String part) {
        try {
            String result = Constanst.TRUE;
            boolean compare = false;
            /*try {
                result  = KeyWordsToActionToVerify.isElementDisplay(locator);
            }catch (Throwable e){
                result = "false";
            }*/
            if(part.length()>=expected.length()){
                compare = true;
            }
            if(compare==true) {
                if (part.contains(expected)) {
                    result = Constanst.FAIL;
                }
            }else {
                if (expected.contains(part)) {
                    result = Constanst.FAIL;
                }
            }
            String path = TestScrip.reportPath;
            if (result.equals(Constanst.TRUE)) {
                ExcelUtils.setCellData(Constanst.YES, Integer.parseInt(to), Constanst.RUN_MODE_TEST_CASE, sheetName, TestScrip.reportPath);
            } else {
                ExcelUtils.setCellData(Constanst.NO, Integer.parseInt(to), Constanst.RUN_MODE_TEST_CASE, sheetName, TestScrip.reportPath);
            }
            ExcelUtils.closeFile(path);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    public static void ignoreScript(String number,String to,String sheetName, String text){
        if(text.length()<=Integer.valueOf(number)){
            ExcelUtils.setCellData(Constanst.NO, Integer.parseInt(to), Constanst.RUN_MODE_TEST_CASE, sheetName, TestScrip.reportPath);
        }
    }
}
