
package com.app.rabbitmq;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/***
 * 
 * RabbitMQ Receiver 
 * It listens to "radio" Queue at localhost
 * 
 * @author Anant Goswami
 * @since 13/11/2017
 *
 */
public class Receive {
	private final static String QUEUE_NAME = "radio";
	private final static String HOST = "localhost";

	public static void main(String[] argv) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(HOST);
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		System.out.println(" [*] Waiting for messages.");

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {

				String message = new String(body, "UTF-8");
				System.out.println(String.format(" [%s] Received '%s'", envelope.getDeliveryTag(), message));
			}
		};
		channel.basicConsume(QUEUE_NAME, true, consumer);
	}
}
