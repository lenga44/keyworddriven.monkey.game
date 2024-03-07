package common.utility;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class FileHelpers {
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
    public static String convertPath(String path){
        return path.replace("\"","");
    }
    public static void genFolderReport(String folderName) throws IOException {
        File f = new File(folderName);
        if (!f.exists())
            f.mkdirs();
    }

    public static String getAllData(String key) throws IOException {
        return new String(Files.readAllBytes(Paths.get(getRootFolder() +getPathConfig(key))), StandardCharsets.UTF_8);
    }
}
