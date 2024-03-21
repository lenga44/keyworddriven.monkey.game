package execute;

import common.utility.Constanst;
import common.utility.ExcelUtils;
import common.utility.JsonHandle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GroupInTest {

    //region Copy
    public static void copyRowIfTCContainGroup(String json, String reportPath) throws Exception {
        ArrayList<String> groups = getGroup();
        int totalGroup = ExcelUtils.getRowCount(Constanst.GROUP_SHEET);
        Map<String,String> mapGroupValue = getValueGroups(json,groups);
        if(totalGroup>0) {
            for (int level : getListLevel(totalGroup)) {
                for (String groupName : getGroupWithLeve(totalGroup, level)) {
                    ArrayList<Integer> list = getRangeByGroup(groups,groupName);
                    int first = list.get(0);
                    int last = list.get(1)+1;
                    int countRow = last - first ;
                    int loop = Integer.valueOf(mapGroupValue.get(groupName));
                    for (int i = 0;i<loop-1;i++){
                        for (int j = 0; j<countRow;j++) {
                            int from = first+j;
                            int to = last +j;
                            ExcelUtils.copyRow(reportPath, Constanst.TESTCASE_SHEET, from, to);
                            /*String id = ExcelUtils.getStringValueInCell(to-1,Constanst.TESTCASE_ID,Constanst.TESTCASE_SHEET);
                           genTestcaseID(id,to,reportPath);*/
                        }
                        first = last;
                        last = first + countRow;
                    }
                }
                break;
            }
        }
    }
    public static void genTestcaseID(String id,int row,String reportPath) throws IOException {
        if(id.contains("TC")) {
            int number = Integer.valueOf(id.replace("TC", "")) + 1;
            String tcId = "TC" + number;
            ExcelUtils.setExcelFile(reportPath);
            ExcelUtils.setCellData(tcId,row,Constanst.TESTCASE_ID,Constanst.TESTCASE_SHEET,reportPath);
            ExcelUtils.closeFile(reportPath);
        }
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
