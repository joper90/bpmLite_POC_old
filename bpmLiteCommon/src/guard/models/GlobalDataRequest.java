package guard.models;

import javax.xml.bind.annotation.XmlRootElement;

import com.bpmlite.api.FieldModeType;

@XmlRootElement
public class GlobalDataRequest {

	private String name;
	private String data;
	private String type = FieldModeType.STRING.toString();
	
	public GlobalDataRequest()
	{
		
	}
	

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	
	
	
}
