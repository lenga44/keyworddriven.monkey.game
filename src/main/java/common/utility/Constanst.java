package common.utility;

public class Constanst {
    public static final String PROJECT_PATH = System.getProperty("user.dir");

    // System Variables
    public static final String KEYWORD_FAIL = "FAIL";
    public static final String KEYWORD_PASS = "PASS";
    public static final String KEYWORD_SKIP = "SKIP";
    public static final String YES = "Y";
    public static final String NO = "N";

    //Scope file
    public static final int TESTCASE_FILE_NAME = 1;
    public static final int STATUS_GAME = 2;
    public static final String SCOPE_FILE_PATH = "\\src\\main\\java\\testcases\\Scope.xlsx";
    public static final String TESTCASE_FILE_PATH = "\\src\\main\\java\\testcases\\";

    //TC sheet in report file
    public static final int TESTCASE_ID = 0;
    public static final int TESTCASE_DESCRIPTION = 1;
    public static final int TESTCASE_STATUS = 3;
    public static final int RUN_MODE = 2;
    public static final int ACTION_KEYWORD = 4;
    public static final int PROCEED_ON_FAIL = 6;
    public static final int PARAM = 5;

    //Excel sheet
    public static final String SCOPE_SHEET = "Scope";
    public static final String TESTCASE_SHEET = "TestCase";
    public static final String TEST_STEP_SHEET = "TestSteps";
}
