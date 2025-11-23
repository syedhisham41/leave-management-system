package event.consumer;

import event.base.Base_event;
import event.base.EventConsumer;
import event.types.EmployeeCreatedEvent;
import exceptions.exception.EventConsumeException;
import service.Service_leave_balance;

public class EmployeeCreatedEventConsumer implements EventConsumer<EmployeeCreatedEvent> {

	private final Service_leave_balance leave_balance_service;

	public EmployeeCreatedEventConsumer(Service_leave_balance leave_balance_service) {
		this.leave_balance_service = leave_balance_service;
	}

	@Override
	public void consumeEvent(Base_event<EmployeeCreatedEvent> event) throws EventConsumeException {

		try {
			int employee_id = event.getPayload().getEmployee_id();

			this.leave_balance_service.createLeaveBalance(employee_id, false);
		} catch (Exception e) {
			throw new EventConsumeException("Failed to consume EmployeeCreatedEvent", e);
		}

	}

}
