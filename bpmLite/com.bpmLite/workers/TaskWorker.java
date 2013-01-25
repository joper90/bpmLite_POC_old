package workers;

import java.util.ArrayList;

import javax.naming.NamingException;

import jmsConnector.QueueJMSMessageSender;

import com.bpmlite.api.WorkItemKeyDetailsDocument;
import com.bpmlite.api.WorkItemKeyDetailsDocument.WorkItemKeyDetails;
import com.bpmlite.api.WorkItemKeyDetailsDocument.WorkItemKeyDetails.KeyFieldDetails;

import config.Statics;
import database.DAO.BpmLiteDAO;
import database.model.ProcessInstanceModel;
import database.model.StepDataModel;

public class TaskWorker {

	public boolean processUserTask(ProcessInstanceModel pModel, StepDataModel stepData)
	{
		//Find out if inital, so we can check the inital data has been correctly set for this.
		if (new Integer(stepData.getStepId()) == Statics.START_STEP_INITAL_VALUE)
		{
			//Need to see if the inital data callback has been completed.
			boolean isStartedCorrectly = spinLooper(pModel.getCaseId(), Statics.LOOP_SLEEP_TIME, Statics.LOOP_SLEEP_MAX_COUNT);
			if (!isStartedCorrectly) return false;
			
		}
		
		//First need to find the user
		String userList = stepData.getUserList(); //single user to start with.
		String tempSingleUser =  userList.split(",")[0]; //TODO: ALLOW MULTI USER DESTINATIONS
		String userKey = BpmLiteDAO.instance.getUserDAO().findGuidByName(tempSingleUser);		
		
		
		
		//So we are not the first step (or we are the first step but have a valid callback, so we can
		//now process the step as we want..
		
		String guidKey = UtilWorker.getRandomGuid();
		
		//We need to do the following.
		//Push a key to the guard + the data?
		WorkItemKeyDetailsDocument wItemDoc = produceWorkKeyDocument(pModel, stepData, guidKey, userKey);
		
		
		QueueJMSMessageSender q = new QueueJMSMessageSender();
		try {
			q.sendMessage(Statics.JMS_TOPIC_GUARD, wItemDoc.xmlText());
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		//Push a message out to the tibbr server.
		
		return true;
	}
	
	
	public boolean spinLooper(String caseId, long sleepTime, int iterations)
	{
		boolean currentState = BpmLiteDAO.instance.getProcessInstanceDAO().isInitalDataSet(caseId);
		if (currentState) return true;
		// if not go into the loop.
		
		for (int i = 0; i < iterations; i++)//x times around the loop.
		{
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			
			currentState = BpmLiteDAO.instance.getProcessInstanceDAO().isInitalDataSet(caseId);
			if (currentState) return true;
			
		}
		return false;
	}
	
	private WorkItemKeyDetailsDocument produceWorkKeyDocument(ProcessInstanceModel pModel, 
																StepDataModel stepData,
																String guid,
																String userKey)
	{
		WorkItemKeyDetailsDocument wItemDoc = WorkItemKeyDetailsDocument.Factory.newInstance();
		
		WorkItemKeyDetails wItem = wItemDoc.addNewWorkItemKeyDetails();
		wItem.setProcessId(pModel.getProcessId());
		wItem.setStepId(new Integer(stepData.getStepId()));
		wItem.setCaseId(pModel.getCaseId());
		wItem.setUniqueFormGuid(guid);// generate unique from guid.
		
		wItem.setUserKey(userKey);
		wItem.setDisplayOnly(stepData.getDisplayOnly());
		wItem.setRootKey(Statics.ADMIN_KEY);
		
		
		//Get the fields for the step.
		String[] fieldData = stepData.getFieldData().split(",");
		String[] globalData = stepData.getGlobalData().split(",");
		
		ArrayList<KeyFieldDetails> kDetails = new ArrayList<KeyFieldDetails>();
		
		for (String field : fieldData)
		{
			KeyFieldDetails k = KeyFieldDetails.Factory.newInstance();
			//Find if a global.
			boolean isGlobal = false;
			
			int fieldAsInt = new Integer(field);
			
			for (String g : globalData)
			{
				
				if (new Integer(g) == fieldAsInt)
				{
					isGlobal = true;
					break;
				}
			}
			
			k.setFieldId(fieldAsInt);
			k.setIsGlobal(isGlobal);
			
			kDetails.add(k);

		}
		
		//All added, not add to the list.
		KeyFieldDetails[] kFieldArray = new KeyFieldDetails[kDetails.size()];
		kDetails.toArray(kFieldArray);
			
		wItem.setKeyFieldDetailsArray(kFieldArray);
		return wItemDoc;
	}
	
}
