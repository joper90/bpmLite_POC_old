package workers;

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
		
		//So we are not the first step (or we are the first step but have a valid callback, so we can
		//now process the step as we want..
		
		//We need to do the following.
		//Push a key to the guard + the data
		
		
		
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
	
}
