package commonModels.deployment;



public class PAProcessData {

	private String processName;
	private String processDescription;
	private String version;
	private String author;
	
	private PAStep[] steps;	
	
	
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public String getProcessDescription() {
		return processDescription;
	}
	public void setProcessDescription(String processDescription) {
		this.processDescription = processDescription;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public PAStep[] getSteps() {
		return steps;
	}
	public void setSteps(PAStep[] steps) {
		this.steps = steps;
	}
	
	
}