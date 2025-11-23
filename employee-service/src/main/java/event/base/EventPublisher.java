package event.base;

import exceptions.exception.EventPublishException;

public interface EventPublisher<T> {
	
	void publishEvent(Base_event<T> event) throws EventPublishException;

}
