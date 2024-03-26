package execute;

import common.utility.*;

import java.io.IOException;
import java.util.*;

public class GroupInTest {

    //region COPY TESTCASES
    public static void copyTestCaseWhichGroupContain(String json, String reportPath) throws Exception {
        ArrayList<String> groups = getGroup();
        int totalGroup = ExcelUtils.getRowCount(Constanst.GROUP_SHEET);
        Map<String,String> mapGroupValue = getValueGroups(json,groups);
        Map<String,ArrayList<Integer>> mapGroupRange = getRangeGroups(groups);
        int totalCellInRow = ExcelUtils.getRow(Constanst.TESTCASE_SHEET,1);
        if(totalGroup>0) {
            for (int level : getListLevel(totalGroup)) {
                for (String groupName : getGroupWithLeve(totalGroup, level)) {
                    ArrayList<Integer> ranges = mapGroupRange.get(groupName);
                    if(level >1) {
                        List<Integer> list = LogicHandle.convertToArrayListInt(mapGroupValue.get(groupName));
                        int rowInsert =0;
                        if(!list.equals(null)){
                            for(int i = 0;i<list.size();i++){
                                int loop = list.get(i);
                                if(loop>10){
                                    loop = 10;
                                }
                                Log.info("=======Copy=======" + rowInsert);
                                ArrayList<Integer> listRange = getListRangeByGroup(rowInsert,groupName,ranges);
                                copyTestCasesWithGroupSubLevel(listRange,loop,reportPath,totalCellInRow);
                                rowInsert = listRange.get(0)+loop+1;
                            }
                        }
                    }else {
                        int loop = Integer.valueOf(mapGroupValue.get(groupName));
                        copyTestCasesWithGroup(ranges,loop,reportPath,totalCellInRow);
                    }
                }
            }
        }
    }
    public static void genTestcaseID(String id,int row,String reportPath) throws IOException {
        ExcelUtils.setExcelFile(reportPath);
        ExcelUtils.setCellData(id,row,Constanst.TESTCASE_ID,Constanst.TESTCASE_SHEET,reportPath);
        ExcelUtils.closeFile(reportPath);
    }
    public static String genTestStepId(int row, int firstStep,String id){
        if(id.contains("TS")){
            if(row !=firstStep) {
                int number = Integer.valueOf(id.replace("TS",""))+1;
                return "TS" + number;
            }
            else
                return "TS1";
        }
        return id;
    }
    public static ArrayList<Integer> getListRangeByGroup(int i,String group, ArrayList<Integer> ranges){
        int totalTestSuites = ExcelUtils.getRowCount(Constanst.TESTCASE_SHEET);
        int countRow = ranges.get(0) - ranges.get(1);
        int last = ranges.get(1)+1;
        ArrayList<Integer> list = new ArrayList<>();
        for (; i<totalTestSuites;i++){
            if(ExcelUtils.getStringValueInCell(i,Constanst.GROUP_COLUM_IN_TC_SHEET,Constanst.TESTCASE_SHEET).equals(group)){
                if(i!=last) {
                    last = i +countRow;
                    list.add(i);
                    list.add(last);
                    break;
                }
            }
        }
        return list;
    }

    private static void copyTestCasesWithGroup(ArrayList<Integer> ranges, int loop, String reportPath,int totalCell) throws Exception {
        int first = ranges.get(0);
        int last = ranges.get(1) + 1;
        int countRow = last - first;
        for (int i = 0; i < loop - 1; i++) {
            for (int j = 0; j < countRow; j++) {
                int from = first + j;
                int to = last + j;
                ExcelUtils.copyRow(reportPath, Constanst.TESTCASE_SHEET, from, to,totalCell);
                ExcelUtils.setExcelFile(reportPath);
                String id = ExcelUtils.getStringValueInCell(ranges.get(0) + j, Constanst.TESTCASE_ID, Constanst.TESTCASE_SHEET) + "_" + (i + 1);
                genTestcaseID(id, to, reportPath);
                ExcelUtils.closeFile(reportPath);
            }
            first = last;
            last = first + countRow;
        }
    }
    private static void copyTestCasesWithGroupSubLevel(ArrayList<Integer> ranges, int loop, String reportPath,int totalCell) throws Exception {
        int first = ranges.get(0);
        int last = ranges.get(1) + 1;
        int countRow = last - first;
        for (int i = 0; i < loop - 1; i++) {
            for (int j = 0; j < countRow; j++) {
                int from = first + j;
                int to = last + j;
                ExcelUtils.copyRow(reportPath, Constanst.TESTCASE_SHEET, from, to,totalCell);
                ExcelUtils.setExcelFile(reportPath);
                String id = ExcelUtils.getStringValueInCell(ranges.get(0) + j, Constanst.TESTCASE_ID, Constanst.TESTCASE_SHEET) + "." + (i + 1);
                genTestcaseID(id, to, reportPath);
                ExcelUtils.closeFile(reportPath);
            }
            first = last;
            last = first + countRow;
        }
    }
    //endregion

