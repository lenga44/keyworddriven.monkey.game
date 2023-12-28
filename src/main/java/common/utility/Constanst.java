package common.utility;

public class Constanst {
    public static final String PROJECT_PATH = System.getProperty("user.dir");

    // System Variables
    public static final String FAIL = "FAIL";
    public static final String PASS = "PASS";
    public static final String SKIP = "SKIP";
    public static final String YES = "Y";
    public static final String NO = "N";

    //Scope file
    public static final int TESTCASE_FILE_NAME = 1;
    public static final int STATUS_GAME = 3;
    public static final String SCOPE_FILE_PATH = "\\src\\main\\java\\testcases\\Scope.xlsx";
    public static final String TESTCASE_FILE_PATH = "\\src\\main\\java\\testcases\\";

    //TC sheet in report file
    public static final int TESTCASE_ID = 0;
    public static final int TESTCASE_STATUS = 3;
    public static final int RUN_MODE = 2;
    public static final int TESTCASE_ERROR = 4;
    //TS sheet
    public static final int KEYWORD = 3;
    public static final int PROCEED_ON_FAIL = 5;
    public static final int DATA_SET = 6;
    public static final int PARAMS = 4;
    public static final int VERIFY_STEP = 7;
    public static final int PARAM_VERIFY_STEP = 8;
    public static final int EXPECTED = 9;
    public static final int RESULT = 10;
    public static final int ERROR = 11;
    public static final int IMAGE = 12;

    //Excel sheet
    public static final String SCOPE_SHEET = "Scope";
    public static final String TESTCASE_SHEET = "TestCase";
    public static final String TEST_STEP_SHEET = "TestSteps";

    //KeWords class
    public static final String SIMULATE_URL = "http://localhost:8342/q/simulate";
    public static final String SCENE_URL = "http://localhost:8342/q/scene";
    public static final String DRAG_ACTION = ".drag";
}
