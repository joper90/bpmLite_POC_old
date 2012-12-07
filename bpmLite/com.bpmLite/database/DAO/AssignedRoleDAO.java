package database.DAO;


public class AssignedRoleDAO extends StandardDAO{

	
	public AssignedRoleDAO()
	{
		super();
		System.out.println("--> [DAO] init AssignedRoleDAO...");
	}
	
	
/*	public boolean deleteRoleModelFromSkill(String name,  String skillId)
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
				ArrayList<String> skillArray = (ArrayList<String>) Arrays.asList(f.getUserSkills().split(","));
				boolean remove = skillArray.remove(skillId);
				if (remove) //found and removed
				{
					//Build new list and then remove..
					String newSkillsString = "";
					for (String sInternal : skillArray)
					{
						newSkillsString = newSkillsString + sInternal + ",";
					}
					f.setUserSkills(newSkillsString);
					session.update(f);
					session.getTransaction();
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
			return false;
		} finally {
			if (!errorCreated) session.flush();
			session.close();
		}
		return true;
	}*/
	
	
	
}
