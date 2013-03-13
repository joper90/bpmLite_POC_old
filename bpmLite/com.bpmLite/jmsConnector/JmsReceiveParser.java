package jmsConnector;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.naming.NamingException;

import org.apache.xmlbeans.XmlException;

import workers.ProcessStepWorker;

import com.bpmlite.api.CallBackType;
import com.bpmlite.api.CallBackType.Enum;
import com.bpmlite.api.CompleteWorkItemRequestDocument;
import com.bpmlite.api.CompleteWorkItemRequestDocument.CompleteWorkItemRequest;
import com.bpmlite.api.RequestCallBackDocument;
import com.bpmlite.api.RequestCallBackDocument.RequestCallBack;
import com.bpmlite.api.ServerCommandDocument.ServerCommand;
import com.bpmlite.api.ServerCommandDocument;
import com.bpmlite.api.ServerInstruction;

import config.StaticsCommon;
import connector.tibbrConnector.TibbrConnector;
import database.DAO.BpmLiteDAO;
import database.model.ProcessInstanceModel;
import database.model.StepDataModel;
import database.model.UserModel;

public class JmsReceiveParser {


	
	public JmsReceiveParser()
	{

	}
	
	public boolean parseData(Message tibMsg)
	{
		String textRecived = "";
		boolean isSuccess = false;
		
		try {
			textRecived = ((com.tibco.tibjms.TibjmsTextMessage) tibMsg).getText();
			
			//Need to find is a callback or a complete.
			
			//Parse into a callback message.
			if (textRecived.contains(StaticsCommon.CALL_BACK_DETAILS))
			{
				RequestCallBackDocument callbackDoc = RequestCallBackDocument.Factory.parse(textRecived);
				//Ok so we need to see if this is for the start case.
				if (parseCallbackDetails(callbackDoc))
				{
					isSuccess=true;
				}
			}
			else if (textRecived.contains(StaticsCommon.SERVER_COMMAND)) // technically from local
			{
				//Lets see where to send it
				ServerCommandDocument serverDoc = ServerCommandDocument.Factory.parse(textRecived);
				ServerInstruction.Enum instruction = serverDoc.getServerCommand().getInstruction();
				if (instruction == ServerInstruction.NEXT_STEP)
				{
					//Process the next step...
					try {
						ProcessStepWorker.processNextStep(serverDoc.getServerCommand().getCaseId());
					} catch (Exception e) {
						// Lets throw this again on the queue.
						QueueJMSMessageSender q = new QueueJMSMessageSender();
						try {
							q.sendMessage(StaticsCommon.JMS_TOPIC_SERVER, textRecived);
						} catch (NamingException ne) {
							// TODO Auto-generated catch block
							return false;
						}
					}
				}
				else if (instruction == ServerInstruction.END_PROCESS)
				{
					//Blah
				}
			}else if (textRecived.contains(StaticsCommon.COMPLETE_COMMAND))
			{
				//Complete command from the guard, so we can move to the next step.
				CompleteWorkItemRequestDocument comp = CompleteWorkItemRequestDocument.Factory.parse(textRecived);
				int processId = comp.getCompleteWorkItemRequest().getProcessId();
				int stepId = comp.getCompleteWorkItemRequest().getStepId();
				String caseId = comp.getCompleteWorkItemRequest().getCaseId();
				
				//So we know the guard has the updated values, we need to move onto the next step now.
				//1) get the current step, where we are.
				StepDataModel stepByStepId = BpmLiteDAO.instance.getStepDataDAO().getStepByStepId(stepId, processId);
				String nextId = stepByStepId.getNextId();
				
				//Update the processId to the now current step that we are on.
				ProcessInstanceModel byCaseId = BpmLiteDAO.instance.getProcessInstanceDAO().getByCaseId(caseId);
				byCaseId.setCurrentStepId(new Integer(nextId));
				
				boolean updateModel = BpmLiteDAO.instance.getProcessInstanceDAO().updateModel(byCaseId);
				
				if (updateModel) //updated the new 
				{
					ServerCommandDocument serverCommand = ServerCommandDocument.Factory.newInstance();
					ServerCommand c = serverCommand.addNewServerCommand();
					c.setCaseId(caseId);
					
					//Now need to see if we have ended the process? i.e, is the nex step a -1 type.
					if (new Integer(nextId) == StaticsCommon.PROCESS_ENDED)
					{
						c.setInstruction(ServerInstruction.END_PROCESS);
					}else
					{
						//Now updated. so we need to call the Server to setnd out the next item.
						c.setInstruction(ServerInstruction.NEXT_STEP);
					}

					QueueJMSMessageSender q = new QueueJMSMessageSender();
					try {
						q.sendMessage(StaticsCommon.JMS_TOPIC_SERVER, serverCommand.xmlText());
					} catch (NamingException e) {
						// TODO Auto-generated catch block
						System.out.println("[completed workItem] Cannot post next Step message");
						return false;
					}
				}
				else
				{
					//log out an error.. 
				}
				
			}
				
			
			
		} catch (JMSException e) {
			
			e.printStackTrace();
		} catch (XmlException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		System.out.println("Text Received : " + textRecived);
		return isSuccess;
	}
	
	
	private void postBackResults(boolean worked, String callbackGuid)
	{
		RequestCallBackDocument rCallback = RequestCallBackDocument.Factory.newInstance();
		RequestCallBack addNewRequestCallBack = rCallback.addNewRequestCallBack();
		addNewRequestCallBack.setRequestGuid(callbackGuid);
		addNewRequestCallBack.setWorked(worked);
		
		QueueJMSMessageSender qSender = new QueueJMSMessageSender();
		boolean sendWorked = qSender.sendMessageCheck(StaticsCommon.JMS_TOPIC_GUARD, rCallback.xmlText());
		System.out.println("Send to bpmLite results " + sendWorked);
		
	}
	
	private boolean parseCallbackDetails(RequestCallBackDocument callbackDoc)
	{
		Enum type =callbackDoc.getRequestCallBack().getType();
		if (type == CallBackType.START_CASE)
		{
			//get the callback key
			String key = callbackDoc.getRequestCallBack().getRequestGuid();
			//Now see if in the database and update to valid if done..
			return BpmLiteDAO.instance.getProcessInstanceDAO().updateInitialData(key, true);
		}
		else if (type == CallBackType.INJECTED_KEY)
		{
			//Now we need to find out what was sent and post the tibbr message.
			String guid = callbackDoc.getRequestCallBack().getRequestGuid();
			String userGuid = callbackDoc.getRequestCallBack().getUserGuid();
			
			//So we now have the userGuid and the guid.. lets get the userDetails for posting.
			UserModel user = BpmLiteDAO.instance.getUserDAO().findModelfromGuid(userGuid);
			
			TibbrConnector.postWorkItemMessage(guid, user);
			
		}
		
		return true;
	}
}
	

