package event.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;

import event.base.Base_event;
import event.core.EventConsumerRouter;
import event.types.EventType;

public class RabbitMQEventListener {

	private final String queueName;
	private final EventConsumerRouter router;
	private final ObjectMapper objectMapper;

	public RabbitMQEventListener(String queueName, EventConsumerRouter router, ObjectMapper objectMapper) {
		this.queueName = queueName;
		this.router = router;
		this.objectMapper = objectMapper;
	}

	public void startListening() throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost"); // or use docker IP
		factory.setUsername("guest");
		factory.setPassword("guest");
		factory.setPort(5672);

		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.queueDeclare(queueName, true, false, false, null);
		System.out.println(" [*] Waiting for messages in queue: " + queueName);
		
		channel.queueDeclare(queueName, true, false, false, null);

		channel.exchangeDeclare("employee.exchange", "topic", true);
		channel.queueBind(queueName, "employee.exchange", "employee.created");
		channel.queueBind(queueName, "employee.exchange", "employee.deleted");

		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			try {
				String message = new String(delivery.getBody(), "UTF-8");

				// Generic deserialization: Base_event with raw payload
				Base_event<?> baseEvent = objectMapper.readValue(message, Base_event.class);

				// We need to re-deserialize payload to the correct type
				String eventType = baseEvent.getEventType();

				Class<?> payloadClass = switch (eventType) {
				case EventType.EMPLOYEE_CREATED -> event.types.EmployeeCreatedEvent.class;
				case EventType.EMPLOYEE_DELETED -> event.types.EmployeeDeletedEvent.class;
				default -> throw new IllegalArgumentException("Unknown event type: " + eventType);
				};

				Base_event<?> typedEvent = objectMapper.readValue(message,
						objectMapper.getTypeFactory().constructParametricType(Base_event.class, payloadClass));

				router.dispatch(typedEvent);

			} catch (Exception e) {
				System.err.println("Failed to process event: " + e.getMessage());
				e.printStackTrace();
			}
		};

		channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
		});
	}
}
