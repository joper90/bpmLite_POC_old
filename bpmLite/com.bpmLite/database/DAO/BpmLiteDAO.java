package database.DAO;

public class BpmLiteDAO {

	public static BpmLiteDAO instance = new BpmLiteDAO();
	
	private AssignedRoleDAO assignedRoleDAO = new AssignedRoleDAO();
	private AuditDAO auditDAO = new AuditDAO();
	private JoinStatusDAO joinStatusDAO = new JoinStatusDAO();
	private ProcessDAO processDAO = new ProcessDAO();
	private ProcessInstanceDAO processInstanceDAO = new ProcessInstanceDAO();
	private RolesDAO rolesDAO = new RolesDAO();
	private StepDataDAO stepDataDAO = new StepDataDAO();
	private UserDAO userDAO = new UserDAO();
	private UserGroupsDAO userGroupsDAO = new UserGroupsDAO();
	private UserSkillsDAO userSkillsDAO = new UserSkillsDAO();
	private ServerInfoDAO serverInfoDAO = new ServerInfoDAO();
	private FieldDataDAO fieldDataDAO = new FieldDataDAO();
	private GlobalMappingsDAO globalMappingsDAO = new GlobalMappingsDAO();
	
	
	private BpmLiteDAO()
	{
		
	}



	public AssignedRoleDAO getAssignedRoleDAO() {
		return assignedRoleDAO;
	}


	public AuditDAO getAuditDAO() {
		return auditDAO;
	}


	public JoinStatusDAO getJoinStatusDAO() {
		return joinStatusDAO;
	}


	public ProcessDAO getProcessDAO() {
		return processDAO;
	}


	public ProcessInstanceDAO getProcessInstanceDAO() {
		return processInstanceDAO;
	}


	public RolesDAO getRolesDAO() {
		return rolesDAO;
	}


	public StepDataDAO getStepDataDAO() {
		return stepDataDAO;
	}


	public UserDAO getUserDAO() {
		return userDAO;
	}


	public UserGroupsDAO getUserGroupsDAO() {
		return userGroupsDAO;
	}


	public UserSkillsDAO getUserSkillsDAO() {
		return userSkillsDAO;
	}



	public ServerInfoDAO getServerInfoDAO() {
		return serverInfoDAO;
	}



	public void setServerInfoDAO(ServerInfoDAO serverInfoDAO) {
		this.serverInfoDAO = serverInfoDAO;
	}



	public FieldDataDAO getFieldDataDAO() {
		return fieldDataDAO;
	}



	public GlobalMappingsDAO getGlobalMappingsDAO() {
		return globalMappingsDAO;
	}



	public void setGlobalMappingsDAO(GlobalMappingsDAO globalMappingsDAO) {
		this.globalMappingsDAO = globalMappingsDAO;
	}
	
	
}
