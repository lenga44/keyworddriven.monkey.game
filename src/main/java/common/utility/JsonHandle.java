package common.utility;

import com.jayway.jsonpath.JsonPath;
import java.io.IOException;

public class JsonHandle {
    public static void main(String[] args) throws IOException {
        String s = FileHelpers.getAllData(Constanst.DATA_FILE_PATH);

        Object id = JsonPath.read(s, "$.Page[0].Id");
        String j = id.toString();
        System.out.println(j);

    }

}
