package com.stephan.jms.basics;

import java.util.Enumeration;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class QueueBrowserDemo {

	public static void main(String[] args) {
		
     InitialContext initialContext= null;
     Connection connection = null;
     
     try {
		initialContext = new InitialContext();
		
		ConnectionFactory cf=(ConnectionFactory) initialContext.lookup("ConnectionFactory");
		connection = cf.createConnection();
		
		Session session = connection.createSession();
		
		Queue queue =(Queue) initialContext.lookup("queue/myQueue");
		MessageProducer producer = session.createProducer(queue);
		
		//send a text message
		TextMessage message1 = session.createTextMessage("message1 ");
		TextMessage message2 = session.createTextMessage("message2 ");
		producer.send(message1);
		producer.send(message2);
		
		QueueBrowser browser = session.createBrowser(queue);
		
		Enumeration messagesenum = browser.getEnumeration();
		
		while(messagesenum.hasMoreElements()) {
			TextMessage eachMessage =(TextMessage) messagesenum.nextElement();
			System.out.println("Browsing:"+eachMessage.getText());
			
			
			
		}
		
		MessageConsumer consumer = session.createConsumer(queue);
		connection.start();
		TextMessage messageReceive = (TextMessage) consumer.receive(5000);
		System.out.println("messagerecived :"+messageReceive.getText());
		
		messageReceive = (TextMessage) consumer.receive(5000);
		System.out.println("messagerecived :"+messageReceive.getText());
		
	} catch (NamingException e) {
		e.printStackTrace();
	} catch (JMSException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally {
		if(initialContext!=null) {
			try {
				initialContext.close();
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		if(connection!=null) {
			try {
				connection.close();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
     
     //retrive connectiuo factory
     
     
     
	}

}
