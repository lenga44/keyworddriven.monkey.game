package execute;

import common.utility.Constanst;
import common.utility.ExcelUtils;
import common.utility.FileHelpers;
import common.utility.Log;

import java.util.Arrays;
import java.util.Locale;

public class EndTestScript {
    public static void saveReportFail(String path){
        try {
            ExcelUtils.setExcelFile(path);
            int fail =0;
            int total = ExcelUtils.getRowCount(Constanst.TESTCASE_SHEET);
            for (int i = 0;i<total;i++){
                if (ExcelUtils.getStringValueInCell(i,Constanst.TESTCASE_STATUS,Constanst.TESTCASE_SHEET).equals(Constanst.FAIL)){
                    fail ++;
                    break;
                }
            }
            String file = "";
            if (fail>0) {
                for (String name : Arrays.stream(path.split("\\\\")).toList()) {
                    if (name.contains(".xlsx")) {
                        file = name.replace(".xlsx", "");
                    }
                }
                String content = FileHelpers.readFile(Constanst.LIST_FAIL_PATH_FILE);
                if (!content.equals("")) {
                    content = content + ",\n" + file;
                } else {
                    content = file;
                }
                FileHelpers.writeFile(content, Constanst.LIST_FAIL_PATH_FILE);
            }
        }catch (Exception e){
            Log.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
