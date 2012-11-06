package servlets;

import java.util.Date;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import jmsConnector.JmsConnector;
import config.Statics;
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
		Statics.setAlive(true);
		
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
		
		
		System.out.println("====>Started Hibernate System...");
		
		System.out.println("====>End init the system...\n\n");
		
	}

}
