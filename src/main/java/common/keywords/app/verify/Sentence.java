package common.keywords.app.verify;

import common.keywords.app.Convert;
import common.keywords.app.ExceptionEx;
import common.keywords.app.RequestEx;
import common.utility.Constanst;
import common.utility.LogicHandle;
import io.restassured.response.Response;

import java.util.List;

public class Sentence {
    public static String getSentenceByText(String locators,String component){
        String sentence = null;
        Response response = RequestEx.request(Constanst.SCENE_URL,"//" +locators+"."+component);
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
    public static String getSentenceByText(String locators,String component,String strSplit){
        String sentence = "";
        try {
            Response response = RequestEx.request(Constanst.SCENE_URL, "//" + locators + "." + component);
            List<String> list = Convert.convertToList(response, "text");
            for (String text : list) {
                String str = LogicHandle.replaceStr(text,strSplit);
                sentence = (sentence.equals("")) ? sentence + str : sentence + " " + str;
            }
        }catch (Exception e){
            ExceptionEx.exception("|getSentenceByText| "+e.getMessage());
        }
        return sentence.trim();
    }
}
