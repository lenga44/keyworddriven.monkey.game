package common.keywords.app.action;

import common.keywords.app.ExceptionEx;
import common.utility.Constanst;
import common.utility.FileHelpers;
import common.utility.Log;
import io.restassured.response.Response;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static common.keywords.app.KeyWordsToAction.request;

public class TakePhoto {
    public static byte[] takePhoto(){
        Response response = request(Constanst.TAKE_PHOTO,"");
        return response.asByteArray();
    }
    public static void takePhoto(String path,String name,String folder){
        try {
            byte[] bytes = TakePhoto.takePhoto();
            FileHelpers.genFolderReport(path + "//" + folder);
            Path p =Path.of(path + "//" + folder + "//" + name + ".png");
            System.out.printf(p.toString());
            Files.write(p, bytes);
            SleepEx.sleep(2);
        }catch (Exception e){
            ExceptionEx.exception(e.getMessage());
        }
    }
}
