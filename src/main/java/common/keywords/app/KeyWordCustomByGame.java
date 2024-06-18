package common.keywords.app;

import common.utility.*;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;

import java.io.IOException;
import java.time.LocalDateTime;

import static common.keywords.app.KeyWordsToAction.*;

public class KeyWordCustomByGame {
    //region Dien the
    public static void deFindAnswer(String locator,String component,String property,String expect,String strReplace,String strAdd,String locator1) throws IOException {
        Log.info("Dien the choose image or text");
        Response response = request(Constanst.SCENE_URL_UNIUM,"//"+locator+"."+component);
        String value = KeyWordsToAction.convertNotNull(response,property);
        if(!strReplace.equals("")){
            if(value.contains(strReplace)){
                value = value.replace(strReplace,"");
            }
        }
        if(!strAdd.equals("")){
            value = value+strAdd;
        }
        if(expect.contains(value)&& !value.equals("")){
            JsonHandle.setValueInJsonObject(Constanst.VARIABLE_PATH_FILE,"path_type_dien_the",locator);
            JsonHandle.setValueInJsonObject(Constanst.VARIABLE_PATH_FILE,"type_dien_the","image");
        }else{
            JsonHandle.setValueInJsonObject(Constanst.VARIABLE_PATH_FILE,"path_type_dien_the",locator1);
            JsonHandle.setValueInJsonObject(Constanst.VARIABLE_PATH_FILE,"type_dien_the","text");
        }
        ExcelUtils.closeFile(Constanst.VARIABLE_PATH_FILE);
    }
    public static void waitForFrameVideo(String locator,String second,String frame){
        int frameNumber = Integer.parseInt(frame);
        String property = "frame";
        try {
            LocalDateTime time = LocalDateTime.now();
            LocalDateTime time1 = time.plusSeconds(Integer.parseInt(second));
            Response response = null;
            String value = null;
            do {
                response = request(Constanst.SCENE_URL_UNIUM, "//" + locator+".VideoPlayer");
                if(response!=null) {
                    if (!convertToList(response, property).isEmpty()) {
                        JsonPath json = response.jsonPath();
                        if (json != null && json.toString() != "") {
                            value = convert(response, property);
                            if (value != null) {
                                if (value.contains(frame))
                                    break;
                            }
                            Thread.sleep(500);
                        }
                    }
                }
                time = LocalDateTime.now();
            } while (time.compareTo(time1) <= 0);
            Assert.assertTrue(value.contains(frame));
        }catch (Throwable e){
            exception(e);
        }
        Log.info("waitForObjectContain :" + locator);
    }
    //endregion
}
