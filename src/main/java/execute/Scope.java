package execute;

import common.keywords.KeyWordCustomForAISpeak;
import common.keywords.KeyWordsToAction;
import common.keywords.KeyWordsToActionToVerify;
import common.utility.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static execute.GroupInTest.*;

public class Scope {
    public static void deFindFlowGame(int row, String path){
        if(TestScrip.tcName.equals(Constanst.TEST_CASE_GAME_NAME_IN_FLOW))
        {
            KeyWordsToAction.sleep("1");
            returnGame(row,path);
        }
    }
    private static String deFindGame(int row){
        String course = ExcelUtils.getStringValueInCell(row,Constanst.COURSE_PLAN_COLUM,Constanst.PLAN_SHEET);
        String game = null;
        if(course.equals(Constanst.AI_COURSE)){
            game = KeyWordsToActionToVerify.getAllScene();
        }else if (course.equals(Constanst.EE_COURSE)){
            game = KeyWordsToActionToVerify.getCurrentScene();
        }
        return game;
    }
    private static void returnGame(int row,String path){
        String games = deFindGame(row);
        boolean exits = false;
        for (String game: games.split(",")) {
            TestScrip.tcName = "Report_" + game;
            TestScrip.tcPath = FileHelpers.getRootFolder() + FileHelpers.getValueConfig(Constanst.TESTCASE_FILE_PATH)+ TestScrip.tcName + ".xlsx";
            if(new File(TestScrip.tcPath).exists()){
                exits = true;
                ExcelUtils.setCellData(TestScrip.tcName, row, Constanst.TEST_SUITE_FILE_NAME, Constanst.SCOPE_SHEET, path);
                break;
            }
        }
        if (exits == false){
            TestScrip.tcResult = Constanst.SKIP;
            ExcelUtils.setCellData(Constanst.NO,row,Constanst.RUN_MODE_SCOPE,Constanst.SCOPE_SHEET,path);
        }
    }
    public static void genFlowLesson(String json) throws Exception {
        ExcelUtils.setExcelFile(Constanst.SCOPE_FILE_PATH);
        try {
            ArrayList<String> groups = getGroup();
            int totalGroup = ExcelUtils.getRowCount(Constanst.GROUP_SHEET);
            Map<String, String> mapGroupValue = getValueGroups(json, groups);
            Map<String, ArrayList<Integer>> mapGroupRange = getRangeGroups(groups,Constanst.GROUP_COLLUM_IN_SCOPE_SHEET,Constanst.SCOPE_SHEET);
            if (totalGroup > 0) {
                for (int level : getListLevel(totalGroup)) {
                    for (String groupName : getGroupWithLeve(totalGroup, level)) {
                        ArrayList<Integer> ranges = mapGroupRange.get(groupName);
                        if (level > 1) {
                            List<Integer> list = LogicHandle.convertToArrayListInt(mapGroupValue.get(groupName));
                            int rowInsert = 0;
                            if (!list.equals(null)) {
                                for (int i = 0; i < list.size(); i++) {
                                    int loop = list.get(i);
                                    if (loop > 10) {
                                        loop = 10;
                                    }
                                    ArrayList<Integer> listRange = getListRangeByGroup(rowInsert, groupName, ranges);
                                    copyTestSuiteWithGroupSubLevel(listRange, loop, Constanst.TOTAL_CELL_SCOPE_SHEET);
                                    rowInsert = listRange.get(0) + loop + 1;
                                }
                            }
                        } else {
                            int loop = Integer.valueOf(mapGroupValue.get(groupName));
                            copyTestSuiteWithGroup(ranges, loop, Constanst.TOTAL_CELL_SCOPE_SHEET);
                            int totalRow = ExcelUtils.getRowCount(Constanst.TESTCASE_SHEET);
                            ExcelUtils.deleteRow(totalRow - 1, Constanst.TESTCASE_SHEET);
                        }
                    }
                }
            }
            ExcelUtils.closeFile(Constanst.SCOPE_FILE_PATH);
        }catch (Exception e){
            Log.error("|genFlowLesson| "+e.getMessage());
        }
    }
    public static void copyTestSuiteWithGroup(ArrayList<Integer> ranges, int loop,int totalCell) throws Exception {
        int first = ranges.get(0);
        int last = ranges.get(1) + 1;
        int countRow = last - first;
        for (int i = 0; i < loop - 1; i++) {
            for (int j = 0; j < countRow; j++) {
                int from = first + j;
                int to = last + j;
                ExcelUtils.copyRow(Constanst.SCOPE_FILE_PATH, Constanst.SCOPE_SHEET, from, to,totalCell);
                ExcelUtils.setExcelFile(Constanst.SCOPE_FILE_PATH);
                int stt = ExcelUtils.getNumberValueInCell(from-1,Constanst.STT_COLUM,Constanst.SCOPE_SHEET);
                ExcelUtils.setCellData(stt+1,from,Constanst.STT_COLUM,Constanst.SCOPE_SHEET,Constanst.SCOPE_FILE_PATH);
                ExcelUtils.closeFile(Constanst.SCOPE_FILE_PATH);
            }
            JsonHandle.setValueInJsonObject(Constanst.VARIABLE_PATH_FILE,"activity",i);
            first = last;
            last = first + countRow;
        }
    }
    public static void copyTestSuiteWithGroupSubLevel(ArrayList<Integer> ranges, int loop,int totalCell) throws Exception {
        int first = ranges.get(0);
        int last = ranges.get(1) + 1;
        int countRow = last - first;
        for (int i = 0; i < loop - 1; i++) {
            for (int j = 0; j < countRow; j++) {
                int from = first + j;
                int to = last + j;
                ExcelUtils.copyRow(Constanst.SCOPE_FILE_PATH, Constanst.SCOPE_SHEET, from, to,totalCell);
                ExcelUtils.setExcelFile(Constanst.SCOPE_FILE_PATH);
                int stt = ExcelUtils.getNumberValueInCell(from-1,Constanst.STT_COLUM,Constanst.SCOPE_SHEET);
                ExcelUtils.setCellData(stt+1,from,Constanst.STT_COLUM,Constanst.SCOPE_SHEET,Constanst.SCOPE_FILE_PATH);
                ExcelUtils.closeFile(Constanst.SCOPE_FILE_PATH);
            }
            JsonHandle.setValueInJsonObject(Constanst.VARIABLE_PATH_FILE,"activity",i);
            first = last;
            last = first + countRow;
        }
    }
}
