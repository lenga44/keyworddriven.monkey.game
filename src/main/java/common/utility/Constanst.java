package common.utility;

import java.util.Arrays;
import java.util.List;

public class Constanst {

    public static final String PROJECT_PATH = System.getProperty("user.dir");
    //region System Variables
    public static final String TRUE = "true";
    public static final String FALSE = "false";
    public static final String FAIL = "FAIL";
    public static final String PASS = "PASS";
    public static final String SKIP = "SKIP";
    public static final String YES = "Y";
    public static final String NO = "N";
    public static final String WITH = "w";
    public static final String HEIGHT = "h";
    public static final String X = "x";
    public static final String Y = "y";
    public static final String DATA_MODE = "Data";
    public static final int GAME_MODE = 1;
    public static final String CHECK_CONTAIN = "_boolean_";
    public static final String CHECK_SKIP = "_skip_";
    //endregion

    //region Scope file
    public static final int TEST_SUITE_FILE_NAME = 1;
    public static final int STATUS_SUITE = 3;
    public static final int RUN_MODE_SCOPE = 2;
    public static final String TEST_CASE_GAME_NAME_IN_FLOW = "Report_GameName";
    public static final int LEVEL_COLUM = 1;
    public static final int BEGIN_INDEX_COLUM = 3;
    public static final int END_INDEX_COLUM = 4;
    public static final int CURRENT_INDEX_COLUM = 5;
    public static final String PLAN_SHEET = "Plan";
    public static final String SCOPE_FILE_PATH = "scope_path";
    public static final String SCOPE_COPY_FILE_PATH = "scope_copy_path";
    public static final String SCOPE_REPORT_FILE_PATH = "scope_report";
    public static final String TESTCASE_FILE_PATH = "testcase_path";
    public static final String LOG_FILE_PATH = "\\tool_test_game\\config\\logging.txt";
    public static final String CONFIG_FILE_PATH = "\\tool_test_game\\config\\config.json";
    public static final String REPORT_FILE_PATH = "\\tool_test_game\\config\\report";
    public static final String ONCE_TIME_KEY = "OnceTime";
    public static final int TOTAL_CELL_SCOPE_SHEET = 5;
    public static final int STT_COLUM = 0;
    public static final int GROUP_COLUM_IN_SCOPE_SHEET = 4;
    public static final int GROUP_ID_COLUM_IN_SCOPE = 5;
    public static final String SCOPE_COPY_SHEET = "Scope copy";

    //region PLAN
    public static final String MODULE_FLOW = "Module";
    public static final String DATA_FLOW = "Data";
    public static final int FLOW_COLUM = 0;
    public static final int PASS_PLAN_COLUM = 9;
    public static final int FAIL_PLAN_COLUM = 10;
    public static final int LESSON_PLAN_COLUM = 6;
    public static final int TOPIC_PLAN_COLUM = 7;
    public static final int COURSE_PLAN_COLUM = 8;
    public static final String EE_COURSE = "EE";
    public static final String LRC_COURSE = "LRC";
    public static final String AI_COURSE = "AI_Speak";
    //endregion

    //endregion

    //region TC sheet in report file
    public static final int TESTCASE_ID = 0;
    public static final int TESTCASE_STATUS = 3;
    public static final int TESTCASE_ERROR = 4;
    //endregion

    //region TS sheet
    public static final int RUN_MODE_TEST_STEP = 2;
    public static final int RUN_MODE_TEST_CASE = 2;
    public static final int KEYWORD = 3;
    public static final int PROCEED= 6;
    public static final int DATA_SET = 5;
    public static final int PARAMS = 4;
    public static final int VERIFY_STEP = 7;
    public static final int PARAM_VERIFY_STEP = 8;
    public static final int DATA_SET_ACTUAL = 9;
    public static final int EXPECTED = 10;
    public static final int RESULT = 11;
    public static final int ERROR = 12;
    public static final int IMAGE = 13;
    public static final String PROCESS_YES = "Y";
    public static final int DESCRIPTION = 2;
    public static final int TEST_STEP = 1;
    //endregion

