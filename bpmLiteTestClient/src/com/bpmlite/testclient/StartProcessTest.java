package com.bpmlite.testclient;

import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import lite.models.ReturnModel;

import org.apache.xmlbeans.XmlException;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class StartProcessTest {
	
	@Test
	public void testDeploy() throws XmlException
	{
			
		ClientConfig config = new DefaultClientConfig();
	    Client client = Client.create(config);
	    WebResource service = client.resource(getBaseURI());
	    //public Response setDetailsWithJsonString(@QueryParam("userId") String userId, @QueryParam("requestId") String requestId,  @QueryParam("action") String action, CompleteFormData completeFormData)
	    ClientResponse  response = service.path("rest").path("startProcess")
	    										.queryParam("user","root")
	    										.queryParam("password","12345")
	    										.queryParam("processName", "testProcess")
	    										.queryParam("version", "1")
	    										.type(MediaType.APPLICATION_JSON)
	    										.post(ClientResponse.class); 
	    
	    if (response.getStatus() != 201) {
			throw new RuntimeException("Failed : HTTP error code : "
			     + response.getStatus());
		}
	    
	    String output = response.getEntity(String.class);
	    System.out.println("Ret: " + output);
	    
	    //ReturnModel ret = JAXB.unmarshal(new StringReader(output), ReturnModel.class);
	    ReturnModel ret = new Gson().fromJson(output, ReturnModel.class);

	    Assert.assertEquals(ret.isWorked(), "true");
	}
	
	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8188/bpmLite").build();
	}
}