    //region COPY TEST STEP
    public static void copyTestStepByTestCase(String path){
        List<String> testCaseIDs = getToTalTestCaseID();
        int totalTestStep = ExcelUtils.getRowCount(Constanst.TEST_STEP_SHEET);
        int totalCellInRow = ExcelUtils.getRow(Constanst.TEST_STEP_SHEET,1);
        ExcelUtils.createRowLastest(totalTestStep,Constanst.TEST_STEP_SHEET,path);
        for (String tcID: testCaseIDs) {
            if(ExcelUtils.getRowContains(tcID,Constanst.TESTCASE_ID,Constanst.TEST_STEP_SHEET)==0){
                String id = tcID;
                if(id.contains(".")){
                    id = Arrays.stream(id.split("\\.")).toList().get(0);
                }else if (id.contains("_")){
                    id = Arrays.stream(id.split("_")).toList().get(0);
                }
                int start = ExcelUtils.getRowContains(id,Constanst.TESTCASE_ID,Constanst.TEST_STEP_SHEET);
                int end = ExcelUtils.getTestStepCount(Constanst.TEST_STEP_SHEET,id,start);
                for (int i = 0;i<(end-start+1);i++) {
                    ///////////////////////////////////////////////
                    ExcelUtils.copyRow(path, Constanst.TEST_STEP_SHEET,start+i,totalTestStep,totalCellInRow);
                    totalTestStep = ExcelUtils.getRowCount(Constanst.TEST_STEP_SHEET)-1;
                    ExcelUtils.setCellData(tcID,totalTestStep,Constanst.TESTCASE_ID,Constanst.TEST_STEP_SHEET,path);
                }
            }
        }
    }
    private static List<String> getToTalTestCaseID(){
        List<String> testCaseIDs = new ArrayList<>();
        int totalTestCase = ExcelUtils.getRowCount(Constanst.TESTCASE_SHEET)-1;
        for (int i =1;i<totalTestCase;i++){
            testCaseIDs.add(ExcelUtils.getStringValueInCell(i,Constanst.TESTCASE_ID,Constanst.TESTCASE_SHEET));
        }
        return testCaseIDs;
    }
    private static int getToTalTestStep(){
        return ExcelUtils.getRowCount(Constanst.TEST_STEP_SHEET);
    }
    //endregion
    //region COPY TEST STEPS
    //endregion

    //region Group
    private static ArrayList<String> getGroup(){
        ArrayList<String> list = new ArrayList<>();
        int totalGroup = ExcelUtils.getRowCount(Constanst.GROUP_SHEET);
        for(int i=1;i<totalGroup;i++){
            String name = ExcelUtils.getStringValueInCell(i,Constanst.GROUP_NAME_COLUM,Constanst.GROUP_SHEET);
            if(!name.equals(""))
                list.add(name);
        }
        return list;
    }
    private static Map<String,Integer> mapGroupLevel(int totalGroup){
        Map<String,Integer> map = new HashMap<>();
        for(int i=1;i<totalGroup;i++){
            map.put(ExcelUtils.getStringValueInCell(i,Constanst.GROUP_NAME_COLUM,Constanst.GROUP_SHEET)
                    ,ExcelUtils.getNumberValueInCell(i,Constanst.GROUP_LEVEL_COLUM,Constanst.GROUP_SHEET));
        }
        return map;
    }
    private static ArrayList<Integer> getListLevel(int totalGroup){
        Map<String,Integer> map = mapGroupLevel(totalGroup);
        ArrayList<Integer> list = new ArrayList<>();
        for (String group: map.keySet()) {
            int level = map.get(group);
            if(!list.contains(level))
                list.add(level);
        }
        Collections.sort(list);
        return list;
    }
    private static ArrayList<String> getGroupWithLeve(int totalGroup,int level){
        Map<String,Integer> map = mapGroupLevel(totalGroup);
        ArrayList<String > list = new ArrayList<>();
        for (String group: map.keySet()) {
            if(map.get(group)==level)
                list.add(group);
        }
        return list;
    }
    private static Map<String,ArrayList<Integer>> getRangeGroups(ArrayList<String> groups){
        Map<String,ArrayList<Integer>> map = new HashMap<>();
        if(groups.size()>0){
            for (String group:groups) {
                int first = ExcelUtils.getRowContains(group,Constanst.GROUP_COLUM_IN_TC_SHEET,Constanst.TESTCASE_SHEET);
                int last = ExcelUtils.getLastByContain(Constanst.TESTCASE_SHEET,group,first,Constanst.GROUP_COLUM_IN_TC_SHEET);
                ArrayList<Integer> list = new ArrayList<>();
                list.add(first);
                list.add(last);
                if(list.size()>0)
                    map.put(group,list);
            }
        }
        return map;
    }
    private static ArrayList<Integer> getRangeByGroup(ArrayList<String> groups, String group){
        Map<String,ArrayList<Integer>> map = getRangeGroups(groups);
        ArrayList<Integer> list = new ArrayList<>();
        for (String name: map.keySet()) {
            if(name.equals(group)){
                list=map.get(name);
            }
        }
        return list;
    }
    private static Map<String,String> getValueGroups(String json,ArrayList<String> groups){
        Map<String,String> map = new HashMap<>();
        for(int i =0;i<groups.size();i++){
            map.put(groups.get(i), JsonHandle.getValue(json,ExcelUtils.getStringValueInCell(i+1,Constanst.GROUP_VALUE_COLUM,Constanst.GROUP_SHEET)));
        }
        return map;
    }
    //endregion
}
