package common.keywords.app.custom;

import common.utility.Constanst;
import common.utility.FileHelpers;
import common.utility.JsonHandle;
import common.utility.LogicHandle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static common.keywords.app.verify.Check.check;
import static execute.TestScrip.flow;

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
    public static String verifyFlow(String flowExpected,String lesson,String flowIndex,String level,String topic){
        boolean correct = false;
        String flowExpectedJson = FileHelpers.readFile(Constanst.DATA_FOLDER_PATH + flowExpected);
        String actName = JsonHandle.getValue(flowExpectedJson,"$.[?(@.name=="+flowIndex+")].lesson[?(@.name_lesson=="
                + LogicHandle.removeString(lesson,"Lesson ") +")].act");
        System.out.println(actName);
        Map<Integer,List<String>> map = new HashMap<>();
        int i = 0;
        List<String> list = LogicHandle.convertStringToList(actName);
        for (String item: list){
            map.put(i,LogicHandle.convertStringToList(item));
            i++;
        }

        for (int index = 0;index<map.keySet().size();index++){
            correct = (map.get(index).contains(flow.get(index)))? true: false;
        }
        if(correct==false){
            FileHelpers.writeNewLine(Constanst.LIST_FAIL_PATH_FILE,level+"_"+topic+"_"+lesson+"_Flow "+flowIndex);
        }
        return String.valueOf(correct);
    }
}
