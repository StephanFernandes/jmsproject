package com.stephan.jms.basics;

import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;


public class JMSContextDemo {

	public static void main(String[] args) throws NamingException  {
       //jndi initial context retriveing the queue from jnd
		InitialContext context = new InitialContext();
		Queue queue = (Queue) context.lookup("queue/myQueue");
		
		//created activemq connection factory by instantiating it inside the try block ,
		//once you instantiate or create a resource inside try with resource block we need notclsoe those resource block
		//automaticaly those will be closed ,java will invoke close method on them
		
		try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmscontext =cf.createContext()){
			
			jmscontext.createProducer().send(queue, "Arrised awake sun techlonogies");
			String messageRecived = jmscontext.createConsumer(queue).receiveBody(String.class);
			
			System.out.println(messageRecived);
			
		}
		
		
		
	}

}
