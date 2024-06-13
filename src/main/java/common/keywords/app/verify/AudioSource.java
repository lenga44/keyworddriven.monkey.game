package common.keywords.app.verify;

import common.keywords.app.ExceptionEx;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AudioSource {
    public static String getAudioSource(String locator){
        return Common.getPropertyValue(locator,"AudioSource","clip","(UnityEngine.AudioClip)")+".mp3";
    }
    public static String getListAudioSource(String locator,String count) {
        String audio = null;
        ArrayList<String> list = null;
        try {
            LocalDateTime time = LocalDateTime.now();
            LocalDateTime time1 = time.plusSeconds(Integer.valueOf(25));
            list = new ArrayList<>();
            do {
                String value = Common.getPropertyValue(locator, "AudioSource", "clip","(UnityEngine.AudioClip)");
                if (!list.contains(value))
                    list.add(value);
                if (list.size() <= Integer.valueOf(count))
                    break;
                Thread.sleep(10);
                time = LocalDateTime.now();
            } while (time.compareTo(time1) <= 0);
        } catch (Throwable e) {
            ExceptionEx.exception(e);
        }
        return list.toString().replace("[","").replace("]","");
    }
    public static String getListAudioSource(String locator,String count,String audios) {
        String audio = null;
        ArrayList<String> list = null;
        audios = audios.replace("[","").replace("]","");
        List<String> expects = Arrays.stream(audios.split("\\;")).toList();
        try {
            LocalDateTime time = LocalDateTime.now();
            LocalDateTime time1 = time.plusSeconds(25);
            list = new ArrayList<>();
            do {
                String value = Common.getPropertyValue(locator, "AudioSource", "clip");
                if (!list.contains(value))
                    list.add(value);
                if (list.size() <= Integer.valueOf(count))
                    break;
                Thread.sleep(10);
                time = LocalDateTime.now();
            } while (time.compareTo(time1) <= 0);
        } catch (Throwable e) {
            ExceptionEx.exception(e);
        }
        return String.valueOf(expects.contains(list));
    }
}