    //region Group-Turn sheet
    public static final String GROUP_SHEET = "Group-Turn";
    public static final int GROUP_NAME_COLUM = 0;
    public static final int GROUP_LEVEL_COLUM = 2;
    public static final int GROUP_LOOP_COLUM = 1;
    public static final int GROUP_VALUE_COLUM = 3;
    public static final int CALCULATE_VALUE_COLUM = 4;
    //region TC_TS sheet
    public static final String TC_TS_SHEET = "TC_TS";
    //endregion
    //endregion
    //region TC sheet
    public static final int GROUP_COLLUM_IN_TC_SHEET = 5;
    //endregion

    //region Excel sheet
    public static final String SCOPE_SHEET = "Scope";
    public static final String TESTCASE_SHEET = "TestCase";
    public static final String TEST_STEP_SHEET = "TestSteps";
    //endregion

    //region KeWords class
    public static final String SIMULATE_URL_UNIUM = "http://localhost:8342/q/simulate";
    public static final String SCENE_URL_UNIUM = "http://localhost:8342/q/scene";
    public static final String POINTER_URL_UNIUM = "http://localhost:8342/q/pointer";
    public static final String TIME_SCALE_ACTION =".timeScale";
    public static final String STATUS_URL_UNIUM = "http://localhost:8342/about";
    public static final String ALL_SCENE_UNIUM = "http://localhost:8342/q/scene";
    public static final String TAKE_PHOTO = "http://localhost:8342/utils/appscreenshot";
    public static final String URL_POCO = "http://127.0.0.1:6868/";
    public static final String DRAG_ACTION = ".drag";
    public static final String DRAG_UP_ACTION = ".dragUp";
    public static final String MOVE_ACTION = ".Move";
    public static final String CLICK_ACTION = "click";
    public static final String CLICK_POSITION_ACTION = "click_position";
    public static final String TOUCH_ANDROID = "touch_android?";
    public static final String CLICK_IMAGE_ACTION = "click_image?image=";
    public static final String MOVE_UP_ACTION = ".MoveUp";
    public static final String MOVE_COORDINATE = ".MoveX";
    //endregion

    //region Set up file
    public static final String TC_SETUP_FILE_NAME = "//Report_SetUp.xlsx";
    public static final int MARK_IS_PASS_COLUM = 5;
    //endregion

    //region Tear down file
    public static final String TC_TEARDOWN_FILE_NAME = "//Report_TearDown.xlsx";
    //endregion

    //region file config
    public static final String DATA_FILE_PATH = "data_file_path";
    public static final String FILE_NAME_REPORT_DATA_FLOW = "file_name_report_data_flow";
    public static final String LIST_FAIL_PATH_FILE = FileHelpers.getRootFolder()
            +FileHelpers.convertPath("\\tool_test_game\\config\\report\\list_fail.txt");
    //endregion

    //region file variable file
    public static final String PATH_GAME_OBJECT = "path";
    public static final String RUN_MODE_TC_VAR = "mode";
    public static final String INDEX_GAME_OBJECT = "index";
    public static final String CURRENT_SCENE = "current_scene";
    public static final String VARIABLE_PATH_FILE = FileHelpers.getRootFolder()
            +FileHelpers.convertPath("\\tool_test_game\\config\\data\\variable.json");
    //endregion

    //region file report file
    public static final int FAIL_REPORT_COLUM = 9;
    //endregion
    public static final String PAUSE_PROGRAM_URL = ".pause()";
    public static final String RESUME_PROGRAM_URL = ".resume()";

    //region logicHandle
    public static final List<String> splits = Arrays.asList("\\|", "\\;",",",", ");
    //endregion

    //region OS
    public static final String ANDROID_OS = "android";
    //endregion
}
