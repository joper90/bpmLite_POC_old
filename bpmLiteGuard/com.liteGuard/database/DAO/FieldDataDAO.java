package database.DAO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import database.FieldDataModel;
import database.HibernateUtil;

public class FieldDataDAO {

	private boolean errorCreated = false;
	
	public FieldDataDAO()
	{
		System.out.println("--> [DAO] init FieldDataDAO...");
	}
	
	public boolean insertFieldData(FieldDataModel fieldDataModel)
	{
		this.errorCreated =false;
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();
			
			session.save(fieldDataModel);
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
	
	public boolean updateField(FieldDataModel fieldDataModel) {
		this.errorCreated =false;
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();

			session.update(fieldDataModel);

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
	
	public FieldDataModel getFieldById(int fieldId)
	{
		this.errorCreated =false;
		FieldDataModel fInfo = null;
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();
			List<FieldDataModel> sInfoList = session.createQuery("from FieldDataModel where fieldId = :value").setInteger("value", fieldId).list();
			
			if (sInfoList.size() == 1)
			{
				fInfo = sInfoList.get(0);
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
		
		
		return fInfo;
	}

	
	public FieldDataModel[] getAllFieldsByStringOfIds(String listOfIds)
	{
		ArrayList<FieldDataModel> dModelList = new ArrayList<FieldDataModel>();
		//First get a list of ids.
		String[] ids = listOfIds.split(",");
		for (String id : ids)
		{
			FieldDataModel fTemp = getFieldById(new Integer(id));
			if (fTemp != null)
			{
				dModelList.add(fTemp);
			}
			else
			{
				System.out.println("--> [HIB] fieldDataModel not found id (May be global): " + id);
			}
		}
		
		//Convert to []
		FieldDataModel[] ret = new FieldDataModel[dModelList.size()];
		for (int a = 0; a < dModelList.size(); a++)
		{
			ret[a] = dModelList.get(a);
		}
		return ret;
	}
	
	public boolean deleteAllDataByProcessId(int caseId)
	{
		this.errorCreated =false;
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();
			List<FieldDataModel> sInfoList = session.createQuery("from FieldDataModel where caseId = :value").setInteger("value", caseId).list();
			
			if (sInfoList != null)
			{
				for (FieldDataModel f : sInfoList)
				{
					session.delete(f);
				}
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
