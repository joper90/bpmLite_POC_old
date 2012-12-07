package database.DAO;


public class AuditDAO extends StandardDAO{

	private boolean errorCreated = false;
	public AuditDAO()
	{
		super();
		System.out.println("--> [DAO] init AuditDAO...");
	}
	
}
