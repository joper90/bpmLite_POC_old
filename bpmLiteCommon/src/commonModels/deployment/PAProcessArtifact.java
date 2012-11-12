package commonModels.deployment;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class PAProcessArtifact {

	private String uniqueGuid;
	private PAFields[] fields;
	private PAProcessData processData;
	
	public String getUniqueGuid() {
		return uniqueGuid;
	}
	public void setUniqueGuid(String uniqueGuid) {
		this.uniqueGuid = uniqueGuid;
	}
	public PAFields[] getFields() {
		return fields;
	}
	public void setFields(PAFields[] fields) {
		this.fields = fields;
	}
	public PAProcessData getProcessData() {
		return processData;
	}
	public void setProcessData(PAProcessData processData) {
		this.processData = processData;
	}

}
