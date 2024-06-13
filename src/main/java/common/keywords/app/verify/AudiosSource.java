package common.keywords.app.verify;

import common.utility.Log;
import common.utility.LogicHandle;

public class AudiosSource {
    public static String getAudiosSourceByTime(String locator,String second,String expect){
        Log.info("getAudiosSourceByTime");
        String actual = LogicHandle.replaceStr(LogicHandle.getProValuesByTime(locator,"AudioSource","clip",second,expect,".mp3"),"(UnityEngine.AudioClip)").trim()+".mp3";
        return actual;
    }
    public static String getAudiosSource(String locator,String expect){
        Log.info("getAudiosSourceByTime");
        String actual =LogicHandle.replaceStr(LogicHandle.getProValues(locator,"AudioSource","clip",expect,".mp3"),"(UnityEngine.AudioClip)").trim()+".mp3";
        return actual;
    }
    public static String getAudiosSourceByLocator(String locator,String locator2,String expect){
        Log.info("getAudiosSourceByTime");
        return LogicHandle.replaceStr(LogicHandle.getProValueByLocator(locator,"AudioSource","clip",locator2,expect,".mp3"),"(UnityEngine.AudioClip)").trim()+".mp3";
    }
}
