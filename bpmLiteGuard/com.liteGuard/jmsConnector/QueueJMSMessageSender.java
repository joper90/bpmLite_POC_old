package jmsConnector;

import javax.jms.JMSException;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.naming.NamingException;

import config.Statics;

public class QueueJMSMessageSender
{

	public QueueJMSMessageSender() 
	{

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
	public boolean sendMessageCheck(String queueName, String messageToSend)
	{
		if (Statics.TEST_MODE)
		{
			return true;
		}
		else
		{
			try {
				sendMessage(queueName, messageToSend);
				return true;
			} catch (NamingException e) {
				// TODO Correctly handle the error.. i.e log out
				e.printStackTrace();
			}
			return false;
		}
		
		
	}
	
	public void sendMessage(String queueName, String messageToSend) throws NamingException
	{
		try
        {
            QueueConnectionFactory factory = new com.tibco.tibjms.TibjmsQueueConnectionFactory("localhost");

            QueueConnection connection = factory.createQueueConnection(null,null);

            QueueSession session = connection.createQueueSession(false,javax.jms.Session.AUTO_ACKNOWLEDGE);

            /*
             * Use createQueue() to enable sending into dynamic queues.
             */
            javax.jms.Queue queue = session.createQueue(queueName);

            QueueSender sender = session.createSender(queue);

            /* publish messages */
            javax.jms.TextMessage message = session.createTextMessage();
            String text = messageToSend;
            message.setText(text);
            sender.send(message);
            System.err.println("Sent message: "+text);


            connection.close();
        }
        catch(JMSException e)
        {
            e.printStackTrace();
            System.exit(0);
        }
		
	}
}