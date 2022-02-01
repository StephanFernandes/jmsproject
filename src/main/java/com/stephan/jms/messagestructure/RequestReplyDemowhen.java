package com.stephan.jms.messagestructure;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.jms.TemporaryQueue;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

//dynamicaly we create queue

public class RequestReplyDemowhen {

	public static void main(String[] args) throws NamingException, JMSException{
 
		 //jndi initial context retriveing the queue from jnd
		InitialContext context = new InitialContext();
		Queue queue = (Queue) context.lookup("queue/requestQueue");
		//Queue replyQueue = (Queue) context.lookup("queue/replyQueue");
		
		
		
		try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmscontext =cf.createContext()){
			//producing the msg ,extract the value
			JMSProducer producer = jmscontext.createProducer();
			TemporaryQueue replyQueue = jmscontext.createTemporaryQueue();
			TextMessage message = jmscontext.createTextMessage("Arrised awake sun techlonogies");
            message.setJMSReplyTo(replyQueue);
			producer.send(queue, message);
			//consuming right herr
			
			JMSConsumer consumer = jmscontext.createConsumer(queue);
			TextMessage messageRecived = (TextMessage) consumer.receive();
			System.out.println(messageRecived.getText());
			
			JMSProducer replyProducer = jmscontext.createProducer();
			replyProducer.send(messageRecived.getJMSReplyTo(), "you are super");
			
			
			JMSConsumer replyConsumer = jmscontext.createConsumer(replyQueue);
			//String messageRecived = consumer.receiveBody(String.class);
			System.out.println(replyConsumer.receiveBody(String.class));
			
		}
	}

}
