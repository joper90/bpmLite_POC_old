package engineConnector;

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

	private String				serverUrl	= Statics.jmsServer;

	private String				userName	= Statics.jmsUser;

	private String				password	= Statics.jmsPassword;

	private String				topic		= Statics.jmsTopicPull;

	private Connection			connection	= null;

	private Session				session		= null;

	private MessageConsumer		msgConsumer	= null;

	private Destination			destination	= null;

	private boolean				useTopic	= true;
	
	public boolean connectAndSpawn() 
	{

		System.out.println("Connecting to jms : " + serverUrl + " " + userName + " " + topic);

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

			/* create the destination */
			if (useTopic)
			{
				destination = session.createTopic(topic);
			}
			else
			{
				destination = session.createQueue(topic);
			}

			System.out.println("Subscribing to destination: " + topic);

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
		// TODO Auto-generated method stub
		System.out.println("Gone a message ");
	}

	@Override
	public void onException(JMSException e) {
		/* print the connection exception status */
		System.err.println("CONNECTION EXCEPTION: " + e.getMessage());
		
	}
	
}
