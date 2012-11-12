package com.bpmlite.testclient;

import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.testng.annotations.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class BpmLiteServerTest {

	
	@Test
	public void quickTest()
	{
		ClientConfig config = new DefaultClientConfig();
	    Client client = Client.create(config);
	    WebResource service = client.resource(getBaseURI());
	    // Fluent interfaces

	    //JSON
	    System.out.println(service.path("rest").path("hello").accept(MediaType.APPLICATION_JSON).get(String.class));
	}
	
	
	
	
	
	
	
	
	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8188/bpmLite").build();
	}
}
