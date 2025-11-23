package event.registry;

import event.consumer.EmployeeDeletedEventConsumer;
import event.core.EventConsumerRouter;
import event.types.*;
import service.*;

public class EventConsumerRegistry {

	private final EventConsumerRouter router = new EventConsumerRouter();

	public EventConsumerRegistry(Service_signup signUpService) {
//        router.register(EventType.EMPLOYEE_CREATED, new EmployeeCreatedEventConsumer(signUpService));
		router.register(EventType.EMPLOYEE_DELETED, new EmployeeDeletedEventConsumer(signUpService));
	}

	public EventConsumerRouter getRouter() {
		return router;
	}
}
