package database.DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import config.StaticsCommon;
import config.StaticsCommon.GUID_KEY_MODE;
import database.HibernateUtil;
import database.KeyStoreModel;

public class KeyStoreDAO {

private boolean errorCreated = false;
	
	public KeyStoreDAO()
	{
		System.out.println("--> [DAO] init KeyStoreDAO...");
	}
	
	public boolean addKeyStoreRecord(KeyStoreModel keyStoreModel)
	{
		this.errorCreated =false;
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();
			
			session.save(keyStoreModel);
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
	
	public KeyStoreModel findDataByGuid(String requestId)
	{
		this.errorCreated =false;
		KeyStoreModel keyInfo = null;
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();
			@SuppressWarnings("unchecked")
			List<KeyStoreModel> sInfoList = session.createQuery("from KeyStoreModel where userGuid = :value").setString("value", requestId).list();
			
			if (sInfoList.size() == 1)
			{
				keyInfo = sInfoList.get(0);
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
		
		
		return keyInfo;
	}
	
	public KeyStoreModel findDataByGuidAndUserId(String requestId, String userId)
	{
		this.errorCreated =false;
		KeyStoreModel keyInfo = null;
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();
			@SuppressWarnings("unchecked")
			List<KeyStoreModel> sInfoList = session.createQuery("from KeyStoreModel where userGuid = :value and userId = :userid").setString("value", requestId).setString("userid", userId).list();
			
			if (sInfoList.size() == 1)
			{
				keyInfo = sInfoList.get(0);
				session.update(keyInfo);
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
		
		
		return keyInfo;
	}
	
	public KeyStoreModel updateKeyStoreStatus(String requestId, String userId, StaticsCommon.GUID_KEY_MODE newMode)
	{
		this.errorCreated =false;
		KeyStoreModel keyInfo = null;
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();
			@SuppressWarnings("unchecked")
			List<KeyStoreModel> sInfoList = session.createQuery("from KeyStoreModel where userGuid = :userguid and userId = :userid").setString("userguid", requestId).setString("userid", userId).list();
			
			if (sInfoList.size() == 1)
			{
				keyInfo = sInfoList.get(0);
				keyInfo.setKeyState(newMode.toString());
				
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
		
		
		return keyInfo;
	}
	
	public boolean isKeyTaken(String guid)
	{
		this.errorCreated =false;
		boolean ret = true;
		KeyStoreModel sInfo = null;
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();
			@SuppressWarnings("unchecked")
			List<KeyStoreModel> sInfoList = session.createQuery("from KeyStoreModel where userGuid = :guid").setString("guid", guid).list();
			
			if (sInfoList.size() == 1)
			{
				sInfo = sInfoList.get(0);

				if (StaticsCommon.GUID_KEY_MODE.INJECTED.toString().equalsIgnoreCase(sInfo.getKeyState()))
				{
					ret = false;
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
		return ret;
	}
	
	public StaticsCommon.GUID_KEY_MODE getKeyState(String requestId)
	{
		KeyStoreModel sInfo = null;
		GUID_KEY_MODE gMode = null;
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();
			@SuppressWarnings("unchecked")
			List<KeyStoreModel> sInfoList = session.createQuery("from KeyStoreModel where userGuid = :guid").setString("guid", requestId).list();
			
			if (sInfoList.size() == 1)
			{
				sInfo = sInfoList.get(0);
				String check = sInfo.getKeyState();
				for (GUID_KEY_MODE gCheck : GUID_KEY_MODE.values())
				{
					if (gCheck.toString().equalsIgnoreCase(check))
					{
						gMode = gCheck;
					}
				}
				
			}
			trns.commit();
		} catch (RuntimeException e) {
			if (trns != null) {
				trns.rollback();
			}
			System.out.println("--> [HIB] "+ e.getLocalizedMessage());
			this.errorCreated = true;
			return null;
		} finally {
			if (!errorCreated) session.flush();
			session.close();
		}
		return gMode;
	}
	
	public boolean deleteRecordByGuid(String guid)
	{
		this.errorCreated =false;
		KeyStoreModel sInfo = null;
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();
			@SuppressWarnings("unchecked")
			List<KeyStoreModel> sInfoList = session.createQuery("from KeyStoreModel where userGuid = :guid").setString("guid", guid).list();
			
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
