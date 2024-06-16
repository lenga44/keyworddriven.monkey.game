package common.keywords.app.action;

import common.utility.Constanst;
import common.utility.FileHelpers;
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
    public static void takePhoto(String path,String folder,String name) throws IOException {
        byte[] bytes = TakePhoto.takePhoto();
        FileHelpers.createFile(path+"//"+folder);
        Files.write(Path.of(path + "//" + folder + "//" + name + ".png"), bytes);
        SleepEx.sleep(2);
    }
}
