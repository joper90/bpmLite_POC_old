package workers;

import javax.naming.NamingException;

import jmsConnector.QueueJMSMessageSender;
import model.ReturnModel;

import com.bpmlite.api.FieldModeType;
import com.bpmlite.api.ServerCommandDocument;
import com.bpmlite.api.ServerInstruction;
import com.bpmlite.api.ServerCommandDocument.ServerCommand;
import com.bpmlite.api.StartCaseDetailsDocument;
import com.bpmlite.api.StartCaseDetailsDocument.StartCaseDetails;
import com.bpmlite.api.StartCaseDetailsDocument.StartCaseDetails.FieldDetails;

import config.Statics;
import database.DAO.BpmLiteDAO;
import database.model.FieldDataModel;
import database.model.ProcessInstanceModel;
import database.model.ProcessModel;

public class StartProcessWorker {

	public static ReturnModel startProcess(String name, String password, String processName, String version)
	{
		ReturnModel retModel = new ReturnModel();
		//First check we have a valid user/id.
		//And they can start a process.
		if (ValidationWorker.validateUser(name, password))
		{
			String caseId = startProcessByName(processName,version);
			if (caseId != null)
			{

				//Process is started.. So just fire a message to the server (ourtselfs to process the first step).
				ServerCommandDocument serverCommand = ServerCommandDocument.Factory.newInstance();
				ServerCommand c = serverCommand.addNewServerCommand();
				c.setCaseId(caseId);
				c.setInstruction(ServerInstruction.NEXT_STEP);
				
				QueueJMSMessageSender q = new QueueJMSMessageSender();
				try {
					q.sendMessage(Statics.JMS_TOPIC_SERVER, serverCommand.xmlText());
				} catch (NamingException e) {
					// TODO Auto-generated catch block
					retModel.setReason("[Start process] Cannot post next Step message");
				}
				
				//All worked
				retModel.setValues("Case Started", caseId, true);
				
			}else
			{
				retModel.setReason("[Start process] Invalid Process, cannot be started..");
			}
		}
		else
		{
			retModel.setReason("[Start process] Invalid User..");
		}

		return retModel;
	}
	
	private static String startProcessByName(String processName, String version)
	{
		//First validate the procesName.
		ProcessModel pModel = BpmLiteDAO.instance.getProcessDAO().getByNameAndVersion(processName, version);
		String caseId = null;
		if (pModel != null)
		{			
			//First create the local data, then send the field info over the wire..  //possible need thread lock?
			caseId = UtilWorker.getAndThenIncrementCaseId();
			//Inject into process_instace to kick start the whole case start
			
			ProcessInstanceModel pInstance = new ProcessInstanceModel();
			pInstance.setCaseId(caseId);
			pInstance.setProcessId(pModel.getId());
			pInstance.setCurrentStepId(pModel.getStartStep());
			pInstance.setInitialDataSet(false);
			
			String callbackGuid = UtilWorker.getRandomGuid();
			pInstance.setGuidCallback(callbackGuid);
			
			boolean insertData = BpmLiteDAO.instance.getProcessInstanceDAO().insertData(pInstance);
			
			if (insertData)
			{
				//Now atttempt to populate the inistal data on the guard server.
				if (!populateGuardServer(pModel, pInstance))
				{
					return null;
				}
			}
			
			//All seemed to work.
			return caseId;
			
		}
	
		return null;
	}
	
	public static boolean populateGuardServer(ProcessModel pModel,ProcessInstanceModel pInstance)
	{
		//Populate and then send the message.. thats all we care about.. 
		StartCaseDetailsDocument startCaseDoc = StartCaseDetailsDocument.Factory.newInstance();
		
		StartCaseDetails startCase = startCaseDoc.addNewStartCaseDetails();
		startCase.setProcessId(pModel.getId());
		startCase.setCallbackGuidKey(pInstance.getGuidCallback());
		startCase.setCaseId(new Integer(pInstance.getCaseId()));
		startCase.setRequireCallback(true);
		
		//Get the list of field Ids.
		String fieldIds = pModel.getFieldIds();
		String[] fieldIdArray = fieldIds.split(",");
		
		//Fill in the rest of the stuff here.. !
		for (String f : fieldIdArray)
		{
			int fInt = new Integer(f);
			//Get the field information or is it a global.
			FieldDataModel fData = BpmLiteDAO.instance.getFieldDataDAO().getFieldDataByFieldId(fInt);
			
			
			//get the field data with this item. Ignore globals, as these have already been populated.
			FieldDetails fDetails = startCase.addNewFieldDetails();
			
			//fDetails.setName(arg0)
			fDetails.setName(fData.getName());
			fDetails.setFieldId(fData.getFieldId());
			fDetails.setFieldType(FieldModeType.Enum.forString(fData.getType()));
			fDetails.setInitalValue(fData.getInitialData());
			
		}
		
		System.out.println(startCaseDoc.xmlText());
		
		
		QueueJMSMessageSender q = new QueueJMSMessageSender();
		try {
			q.sendMessage(Statics.JMS_TOPIC_GUARD, startCaseDoc.xmlText());
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		
		
		return true;
	}
	
}
