package jmsConnector;

import javax.jms.JMSException;
import javax.jms.Message;

import com.bpmlite.api.RequestCallBackDocument;
import com.bpmlite.api.RequestCallBackDocument.RequestCallBack;

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
			
		} catch (JMSException e) {
			
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
	

