package com.bpmlite.guard.testclient;

import javax.naming.NamingException;

import jms.QueueJMSMessageSender;

import org.testng.annotations.Test;

import com.bpmlite.api.FieldModeType;
import com.bpmlite.api.StartCaseDetailsDocument;
import com.bpmlite.api.StartCaseDetailsDocument.StartCaseDetails;
import com.bpmlite.api.StartCaseDetailsDocument.StartCaseDetails.FieldDetails;
import com.bpmlite.api.WorkItemKeyDetailsDocument;
import com.bpmlite.api.WorkItemKeyDetailsDocument.WorkItemKeyDetails;
import com.bpmlite.api.WorkItemKeyDetailsDocument.WorkItemKeyDetails.KeyFieldDetails;

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
		wItem.setCaseId("BPM222");
		wItem.setUniqueFormGuid("Guid1234ABC");
		wItem.setUserKey("UserKey1234");
		wItem.setDisplayOnly("1,2");
		wItem.setRootKey("12345");
		
		//Add field details.
		KeyFieldDetails fOne = KeyFieldDetails.Factory.newInstance();
		KeyFieldDetails fTwo = KeyFieldDetails.Factory.newInstance();
		KeyFieldDetails fThree = KeyFieldDetails.Factory.newInstance();
		KeyFieldDetails fFour = KeyFieldDetails.Factory.newInstance();
		fOne.setFieldId(1);
		fOne.setIsGlobal(false);

		fTwo.setFieldId(2);
		fTwo.setIsGlobal(false);
		
		fThree.setFieldId(3);
		fThree.setIsGlobal(false);
		
		fFour.setFieldId(101);
		fFour.setIsGlobal(true);
		
		KeyFieldDetails[] keyFieldArray = {fOne,fTwo,fThree,fFour};
		wItem.setKeyFieldDetailsArray(keyFieldArray);
				
		
		QueueJMSMessageSender q = new QueueJMSMessageSender();
		q.sendMessage(queue, wItemDoc.xmlText());
	}
	
	@Test
	public void test() throws NamingException
	{
		String queue = "BpmGuardQueue";
		
		StartCaseDetailsDocument startCaseDoc = StartCaseDetailsDocument.Factory.newInstance();
		
		StartCaseDetails startCase = startCaseDoc.addNewStartCaseDetails();
		startCase.setProcessId(11111);
		startCase.setCallbackGuidKey("12345");
		startCase.setCaseId("222");
		startCase.setRequireCallback(false);
		
		FieldDetails fOne = startCase.addNewFieldDetails();
		fOne.setName("Name");
		fOne.setFieldId(1);
		fOne.setFieldType(FieldModeType.STRING);
		fOne.setInitalValue("Test Intial Field");

		FieldDetails fTwo = startCase.addNewFieldDetails();
		fTwo.setName("Age");
		fTwo.setFieldId(2);
		fTwo.setFieldType(FieldModeType.INT);
		fTwo.setInitalValue("3");

		
		FieldDetails fThree = startCase.addNewFieldDetails();
		fThree.setName("StartData");
		fThree.setFieldId(3);
		fThree.setFieldType(FieldModeType.STRING);
		//No inital data
	
		
		FieldDetails fFour = startCase.addNewFieldDetails();
		fFour.setName("CompletData");
		fFour.setFieldId(101);
		fFour.setFieldType(FieldModeType.STRING);
		fFour.setInitalValue("End of March");
		
		
		System.out.println(startCaseDoc.xmlText());
		
		
		QueueJMSMessageSender q = new QueueJMSMessageSender();
		q.sendMessage(queue, startCaseDoc.xmlText());
	}

}
