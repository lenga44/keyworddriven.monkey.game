package common.keywords.app.verify;

import common.keywords.app.Convert;
import common.keywords.app.ExceptionEx;
import common.keywords.app.KeyWordsToAction;
import common.keywords.app.RequestEx;
import common.utility.Constanst;
import common.utility.LogicHandle;
import io.restassured.response.Response;

import java.util.List;

public class Sentence {
    public static String getSentenceByText(String locators,String component){
        String sentence = "";
        Response response = RequestEx.request(Constanst.SCENE_URL_UNIUM,"//" +locators+"."+component);
        List<String> list = Convert.convertToList(response,"text");
        for (String text: list) {
            if(text.matches("^[a-z0-9A-Z]{2,25}$")){
                sentence = sentence + text +" ";
            }else {
                sentence = sentence + text +" ";
            }
        }
        return sentence;
    }
    public static String compareSentenceByText(String locators,String component,String expected){
        String sentence = "";
        Response response = RequestEx.request(Constanst.SCENE_URL_UNIUM,"//" +locators+"."+component);
        List<String> list = Convert.convertToList(response,"text");
        for (String text: list) {
            if(text.matches("^[a-z0-9A-Z]{2,25}$")){
                sentence = sentence + text +" ";
            }else {
                sentence = sentence + text +" ";
            }
        }
        System.out.println(LogicHandle.getTextAlphabet(sentence));
        System.out.println(LogicHandle.getTextAlphabet(expected));
        return String.valueOf(LogicHandle.getTextAlphabet(expected).trim().contains(LogicHandle.getTextAlphabet(sentence).trim()));
    }
    public static String getSentenceByText(String locators,String component,String... strSplit){
        String sentence = "";
        try {
            Response response = RequestEx.request(Constanst.SCENE_URL_UNIUM, "//" + locators + "." + component);
            List<String> list = Convert.convertToList(response, "text");
            for (String text : list) {
                sentence = (sentence.equals("")) ? sentence + text : sentence + " " + text;
            }
            for (String str: strSplit){
                sentence = LogicHandle.removeString(sentence,str);
            }
        }catch (Exception e){
            ExceptionEx.exception("|getSentenceByText| "+e.getMessage());
        }
        return sentence.trim();
    }
    public static String getTextContainSentence(String sentence, String text){
        return String.valueOf(sentence.contains(text));
    }
    public static String verifySentence(String locator,String component,String text,String sentence){
        List<String> actuals = LogicHandle.convertStringToList(LogicHandle.getTextAlphabet(getSentenceByText(locator,component)));
        actuals.add(text);
        return String.valueOf(LogicHandle.convertStringToList(LogicHandle.getTextAlphabet(sentence)).contains(actuals));
    }
}
