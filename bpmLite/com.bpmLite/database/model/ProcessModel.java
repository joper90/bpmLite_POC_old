package database.model;

public class ProcessModel extends BaseModel{

	private String name;
	private String version;
	private String uniqueGuid;
	private Integer startStep;
	private String fieldIds;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getUniqueGuid() {
		return uniqueGuid;
	}
	public void setUniqueGuid(String uniqueGuid) {
		this.uniqueGuid = uniqueGuid;
	}
	public Integer getStartStep() {
		return startStep;
	}
	public void setStartStep(Integer startStep) {
		this.startStep = startStep;
	}
	public String getFieldIds() {
		return fieldIds;
	}
	public void setFieldIds(String fieldIds) {
		this.fieldIds = fieldIds;
	}
	
	
	
}
