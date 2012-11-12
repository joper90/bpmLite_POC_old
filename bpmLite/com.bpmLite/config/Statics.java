package config;

public class Statics {
public final static boolean TEST_MODE = true;
	
	public final static String BPM_GUARD_PATH = "http://localhost:8088/bpmGuard";
	
	public final static String VERSION = "0.1";
	public final static String ADMIN ="root";
	public final static String ADMIN_KEY = "12345";
	
	public final static String EMS_PUSH_WORKED = "success";
	public final static String EMS_PUSH_FAILED = "failed";
	public final static String START_CASE = "started case";
	
	public static boolean isAlive = false;
	
	// JMS Connection information

	public static String					JMS_SERVER				= "localhost";
	public static String					JMS_USER				= null;
	public static String					JMS_PASSWORD				= null;
	public static String					JMS_TOPIC_PULL			= "BpmLiteQueue"; //Topic to connect to the serverWith
	public static String					JMS_TOPIC_PUSH			= "BpmGuardQueue"; // Topic to listen on.
	
	//JMS Message Parsing
	public static String WORK_ITEM_KEY_DETAILS 	= "WorkItemKeyDetails";
	public static String SET_USER_DETAILS 		= "SetUserDetailsRequest";
	public static String START_CASE_DETAILS		= "StartCaseDetails";
	
	
	public static void setAlive(boolean b)
	{
		isAlive = b;
	}
}
