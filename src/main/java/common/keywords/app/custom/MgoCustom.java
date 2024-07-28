package common.keywords.app.custom;

import common.keywords.app.ExceptionEx;
import common.keywords.app.action.Click;
import common.keywords.app.action.Drag;
import common.keywords.app.verify.GetElement;
import common.keywords.app.verify.GetText;
import common.utility.Log;
import common.utility.LogicHandle;

import java.util.Collections;
import java.util.List;

public class MgoCustom {
    public static void swipeMapMgo(String locator1,String locator2,String locator,String  component,String expected){
        boolean correct = false;
        int times=0;
        do {
            List<Integer> list = LogicHandle.convertToArrayListInt(GetText.getListTexts(locator, component).toString());
            int lesson = Integer.valueOf(expected);
            if (!list.contains(lesson)) {
                int max = Collections.max(list);
                if (lesson > max) {
                    Drag.drag_simulate(locator1,"0",locator2,"0");
                } else {
                    Drag.drag_simulate(locator2,"0",locator1,"0");
                }
            }else {
                correct = true;
            }
            times++;
        }while (correct == false && times<20);
    }
    public static void clickLesson(String locator1,String locator,String component,String property){
        Log.info("clickLesson "+locator);
        try {
            int number = Integer.parseInt(GetElement.getElements(locator1));
            if (number > 1) {
                Click.click(LogicHandle.replaceStr(locator, "[0]", "[1]"), component, property);
            } else {
                Click.click(locator, component, property);
            }
        }catch (Exception e){
            ExceptionEx.exception("clickLesson "+e.getMessage());
            e.printStackTrace();
        }
    }
}
