package common.keywords.app.action;

import common.keywords.app.RequestEx;
import common.utility.Constanst;

public class Swipe {
    public static void swipe(String x1, String x2, String y,String number){
        int loop = Integer.valueOf(number);
        if(loop!=0) {
            for(int i=0;i<loop;i++) {
                RequestEx.request(Constanst.SIMULATE_URL, Constanst.DRAG_ACTION + "(" + x1 + "," + y + "," + x2 + "," + y + ",0.5)");
            }
        }
    }
    /*public static void swipeToRight(String number){
        for(int i = 0; i<Integer.valueOf(number);i++){
            request(Constanst.SIMULATE_URL,Constanst.DRAG_ACTION + "(500,750,500,800,0.5)");
        }
    }*/
    public static void swipeToRight(String x1, String x2, String y){
        RequestEx.request(Constanst.SIMULATE_URL,Constanst.DRAG_ACTION + "("+x2+","+y+","+x1+","+y+",0.5)");
    }
    public static void swipeToDown(String number){
        for(int i = 0; i<Integer.valueOf(number);i++){
            RequestEx.request(Constanst.SIMULATE_URL,Constanst.DRAG_ACTION + "(400,500,100,100,0.5)");
            SleepEx.sleep("1");
        }
    }
}
