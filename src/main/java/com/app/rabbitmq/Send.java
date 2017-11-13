package com.app.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/***
 * 
 * Sends messages to "radio" Queue at localhost 
 * 
 * @author Anant Goswami
 * @since 13/11/2017
 */
public class Send {

	private final static String QUEUE_NAME = "radio";
	private final static String HOST = "localhost";

	public static void main(String[] argv) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(HOST);
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		String message = "Good Morning Mumbai....!";
		channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
		System.out.println(String.format(" [x] Sent '%s'", message));

		channel.close();
		connection.close();
	}
}
