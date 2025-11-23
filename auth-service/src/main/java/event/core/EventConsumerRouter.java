package event.core;

import java.util.HashMap;
import java.util.Map;

import event.base.Base_event;
import event.base.EventConsumer;
import exceptions.exception.EventConsumeException;

public class EventConsumerRouter {

	private final Map<String, EventConsumer<?>> consumerMap = new HashMap<>();

	public <T> void register(String eventType, EventConsumer<T> consumer) {
	    consumerMap.put(eventType, consumer);
	}

	public <T> void dispatch(Base_event<T> event) throws EventConsumeException {
	    @SuppressWarnings("unchecked")
	    EventConsumer<T> consumer = (EventConsumer<T>) consumerMap.get(event.getEventType());

	    if (consumer == null) {
	        throw new EventConsumeException("No consumer registered for event type: " + event.getEventType(), null);
	    }

	    consumer.consumeEvent(event);
	}

}
