package com.bpmlite.guard.testclient;

import guard.models.CompleteFormData;

import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class CompleteTest {

	
	public static String USER_ID = "12345"; //key
	public static String GUID = "691d481f-58ed-4b1b-a0ab-bc1fcb1b1a89";			//The guid
	
	
	private static String formData = "";
	private static CompleteFormData fData;
	
	@Test
	public static void TestGetFormData()
	{
		ClientConfig config = new DefaultClientConfig();
	    Client client = Client.create(config);
	    WebResource service = client.resource(getBaseURI());
	    formData =  service.path("rest").path("getFormData")
	    			.queryParam("userId", USER_ID)
	    			.queryParam("requestId", GUID)
	    			.accept(MediaType.APPLICATION_JSON)
	    			.get(String.class);
	    System.out.println(formData);
	}
	
	@Test(dependsOnMethods = {"TestGetFormData"})
	public static void PostDataToServerCompleteWorkItem()
	{

		fData = new Gson().fromJson(formData, CompleteFormData.class);
		System.out.println("Converted ==> " + fData.toString());
		
		//adding test values
		fData.getByName("TestText").setFieldData("DummyData");
		fData.getByName("IntTest").setFieldData("2");
		
		
		ClientConfig config = new DefaultClientConfig();
	    Client client = Client.create(config);
	    WebResource service = client.resource(getBaseURI());
	    //public Response setDetailsWithJsonString(@QueryParam("userId") String userId, @QueryParam("requestId") String requestId,  @QueryParam("action") String action, CompleteFormData completeFormData)
	    ClientResponse  response = service.path("rest").path("completeWorkItem")
	    										.queryParam("userId",USER_ID)
	    										.queryParam("requestId",GUID)
	    										.queryParam("action","SUBMIT")
	    										.type(MediaType.APPLICATION_JSON)
	    										.post(ClientResponse.class, fData); //ret and the data post.
	    
	    if (response.getStatus() != 201) {
			throw new RuntimeException("Failed : HTTP error code : "
			     + response.getStatus());
		}
	    
	    String output = response.getEntity(String.class);
	    
	    
	    
	    System.out.println("Ret: " + output);
		
	}
	
	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8088/bpmLiteGuard").build();
	}
}
