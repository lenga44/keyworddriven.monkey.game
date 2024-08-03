package common.keywords.app.verify;

import common.keywords.app.ExceptionEx;
import common.utility.Constanst;
import common.utility.LogicHandle;
import execute.TestScrip;
import org.testng.Assert;

import java.util.List;

public class Check {
    public static void check(String actual,String expect){
        actual = LogicHandle.replaceStr(actual,"\"");
        expect = LogicHandle.replaceStr(expect,"\"");

        TestScrip.result = Constanst.PASS;
        TestScrip.error = "";
        try{
            if(expect.contains("[")||actual.contains("[")
            || actual.contains(";")) {
                if(expect.contains("[")&& actual.contains("[")) {
                    assertEqual(LogicHandle.convertStringToList(actual), LogicHandle.convertStringToList(expect));
                }else {
                    if (expect.contains("[")) {
                        assertEqual(actual, LogicHandle.convertStringToList(expect));
                    } else {
                        assertEqual(expect, LogicHandle.convertStringToList(actual));
                    }
                }
            }else {
                assertEqual(actual, expect);
            }
        }catch (Throwable e){
            ExceptionEx.exception("expect ["+expect.toString()+"] but found ["+actual.toString()+"]");
        }
    }
    public static void checkContain(String actual,String expect){
        TestScrip.result = Constanst.PASS;
        TestScrip.error = "";
        try{
            if(expect.length()<actual.length()) {
                assertContain(actual, expect);
            }else if(expect.length() == actual.length()) {
                assertEqual(expect,actual);
            }else {
                assertContain(expect,actual);
            }
        }catch (Throwable e){
            ExceptionEx.exception("expect ["+expect+"] but found ["+actual+"]");
        }
    }
    public static void assertEqual(String actual,String expect){
        Assert.assertEquals(actual,expect);
    }
    private static void assertEqual(List<String> actual, List<String> expect){
        boolean corect = true;
        for (String item:expect){
            if(!actual.contains(item)){
                corect=false;
                break;
            }
        }
        Assert.assertEquals(corect,true);
    }
    private static void assertEqual(String actual, List<String> expect){
        Assert.assertTrue(expect.contains(actual));
    }
    private static void assertContain(String actual,String expect){
        Assert.assertTrue(actual.contains(expect));
    }
}
