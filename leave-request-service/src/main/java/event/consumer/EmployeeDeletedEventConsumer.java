package event.consumer;

import event.base.Base_event;
import event.base.EventConsumer;
import event.types.EmployeeDeletedEvent;
import exceptions.exception.EventConsumeException;
import service.Service_leave_balance;

public class EmployeeDeletedEventConsumer implements EventConsumer<EmployeeDeletedEvent> {

	private final Service_leave_balance leave_balance_service;

	public EmployeeDeletedEventConsumer(Service_leave_balance leave_balance_service) {
		this.leave_balance_service = leave_balance_service;
	}

	@Override
	public void consumeEvent(Base_event<EmployeeDeletedEvent> event) throws EventConsumeException {

		try {
			int employee_id = event.getPayload().getEmployee_id();

			this.leave_balance_service.deleteLeaveBalance(employee_id, true);
		} catch (Exception e) {
			throw new EventConsumeException("Failed to consume EmployeeDeletedEvent", e);
		}

	}

}
