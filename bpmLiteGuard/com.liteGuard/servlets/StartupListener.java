package servlets;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class StartupListener  implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {

		System.out.println("\n\n====>Starting to init the system...\n\n");
		
		System.out.println("\n\n====>End init the system...\n\n");
		
	}

}
