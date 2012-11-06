package tests;

import org.testng.annotations.Test;

import database.ServerInfoModel;
import database.DAO.ServerInfoDAO;

public class QuickTests {

	@Test
	public void insertData()
	{
		System.out.println("Rnnning Insert Data");
		ServerInfoModel s = new ServerInfoModel();
		s.setValue("Name");
		s.setData("jester");
		
		ServerInfoDAO db = new ServerInfoDAO();
		boolean worked = db.addServerInfoRecord(s);
		System.out.println("Inserted data : " + worked);
	}
	
	@Test(dependsOnMethods = {"insertData"})
	public void insertDuplicateData()
	{
		System.out.println("Running Insert Duplicate data");
		ServerInfoModel s = new ServerInfoModel();
		s.setValue("Name");
		s.setData("jesterUnique");
		
		ServerInfoDAO db = new ServerInfoDAO();
		boolean worked = db.addServerInfoRecord(s);
		System.out.println("Inserted data (should be false): " + worked);
	}
	
	@Test(dependsOnMethods = {"insertDuplicateData"})
	public void updateData()
	{
		ServerInfoModel s = new ServerInfoModel();
		s.setId(1);
		s.setValue("Name");
		s.setData("jesterUpdated");
		ServerInfoDAO db = new ServerInfoDAO();
		boolean worked = db.updateUser(s);
		System.out.println("Updated data : " + worked);
	}
	
	@Test(dependsOnMethods = {"updateData"})
	public void updateDataNonExisting()
	{
		ServerInfoModel s = new ServerInfoModel();
		s.setId(19);
		s.setValue("Name");
		s.setData("jesterUpdated");
		ServerInfoDAO db = new ServerInfoDAO();
		boolean worked = db.updateUser(s);
		System.out.println("Updated data : " + worked);
	}
	
	
	@Test (dependsOnMethods = {"updateDataNonExisting"})
	public void getData()
	{
		System.out.println("Getting info");
		ServerInfoModel s = new ServerInfoModel();
		ServerInfoDAO db = new ServerInfoDAO();
		s = db.findDataByValue("name");
		System.out.println("Found : " + s);
	}
	
	@Test (dependsOnMethods = {"getData"})
	public void getDataErrorRecord()
	{
		System.out.println("Getting info for non existing record");
		ServerInfoModel s = new ServerInfoModel();
		ServerInfoDAO db = new ServerInfoDAO();
		s = db.findDataByValue("poot");
		System.out.println("Found (should be null): " + s);
	}
	
	@Test  (dependsOnMethods = {"getDataErrorRecord"})
	public void deleteData()
	{
		System.out.println("Deleting info");
		ServerInfoDAO db = new ServerInfoDAO();
		boolean s = db.deleteDataByValue("name");
		System.out.println("Deleted : " + s);
	}
	
	@Test  (dependsOnMethods = {"deleteData"})
	public void deleteDataAlreadyGone()
	{
		System.out.println("Deleting Already gone info");
		ServerInfoDAO db = new ServerInfoDAO();
		boolean s = db.deleteDataByValue("name");
		System.out.println("Deleted : " + s);
	}

}
