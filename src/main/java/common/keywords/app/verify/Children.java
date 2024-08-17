package common.keywords.app.verify;

import common.keywords.app.Convert;
import common.utility.LogicHandle;
import io.restassured.response.Response;

import java.util.List;

public class Children {
    public static String getChild(String locator,String index){
        List<String> list =  getChildren(locator);
        System.out.println(list);
        return list.get(Integer.parseInt(index)).trim();
    }
    public static List<String> getChildren(String locator){
        Response response = GetElement.getElement(locator);
        return LogicHandle.convertStringToList(Convert.convert(response,"children"));
    }
}
