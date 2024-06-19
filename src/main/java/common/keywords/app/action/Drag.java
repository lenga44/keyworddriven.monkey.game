package common.keywords.app.action;

import common.keywords.app.RequestEx;
import common.utility.Constanst;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Drag {

    public static void drag(String locator1, String locator2){
        for(int i = 0; i<2;i++) {
            RequestEx.request(Constanst.POINTER_URL_UNIUM, Constanst.DRAG_ACTION + "(" + locator1 + "," + locator2 + ")");
            SleepEx.sleep("1");
        }
    }
    public static void drag_simulate(String locator1,int index1, String locator2,int index2){
        RequestEx.GET(Constanst.URL_POCO,"drag_to?from="+locator1+"&index_from="+index1+"&to="+locator2+"&index_to="+index2);
        //http://127.0.0.1:6868/drag_to?from=wooden%20tray/LettersContainer/E&index_from=0&to=Wooden%20Table/LettersHolderContainer/E&index_to=0
    }
    public static void dragUp(String locator1, String locator2){
        for(int i = 0; i<2;i++) {
            RequestEx.request(Constanst.POINTER_URL_UNIUM, Constanst.DRAG_UP_ACTION + "(" + locator1 + "," + locator2 + ")");
            SleepEx.sleep("1");
        }
    }
    public static void dragTheLetter(String preLocator1,String preLocator2,String expected){
        int index = 0;
        List<Character> dragged = new ArrayList<>();
        for(char c:expected.toCharArray()){
            if(dragged.contains(c))
                index++;
            String locator1 = URLEncoder.encode(preLocator1+"/"+String.valueOf(c).toUpperCase(), StandardCharsets.UTF_8);
            String locator2 = URLEncoder.encode(preLocator2+"/"+String.valueOf(c).toUpperCase(), StandardCharsets.UTF_8);
            drag_simulate(locator1,index,locator2,index);
            dragged.add(c);
            SleepEx.sleep(3);
        }
    }
}
