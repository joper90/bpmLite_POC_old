package com.bpmlite.testclient;

import javax.naming.NamingException;

public class EmsTests {

	/**
	 * @param args
	 * @throws NamingException 
	 */
	public static void main(String[] args) throws NamingException {
		// TODO Auto-generated method stub
		
		String queue = "";
		String message = "";
		
		QueueJMSMessageSender q = new QueueJMSMessageSender();
		q.sendMessage(queue, message);
	}

}
