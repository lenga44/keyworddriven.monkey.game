package common.keywords;

import common.utility.Log;

public class KeyWordsToComPair extends KeyWordsToActionToVerify{
    public static String comPairImage(String locator,String expect){
        Log.info("comPairImage " +locator+ "_"+expect);
        boolean correct = false;
        boolean show = isElementDisplay(locator);
        if(!expect.equals("[]") && show){
            correct = getImageName(locator).equals(expect);
        }
        if (expect.equals("[]") && !show){
            correct=true;
        }
        return String.valueOf(correct);
    }

}
