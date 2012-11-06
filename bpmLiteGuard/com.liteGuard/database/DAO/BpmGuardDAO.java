package database.DAO;


public class BpmGuardDAO {

	public static BpmGuardDAO instance = new BpmGuardDAO();
	
	private ServerInfoDAO serverInfoDAO =null;
	
	private BpmGuardDAO()
	{
		this.serverInfoDAO = new ServerInfoDAO();
		
	}

	public ServerInfoDAO getServerInfoDAO() {
		return serverInfoDAO;
	}

	
}
