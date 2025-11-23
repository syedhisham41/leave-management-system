package event.publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

import event.base.Base_event;
import event.base.EventPublisher;
import exceptions.exception.EventPublishException;

public class RabbitMQEventPublisher<T> implements EventPublisher<T> {

	private final String exchangeName;
	private final String routingKey;
	private final ConnectionFactory connectionFactory;
	private final ObjectMapper objectMapper;

	public RabbitMQEventPublisher(String exchangeName, String routingKey, ObjectMapper objectMapper) {
		this.exchangeName = exchangeName;
		this.routingKey = routingKey;
		this.objectMapper = objectMapper;

		this.connectionFactory = new ConnectionFactory();

//		this.connectionFactory.setHost("localhost"); // or your Docker container IP
		this.connectionFactory.setHost("localhost");
		this.connectionFactory.setPort(5672); // default RabbitMQ port
		this.connectionFactory.setUsername("guest");
		this.connectionFactory.setPassword("guest");
	}

	@Override
	public void publishEvent(Base_event<T> event) throws EventPublishException {
		try (Connection connection = connectionFactory.newConnection(); Channel channel = connection.createChannel()) {
			System.out.println("Publishing event via " + this.getClass().getSimpleName());
			System.out.println("Connecting to RabbitMQ at " + connectionFactory.getHost());

			channel.exchangeDeclare(exchangeName, "topic", true); // durable exchange
			String message = objectMapper.writeValueAsString(event);

			System.out.println("Publishing message to exchange: " + exchangeName + " with routing key: " + routingKey);
			System.out.println("Payload: " + message);

			channel.basicPublish(exchangeName, routingKey, null, message.getBytes());
			System.out.println("Event published to RabbitMQ → " + routingKey + ": " + message);
			System.out.println("Event published successfully");

		} catch (Exception e) {
			System.err.println("Error publishing to RabbitMQ: " + e.getMessage());
			e.printStackTrace();
			throw new EventPublishException("Failed to publish event to RabbitMQ", e);
		}
	}
}
