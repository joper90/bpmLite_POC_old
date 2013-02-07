package database.DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import database.model.ProcessInstanceModel;
import engine.HibernateUtil;


public class ProcessInstanceDAO extends StandardDAO{

	public ProcessInstanceDAO()
	{
		super();
		System.out.println("--> [DAO] init ProcessInstanceDAO...");
	}
	
	public ProcessInstanceModel getByCaseId(String caseId)
	{
		boolean errorCreated =false;
		ProcessInstanceModel ret = null;
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();

			@SuppressWarnings("unchecked")
			List<ProcessInstanceModel> sInfoList = session.createQuery("from ProcessInstanceModel where caseId = :value").setString("value", caseId).list();
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
			errorCreated = true;
		} finally {
			if (!errorCreated) session.flush();
			session.close();
		}
		return ret;
	}
	
	public boolean updateInitialData(String guid, boolean state)
	{
		boolean worked =true;
		ProcessInstanceModel ret = null;
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();

			@SuppressWarnings("unchecked")
			List<ProcessInstanceModel> sInfoList = session.createQuery("from ProcessInstanceModel where guidCallback = :value").setString("value", guid).list();
			if (sInfoList.size() == 1)
			{
				ret = sInfoList.get(0);
				ret.setInitialDataSet(state);
				session.update(ret);
			}
			trns.commit();
		} catch (RuntimeException e) {
			if (trns != null) {
				trns.rollback();
			}
			System.out.println("--> [HIB] "+ e.getLocalizedMessage());
			worked = false;
		} finally {
			if (!errorCreated) session.flush();
			session.close();
		}
		return worked;
	}
	
	public boolean isInitalDataSet(String caseId)
	{
		boolean found =false;
		ProcessInstanceModel ret = null;
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();

			@SuppressWarnings("unchecked")
			List<ProcessInstanceModel> sInfoList = session.createQuery("from ProcessInstanceModel where caseId = :value").setString("value", caseId).list();
			if (sInfoList.size() == 1)
			{
				ret = sInfoList.get(0);
				if (ret.isInitialDataSet())
				{
					found = true;
				}
			}
			trns.commit();
		} catch (RuntimeException e) {
			if (trns != null) {
				trns.rollback();
			}
			System.out.println("--> [HIB] "+ e.getLocalizedMessage());
			found = false;
		} finally {
			if (!errorCreated) session.flush();
			session.close();
		}
		return found;
	}

}
