package servlets;

import java.util.Date;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import jmsConnector.JmsConnector;
import config.StaticsCommon;
import database.ServerInfoModel;
import database.DAO.BpmGuardDAO;

public class StartupListener  implements ServletContextListener{

	private JmsConnector jmsConnector;
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("JmsConnector distroyed..");
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {

		System.out.println("\n\n====>Starting to init the system...");
		StaticsCommon.setGuardAlive(true);
		
		System.out.println("====>Starting JMS...");
		jmsConnector = new JmsConnector();
		jmsConnector.connectAndSpawn();
		System.out.println("====>Started JMS...");
		
		
		//Init the Hiberate System.
		System.out.println("====>Starting Hibernate System...");
		
		ServerInfoModel sStatus = new ServerInfoModel();
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
		}
		System.out.println("====>[HIB] Start time added...");
		
		sStatus = BpmGuardDAO.instance.getServerInfoDAO().findDataByValue(StaticsCommon.ADMIN);
		if (sStatus == null)
		{
			ServerInfoModel sModel = new ServerInfoModel();
			sModel.setValue(StaticsCommon.ADMIN);
			sModel.setData(StaticsCommon.ADMIN_KEY);
			BpmGuardDAO.instance.getServerInfoDAO().addServerInfoRecord(sModel);
		}
		
		sStatus = BpmGuardDAO.instance.getServerInfoDAO().findDataByValue(StaticsCommon.INITIAL_GLOBAL_COUNT);
		if (sStatus == null)
		{
			ServerInfoModel sModel = new ServerInfoModel();
			sModel.setValue(StaticsCommon.INITIAL_GLOBAL_COUNT);
			sModel.setData(StaticsCommon.INITIAL_GLOBAL_COUNT_START);
			BpmGuardDAO.instance.getServerInfoDAO().addServerInfoRecord(sModel);
		}
		
		
		System.out.println("====>[HIB] root key added...");
				
		System.out.println("====>Started Hibernate System...");
		
		System.out.println("====>End init the system...\n\n");
		
	}

}
