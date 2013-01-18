package database.DAO;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import database.model.AssignedRoleModel;
import database.model.BaseModel;
import engine.HibernateUtil;

public abstract class StandardDAO {

	protected boolean errorCreated = false;
	
	public StandardDAO()
	{
		System.out.println("--> [DAO] init StandardDAO...");
	}
	
	public boolean insertData(Object objectModel)
	{
		this.errorCreated =false;
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();
			
			session.save(objectModel);
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
	
	public boolean updateModel(Object objectModel) {
		this.errorCreated =false;
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();

			session.update(objectModel);

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
	
	
	public boolean deleteModel(Object objectModel)
	{
		this.errorCreated =false;
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();

			BaseModel baseModel = (BaseModel) objectModel;
			String clazzName = objectModel.getClass().getSimpleName();
			@SuppressWarnings("unchecked")
			List<AssignedRoleModel> sInfoList = session.createQuery("from "+ clazzName + " where id = :value").setInteger("value", baseModel.getId()).list();
			
			if (sInfoList != null)
			{
				for (AssignedRoleModel f : sInfoList)
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
