package common.keywords.app.custom;

import common.keywords.app.Convert;
import common.keywords.app.RequestEx;
import common.keywords.app.verify.GetText;
import common.utility.Constanst;
import common.utility.Log;
import common.utility.LogicHandle;
import io.restassured.response.Response;

import java.util.List;

public class WordMachine {
    public static String getSentenceWordMachine(String parent,String remove,String component,String right){
        String sentence = "";
        try {
            Response response = RequestEx.request(Constanst.SCENE_URL_UNIUM, "//" + parent);
            List<String> children = LogicHandle.convertStringToList(Convert.convert(response, "children"));
            for (String child : children) {
                child = child.trim();
                if (!child.equals(remove)) {
                    String text = LogicHandle.getTextNoColor(GetText.getText(child, component),"</color>",">");
                    sentence = LogicHandle.convertTextToSentence(sentence, text);
                } else {
                    sentence = LogicHandle.convertTextToSentence(sentence, right);
                }
                System.out.println(sentence);
            }
        }catch (Exception e){
            Log.error(e.getMessage());
            e.printStackTrace();
        }
        return sentence;
    }
}
