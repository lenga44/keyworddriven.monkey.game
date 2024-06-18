package common.keywords.app.action;

import common.keywords.app.RequestEx;
import common.utility.Constanst;

public class Drag {
    public static void drag(String locator1, String locator2){
        for(int i = 0; i<2;i++) {
            RequestEx.request(Constanst.POINTER_URL_UNIUM, Constanst.DRAG_ACTION + "(" + locator1 + "," + locator2 + ")");
            SleepEx.sleep("1");
        }
    }
    public static void dragUp(String locator1, String locator2){
        for(int i = 0; i<2;i++) {
            RequestEx.request(Constanst.POINTER_URL_UNIUM, Constanst.DRAG_UP_ACTION + "(" + locator1 + "," + locator2 + ")");
            SleepEx.sleep("1");
        }
    }
}
