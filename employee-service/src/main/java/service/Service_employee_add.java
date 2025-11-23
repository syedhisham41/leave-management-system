package service;

import java.sql.SQLException;
import java.time.Instant;

import dao.interfaces.DAO_employee_insert;
import dto.DTO_employee_add_result;
import event.registry.EventPublishRegistry;
import event.types.EmployeeCreatedEvent;
import event.types.EventType;
import event.base.Base_event;
import exceptions.exception.DataAccessException;
import exceptions.exception.EmployeeNotFoundException;
import exceptions.exception.EventPublishException;
import exceptions.exception.InvalidEmployeeEmailException;
import exceptions.exception.InvalidEmployeeJoiningDateException;
import exceptions.exception.InvalidEmployeeNameException;
import model.Employee;
import utils.service.validator.Utils_service_employee_validator;

public class Service_employee_add {

	private final DAO_employee_insert employee_insert_dao;

	public Service_employee_add(DAO_employee_insert employee_insert_dao) {
		this.employee_insert_dao = employee_insert_dao;
	}

	public DTO_employee_add_result addEmployee(Employee employee)
			throws DataAccessException, InvalidEmployeeNameException, InvalidEmployeeEmailException,
			InvalidEmployeeJoiningDateException, EmployeeNotFoundException {

		boolean eventFailed = false;
		Utils_service_employee_validator.employeeNameValidate(employee.getName());

		Utils_service_employee_validator.employeeEmailValidate(employee.getEmail());

		Utils_service_employee_validator.employeeJoiningDateValidate(employee.getJoining_date());

		try {
			int resultDAO = this.employee_insert_dao.InsertEmployeeDAO(employee);

			try {
				EmployeeCreatedEvent payload = new EmployeeCreatedEvent(resultDAO, employee.getEmp_department_id());
				Base_event<EmployeeCreatedEvent> event = new Base_event<EmployeeCreatedEvent>(
						EventType.EMPLOYEE_CREATED, 1, Instant.now(), payload);
				EventPublishRegistry.getRouter().publish(event);

			} catch (EventPublishException e) {
				System.err.println("Failed to publish EMPLOYEE_CREATED event: " + e.getMessage());
				eventFailed = true;
			}

			return new DTO_employee_add_result(resultDAO, employee.getEmp_department_id(), eventFailed);

		} catch (SQLException e) {
			throw new DataAccessException("failed to add new employee", e);
		}
	}
}
