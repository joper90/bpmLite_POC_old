package servlets;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import config.Statics;
import engineConnector.JmsConnector;

public class StartupListener  implements ServletContextListener{

	private JmsConnector jmsConnector;
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {

		System.out.println("\n\n====>Starting to init the system...\n\n");
		Statics.setAlive(true);
		
		System.out.println("\n\n====>Starting JMS...\n\n");
		jmsConnector = new JmsConnector();
		jmsConnector.connectAndSpawn();
		System.out.println("\n\n====>Started JMS...\n\n");
		
		System.out.println("\n\n====>End init the system...\n\n");
		
	}

}
