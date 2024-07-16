package common.keywords.app.custom;

import common.keywords.app.verify.IsElement;
import common.utility.Constanst;
import common.utility.ExcelUtils;
import common.utility.JsonHandle;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ThreeTapOption {
    private static final List<String> audios = Arrays.asList("Spaceship Take Off", "Food-Chewing-1","BoingsBouncingBoin-AC020801");
    public static void returnPathByAudio() throws IOException {
        System.out.println(audios);
        String path ="";

        for(String audio:audios){
            System.out.println(audio);
            if(audio=="Spaceship Take Off" && IsElement.elementDisplay(audio+"[0]")){
                path = "Blue rocket";
                break;
            }
            if(audio=="Food-Chewing-1" && IsElement.elementDisplay(audio+"[0]")){
                path = "Blue monster";
                break;
            }
            if(audio=="BoingsBouncingBoin-AC020801" && IsElement.elementDisplay(audio+"[0]")){
                path = "Blue firework";
                break;
            }
        }
        JsonHandle.setValueInJsonObject(Constanst.VARIABLE_PATH_FILE,"path",path);
        ExcelUtils.closeFile(Constanst.VARIABLE_PATH_FILE);
    }
}
