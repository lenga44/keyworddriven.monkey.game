package common.utility;

public class Constanst {
    public static final String SEPARATOR_PATH = System.getProperty("file.separator");
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
    public static final String SCOPE_FILE_PATH = "scope_path";
    public static final String TESTCASE_FILE_PATH = "testcase_path";
    public static final String LOG_FILE_PATH = "\\tool_test_game\\config\\logging.txt";
    public static final String CONFIG_FILE_PATH = "\\tool_test_game\\config\\config.json";

    //TC sheet in report file
    public static final int TESTCASE_ID = 0;
    public static final int TESTCASE_STATUS = 3;
    public static final int RUN_MODE = 2;
    public static final int TESTCASE_ERROR = 4;
    //TS sheet
    public static final int KEYWORD = 3;
    public static final int PROCEED= 5;
    public static final int DATA_SET = 6;
    public static final int PARAMS = 4;
    public static final int VERIFY_STEP = 7;
    public static final int PARAM_VERIFY_STEP = 8;
    public static final int KEY_DATA_EXPECTED = 9;
    public static final int EXPECTED = 10;
    public static final int RESULT = 11;
    public static final int ERROR = 12;
    public static final int IMAGE = 13;
    public static final String PROCESS_YES = "Y";
    public static final int DESCRIPTION = 2;
    public static final int TEST_STEP = 1;
    //Excel sheet
    public static final String SCOPE_SHEET = "Scope";
    public static final String TESTCASE_SHEET = "TestCase";
    public static final String TEST_STEP_SHEET = "TestSteps";

    //KeWords class
    public static final String SIMULATE_URL = "http://localhost:8342/q/simulate";
    public static final String SCENE_URL = "http://localhost:8342/q/scene";
    public static final String POINTER_URL = "http://localhost:8342/q/pointer";
    public static final String STATUS_URL = "http://localhost:8342/about";
    public static final String TAKE_PHOTO = "http://localhost:8342/utils/appscreenshot";
    public static final String DRAG_ACTION = ".drag";

}
