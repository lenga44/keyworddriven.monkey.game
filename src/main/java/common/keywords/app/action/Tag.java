package common.keywords.app.action;

import common.keywords.app.RequestEx;
import common.utility.Constanst;

public class Tag {
    public static void addTagForObject(String locator,String tag){
        RequestEx.request(Constanst.SCENE_URL_UNIUM,"//"+locator+".tag="+tag);
    }
}
