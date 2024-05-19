package common.keywords.ui;

import common.utility.FileHelpers;

import java.io.IOException;

public class KeyWordsToActionPocoSDK{
    public static void clickInput(){

    }
    public static void swipeInput() throws IOException {
        try{
            ProcessBuilder builder = new ProcessBuilder("python", FileHelpers.getRootFolder()+ "\\keyworddriven.monkey.game\\src\\main\\java\\simulate_py\\swipe.py"
                    ,"7cbc1b6a",FileHelpers.getRootFolder(),"1000","400","400","400");
            Process process = builder.start();
        }catch (Exception e){
            e.getMessage();
            e.printStackTrace();
        }
    }
}
