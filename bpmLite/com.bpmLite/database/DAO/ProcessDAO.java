package database.DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import config.StaticsCommon;
import database.model.ProcessModel;
import engine.HibernateUtil;

public class ProcessDAO extends StandardDAO{

	public ProcessDAO()
	{
		super();
		System.out.println("--> [DAO] init ProcessDAO...");
	}
	
	public Integer getIdByGuid(String guid)
	{
		boolean errorCreated =false;
		int retValue = StaticsCommon.FAILED;
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();

			@SuppressWarnings("unchecked")
			List<ProcessModel> sInfoList = session.createQuery("from ProcessModel where uniqueGuid = :value").setString("value", guid).list();
			
			if (sInfoList != null)
			{
				retValue =  sInfoList.get(0).getId();
			}
			trns.commit();
		} catch (RuntimeException e) {
			if (trns != null) {
				trns.rollback();
			}
			System.out.println("--> [HIB] "+ e.getLocalizedMessage());
			errorCreated = true;
		} finally {
			if (!errorCreated) session.flush();
			session.close();
		}
		return retValue;
	}
	
	public ProcessModel getByNameAndVersion(String name, String version)
	{
		boolean errorCreated =false;
		ProcessModel ret = null;
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();

			@SuppressWarnings("unchecked")
			List<ProcessModel> sInfoList = session.createQuery("from ProcessModel where name = :name and version = :version")
													.setString("name", name)
													.setString("version",version)
													.list();
			
			if (sInfoList != null)
			{
				ret =sInfoList.get(0);
			}
			trns.commit();
		} catch (RuntimeException e) {
			if (trns != null) {
				trns.rollback();
			}
			System.out.println("--> [HIB] "+ e.getLocalizedMessage());
			errorCreated = true;
		} finally {
			if (!errorCreated) session.flush();
			session.close();
		}
		return ret;
	}
}
