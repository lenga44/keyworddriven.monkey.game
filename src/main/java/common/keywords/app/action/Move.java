package common.keywords.app.action;

import common.keywords.app.RequestEx;
import common.utility.Constanst;

public class Move {
    public static void move(String locator1, String locator2){
        Wait.waitForObject(locator1);
        Wait.waitForObject(locator2);
        String absolutePath1 = GetAbsolutePath.getAbsolutePath(locator1,"0");
        String absolutePath2 = GetAbsolutePath.getAbsolutePath(locator2,"0");
        RequestEx.request(Constanst.POINTER_URL, Constanst.MOVE_ACTION + "(" + absolutePath1 + "," + absolutePath2 + ")");
        SleepEx.sleep("1");
    }
    public static void moveAndUp(String locator1, String locator2){
        Wait.waitForObject(locator1);
        Wait.waitForObject(locator2);
        String absolutePath1 = GetAbsolutePath.getAbsolutePath(locator1,"0");
        String absolutePath2 = GetAbsolutePath.getAbsolutePath(locator2,"0");
        RequestEx.request(Constanst.POINTER_URL, Constanst.MOVE_UP_ACTION + "(" + absolutePath1 + "," + absolutePath2 + ")");
        SleepEx.sleep("1");
    }
    public static void moveByCoordinates(String locator1, String number){
        Wait.waitForObject(locator1);
        String absolutePath1 = GetAbsolutePath.getAbsolutePath(locator1,"0");
        RequestEx.request(Constanst.POINTER_URL, Constanst.MOVE_COORDINATE + "(" + absolutePath1 + "," + number + ")");
        SleepEx.sleep("1");
    }
}
