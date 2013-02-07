package com.bpmlite.guard.testclient;


import guard.models.CompleteFormData;
import guard.models.GlobalDataRequest;

import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import lite.models.ReturnModel;

import com.bpmlite.api.FieldModeType;
import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class RestTests {

	private static String formData = "";
	private static CompleteFormData fData;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

/*		TestHello();
		TestRootKeyValidation();
		TestGetFormData();
		ConvertFormDataToJsonObject();
		PostDataToServerCompleteWorkItem();*/
	  
		PostGlobalDataTest();

	}

	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8088/bpmLiteGuard").build();
	}
	
	private static void TestHello()
	{
		ClientConfig config = new DefaultClientConfig();
	    Client client = Client.create(config);
	    WebResource service = client.resource(getBaseURI());
	    // Fluent interfaces
	    System.out.println(service.path("rest").path("hello").accept(MediaType.TEXT_PLAIN).get(ClientResponse.class).toString());
	    // Get plain text
	    System.out.println(service.path("rest").path("hello").accept(MediaType.TEXT_PLAIN).get(String.class));
	    // Get XML
	    System.out.println(service.path("rest").path("hello").accept(MediaType.TEXT_XML).get(String.class));
	    // The HTML
	    System.out.println(service.path("rest").path("hello").accept(MediaType.TEXT_HTML).get(String.class));    
	    //JSON
	    System.out.println(service.path("rest").path("hello").accept(MediaType.APPLICATION_JSON).get(String.class));
	}
	
	public static void TestRootKeyValidation()
	{
		ClientConfig config = new DefaultClientConfig();
	    Client client = Client.create(config);
	    WebResource service = client.resource(getBaseURI());
	    System.out.println(service.path("rest").path("validateGuardServer").queryParam("key", "12345").accept(MediaType.APPLICATION_JSON).get(String.class));
	}
	
/*	public static void TestSetUserDetails()
	{
		String json = "{\"userGuid\":\"12345ABC\",\"userKey\":\"12345\",\"userName\":\"joeh\"}";
		ClientConfig config = new DefaultClientConfig();
	    Client client = Client.create(config);
	    WebResource service = client.resource(getBaseURI());
	    //System.out.println(service.path("rest").path("setUserDetails").put(String.class, json).accept(MediaType.APPLICATION_JSON).get(String.class));
	    String response = service.path("rest").path("setUserDetails").queryParam("rootKey","12345").type(MediaType.APPLICATION_JSON).post(String.class, json);
	    System.out.println("Ret: " + response);
	    
	} */
	
	public static void TestGetFormData()
	{
		ClientConfig config = new DefaultClientConfig();
	    Client client = Client.create(config);
	    WebResource service = client.resource(getBaseURI());
	    formData =  service.path("rest").path("getFormData")
	    			.queryParam("userId", "UserKey1234")
	    			.queryParam("requestId", "Guid1234ABC")
	    			.accept(MediaType.APPLICATION_JSON)
	    			.get(String.class);
	    System.out.println(formData);
	}
	
	public static void ConvertFormDataToJsonObject()
	{
		fData = new Gson().fromJson(formData, CompleteFormData.class);
		System.out.println(fData);
	}
	
	public static void PostDataToServerCompleteWorkItem()
	{
		ClientConfig config = new DefaultClientConfig();
	    Client client = Client.create(config);
	    WebResource service = client.resource(getBaseURI());
	    //public Response setDetailsWithJsonString(@QueryParam("userId") String userId, @QueryParam("requestId") String requestId,  @QueryParam("action") String action, CompleteFormData completeFormData)
	    ClientResponse  response = service.path("rest").path("completeWorkItem")
	    										.queryParam("userId","UserKey1234")
	    										.queryParam("requestId","Guid1234ABC")
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
	
	public static void PostGlobalDataTest()
	{
		GlobalDataRequest gR = new GlobalDataRequest();
		gR.setName("TestGlobal");
		gR.setData("Joe");
		gR.setType(FieldModeType.STRING.toString());
		
		ClientConfig config = new DefaultClientConfig();
	    Client client = Client.create(config);
	    WebResource service = client.resource(getBaseURI());
	    
	    //public ReturnModel createGlobal(@QueryParam("userId") String userId, @QueryParam("key") String key, GlobalDataRequest globalDataRequest)
	    ReturnModel  response = service.path("rest").path("createGlobals")
				.queryParam("userId","root")
				.queryParam("key","12345")
				.type(MediaType.APPLICATION_JSON)
				.post(ReturnModel.class, gR);

	    
	    
	    
	    System.out.println("Ret: " + response.getExtendedData().get("fieldId"));
	}
	
	

}
