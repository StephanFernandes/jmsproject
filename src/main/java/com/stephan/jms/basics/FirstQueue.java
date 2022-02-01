package com.stephan.jms.basics;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class FirstQueue {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//creating initialcontext root to jndi registry
		InitialContext initialContext= null;
	     Connection connection = null;
	     
	     try {
			initialContext = new InitialContext();//instance oof initial context
			//lookup method always return object type ,typecast
			ConnectionFactory cf=(ConnectionFactory) initialContext.lookup("ConnectionFactory");//retrive connectionfactory
			connection = cf.createConnection();//
			
			Session session = connection.createSession();
			//messageproducer
			Queue queue =(Queue) initialContext.lookup("queue/myQueue");
			MessageProducer producer = session.createProducer(queue);
			
			//send a text message
			TextMessage message = session.createTextMessage("I am the craetor ");
			producer.send(message);
			System.out.println("Messagesent:"+message.getText());
			
			MessageConsumer consumer = session.createConsumer(queue);
			connection.start();
			TextMessage messageReceive = (TextMessage) consumer.receive(5000);
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
