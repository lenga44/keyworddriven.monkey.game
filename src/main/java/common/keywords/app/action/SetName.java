package common.keywords.app.action;

import common.keywords.app.RequestEx;
import common.utility.Constanst;

public class SetName {
    public static void setNameGoes(String locator,String text){
        for (char c: text.toCharArray()) {
            RequestEx.request(Constanst.SCENE_URL_UNIUM, "//" + locator+"/"+c + ".name="+String.valueOf(c).toUpperCase());
        }
    }
}
