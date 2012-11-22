package engineConnector;

import config.Statics;

public class EngineRestConnector {

	public static boolean isAlive()
	{
		//TODO: add server alive check
		//Checks if the server is alive and up.
		//Do a rest call and get the results.
		if (Statics.TEST_MODE)
		{
			return true;
		}
		else
		{
			//Check if the server is up
			//Call Rest isAlive.
			return true;
		}
		
	}
		
}
