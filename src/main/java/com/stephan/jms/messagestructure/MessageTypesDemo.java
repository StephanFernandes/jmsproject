package com.stephan.jms.messagestructure;

import java.io.Serializable;

import javax.jms.BytesMessage;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class MessageTypesDemo {

	public static void main(String[] args) throws NamingException, InterruptedException, JMSException {

		 //jndi initial context retriveing the queue from jnd
		InitialContext context = new InitialContext();
		
		Queue queue = (Queue) context.lookup("queue/myQueue");
		
		
		try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmscontext =cf.createContext()){
			
			JMSProducer producer = jmscontext.createProducer();
			
			TextMessage textMessage = jmscontext.createTextMessage("Arrised awake sun techlonogies");
			BytesMessage bytesMessage = jmscontext.createBytesMessage();
			bytesMessage.writeUTF("John");
			bytesMessage.writeLong(1234589l);
			
			StreamMessage streamMessage= jmscontext.createStreamMessage();
			streamMessage.writeBoolean(true);
			streamMessage.writeFloat(2.5f);
			
			MapMessage mapMessage=  jmscontext.createMapMessage();
			mapMessage.setBoolean("iscredit", true);
			
			ObjectMessage objectMessage = jmscontext.createObjectMessage();
			
			Patient patient = new Patient();
			patient.setId(123);
			patient.setName("john");
			objectMessage.setObject(patient);
      		//producer.send(queue, bytesMessage);
      		//producer.send(queue, streamMessage);
            //producer.send(queue, mapMessage);
			//producer.send(queue, objectMessage);
			producer.send(queue, patient);
			
//         BytesMessage messageRecived = (BytesMessage) jmscontext.createConsumer(queue).receive(5000);
//			
//			System.out.println(messageRecived.readUTF());
//			System.out.println(messageRecived.readLong());
			
//      	StreamMessage messageRecived = (StreamMessage) jmscontext.createConsumer(queue).receive(5000);
//     		System.out.println(messageRecived.readBoolean());
//     		System.out.println(messageRecived.readFloat());
      		
//     		MapMessage messageRecived = (MapMessage) jmscontext.createConsumer(queue).receive(5000);
//     		System.out.println(messageRecived.getBoolean("iscredit"));
			
			Patient patientRecived =  jmscontext.createConsumer(queue).receiveBody(Patient.class);
			//Patient object = (Patient) messageRecived.getObject();
			System.out.println(patientRecived.getId());
			System.out.print(patientRecived.getName());
      		
			
			
		}
		
	}

}
