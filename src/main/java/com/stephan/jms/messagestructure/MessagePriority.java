package com.stephan.jms.messagestructure;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class MessagePriority {

	public static void main(String[] args) throws NamingException {
		

		InitialContext context = new InitialContext();
		Queue queue = (Queue) context.lookup("queue/myQueue");
		try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext = cf.createContext()){
			JMSProducer producer = jmsContext.createProducer();
			String[] messages =new String[3];
			messages[0]="message1";
			messages[1]="message2";
			messages[2]="message3";
			//default priority comment producer.setpriority
			//default priority is 4
			//producer.setPriority(3);
			producer.send(queue, messages[0]);
			
		   // producer.setPriority(1);
			producer.send(queue, messages[1]);
			
			
			//producer.setPriority(9);
			producer.send(queue, messages[2]);
			//as higher the number the greater the priority
			
			//create consumer
			JMSConsumer consumer = jmsContext.createConsumer(queue);
			
			for(int i=0;i<3;i++) {
				//we will get priority values
				Message recivedMessage = consumer.receive();
				System.out.println(recivedMessage.getJMSPriority());
				//1strun this 
				//System.out.println(consumer.receiveBody(String.class)); 
			}
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
	}

}
