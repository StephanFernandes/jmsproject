package com.stephan.jms.basics;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

//Same msg multiple consumer reciving it
//multiple consumer or subscriber can read the msg
public class FirstTopic {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		  
//		InitialContext initialContext= null;
//	     Connection connection = null;
	     //1st step instantiate initail context
	     
	     InitialContext initialContext = new InitialContext();
	     //retrive connection factory
	     Topic topic =(Topic) initialContext.lookup("topic/myTopic");
	   //retrive the connection factory from jndi or jms
	     ConnectionFactory cf= (ConnectionFactory) initialContext.lookup("ConnectionFactory");
	     Connection connection = cf.createConnection();
	     
	     Session session = connection.createSession();
	     MessageProducer producer = session.createProducer(topic);
	     
	     MessageConsumer consumer1 = session.createConsumer(topic);
	     MessageConsumer consumer2 = session.createConsumer(topic);
	     MessageConsumer consumer3 = session.createConsumer(topic);
	     
	     
	     
	     TextMessage message = session.createTextMessage("hello world");
	     producer.send(message);
	     
	     connection.start();
	     
	     
	     TextMessage message1 =(TextMessage) consumer1.receive();
	     System.out.println("consumer1 msg recived :"+message1.getText());
	     
         TextMessage message2 =(TextMessage) consumer2.receive();
	     System.out.println("consumer2 msg recived :"+message2.getText());
	     
	     TextMessage message3 =(TextMessage) consumer3.receive();
	     System.out.println("consumer2 msg recived :"+message3.getText());
	     
	     connection.close();
	     initialContext.close();

	}

}
