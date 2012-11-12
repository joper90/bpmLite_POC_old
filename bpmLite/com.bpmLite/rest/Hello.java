package rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import commonModels.deployment.PAFields;
import commonModels.deployment.PAProcessArtifact;
import commonModels.deployment.PAProcessData;
import commonModels.deployment.PASteps;

@Path("/hello")
public class Hello {

	// This method is called if TEXT_PLAIN is request
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String sayPlainTextHello() {

		return "Hello from BpmLite";
	}

	// This method is called if XML is request
	@GET
	@Produces(MediaType.TEXT_XML)
	public String sayXMLHello() {
		return "<?xml version=\"1.0\"?>" + "<hello> Hello from BpmLite" + "</hello>";
	}

	// This method is called if HTML is request
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String sayHtmlHello() {
		return "<html> " + "<title>" + "Hello from BpmLite" + "</title>"
				+ "<body><h1>" + "Hello from BpmLite" + "</body></h1>" + "</html> ";
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public PAProcessArtifact sayJsonHello() {
		
		//Test get for PA
		PAFields paFields = new PAFields();
		paFields.setFieldId(1);
		paFields.setInitalData("One");
		paFields.setName("FieldOne");
		paFields.setType("STRING");
		
		PAFields paFields1 = new PAFields();
		paFields1.setFieldId(2);
		paFields1.setInitalData("Two");
		paFields1.setName("FieldTWO");
		paFields1.setType("STRING");
		
		PAFields paFields2 = new PAFields();
		paFields2.setFieldId(3);
		paFields2.setInitalData("Three");
		paFields2.setName("3");
		paFields2.setType("INT");
		
		PAFields[] data = new PAFields[3];
		data[0] = paFields;
		data[1] = paFields1;
		data[2] = paFields2;
		
		
		
		
		PASteps stepOne = new PASteps();
		stepOne.setStepId(1);
		stepOne.setStepName("User Task One");
		stepOne.setStepName("USERTASK");
		stepOne.setFieldIds(new int[] {1,2,3});
		stepOne.setPreviousStepId(new int[]{0});
		stepOne.setNextStepId(new int[]{2});
		stepOne.setParticipants(new String[] {"Joe","GVS"});

		PASteps stepTwo = new PASteps();
		stepTwo.setStepId(2);
		stepTwo.setStepName("User Task Two");
		stepTwo.setStepName("USERTASK");
		stepTwo.setFieldIds(new int[] {1,2,3});
		stepTwo.setPreviousStepId(new int[]{1});
		stepTwo.setNextStepId(new int[]{3});
		stepTwo.setParticipants(new String[] {"Joe","GVS","Mike"});

		PASteps[] stepData = new PASteps[2];
		stepData[0] = stepOne;
		stepData[1] = stepTwo;
		
		
		
		PAProcessData process = new PAProcessData();
		process.setAuthor("Joe");
		process.setProcessDescription("Process Test One");
		process.setProcessDescription("Joes First Test Process");
		process.setVersion("1.0.0");
		process.setSteps(stepData);
		
		PAProcessArtifact pa = new PAProcessArtifact();
		pa.setUniqueGuid("UG1234");
		pa.setFields(data);
		pa.setProcessData(process);
		
		return pa;
	}
}
