package guard.models;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class Validation {

	private String version;
	private boolean isAlive;
	private boolean isValid;
	
	public Validation()
	{
		
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
	
	
	
	
}
