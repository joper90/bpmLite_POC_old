package workers;

import guard.models.GlobalDataRequest;
import database.GlobalData;

public class Transformers {

	public static GlobalData requestToGlobalData(GlobalDataRequest globalDataRequest)
	{
		GlobalData ret = new GlobalData();
		
		ret.setName(globalDataRequest.getName());
		ret.setData(globalDataRequest.getData());
		ret.setType(globalDataRequest.getType().toString());

		return ret;
	}
	
}
