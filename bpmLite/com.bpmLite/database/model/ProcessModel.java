package database.model;

public class ProcessModel extends BaseModel{

	private String name;
	private String version;
	private String uniqueGuid;
	private Integer startStep;
	private String fieldIds;
	private String globalIds;

	public ProcessModel(){};
	
	
	
	public ProcessModel(String name, String version, String uniqueGuid,
			Integer startStep, String fieldIds, String globalIds) {
		super();
		this.name = name;
		this.version = version;
		this.uniqueGuid = uniqueGuid;
		this.startStep = startStep;
		this.fieldIds = fieldIds;
		this.globalIds = globalIds;
	}



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
	public String getGlobalIds() {
		return globalIds;
	}
	public void setGlobalIds(String globalIds) {
		this.globalIds = globalIds;
	}
	
	
	
}
