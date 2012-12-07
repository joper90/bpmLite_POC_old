package com.bpmlite.testclient;

import java.net.URI;

import javax.naming.NamingException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import jms.QueueJMSMessageSender;

import org.testng.annotations.Test;

import com.bpmlite.api.RequestCallBackDocument;
import com.bpmlite.api.RequestCallBackDocument.RequestCallBack;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class BpmLiteServerTest {

	@Test
	public void quickEms() throws NamingException
	{
		QueueJMSMessageSender q = new QueueJMSMessageSender();
		q.sendMessage("BpmLiteQueue", "TestData");
	}
	
	
	public void quickTest() {
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(getBaseURI());
		// Fluent interfaces

		RequestCallBackDocument r = RequestCallBackDocument.Factory
				.newInstance();
		RequestCallBack callBack = r.addNewRequestCallBack();
		callBack.setRequestGuid("joe");

		ClientResponse response = service.path("rest").path("TestBean")
				.type(MediaType.APPLICATION_XML)
				.post(ClientResponse.class, r.xmlText());

		if (response.getStatus() != 201 ) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
		}

		String output = response.getEntity(String.class);

		System.out.println(output);
	}

	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8188/bpmLite").build();
	}
}
