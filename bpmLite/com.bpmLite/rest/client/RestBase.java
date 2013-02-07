package rest.client;

import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import config.StaticsCommon;

public class RestBase {

	
	public static WebResource getWebClient()
	{
		ClientConfig config = new DefaultClientConfig();
	    Client client = Client.create(config);
	    WebResource service = client.resource(UriBuilder.fromUri(StaticsCommon.BPM_GUARD_PATH).build());
	    return service;
	}
}
