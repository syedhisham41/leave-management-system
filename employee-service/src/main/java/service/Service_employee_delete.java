package service;

import java.sql.SQLException;
import java.time.Instant;

import dao.interfaces.DAO_employee_delete;
import dto.DTO_employee_delete_result;
import event.base.Base_event;
import event.registry.EventPublishRegistry;
import event.types.EmployeeDeletedEvent;
import event.types.EventType;
import exceptions.exception.DataAccessException;
import exceptions.exception.EmployeeNotFoundException;
import exceptions.exception.EventPublishException;
import exceptions.exception.EmployeeIdMissingException;

public class Service_employee_delete {

	private final DAO_employee_delete employee_delete_dao;

	public Service_employee_delete(DAO_employee_delete employee_delete_dao) {
		this.employee_delete_dao = employee_delete_dao;
	}

	public DTO_employee_delete_result deleteEmployee(int id)
			throws DataAccessException, EmployeeIdMissingException, EmployeeNotFoundException {

		boolean eventFailed = false;
		if (id < 0)
			throw new EmployeeIdMissingException("'Id' parameter is invalid", null);

		try {
			int affectedRows = this.employee_delete_dao.deleteEmployeeDAO(id);
			System.out.println("affected rows in delete :"+affectedRows);
			if (affectedRows <= 0)
				throw new EmployeeNotFoundException("Employee with ID " + id + " not found");

			try {
				EmployeeDeletedEvent payload = new EmployeeDeletedEvent(id);
				Base_event<EmployeeDeletedEvent> event = new Base_event<EmployeeDeletedEvent>(EventType.EMPLOYEE_DELETED, 1,
						Instant.now(), payload);
				EventPublishRegistry.getRouter().publish(event);

			} catch (EventPublishException e) {
				System.err.println("Failed to publish EMPLOYEE_DELETED event: " + e.getMessage());
				eventFailed = true;
			}
			return new DTO_employee_delete_result(id, eventFailed);
		} catch (SQLException e) {
			throw new DataAccessException("failed to delete employee with Id " + id, e);
		}
	}

}
