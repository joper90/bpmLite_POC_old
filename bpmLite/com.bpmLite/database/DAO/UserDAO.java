package database.DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import database.model.UserModel;
import engine.HibernateUtil;

public class UserDAO extends StandardDAO{

	public UserDAO()
	{
		super();
		System.out.println("--> [DAO] init UserDAO...");
	}
	
	public boolean findDataByValue(String name, String password)
	{
		this.errorCreated =false;
		boolean validUser = false;
		UserModel sInfo = null;
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();
			@SuppressWarnings("unchecked")
			List<UserModel> sInfoList = session.createQuery("from UserModel where name = :value").setString("value", name).list();
			
			if (sInfoList.size() == 1)
			{
				sInfo = sInfoList.get(0);
				if (sInfo.getUniqueKey().equalsIgnoreCase(password)) validUser = true;
				
			}
			trns.commit();
		} catch (RuntimeException e) {
			if (trns != null) {
				trns.rollback();
			}
			this.errorCreated = true;
			System.out.println("--> [HIB] "+e.getLocalizedMessage());
		} finally {
			if (!errorCreated) session.flush();
			session.close();
		}
		
		
		return validUser;
	}

}
