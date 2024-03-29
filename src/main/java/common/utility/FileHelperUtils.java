package common.utility;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class FileHelperUtils {
    public static String getRootFolder(){
        return Arrays.stream(Constanst.PROJECT_PATH.split("\\:")).toList().get(0)+":";
    }
    public static String getPathConfig(String key){
        try {
            String json = new String(Files.readAllBytes(Paths.get(getRootFolder() +Constanst.CONFIG_FILE_PATH)), StandardCharsets.UTF_8);
            JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
            return jsonObject.get(key).toString().replace("\"","");
        } catch (IOException e) {
            Log.error(e.getMessage());
        }
        return null;
    }
}
