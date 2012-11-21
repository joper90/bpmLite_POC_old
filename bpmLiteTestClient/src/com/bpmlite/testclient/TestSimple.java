package com.bpmlite.testclient;


import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class TestSimple {

	private static String formData = "";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		TestHello();
		TestRootKeyValidation();
		TestGetFormData();
	  

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
	    formData =  service.path("rest").path("getFormData").queryParam("userId", "UserKey1234").queryParam("requestId", "Guid1234ABC").accept(MediaType.APPLICATION_JSON).get(String.class);
	    System.out.println(formData);
	}
	
	public static void ConvertFormDataToJsonObject()
	{
		
	}

}
