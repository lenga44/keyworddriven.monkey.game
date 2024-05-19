package common.keywords.ui;

import common.utility.Log;

public class KeyWordsToComPair extends KeyWordsToActionToVerify{
    public static String comPairImage(String locator,String expect){
        Log.info("comPairImage " +locator+ "_"+expect);
        boolean correct = false;
        boolean show = isElementDisplay(locator);
        if(!expect.equals("[]") && show){
            System.out.println(getImageName(locator));
            System.out.println(expect);
            correct = getImageName(locator).equals(expect);
        }
        if (expect.equals("[]") && !show){
            correct=true;
        }
        return String.valueOf(correct);
    }
    public static String comPairWordHasImage(String locator,String expect){
        Log.info("comPairImage " +locator+ "_"+expect);
        boolean correct = false;
        boolean show = isElementDisplay(locator);
        if(!expect.equals("[]")){
            System.out.println(getImageName(locator));
            System.out.println(expect);
            correct = getImageName(locator).equals(expect);
        }
        if (expect.equals("[]")){
            correct=true;
        }
        return String.valueOf(correct);
    }
}
