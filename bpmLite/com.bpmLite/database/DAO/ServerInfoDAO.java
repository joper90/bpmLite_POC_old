package database.DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import database.model.AssignedRoleModel;
import database.model.ServerInfoModel;
import engine.HibernateUtil;

public class ServerInfoDAO extends StandardDAO{

	private boolean errorCreated = false;
	
	public ServerInfoDAO()
	{
		super();
		System.out.println("--> [DAO] init ServerInfoDAO...");
	}
	
	
	public ServerInfoModel getValueByName(String name)
	{
		this.errorCreated =false;
		Transaction trns = null;
		
		ServerInfoModel ret = null;
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();

			@SuppressWarnings("unchecked")
			List<ServerInfoModel> sInfoList = session.createQuery("from ServerInfoModel where name= :value").setString("value", name).list();
			
			if (sInfoList.size() == 1)
			{
				ret = sInfoList.get(0);
			}
			
			trns.commit();
		} catch (RuntimeException e) {
			if (trns != null) {
				trns.rollback();
			}
			System.out.println("--> [HIB] "+ e.getLocalizedMessage());
			this.errorCreated = true;
		} finally {
			if (!errorCreated) session.flush();
			session.close();
		}
		return ret;
	}

}
