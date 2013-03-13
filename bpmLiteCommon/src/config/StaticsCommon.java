package config;

public class StaticsCommon {
	public final static boolean TEST_MODE = false;
	
	public final static String BPM_GUARD_PATH = "http://localhost:8088/bpmGuard";
	public final static String BPM_SERVER_PATH = "http://localhost:8088/bpmLite";
	
	public final static String VERSION = "0.1";
	
	public final static String SERVER_INFO_ADMIN_KEY = "rootKey";
	public final static String SERVER_INFO_ADMIN_PASS = "rootPass";
	
	public final static String ADMIN ="root";
	public final static String ADMIN_KEY = "12345";
	
	public final static String CASE_ID = "caseId";
	public final static String CASE_ID_START_VALUE = "bpm:0000001";
	
	public final static String EMS_PUSH_WORKED = "success";
	public final static String EMS_PUSH_FAILED = "failed";
	
	public final static int	   START_STEP_INITAL_VALUE = 0;
	public final static String INITIAL_GLOBAL_COUNT = "gCount";
	public final static String INITIAL_GLOBAL_COUNT_START = "5000";
	
	public static enum STEP_TYPE {USER, AMXBPM, DB, EMAIL, POJO, TIBBR};
	public static enum GUID_KEY_MODE  {INJECTED,TAKEN,ACTIONED};
	
	public static boolean guardIsAlive = false;
	public static boolean serverIsAlive = false;
	
	public final static String SUCCESS = "success";
	public final static String FAILURE = "failed";
	
	public static int FAILED = -1;
	public static int WORKED = 0;
	
	//SpinTime after inital stasrt on the next step to find out if the calback has happened.
	public static long	LOOP_SLEEP_TIME = 5000; // 5 seconds
	public static int	LOOP_SLEEP_MAX_COUNT = 5; // 5 times around and around.
	
	// JMS Connection information

	public static String					JMS_SERVER				= "localhost";
	public static String					JMS_USER				= null;
	public static String					JMS_PASSWORD				= null;
	public static String					JMS_TOPIC_SERVER			= "BpmLiteQueue"; //Topic us..
	public static String					JMS_TOPIC_GUARD			= "BpmGuardQueue"; //Topic to push to (
	
	//JMS Message Parsing
	public static String WORK_ITEM_KEY_DETAILS 	= "WorkItemKeyDetails";
	public static String SET_USER_DETAILS 		= "SetUserDetailsRequest";
	public static String START_CASE_DETAILS		= "StartCaseDetails";
	public static String CALL_BACK_DETAILS		= "CallBack";
	public static String SERVER_COMMAND			= "ServerCommand";
	public static String COMPLETE_COMMAND		= "CompleteWorkItem";

	//Process stuff
	public static int PROCESS_ENDED	= -1;
	
	public static void setGuardAlive(boolean b)
	{
		guardIsAlive = b;
	}
	
	public static void setBpmServerlive(boolean b)
	{
		serverIsAlive = b;
	}
	
}
