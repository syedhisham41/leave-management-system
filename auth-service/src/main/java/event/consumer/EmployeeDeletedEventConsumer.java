package event.consumer;

import event.base.Base_event;
import event.base.EventConsumer;
import event.types.EmployeeDeletedEvent;
import exceptions.exception.EventConsumeException;
import service.Service_signup;

public class EmployeeDeletedEventConsumer implements EventConsumer<EmployeeDeletedEvent> {

	private final Service_signup signup_service;

	public EmployeeDeletedEventConsumer(Service_signup signup_service) {
		this.signup_service = signup_service;
	}

	@Override
	public void consumeEvent(Base_event<EmployeeDeletedEvent> event) throws EventConsumeException {

		try {
			int employee_id = event.getPayload().getEmployee_id();

			this.signup_service.deleteUserRecord(employee_id);
		} catch (Exception e) {
			throw new EventConsumeException("Failed to consume EmployeeDeletedEvent :" + e.getMessage(), e);
		}

	}

}
