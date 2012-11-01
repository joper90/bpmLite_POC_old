package engineConnector;

import java.util.Hashtable;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class QueueJMSMessageSender
{
	// These Strings detail which EMS server is going to be connected to and which queue
	private String			initialContextFactoryName	= "com.tibco.tibjms.naming.TibjmsInitialContextFactory";
	private String			initialURL					= "tcp://localhost:7222";
	private String			securityPrinciple			= null;
	private String			securityCredentials			= null;
	private String			queueConnectionFactoryName	= "QueueConnectionFactory";
	
	private InitialContext	context						= null; 
	private Destination		messageDestination			= null;
	private	QueueSession	queueSession				= null;

	public QueueJMSMessageSender() throws NamingException, JMSException
	{
		setupEMSConnection();
	}

	/*
	 * =====================================================
	 * METHOD : setupEMSConnection
	 * =====================================================
	 */
	/**
	 * Setup a connection to the specified EMS queue
	 *
	 * @throws JMSException
	 * @throws NamingException
	 */
	private void setupEMSConnection() throws JMSException, NamingException
	{
		// Now create the connection
        context = new InitialContext(getConnectionEnv());

        QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory) context.lookup(queueConnectionFactoryName);
        QueueConnection connection = queueConnectionFactory.createQueueConnection(securityPrinciple, securityCredentials);
        queueSession = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

	}

	private Hashtable<String, String> getConnectionEnv()
    {
        Hashtable<String, String> env = new Hashtable<String, String>();
        
        env.put(Context.INITIAL_CONTEXT_FACTORY, initialContextFactoryName);
        env.put(Context.PROVIDER_URL, initialURL);
        
        return(env);
    }
	
	/*
	 * =====================================================
	 * METHOD : sendMessage
	 * =====================================================
	 */
	/**
	 * Send the specified message to the specified queue
	 * @param soapAction 
	 * 
	 * @throws JMSException 
	 * @throws NamingException 
	 *
	 */
	public void sendMessage(String queueName, String message, String soapAction) throws NamingException
	{
		
		if (queueSession != null)
		{
	        messageDestination = (Destination) context.lookup(queueName);
			
			// Set the text message we are going to publish
	        TextMessage tMessage;
			try
			{
				tMessage = queueSession.createTextMessage();
		        tMessage.setText(message);
		        
		        // If set then set any properties
		        if (soapAction != null)
		        {
		        	tMessage.setStringProperty( "SOAPAction", soapAction );
		        }

		        
		        MessageProducer producer = queueSession.createProducer(messageDestination);
		        producer.send(tMessage);
			}
			catch (JMSException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}