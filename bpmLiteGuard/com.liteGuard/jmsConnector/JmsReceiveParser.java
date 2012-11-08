package jmsConnector;

import javax.jms.JMSException;
import javax.jms.Message;

import org.apache.xmlbeans.XmlException;

import workers.SetUserDetailsWorker;
import workers.StartCaseRequestWorker;
import workers.ValidationWorker;
import workers.WorkItemRequestWorker;

import com.bpmlite.api.RequestCallBackDocument;
import com.bpmlite.api.RequestCallBackDocument.RequestCallBack;
import com.bpmlite.api.SetUserDetailsRequestDocument;
import com.bpmlite.api.StartCaseDetailsDocument;
import com.bpmlite.api.WorkItemKeyDetailsDocument;

import config.Statics;

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
			//Do we need to process this...
			if (textRecived.contains(Statics.WORK_ITEM_KEY_DETAILS))
			{
				WorkItemKeyDetailsDocument wDetailsDoc = WorkItemKeyDetailsDocument.Factory.parse(textRecived);
				System.out.println("--> [JMS] Valid WorkItemKeyDetails JMS arrived..");
				
				/*
				 * / We have a valid set of details do now we can send to update the database
				 */
				isSuccess = WorkItemRequestWorker.injectNewKey(wDetailsDoc);
				//This is a fire and forget, not need to return here..
				return isSuccess;
			}
			else if (textRecived.contains(Statics.SET_USER_DETAILS))
			{
				SetUserDetailsRequestDocument setUserDetailsDoc = SetUserDetailsRequestDocument.Factory.parse(textRecived);
				System.out.println("--> [JMS] Valid SetUserDetailsRequest JMS arrived..");
				/*
				 * we now have a valid user add request, so lets attempt to add all.
				 */
				
				isSuccess = SetUserDetailsWorker.validateNewUserData(setUserDetailsDoc);
				postBackResults(isSuccess, setUserDetailsDoc.getSetUserDetailsRequest().getCallbackGuidKey());
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
				postBackResults(isSuccess, startCaseDoc.getStartCaseDetails().getCallbackGuidKey());
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
			// TODO Correctly handle and error when xml fails to parse.
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
		boolean sendWorked = qSender.sendMessageCheck(Statics.JMS_TOPIC_PUSH, rCallback.xmlText());
		System.out.println("Send to bpmLite results " + sendWorked);
		
	}
}
	

