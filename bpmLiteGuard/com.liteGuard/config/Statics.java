package config;


public class Statics {

	public final static boolean TEST_MODE = true;
	
	public final static String BPM_SERVER_PATH = "http://localhost:8088/bpmLite";
	
	public final static String VERSION = "0.1";
	public final static String ADMIN ="root";
	public final static String ADMIN_KEY = "12345";
	
	public final static String SUCCESS = "success";
	public final static String FAILED = "failed";
	
	public final static String INITIAL_GLOBAL_COUNT = "gCount";
	public final static String INITIAL_GLOBAL_COUNT_START = "5000";
		
	public static boolean isAlive = false;
	
	//Current state of the key in the keystore.
	public static enum GUID_KEY_MODE  {INJECTED,TAKEN,ACTIONED};
	
	// JMS Connection information

	public static String					JMS_SERVER				= "localhost";
	public static String					JMS_USER				= null;
	public static String					JMS_PASSWORD				= null;
	public static String					JMS_TOPIC_PUSH			= "BpmLiteQueue"; //Topic to connect to the serverWith
	public static String					JMS_TOPIC_PULL			= "BpmGuardQueue"; // Topic to listen on.
	
	//JMS Message Parsing
	public static String WORK_ITEM_KEY_DETAILS 	= "WorkItemKeyDetails";
	public static String START_CASE_DETAILS		= "StartCaseDetails";
	
	
	
	public static void setAlive(boolean b)
	{
		isAlive = b;
	}
}
