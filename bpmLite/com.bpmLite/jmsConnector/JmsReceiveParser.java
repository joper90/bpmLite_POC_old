package jmsConnector;

import javax.jms.JMSException;
import javax.jms.Message;

import org.apache.xmlbeans.XmlException;

import workers.ProcessStepWorker;

import com.bpmlite.api.CallBackType;
import com.bpmlite.api.CallBackType.Enum;
import com.bpmlite.api.RequestCallBackDocument;
import com.bpmlite.api.RequestCallBackDocument.RequestCallBack;
import com.bpmlite.api.ServerCommandDocument;
import com.bpmlite.api.ServerInstruction;

import config.Statics;
import connector.tibbrConnector.TibbrConnector;
import database.DAO.BpmLiteDAO;
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
			if (textRecived.contains(Statics.CALL_BACK_DETAILS))
			{
				RequestCallBackDocument callbackDoc = RequestCallBackDocument.Factory.parse(textRecived);
				//Ok so we need to see if this is for the start case.
				if (parseCallbackDetails(callbackDoc))
				{
					isSuccess=true;
				}
			}
			else if (textRecived.contains(Statics.SERVER_COMMAND)) // technically from local
			{
				//Lets see where to send it
				ServerCommandDocument serverDoc = ServerCommandDocument.Factory.parse(textRecived);
				ServerInstruction.Enum instruction = serverDoc.getServerCommand().getInstruction();
				if (instruction == ServerInstruction.NEXT_STEP)
				{
					//Process the next step...
					ProcessStepWorker.processNextStep(serverDoc.getServerCommand().getCaseId());
				}
				else if (instruction == ServerInstruction.END_PROCESS)
				{
					//Blah
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
		boolean sendWorked = qSender.sendMessageCheck(Statics.JMS_TOPIC_GUARD, rCallback.xmlText());
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
	

