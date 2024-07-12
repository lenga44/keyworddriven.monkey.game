package common.keywords.app.custom;

import common.keywords.app.ExceptionEx;
import common.keywords.app.action.Click;
import common.keywords.app.action.TimeScale;
import common.keywords.app.variable.ReturnPath;
import common.keywords.app.verify.AudioSource;
import common.keywords.app.verify.IsElement;
import common.utility.LogicHandle;

import java.time.LocalDateTime;
import java.util.List;

public class DinosaurChasing {
    public static void maxJump(String locator,String replaceStr,String expected){
        try {
            boolean elem = false;
            expected= LogicHandle.replaceStr(expected,replaceStr);
            String[] list = new String[0];
            if(expected.contains("[")) {
                list = LogicHandle.convertToArrayListString(expected, "\"").toArray(new String[0]);
            }
            if(list.length==0){
                list[0]=expected;
            }
            LocalDateTime time = LocalDateTime.now();
            LocalDateTime time1 = time.plusSeconds(60);
            do {
                Click.clickByPositionPoco(locator);
                for (String item : list) {
                    elem = IsElement.elementDisplay(item);
                    if (elem == true) {
                        break;
                    }
                }
                time = LocalDateTime.now();
            } while (time.compareTo(time1) <= 0);
        }catch (Exception e){
            ExceptionEx.exception(e.getMessage());
            e.printStackTrace();
        }
    }
    public static void maxJump(String locator,String expected){
        try {
            boolean elem = true;
            /*ReturnPath.returnPathChild()*/
            LocalDateTime time = LocalDateTime.now();
            LocalDateTime time1 = time.plusSeconds(60);
            do {
                Click.clickByPositionPoco(locator);
                elem = IsElement.elementDisplay(expected);
                if(!elem){
                    break;
                }
                time = LocalDateTime.now();
            } while (!time.isAfter(time1));
        }catch (Exception e){
            ExceptionEx.exception(e.getMessage());
            e.printStackTrace();
        }
    }
}
