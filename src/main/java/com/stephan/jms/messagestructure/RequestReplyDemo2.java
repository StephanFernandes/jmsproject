package com.stephan.jms.messagestructure;

import java.util.HashMap;
import java.util.Map;

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
public class RequestReplyDemo2 {

	public static void main(String[] args) throws NamingException, JMSException {

		//jndi initial context retriveing the queue from jnd
				InitialContext context = new InitialContext();
				Queue queue = (Queue) context.lookup("queue/requestQueue");
				//Queue replyQueue = (Queue) context.lookup("queue/replyQueue");
				
				
				
				try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
						JMSContext jmscontext =cf.createContext()){
					//producing the msg
					JMSProducer producer = jmscontext.createProducer();
					//we are creating a queue
					TemporaryQueue replyQueue = jmscontext.createTemporaryQueue();
					TextMessage message = jmscontext.createTextMessage("Arrised awake sun techlonogies");
					message.setJMSReplyTo(replyQueue);
					producer.send(queue, message);
					//correlated 
					System.out.println("Provider="+message.getJMSMessageID());//id will get ,set jms header for us
					
					
					//to create a link we can use map hashmap
					Map<String,TextMessage> requestMessages =new HashMap<>();
					requestMessages.put(message.getJMSMessageID(), message);
					
					
					//consuming right herr
					JMSConsumer consumer = jmscontext.createConsumer(queue);
					TextMessage messageRecived = (TextMessage) consumer.receive();
					System.out.println(messageRecived.getText());
					
					JMSProducer replyProducer = jmscontext.createProducer();
					TextMessage replyMessage = jmscontext.createTextMessage("you are super");
					replyMessage.setJMSCorrelationID(messageRecived.getJMSMessageID());
					replyProducer.send(messageRecived.getJMSReplyTo(), replyMessage);//reply back
					
					
					JMSConsumer replyConsumer = jmscontext.createConsumer(replyQueue);
					//String messageRecived = consumer.receiveBody(String.class);
					
					//System.out.println(replyConsumer.receiveBody(String.class));
					TextMessage replyReceived =(TextMessage) replyConsumer.receive();
		            System.out.println("correlationId come back="+replyReceived.getJMSCorrelationID());		
		            
		            //System.out.println("value="+requestMessages.get(replyReceived.getJMSCorrelationID()));
		            System.out.println("value="+requestMessages.get(replyReceived.getJMSCorrelationID()).getText());
				}
				
				
	}

}
