package event.core;

import java.util.*;

import event.base.Base_event;
import event.base.EventPublisher;
import exceptions.exception.EventPublishException;

public class EventRouter {

	private final Map<Class<?>, EventPublisher<?>> publisherMap = new HashMap<>();

	public <T> void register(Class<T> payloadType, EventPublisher<T> publisher) {
		publisherMap.put(payloadType, publisher);
	}

	public <T> void publish(Base_event<T> event) throws EventPublishException {
		@SuppressWarnings("unchecked")
		EventPublisher<T> publisher = (EventPublisher<T>) publisherMap.get(event.getPayload().getClass());

		if (publisher == null) {
			throw new EventPublishException("No publisher registered for event type: " + event.getPayload().getClass(),
					null);
		}

		publisher.publishEvent(event);
	}
}
