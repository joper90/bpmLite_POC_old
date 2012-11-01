package config;


public class Statics {

	public final static boolean TEST_MODE = true;
	
	public final static String BPM_SERVER_PATH = "http://localhost:8088/bpmLite";
	
	public final static String VERSION = "0.1";
	public final static String ADMIN ="root";
	public final static String ADMIN_KEY = "12345";
	
	public final static String REST_WORKED = "success";
	public final static String REST_FAILED = "failed";
	
	public static boolean isAlive = false;
	
	// JMS Connection information

	public static String					jmsServer				= "localhost";
	public static String					jmsUser					= null;
	public static String					jmsPassword				= null;
	public static String					jmsTopicPush			= "BpmLiteTopic"; //Topic to connect to the serverWith
	public static String					jmsTopicPull			= "BpmGuardTopic"; // Topic to listen on.
	
	
	public static void setAlive(boolean b)
	{
		isAlive = b;
	}
}
