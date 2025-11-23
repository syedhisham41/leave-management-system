package event.registry;

import event.consumer.EmployeeCreatedEventConsumer;
import event.consumer.EmployeeDeletedEventConsumer;
import event.core.EventConsumerRouter;
import event.types.*;
import service.*;

public class EventConsumerRegistry {

    private final EventConsumerRouter router = new EventConsumerRouter();

    public EventConsumerRegistry(Service_leave_balance leaveBalanceService) {
        router.register(EventType.EMPLOYEE_CREATED, new EmployeeCreatedEventConsumer(leaveBalanceService));
        router.register(EventType.EMPLOYEE_DELETED, new EmployeeDeletedEventConsumer(leaveBalanceService));
    }

    public EventConsumerRouter getRouter() {
        return router;
    }
}
