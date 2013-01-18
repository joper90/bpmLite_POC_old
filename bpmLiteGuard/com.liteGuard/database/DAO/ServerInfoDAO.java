package database.DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import database.HibernateUtil;
import database.ServerInfoModel;

public class ServerInfoDAO {

	private boolean errorCreated = false;
	
	public ServerInfoDAO()
	{
		System.out.println("--> [DAO] init ServerInfoDAO...");
	}
	
	
	public boolean addServerInfoRecord(ServerInfoModel serverInfoModel)
	{
		this.errorCreated =false;
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();
			
			session.save(serverInfoModel);
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			if (trns != null) {
				trns.rollback();
			}
			System.out.println("--> [HIB] " + e.getLocalizedMessage());
			this.errorCreated = true;
			return false;
		} finally {
			if (!errorCreated) session.flush();
			session.close();
		}
		
		
		return true;
	}
	
	public ServerInfoModel findDataByValue(String value)
	{
		this.errorCreated =false;
		ServerInfoModel sInfo = null;
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();
			List<ServerInfoModel> sInfoList = session.createQuery("from ServerInfoModel where value = :value").setString("value", value).list();
			
			if (sInfoList.size() == 1)
			{
				sInfo = sInfoList.get(0);
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
		
		
		return sInfo;
	}
	
	public Integer findDataAsIntAndIncrement(String value)
	{
		this.errorCreated =false;
		ServerInfoModel sInfo = null;
		Transaction trns = null;
		int currentValue = 0;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();
			List<ServerInfoModel> sInfoList = session.createQuery("from ServerInfoModel where value = :value").setString("value", value).list();
			
			if (sInfoList.size() == 1)
			{
				sInfo = sInfoList.get(0);
				Integer i = new Integer(sInfo.getData());
				currentValue = i++;
				sInfo.setData(i.toString());
				session.save(sInfo);

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
		
		
		return currentValue;
	}
	
	
	public boolean updateUser(ServerInfoModel serverInfoModel) {
		this.errorCreated =false;
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();

			session.update(serverInfoModel);

			session.getTransaction().commit();
		} catch (RuntimeException e) {
			if (trns != null) {
				trns.rollback();
			}
			this.errorCreated = true;
			System.out.println("--> [HIB] "+e.getLocalizedMessage());
			return false;
		} finally {
			if (!errorCreated) session.flush();
			session.close();
		}
		return true;
	}
	
	public boolean deleteDataByValue(String value)
	{
		this.errorCreated =false;
		ServerInfoModel sInfo = null;
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();
			List<ServerInfoModel> sInfoList = session.createQuery("from ServerInfoModel where value = :value").setString("value", value).list();
			
			if (sInfoList.size() == 1)
			{
				sInfo = sInfoList.get(0);
				session.delete(sInfo);
			}
			trns.commit();
		} catch (RuntimeException e) {
			if (trns != null) {
				trns.rollback();
			}
			System.out.println("--> [HIB] "+ e.getLocalizedMessage());
			this.errorCreated = true;
			return false;
		} finally {
			if (!errorCreated) session.flush();
			session.close();
		}
		return true;
	}
}
