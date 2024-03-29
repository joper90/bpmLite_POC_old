package servlets;

import java.util.Date;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import jmsConnector.JmsConnector;
import config.StaticsCommon;
import database.DAO.BpmLiteDAO;
import database.model.ServerInfoModel;
import database.model.UserModel;

public class StartupListener  implements ServletContextListener{

	private JmsConnector jmsConnector;
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("JmsConnector distroyed..");
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {

		System.out.println("\n\n====>Starting to init the system...");
		StaticsCommon.setBpmServerlive(true);
		
		System.out.println("====>Starting JMS...");
		jmsConnector = new JmsConnector();
		jmsConnector.connectAndSpawn();
		System.out.println("====>Started JMS...");
		
		
		//Init the Hiberate System.
		System.out.println("====>Starting Hibernate System...");
		
		System.out.println("====>Injecting root user System...");

		ServerInfoModel rootInfo = new ServerInfoModel();
		rootInfo.setName(StaticsCommon.SERVER_INFO_ADMIN_KEY);
		rootInfo.setValue(StaticsCommon.ADMIN);
		BpmLiteDAO.instance.getServerInfoDAO().insertData(rootInfo);
		
		rootInfo.setName(StaticsCommon.SERVER_INFO_ADMIN_PASS);
		rootInfo.setValue(StaticsCommon.ADMIN_KEY);
		BpmLiteDAO.instance.getServerInfoDAO().insertData(rootInfo);
		
		System.out.println("====>Completed root user System...");
		
		System.out.println("====>Injecting Init Case Id ...");
		rootInfo.setName(StaticsCommon.CASE_ID);
		rootInfo.setValue(StaticsCommon.CASE_ID_START_VALUE);
		BpmLiteDAO.instance.getServerInfoDAO().insertData(rootInfo);
		System.out.println("====>Completed Init case Id ...");
		
		System.out.println("====>Injecting root user into User tables System...");
		UserModel userInfo = new UserModel();
		userInfo.setName(StaticsCommon.ADMIN);
		userInfo.setDescription(StaticsCommon.ADMIN);
		userInfo.setUniqueKey(StaticsCommon.ADMIN_KEY);
		userInfo.setStartTime(new Date());
		userInfo.setTibbrAddress("#tibbrAdmin");
		
		BpmLiteDAO.instance.getUserDAO().insertData(userInfo);
		
		System.out.println("====>Compelted root user into User tables System...");
		
		
		/*ServerInfoModel sStatus = new ServerInfoModel();
		sStatus = BpmGuardDAO.instance.getServerInfoDAO().findDataByValue("StartTime");
		
		if (sStatus == null)
		{
			ServerInfoModel sModel = new ServerInfoModel();
			sModel.setValue("StartTime");
			sModel.setData(new Date().toString());
			BpmGuardDAO.instance.getServerInfoDAO().addServerInfoRecord(sModel);
		}
		else //update
		{
			sStatus.setData(new Date().toString());
			BpmGuardDAO.instance.getServerInfoDAO().updateUser(sStatus);
		}*/
		
		
		System.out.println("====>Started Hibernate System...");
		
		System.out.println("====>End init the system...\n\n");
		
	}

}
