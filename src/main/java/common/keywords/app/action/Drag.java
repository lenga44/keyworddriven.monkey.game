package common.keywords.app.action;

import common.keywords.app.RequestEx;
import common.keywords.app.verify.Children;
import common.keywords.app.verify.GetElement;
import common.utility.Constanst;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Drag {

    public static void drag(String locator1, String locator2){
        for(int i = 0; i<2;i++) {
            RequestEx.request(Constanst.POINTER_URL_UNIUM, Constanst.DRAG_ACTION + "(" + locator1 + "," + locator2 + ")");
            SleepEx.sleep("1");
            RequestEx.request(Constanst.POINTER_URL_UNIUM, Constanst.DRAG_ACTION + "(" + locator1 + "," + locator2 + ")");
        }
    }
    public static void drag_simulate(String locator1,String index1, String locator2,String index2){
        locator1 = URLEncoder.encode(locator1, StandardCharsets.UTF_8);
        locator2 = URLEncoder.encode(locator2, StandardCharsets.UTF_8);
        RequestEx.GET(Constanst.URL_POCO,"drag_to?from="+locator1+"&index_from="+index1+"&to="+locator2+"&index_to="+index2);
        //http://127.0.0.1:6868/drag_to?from=wooden%20tray/LettersContainer/E&index_from=0&to=Wooden%20Table/LettersHolderContainer/E&index_to=0
    }
    public static void dragUp(String locator1, String locator2){
        for(int i = 0; i<2;i++) {
            RequestEx.request(Constanst.POINTER_URL_UNIUM, Constanst.DRAG_UP_ACTION + "(" + locator1 + "," + locator2 + ")");
            SleepEx.sleep("1");
            RequestEx.request(Constanst.POINTER_URL_UNIUM, Constanst.DRAG_UP_ACTION + "(" + locator1 + "," + locator2 + ")");
        }
    }
    public static void dragTheLetter(String preLocator1,String preLocator2,String expected){
        List<Character> dragged = new ArrayList<>();
        for(char c:expected.toCharArray()){
            int index = Collections.frequency(dragged, c);
            drag_simulate(preLocator1+"/"+String.valueOf(c).toUpperCase(),String.valueOf(index),preLocator2+"/"+String.valueOf(c).toUpperCase(),String.valueOf(index));
            dragged.add(c);
            SleepEx.sleep(3);
        }
    }
    public static void dragTheLetter(String preLocator1,String preLocator2){
        int index = 0;
        List<String> dragged = new ArrayList<>();
        List<String> children = Children.getChildren(preLocator1);
        for(String c:children){
            SleepEx.sleep(5);
            c= c.trim();
            if(dragged.contains(c))
                index++;
            drag_simulate(preLocator1+"/"+c,String.valueOf(index),preLocator2+"/"+c,String.valueOf(index));
            dragged.add(c);
        }
    }
    public static void drag_simulate(String preLocator1,String preLocator2,String expected){
        drag_simulate(preLocator1+expected,"0",preLocator2+expected,"0");
        SleepEx.sleep(3);
    }
    public static void drag_simulate(String preLocator1,String preLocator2,String expected,String index1,String index2){
        drag_simulate(preLocator1+expected,index1,preLocator2+expected,index2);
        SleepEx.sleep(3);
    }
}
