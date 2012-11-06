package com.bpmlite.testclient;

import javax.naming.NamingException;

import org.testng.annotations.Test;

import com.bpmlite.api.FieldModeType;
import com.bpmlite.api.StartCaseDetailsDocument;
import com.bpmlite.api.StartCaseDetailsDocument.StartCaseDetails;
import com.bpmlite.api.StartCaseDetailsDocument.StartCaseDetails.FieldDetails;
import com.bpmlite.api.WorkItemKeyDetailsDocument;
import com.bpmlite.api.WorkItemKeyDetailsDocument.WorkItemKeyDetails;

public class EmsTests {


	@Test
	public void testWorkItemKeyDetails() throws NamingException
	{
		String queue = "BpmGuardQueue";
		
		//Trying a bean
		WorkItemKeyDetailsDocument wItemDoc = WorkItemKeyDetailsDocument.Factory.newInstance();
		
		WorkItemKeyDetails wItem = wItemDoc.addNewWorkItemKeyDetails();
		wItem.setProcessId(1111);
		wItem.setStepId(444);
		wItem.setUniqueFormGuid("Guid1234ABC");
		wItem.setUserKey("UserKey1234");
		
		
		QueueJMSMessageSender q = new QueueJMSMessageSender();
		q.sendMessage(queue, wItemDoc.xmlText());
	}
	
	@Test
	public void testSetUsersDetails()
	{
		
	}
	
	@Test
	public void test() throws NamingException
	{
		String queue = "BpmGuardQueue";
		
		StartCaseDetailsDocument startCaseDoc = StartCaseDetailsDocument.Factory.newInstance();
		
		StartCaseDetails startCase = startCaseDoc.addNewStartCaseDetails();
		startCase.setProcessId(11111);
		FieldDetails fDetails = startCase.addNewFieldDetails();
		fDetails.setFieldId(12345);
		fDetails.setFieldType(FieldModeType.STRING);
		fDetails.setInitalValue("Test Field");
		fDetails.setIsGlobal(false);
		
		System.out.println(startCaseDoc.xmlText());
		
		
		QueueJMSMessageSender q = new QueueJMSMessageSender();
		q.sendMessage(queue, startCaseDoc.xmlText());
	}

}
