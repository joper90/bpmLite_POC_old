package database.DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import database.model.AssignedRoleModel;
import engine.HibernateUtil;

public class AssignedRoleDAO {

private boolean errorCreated = false;
	
	public AssignedRoleDAO()
	{
		System.out.println("--> [DAO] init AssignedRoleDAO...");
	}
	
	public boolean insertRoleModelData(AssignedRoleModel assignedRoleModel)
	{
		this.errorCreated =false;
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();
			
			session.save(assignedRoleModel);
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
	
	public boolean updateRoleModel(AssignedRoleModel assignedRoleModel) {
		this.errorCreated =false;
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();

			session.update(assignedRoleModel);

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
	
	public boolean deleteRoleModelFromSkill(String name,  int skillId)
	{
	this.errorCreated =false;
	Transaction trns = null;
	Session session = HibernateUtil.getSessionFactory().openSession();
	try {
		trns = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<AssignedRoleModel> sInfoList = session.createQuery("from AssignedRoleModel where name = :value").setString("value", name).list();
		
		if (sInfoList != null)
		{
			for (AssignedRoleModel f : sInfoList)
			{
				String[] skillArray = f.getUserSkills().split(",");
				for (String s : skillArray)
				{
					if (skillId == Integer.parseInt(s))
					{
						//Convert to a new string..
						
					}
						
				}
				
				
				//session.delete(f);
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
