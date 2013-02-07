package database.DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import database.model.FieldDataModel;
import engine.HibernateUtil;

public class FieldDataDAO extends StandardDAO{

	public FieldDataDAO()
	{
		super();
		System.out.println("--> [DAO] init FieldDataDAO...");
	}

	public FieldDataModel getFieldDataByFieldId(int fieldId)
	{
		this.errorCreated =false;
		FieldDataModel sInfo = null;
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();
			@SuppressWarnings("unchecked")
			List<FieldDataModel> sInfoList = session.createQuery("from FieldDataModel where fieldId = :value").setInteger("value", fieldId).list();
			
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
	
}
