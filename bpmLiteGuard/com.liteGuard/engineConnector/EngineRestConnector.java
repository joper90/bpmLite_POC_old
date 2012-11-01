package engineConnector;

public class EngineRestConnector {

	public static boolean isAlive()
	{
		//Checks if the server is alive and up.
		//Do a rest call and get the results.
		return true;
	}
	
	public static boolean stepCompleted(int processId, int stepId, String action)
	{
		//Push to the server now..that a complete/close has happened.
		//Uses EMS now.
		return true;
	}
	
}
