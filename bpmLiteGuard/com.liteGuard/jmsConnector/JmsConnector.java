package jmsConnector;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;

import config.Statics;

public class JmsConnector implements  ExceptionListener, MessageListener {

	private String				serverUrl	= Statics.JMS_SERVER;

	private String				userName	= Statics.JMS_USER;

	private String				password	= Statics.JMS_PASSWORD;

	private String				queue		= Statics.JMS_TOPIC_PULL;

	private Connection			connection	= null;

	private Session				session		= null;

	private MessageConsumer		msgConsumer	= null;

	private Destination			destination	= null;
	
	public boolean connectAndSpawn() 
	{

		System.out.println("Connecting to jms : " + serverUrl + " " + userName + " " + queue);

		try
		{
			ConnectionFactory factory = null;
			
			factory = new com.tibco.tibjms.TibjmsConnectionFactory(serverUrl);

			/* create the connection */
			connection = factory.createConnection(userName, password);

			/* create the session */
			session = connection.createSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);

			/* set the exception listener */
			connection.setExceptionListener(this);

			// create the destination 
			destination = session.createQueue(queue);	

			System.out.println("Subscribing to destination: " + queue);

			/* create the consumer */
			msgConsumer = session.createConsumer(destination);

			/* set the message listener */
			msgConsumer.setMessageListener(this);

			/* start the connection */
			connection.start();

		}
		catch (Exception e)
		{
			System.out.println("Connection failed.. ");
			e.printStackTrace();
			return false;
		}
		return true;

	}

	@Override
	public void onMessage(Message message) {
		System.out.println("Got a message ... whoot " );
		JmsReceiveParser jmsRe = new JmsReceiveParser();
		jmsRe.parseData(message);
	}

	@Override
	public void onException(JMSException e) {
		/* print the connection exception status */
		System.err.println("CONNECTION EXCEPTION: " + e.getMessage());
		
	}
	
}
