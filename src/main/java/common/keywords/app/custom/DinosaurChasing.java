package common.keywords.app.custom;

import common.keywords.app.ExceptionEx;
import common.keywords.app.action.Click;
import common.keywords.app.verify.AudioSource;
import common.keywords.app.verify.IsElement;
import common.utility.LogicHandle;

import java.time.LocalDateTime;

public class DinosaurChasing {
    public static void maxJump(String locator,String replaceStr,String expected){
        try {
            expected= LogicHandle.replaceStr(expected,replaceStr);
            LocalDateTime time = LocalDateTime.now();
            LocalDateTime time1 = time.plusSeconds(60);
            do {
                Click.clickByPositionPoco(locator);
                boolean elem = IsElement.elementDisplay(expected);
                time = LocalDateTime.now();
                if (elem == true) {
                    break;
                }
            } while (time.compareTo(time1) <= 0);
        }catch (Exception e){
            ExceptionEx.exception(e.getMessage());
            e.printStackTrace();
        }
    }
}
