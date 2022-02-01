package com.stephan.jms.messagestructure;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;


public class RequestReplyDemo {

	public static void main(String[] args) throws NamingException{
 
		 //jndi initial context retriveing the queue from jnd
		InitialContext context = new InitialContext();
		Queue queue = (Queue) context.lookup("queue/requestQueue");
		Queue replyQueue = (Queue) context.lookup("queue/replyQueue");
		
		
		
		try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmscontext =cf.createContext()){
			//producing the msg ,extract the value
			JMSProducer producer = jmscontext.createProducer();
			producer.send(queue, "Arrised awake sun techlonogies");
			//consuming right herr
			
			JMSConsumer consumer = jmscontext.createConsumer(queue);
			String messageRecived = consumer.receiveBody(String.class);
			System.out.println(messageRecived);
			
			JMSProducer replyProducer = jmscontext.createProducer();
			replyProducer.send(replyQueue, "you are super");
			
			
			JMSConsumer replyConsumer = jmscontext.createConsumer(replyQueue);
			//String messageRecived = consumer.receiveBody(String.class);
			System.out.println(replyConsumer.receiveBody(String.class));
			
		}
	}

}
