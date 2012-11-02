package engineConnector;

import javax.jms.JMSException;
import javax.jms.Message;

public class JmsReceiveParser {

	
	public JmsReceiveParser(Message tibMsg)
	{
		String textRecived = "";
		
		try {
			textRecived = ((com.tibco.tibjms.TibjmsTextMessage) tibMsg).getText();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Text Received : " + textRecived);
	}
	
}
