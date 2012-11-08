package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import database.FieldDataModel;
import database.GlobalData;
import database.KeyStoreModel;
import database.ServerInfoModel;
import database.DAO.FieldDataDAO;
import database.DAO.GlobalDataDAO;
import database.DAO.KeyStoreDAO;
import database.DAO.ServerInfoDAO;

public class HibernateModelTests {

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
		Assert.assertEquals(worked, true);
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
		Assert.assertEquals(worked, false);
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
		Assert.assertEquals(worked, true);
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
		Assert.assertEquals(worked, false);
	}
	
	
	@Test (dependsOnMethods = {"updateDataNonExisting"})
	public void getData()
	{
		System.out.println("Getting info");
		ServerInfoModel s = new ServerInfoModel();
		ServerInfoDAO db = new ServerInfoDAO();
		s = db.findDataByValue("name");
		System.out.println("Found : " + s);
		Assert.assertNotNull(s);
	}
	
	@Test (dependsOnMethods = {"getData"})
	public void getDataErrorRecord()
	{
		System.out.println("Getting info for non existing record");
		ServerInfoModel s = new ServerInfoModel();
		ServerInfoDAO db = new ServerInfoDAO();
		s = db.findDataByValue("poot");
		System.out.println("Found (should be null): " + s);
		Assert.assertEquals(s, null);
	}
	
	@Test  (dependsOnMethods = {"getDataErrorRecord"})
	public void deleteData()
	{
		System.out.println("Deleting info");
		ServerInfoDAO db = new ServerInfoDAO();
		boolean s = db.deleteDataByValue("name");
		System.out.println("Deleted : " + s);
		Assert.assertNotNull(s);
	}
	
	@Test  (dependsOnMethods = {"deleteData"})
	public void deleteDataAlreadyGone()
	{
		System.out.println("Deleting Already gone info");
		ServerInfoDAO db = new ServerInfoDAO();
		boolean s = db.deleteDataByValue("name");
		System.out.println("Deleted : " + s);
		Assert.assertNotNull(s);
	}
	
	@Test (dependsOnMethods = {"deleteDataAlreadyGone"})
	public void insertKeyStore()
	{
		KeyStoreModel kStore = new KeyStoreModel();
		kStore.setUserId("123User");
		kStore.setFieldIds("1,2,3,4,5");
		kStore.setUserGuid("ABC123");
		kStore.setKeyCollected(false);
		
		KeyStoreDAO db = new KeyStoreDAO();
		boolean s = db.addKeyStoreRecord(kStore);
		System.out.println("Added Record : " + s);
		Assert.assertEquals(s, true);
	}
	
	@Test (dependsOnMethods = {"insertKeyStore"})
	public void getDataByGuid()
	{
		KeyStoreDAO db = new KeyStoreDAO();
		KeyStoreModel kStore = db.findDataByGuid("ABC123");
		System.out.println("Found record :" + kStore.getUserGuid());
		Assert.assertEquals(kStore.getUserGuid(), "ABC123");
	}
	
	@Test (dependsOnMethods = {"getDataByGuid"})
	public void getDataByGuidAndUserId()
	{
		KeyStoreDAO db = new KeyStoreDAO();
		KeyStoreModel kStore = db.findDataByGuidAndUserId("ABC123","123User");
		System.out.println("Found record :" + kStore.getUserGuid());
		Assert.assertEquals(kStore.getUserGuid(), "ABC123");
	}
	
	@Test (dependsOnMethods = {"getDataByGuid"})
	public void getFieldsAndMarkAsUsed()
	{
		KeyStoreDAO db = new KeyStoreDAO();
		KeyStoreModel kStore = db.getFieldsAndMarkAsUsed("ABC123","123User");
		System.out.println("Found record :" + kStore.isKeyCollected());
		Assert.assertEquals(kStore.isKeyCollected(), true);
	}
	
	@Test (dependsOnMethods = {"getDataByGuidAndUserId"})
	public void checkIfMarked()
	{
		KeyStoreDAO db = new KeyStoreDAO();
		boolean b = db.isKeyTaken("ABC123");
		System.out.println("Found record :" + b);
		Assert.assertEquals(b, true);
	}
	
	@Test (dependsOnMethods = {"getDataByGuidAndUserId"})
	public void deleteByGuid()
	{
		KeyStoreDAO db = new KeyStoreDAO();
		boolean b = db.deleteRecordByGuid("ABC123");
		System.out.println("Found deleted :" + b);
		Assert.assertEquals(b, true);
	} 
	
	@Test(dependsOnMethods = {"deleteByGuid"})
	public void insertFieldData()
	{
		FieldDataModel fModel = new FieldDataModel();
		fModel.setProcessId(1);
		fModel.setCaseId(111);
		fModel.setFieldId(123);
		fModel.setName("testField");
		fModel.setType("STRING");
		
		FieldDataDAO d = new FieldDataDAO();
		boolean s = d.insertFieldData(fModel);
		System.out.println("Added Record : " + s);
		Assert.assertEquals(s, true);
	}
	
	@Test (dependsOnMethods = {"insertFieldData"})
	public void updateFieldValue()
	{
		FieldDataModel fModel = new FieldDataModel();
		fModel.setId(1);
		fModel.setProcessId(1);
		fModel.setCaseId(111);
		fModel.setFieldId(123);
		fModel.setName("testFieldUpdated");
		fModel.setType("STRING");
		
		FieldDataDAO d = new FieldDataDAO();
		boolean s = d.updateField(fModel);
		
		System.out.println("Added Updated : " + s);
		Assert.assertEquals(s, true);
	}
	
	@Test(dependsOnMethods = {"updateFieldValue"})
	public void getFieldValue()
	{
		FieldDataModel fModel = null;
		FieldDataDAO d = new FieldDataDAO();
		fModel = d.getFieldById(123);
		System.out.println("Found fieldId record:" + fModel.getName());
	}
	
	@Test(dependsOnMethods = {"getFieldValue"})
	public void getListOfIds()
	{
		//First add 2 more 
		FieldDataModel fModel = new FieldDataModel();
		fModel.setProcessId(1);
		fModel.setCaseId(111);
		fModel.setFieldId(124);
		fModel.setName("testField2");
		fModel.setType("STRING");
		
		FieldDataDAO d = new FieldDataDAO();
		boolean s = d.insertFieldData(fModel);
		System.out.println("Added Record : " + s);
		
		
		fModel = new FieldDataModel();
		fModel.setProcessId(1);
		fModel.setCaseId(111);
		fModel.setFieldId(125);
		fModel.setName("testFieldThree");
		fModel.setType("INT");
		
		d = new FieldDataDAO();
		s = d.insertFieldData(fModel);
		System.out.println("Added Record : " + s);
		
		
		//All fields added so now get the lsit of the data back again..
		
		FieldDataModel[] fModelArray= d.getAllFieldsByStringOfIds("123,124,125");
		
		System.out.println("Got back count: " + fModelArray.length);
		Assert.assertEquals(fModelArray.length, 3);
	}
	
	@Test(dependsOnMethods = {"getListOfIds"})
	public void deleteByCaseId()
	{
		FieldDataDAO d = new FieldDataDAO();
		boolean b = d.deleteAllDataByProcessId(111);
		System.out.println("Deteled all records : " + b);
		Assert.assertEquals(b, true);
	}
	
	@Test (dependsOnMethods = {"deleteByCaseId"})
	public void insertGlobalData()
	{
		GlobalData g = new GlobalData();
		g.setFieldId(123);
		g.setName("global One");
		g.setType("STRING");
		
		GlobalDataDAO db = new GlobalDataDAO();
		boolean b = db.insertFieldData(g);
		System.out.println("Inserted entry : " + b);
	}
	
	@Test (dependsOnMethods = {"insertGlobalData"})
	public void updateGlobalData()
	{
		GlobalData g = new GlobalData();
		g.setId(1);
		g.setFieldId(123);
		g.setName("global One Updated");
		g.setType("STRING");
		
		GlobalDataDAO db = new GlobalDataDAO();
		boolean b = db.updateField(g);
		System.out.println("Inserted entry : " + b);
	}
	
	@Test (dependsOnMethods = {"updateGlobalData"})
	public void getGlobalData()
	{
		GlobalDataDAO db = new GlobalDataDAO();
		GlobalData g = db.getFieldById(123);
		Assert.assertNotNull(g);
		System.out.println("Got data : " + g.getName());
	}
	
	@Test (dependsOnMethods = {"getGlobalData"})
	public void getGlobalDataByIds()
	{
		//First Create some new fields.
		GlobalData g = new GlobalData();
		g.setFieldId(124);
		g.setName("global Two");
		g.setType("STRING");
		
		GlobalDataDAO db = new GlobalDataDAO();
		boolean b = db.insertFieldData(g);
		System.out.println("Inserted entry : " + b);
		
		g = new GlobalData();
		g.setFieldId(125);
		g.setName("global Three");
		g.setType("INT");
		
		b = db.insertFieldData(g);
		System.out.println("Inserted entry : " + b);
		
		GlobalData[] ret = db.getAllFieldsByStringOfIds("123,124,125");
		System.out.println("Array Return size : " + ret.length);
		
		Assert.assertEquals(ret.length, 3);	
	}
	
	@Test (dependsOnMethods = {"getGlobalDataByIds"})
	public void deleteGlobalRecords()
	{
		GlobalDataDAO db = new GlobalDataDAO();
		boolean b = db.deletebyFieldId(123);
		if(b) b = db.deletebyFieldId(124);
		if (b) b = db.deletebyFieldId(125);
		System.out.println("All deleted : " + b);
		Assert.assertEquals(b, true);
	}

	
	
}
