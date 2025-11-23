package event.registry;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import config.EventConfig;
import event.core.EventRouter;
import event.publisher.*;
import event.types.*;
import event.base.*;

public class EventPublishRegistry {

	private final static EventRouter router;

	static {
		router = new EventRouter();

		Map<String, String> internalHeaders = new HashMap<String, String>();
		internalHeaders.put("Content-Type", "application/json");
		internalHeaders.put("X-Internal-Call", "true");

		// Use a single ObjectMapper instance with proper time config
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		String mode = EventConfig.PUBLISH_MODE;

		if ("HTTP".equalsIgnoreCase(mode)) {
			EventPublisher<EmployeeCreatedEvent> employeeCreatedPublisher =
				new HttpEventPublisher<EmployeeCreatedEvent>(
					"http://localhost:8081/leavebalance/create", objectMapper, internalHeaders);

			router.register(EmployeeCreatedEvent.class, employeeCreatedPublisher);

			EventPublisher<EmployeeDeletedEvent> employeeDeletedPublisher =
				new HttpEventPublisher<EmployeeDeletedEvent>(
					"http://localhost:8081/leavebalance/delete", objectMapper, internalHeaders);

			router.register(EmployeeDeletedEvent.class, employeeDeletedPublisher);
		}

		if ("MQ".equalsIgnoreCase(mode)) {
			EventPublisher<EmployeeCreatedEvent> employeeCreatedRabbitPublisher =
				new RabbitMQEventPublisher<>(
					"employee.exchange", "employee.created", objectMapper);

			router.register(EmployeeCreatedEvent.class, employeeCreatedRabbitPublisher);

			EventPublisher<EmployeeDeletedEvent> employeeDeletedRabbitPublisher =
				new RabbitMQEventPublisher<>(
					"employee.exchange", "employee.deleted", objectMapper);

			router.register(EmployeeDeletedEvent.class, employeeDeletedRabbitPublisher);
		}
	}

	public static EventRouter getRouter() {
		return router;
	}
}
