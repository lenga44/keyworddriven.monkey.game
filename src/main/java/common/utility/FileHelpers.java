package common.utility;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.basc.framework.io.FileUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class FileHelpers {
    public static String getRootFolder(){
        return Arrays.stream(Constanst.PROJECT_PATH.split("\\:")).toList().get(0)+":";
    }
    public static String getValueConfig(String key){
        try {
            String json = new String(Files.readAllBytes(Paths.get(getRootFolder() +Constanst.CONFIG_FILE_PATH)), StandardCharsets.UTF_8);
            JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
            return jsonObject.get(key).toString().replace("\"","");
        } catch (IOException e) {
            Log.error(e.getMessage());
        }
        return null;
    }
    public static String getValueConfig(String path,String key){
        try {
            String json = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
            JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
            return jsonObject.get(key).toString().replace("\"","");
        } catch (IOException e) {
            Log.error(e.getMessage());
        }
        return null;
    }
    public static String getValueVariableFile(String key){
        try {
            String json = new String(Files.readAllBytes(Paths.get(Constanst.VARIABLE_PATH_FILE)), StandardCharsets.UTF_8);
            JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
            String value = jsonObject.get(key).getAsString();
            return value.trim();
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
        Log.info(getRootFolder() + getValueConfig(key));
        return new String(Files.readAllBytes(Paths.get(getRootFolder() + getValueConfig(key))), StandardCharsets.UTF_8);
    }
    public static String setJsonVariable(String key, String value) {
        return '"'+key+'"'+":"+'"'+value+'"'+",";
    }
    public static void createFile(String path){
        try {
            File file = new File(path);
            if(!file.exists()){
                file.createNewFile();
            }
        } catch (Throwable e) {
            Log.error(e.getMessage());
        }
    }
    public static void deleteFile(String path){
        try {
            File file = new File(path);
            if(file.exists()){
                file.delete();
            }
        } catch (Throwable e) {
            Log.error(e.getMessage());
        }
    }
    public static void writeFile(String variable,String path){
        try {
            createFile(path);
            FileWriter myObj = new FileWriter (path);
            myObj.write(variable);
            myObj.close();
        } catch (Throwable e) {
            Log.error(e.getMessage());
        }
    }
    public static String readFile(String path){
        String json = "";
        try {
            File file = new File(path);
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();
            json = new String(data, "UTF-8");
        } catch (Throwable e) {
            Log.error(e.getMessage());
        }
        return json;
    }
    public static String readFile(String path,String pass){
        try {
            File file = new File(path);
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();
            return new String(data, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return pass;
        }
    }
    public static void copyFile(String sourceFile, String destFile){
        createFile(destFile);
        try (InputStream in = new FileInputStream(sourceFile);
             OutputStream out = new FileOutputStream(destFile)) {

            byte[] buffer = new byte[1024];
            int length;

            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
            Log.info(" copy Scope success!!!");
        } catch (IOException e) {
            System.err.println("An error occurred while copying the file: " + e.getMessage());
        }
    }
}
