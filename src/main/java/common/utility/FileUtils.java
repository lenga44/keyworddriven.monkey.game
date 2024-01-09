package common.utility;

import java.util.Arrays;

public class FileUtils {
    public static String getRootFolder(){
        return Arrays.stream(Constanst.PROJECT_PATH.split("\\:")).toList().get(0);
    }
}
