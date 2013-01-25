package workers;

import java.util.Arrays;
import java.util.UUID;

import com.bpmlite.api.FieldModeType;

import config.Statics;
import database.DAO.BpmLiteDAO;
import database.model.ServerInfoModel;

public class UtilWorker {

	
	public static String arrayToString(Object[] array)
	{
		String string = Arrays.toString(array);
		string = string.substring(1,string.length() - 1); //Strip off [] 
		return string;
	}
	
	public static String getAndThenIncrementCaseId() //thread lock for uniqueness??
	{
		String currentValue = BpmLiteDAO.instance.getServerInfoDAO().getValueByName(Statics.CASE_ID);

		//Split down
		int currentCaseIdCount = new Integer(currentValue.substring(currentValue.indexOf(":")));

		String newValue = "bpm:" + String.format("%06d", ++currentCaseIdCount);
		ServerInfoModel rootInfo = new ServerInfoModel();
		rootInfo.setName(Statics.CASE_ID);
		rootInfo.setValue(newValue);
		BpmLiteDAO.instance.getServerInfoDAO().insertData(rootInfo);
				
		return currentValue;
		
	}
	
	public static String getRandomGuid()
	{
		return UUID.randomUUID().toString();
	}

	
	public static FieldModeType.Enum getFieldModeFromString(String enumAsString)
	{
		return FieldModeType.Enum.forString(enumAsString);
	}
	
}
