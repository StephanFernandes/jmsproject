package com.stephan.jms.messagestructure;

import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class MessagePropertiesDemo {

	public static void main(String[] args) throws NamingException, InterruptedException, JMSException {

		 //jndi initial context retriveing the queue from jnd
		InitialContext context = new InitialContext();
		//run1st
		Queue queue = (Queue) context.lookup("queue/myQueue");
		
		
		try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmscontext =cf.createContext()){
			
			JMSProducer producer = jmscontext.createProducer();
			
			TextMessage textMessage = jmscontext.createTextMessage("Arrised awake sun techlonogies");
			textMessage.setBooleanProperty("loggedIn", true);
			textMessage.setStringProperty("userToken", "abc123");
			
      		producer.send(queue, textMessage);
			Message messageRecived = jmscontext.createConsumer(queue).receive(5000);
			//run 1st
			System.out.println(messageRecived);
			System.out.println(messageRecived.getBooleanProperty("loggedIn"));
			System.out.println(messageRecived.getStringProperty("userToken"));
			
			
		}
		
	}

}
