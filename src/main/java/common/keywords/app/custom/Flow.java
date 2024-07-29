package common.keywords.app.custom;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import common.keywords.app.ExceptionEx;
import common.utility.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

import static common.keywords.app.verify.Check.check;
import static execute.TestScrip.flow;
import static execute.TestScrip.json;

public class Flow {
//    public static void verifyFlow(String flowExpected, String unitHasFlow,String level,String lessonName){
//        String flowExpectedJson = FileHelpers.readFile(Constanst.DATA_FOLDER_PATH + flowExpected);
//        String unitHasFlowJson = FileHelpers.readFile(Constanst.DATA_FOLDER_PATH + unitHasFlow);
//        int flowIndex = 0;
//        if(level.equals("Level 0")){
//            flowIndex = 2;
//        }
//
//        for(String game: flow){
//
//        }
//    }
    public static String verifyFlow(String flowExpected){
        boolean correct = false;
        int number = 0;
        try {
            String flowExpectedJson = FileHelpers.readFile(Constanst.DATA_FOLDER_PATH + flowExpected);
            String lesson = JsonHandle.getValue(json, "$.lesson");
            String flowIndex = JsonHandle.getValue(json, "$.flow");
            String level = JsonHandle.getValue(json, "$.level");
            String topic = JsonHandle.getValue(json, "$.category");
            if(level.equals("Level 0")){
                number = Integer.valueOf(LogicHandle.getNumber(lesson).trim())%5;
                if(number==0){
                    number = 5;
                }
            }else {
                number = Integer.valueOf(LogicHandle.getNumber(lesson).trim())%7;
                if(number==0){
                    number = 7;
                }
            }

            JSONArray actName = JsonHandle.getJsonArray(flowExpectedJson, "$.flow[?(@.name==" + flowIndex + ")].lesson[?(@.name_lesson=='Lesson " + number + "')].act[*].game");
            Map<Integer, List<String>> map = new HashMap<>();
            int i = 0;
            for (Object item : actName) {
                String game = LogicHandle.removeString(item.toString(),"\"");
                map.put(i, LogicHandle.convertStringToList(game));
                i++;
            }
            for (int index = 0; index < map.keySet().size(); index++) {
                correct = (map.get(index).contains(flow.get(index))) ? true : false;
            }
            if (correct == false) {
                ExceptionEx.exception( level + "_" + topic + "_" + lesson + "_Flow " + flowIndex);
            }
        }catch (Exception e){
            ExceptionEx.exception(e.getMessage());
            e.printStackTrace();
        }
        return String.valueOf(correct);
    }
}
