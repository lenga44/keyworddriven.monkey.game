package execute;

import common.keywords.app.KeyWordsToAction;
import common.keywords.app.KeyWordsToActionToVerify;
import common.utility.*;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static execute.GroupInTest.*;
import static execute.TestScrip.*;

public class Scope {
    public static void deFindFlowGame(int row, String path){
        if (TestScrip.tcName.equals(Constanst.TEST_CASE_GAME_NAME_IN_FLOW)) {
            KeyWordsToAction.pause();
            try {
                returnGame(row, path);
            } catch (Exception e) {
                Log.error("|deFindFlowGame| " + e.getMessage());
            }
            KeyWordsToAction.resume();
        }
    }
    private static String deFindGame(){
        String course = ExcelUtils.getStringValueInCell(1,Constanst.COURSE_PLAN_COLUM,Constanst.PLAN_SHEET);
        System.out.println("Course___ "+course);
        String game = null;
        if(course.equals(Constanst.AI_COURSE)){
            game = KeyWordsToActionToVerify.getAllScene();
        }else if (course.equals(Constanst.EE_COURSE)){
            game = KeyWordsToActionToVerify.getCurrentScene();
        }
        return game;
    }
    private static void returnGame(int row,String path) throws InterruptedException {
        boolean exits = false;
        LocalDateTime time = LocalDateTime.now();
        LocalDateTime time1 = time.plusSeconds(15);
        do {
            String games = deFindGame().replace("[", "").replace("]", "");
            if (!games.equals("[]")){
                for (String game : games.split(", ")) {
                    TestScrip.tcName = "Report_" + game;
                    TestScrip.tcPath = FileHelpers.getRootFolder() + FileHelpers.getValueConfig(Constanst.TESTCASE_FILE_PATH) + TestScrip.tcName + ".xlsx";
                    if (new File(TestScrip.tcPath).exists()) {
                        exits = true;
                        ExcelUtils.setCellData(TestScrip.tcName, row, Constanst.TEST_SUITE_FILE_NAME, Constanst.SCOPE_SHEET, path);
                        break;
                    }
                }
            }
            Thread.sleep(500);
            time = LocalDateTime.now();
        }while (time.compareTo(time1) <= 0||exits ==true);
        if (exits == false){
            ExcelUtils.setCellData(Constanst.NO,row,Constanst.RUN_MODE_SCOPE,Constanst.SCOPE_SHEET,path);
            Log.error("TEST CASE IS NOT EXIT!!!");
        }
    }
    public static void genFlowLesson(String json,int total,String path) throws Exception {
        if(isDataFlow==true) {
            ExcelUtils.setExcelFile(FileHelpers.getRootFolder() + FileHelpers.getValueConfig(Constanst.SCOPE_FILE_PATH));
            KeyWordsToAction.pause();
            try {
                ArrayList<String> groups = getGroup();
                int totalGroup = ExcelUtils.getRowCount(Constanst.GROUP_SHEET);
                ;
                Map<String, String> mapGroupValue = getValueGroups(json, groups, path);
                if (totalGroup > 0) {
                    ExcelUtils.createRowLastest(total, Constanst.SCOPE_SHEET, path);
                    for (int level : getListLevel(totalGroup)) {
                        for (String groupName : getGroupWithLeve(totalGroup, level)) {
                            Map<String, ArrayList<Integer>> mapGroupRange = getRangeGroups(groupName, Constanst.GROUP_COLUM_IN_SCOPE_SHEET, Constanst.SCOPE_SHEET);
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
                                int loop = Integer.parseInt(mapGroupValue.get(groupName));
                                if (loop > 1) {
                                    copyTestSuiteWithGroup(ranges, loop, Constanst.TOTAL_CELL_SCOPE_SHEET);
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                Log.error("|genFlowLesson| " + e.getMessage());
                e.printStackTrace();
            }
            ExcelUtils.closeFile(FileHelpers.getRootFolder() + FileHelpers.getValueConfig(Constanst.SCOPE_FILE_PATH));
            KeyWordsToAction.resume();
        }
    }
    public static void copyTestSuiteWithGroup(ArrayList<Integer> ranges, int loop,int totalCell) throws Exception {
        int first = ranges.get(0);
        int last = ranges.get(1)+1;
        int countRow = last - first;
        for (int i = 0; i < loop - 1; i++) {
            for (int j = 0;j<countRow;j++){
                int from = first + j;
                int to = last + j;
                ExcelUtils.copyRow(Constanst.SCOPE_FILE_PATH, Constanst.SCOPE_SHEET, from, to,totalCell);
                ExcelUtils.setExcelFile(Constanst.SCOPE_FILE_PATH);
                int stt = Integer.parseInt(ExcelUtils.getStringValueInCell(first,Constanst.STT_COLUM,Constanst.SCOPE_SHEET));
                ExcelUtils.setCellData(String.valueOf(stt+1),from,Constanst.STT_COLUM,Constanst.SCOPE_SHEET,Constanst.SCOPE_FILE_PATH);
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
    public static void copyFileScope(String source,String des){
        FileHelpers.copyFile(source,des);
    }
    public static void ResetScopeFile(String sourceFile,String copyFile,String scopeReport,int index){
        FileHelpers.copyFile(sourceFile,scopeReport+index+".xlsx");
        FileHelpers.copyFile(copyFile,sourceFile);
        FileHelpers.deleteFile(copyFile);
    }
    public static String genReportName(String key){;
        String report ="";
        int i = 0;
        if (key.contains(",")) {
            for (String k : Arrays.stream(key.split(",")).toList()) {
                String sub = getDataSet(k);
                if(i==0){
                    levelFolder = levelFolder +"/"+sub.replaceAll("[^a-zA-Z]", "")+"/";
                }
                report += sub;
                i++;
            }
        } else {
            report = getDataSet(key);
        }
        return report;
    }
}
