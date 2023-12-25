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
    public static final int VERIFY_STEP = 8;
    public static final int VERIFY_PARAM = 9;
    public static final int EXPECTED = 10;
    public static final int RESULT = 11;
    public static final int ERROR = 12;
    public static final int IMAGE = 13;

    //Excel sheet
    public static final String SCOPE_SHEET = "Scope";
    public static final String TESTCASE_SHEET = "TestCase";
    public static final String TEST_STEP_SHEET = "TestSteps";
}
