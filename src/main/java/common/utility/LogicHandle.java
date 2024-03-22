package common.utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LogicHandle {
    public static List<Integer> convertToArrayListInt(String str) {
        try {
            String replace = str;
            if (replace.contains(",") && replace.contains("[")) {
                replace = str.replaceAll("^\\[|]$", "");
                List<String> myList = new ArrayList<String>(Arrays.asList(replace.split(",")));
                List<Integer> list = new ArrayList<>();
                for (String s : myList) {
                    list.add(Integer.valueOf(s));
                }
                return list;
            }
        }catch (Exception e){
            Log.error("|convertToArrayListInt| "+e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    public static List<String> convertToArrayListString(String str){
        String replace = str;
        if (replace.contains(",")&& replace.contains("[")) {
            replace = str.replaceAll("^\\[|]$", "");
            List<String> myList = new ArrayList<String>(Arrays.asList(replace.split(",")));
            return myList;
        }
        return null;
    }
}
