package execute;

import common.keywords.KeyWordsToActionPocoSDK;
import common.utility.*;
import org.json.simple.parser.ParseException;
import report.GenerateReport;

import java.io.IOException;
import java.lang.reflect.Method;

public class RunTestScriptData extends TestScrip{
    public RunTestScriptData(KeyWordsToActionPocoSDK keyWord, Method method[]){
        super(keyWord, method);
    }

    @Deprecated
    public static void run(String scopePath, int iTotalSuite) throws IOException, ParseException {

        isDataFlow = true;
        int begin = ExcelUtils.getNumberValueInCell(1, Constanst.BEGIN_INDEX_COLUM,Constanst.PLAN_SHEET);
        int end = ExcelUtils.getNumberValueInCell(1,Constanst.END_INDEX_COLUM,Constanst.PLAN_SHEET);
        Log.info("Run data from "+begin+" to "+end);

        for(int index = begin;index<=end;index++){
            getLevelFolder(begin);
            json = JsonHandle.getObjectInJsonData(index-1);

            execute(scopePath,iTotalSuite);

            ExcelUtils.setCellData(begin,1,Constanst.CURRENT_INDEX_COLUM,Constanst.PLAN_SHEET,scopePath);
            String key = ExcelUtils.getStringValueInCell(1,Constanst.DATA_PLAN_COLLUM,Constanst.PLAN_SHEET);
            String dataName = getDataSet(key);

            GenerateReport.genReport(begin,levelFolder,dataName);
            GenerateReport.countResultPlan(scopePath,iTotalSuite);

            ExcelUtils.setCellData(key,1,Constanst.DATA_PLAN_COLLUM,Constanst.PLAN_SHEET,scopePath);
            ExcelUtils.closeFile(scopePath);
        }

    }
    private static void getLevelFolder(int row)throws IOException{
        String courseFolder = FileHelpers.getRootFolder() + Constanst.REPORT_FILE_PATH + ExcelUtils.getStringValueInCell(row, Constanst.COURSE_COLUM, Constanst.PLAN_SHEET);
        levelFolder = courseFolder +"//" + ExcelUtils.getStringValueInCell(row,Constanst.LEVEL_COLUM,Constanst.PLAN_SHEET);
        Log.info("levelFolder: "+levelFolder);
        Log.info("Folder path report course: " + FileHelpers.convertPath(levelFolder));

        FileHelpers.genFolderReport(courseFolder);
        Log.info("Folder path report level: " + FileHelpers.convertPath(levelFolder));
    }

    private static String levelFolder;
}
