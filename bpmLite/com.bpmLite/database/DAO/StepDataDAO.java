package database.DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import database.model.ProcessInstanceModel;
import database.model.StepDataModel;
import engine.HibernateUtil;

public class StepDataDAO extends StandardDAO{

	public StepDataDAO()
	{
		super();
		System.out.println("--> [DAO] init StepDataDAO...");
	}

	public StepDataModel getStepByStepId(Integer stepId, Integer processId)
	{
		boolean errorCreated =false;
		StepDataModel ret = null;
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();

			@SuppressWarnings("unchecked")
			List<StepDataModel> sInfoList = session.createQuery("from StepDataModel where stepId = :value and processId = :valueProcessId")
													.setString("value", stepId.toString())
													.setString("valueProcessId",processId.toString())
													.list();
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
	
}
