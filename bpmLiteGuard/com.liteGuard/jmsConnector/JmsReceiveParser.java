package jmsConnector;

import javax.jms.JMSException;
import javax.jms.Message;

import org.apache.xmlbeans.XmlException;

import workers.StartCaseRequestWorker;
import workers.WorkItemRequestWorker;

import com.bpmlite.api.CallBackType;
import com.bpmlite.api.RequestCallBackDocument;
import com.bpmlite.api.RequestCallBackDocument.RequestCallBack;
import com.bpmlite.api.StartCaseDetailsDocument;
import com.bpmlite.api.WorkItemKeyDetailsDocument;

import config.Statics;

public class JmsReceiveParser {


	/*
	 * 
	 * 
	 * This currently recives and parses for
	 * Work_item_key_details. -> add a unquie key for openign forms from the guard.
	 * Set_user_details.. adds a new user
	 * Start_case adds a new case. from bpmLite
	 * 
	 * 
	 */
	
	
	public JmsReceiveParser()
	{

	}
	
	public boolean parseData(Message tibMsg)
	{
		String textRecived = "";
		boolean isSuccess = false;
		
		try {
			textRecived = ((com.tibco.tibjms.TibjmsTextMessage) tibMsg).getText();
			//Do we need to process this...
			if (textRecived.contains(Statics.WORK_ITEM_KEY_DETAILS))
			{
				WorkItemKeyDetailsDocument wDetailsDoc = WorkItemKeyDetailsDocument.Factory.parse(textRecived);
				System.out.println("--> [JMS] Valid WorkItemKeyDetails JMS arrived..");
				
				/*
				 * / We have a valid set of details do now we can send to update the database
				 */
				isSuccess = WorkItemRequestWorker.injectNewKey(wDetailsDoc);
				postBackResults(isSuccess, wDetailsDoc.getWorkItemKeyDetails().getCallBackGuid(),CallBackType.INJECTED_KEY);
				return isSuccess;
			}
			else if (textRecived.contains(Statics.START_CASE_DETAILS))
			{
				StartCaseDetailsDocument startCaseDoc = StartCaseDetailsDocument.Factory.parse(textRecived);
				System.out.println("--> [JMS] Valid StartCaseDetails JMS arrived..");
				/*
				 * We have a valid start case requets.. so setup inital case details.
				 */
				isSuccess = StartCaseRequestWorker.setupInitialDetails(startCaseDoc);
				if (startCaseDoc.getStartCaseDetails().getRequireCallback())
				{
					postBackResults(isSuccess, startCaseDoc.getStartCaseDetails().getCallbackGuidKey(), CallBackType.START_CASE);
				}
				return isSuccess;
			}
			else
			{
				System.out.println("Invalid message recieved : " +textRecived);
				return isSuccess;
			}
		} catch (JMSException e) {
			
			e.printStackTrace();
		} catch (XmlException e) {
			System.out.println("[JMS] -- Xml Parsing error thown..");
			e.printStackTrace();
		}
		
		System.out.println("Text Received : " + textRecived);
		return isSuccess;
	}
	
	
	private void postBackResults(boolean worked, String callbackGuid, CallBackType.Enum callBackType)
	{
		RequestCallBackDocument rCallback = RequestCallBackDocument.Factory.newInstance();
		RequestCallBack addNewRequestCallBack = rCallback.addNewRequestCallBack();
		addNewRequestCallBack.setRequestGuid(callbackGuid);
		addNewRequestCallBack.setWorked(worked);
		addNewRequestCallBack.setType(callBackType);
			
		QueueJMSMessageSender qSender = new QueueJMSMessageSender();
		boolean sendWorked = qSender.sendMessageCheck(Statics.JMS_TOPIC_PUSH, rCallback.xmlText());
		System.out.println("Send to bpmLite results " + sendWorked);
	}
}
	

