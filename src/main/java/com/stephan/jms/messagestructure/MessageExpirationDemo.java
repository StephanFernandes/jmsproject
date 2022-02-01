package com.stephan.jms.messagestructure;

import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class MessageExpirationDemo {

	public static void main(String[] args) throws NamingException, InterruptedException {

		 //jndi initial context retriveing the queue from jnd
		InitialContext context = new InitialContext();
		//run1st
		Queue queue = (Queue) context.lookup("queue/myQueue");
		Queue expiryQueue = (Queue) context.lookup("queue/expiryQueue");
		
		
		try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmscontext =cf.createContext()){
			
			JMSProducer producer = jmscontext.createProducer();
			//set expiration for the perticular msg we do
			//if we comment then it will give object
			producer.setTimeToLive(2000);
      		producer.send(queue, "Arrised awake sun techlonogies");
      		Thread.sleep(5000);
			Message messageRecived = jmscontext.createConsumer(queue).receive(5000);
			//run 1st
			System.out.println(messageRecived);
			System.out.println(jmscontext.createConsumer(expiryQueue).receiveBody(String.class));
			
			
		}
		
	}

}
