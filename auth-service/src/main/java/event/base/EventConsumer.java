package event.base;

import exceptions.exception.EventConsumeException;

public interface EventConsumer<T> {
	
	void consumeEvent(Base_event<T> event) throws EventConsumeException;

}
