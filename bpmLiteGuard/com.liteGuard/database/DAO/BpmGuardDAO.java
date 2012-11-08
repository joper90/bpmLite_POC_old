package database.DAO;


public class BpmGuardDAO {

	public static BpmGuardDAO instance = new BpmGuardDAO();
	
	private ServerInfoDAO serverInfoDAO =null;
	private KeyStoreDAO keyStoreDAO = null;
	private FieldDataDAO fieldDataDAO = null;
	private GlobalDataDAO  globalDataDAO = null;
	
	private BpmGuardDAO()
	{
		this.serverInfoDAO = new ServerInfoDAO();
		this.keyStoreDAO = new KeyStoreDAO();
		this.fieldDataDAO = new FieldDataDAO();
		this.globalDataDAO = new GlobalDataDAO();
	}

	public ServerInfoDAO getServerInfoDAO() {
		return serverInfoDAO;
	}

	public KeyStoreDAO getKeyStoreDAO() {
		return keyStoreDAO;
	}

	public FieldDataDAO getFieldDataDAO() {
		return fieldDataDAO;
	}

	public GlobalDataDAO getGlobalDataDAO() {
		return globalDataDAO;
	}
	
	
	
}
