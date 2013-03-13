package database.DAO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import database.GlobalData;
import database.HibernateUtil;
import database.KeyStoreModel;

public class GlobalDataDAO {
	private boolean errorCreated = false;
	
	public GlobalDataDAO()
	{
		System.out.println("--> [DAO] init GlobalDataDAO...");
	}
	
	public boolean insertFieldData(GlobalData globalModel)
	{
		this.errorCreated =false;
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();
			
			session.save(globalModel);
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
	
	public boolean updateField(GlobalData globalModel) {
		this.errorCreated =false;
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();

			session.update(globalModel);

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
	
	public GlobalData getGlobalFieldById(int fieldId)
	{
		this.errorCreated =false;
		GlobalData fInfo = null;
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();
			@SuppressWarnings("unchecked")
			List<GlobalData> sInfoList = session.createQuery("from GlobalData where fieldId = :value")
										.setInteger("value", fieldId)
										.list();
			
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
	
	public GlobalData[] getAllGlobalFieldsByStringOfIds(KeyStoreModel keyStoreModel)
	{
		ArrayList<GlobalData> dModelList = new ArrayList<GlobalData>();
		//First get a list of ids.
		String[] ids = keyStoreModel.getGlobalIds().split(",");

		if (ids.length > 1)
		{
			for (String id : ids)
			{
				GlobalData fTemp = getGlobalFieldById(new Integer(id));
				if (fTemp != null)
				{
					dModelList.add(fTemp);
				}
				else
				{
					System.out.println("--> [HIB] fieldDataModel not found id: " + id);
				}
			}
		}
		
		//Convert to []
		GlobalData[] ret = new GlobalData[dModelList.size()];
		for (int a = 0; a < dModelList.size(); a++)
		{
			ret[a] = dModelList.get(a);
		}
		return ret;
	}
	
	public boolean deletebyFieldId(int fieldId)
	{
		this.errorCreated =false;
		GlobalData sInfo = null;
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();
			List<GlobalData> sInfoList = session.createQuery("from GlobalData where fieldId = :value").setInteger("value", fieldId).list();
			
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
