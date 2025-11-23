package service;

import java.sql.SQLException;
import java.util.List;

import dao.interfaces.DAO_department;
import dto.DTO_department;
import exceptions.exception.DataAccessException;
import exceptions.exception.DepartmentNotFoundException;
import exceptions.exception.DepartmentTableEmptyException;
import exceptions.exception.InvalidDepartmentIdException;
import exceptions.exception.UnknownDepartmentIdException;
import utils.service.validator.Utils_service_department_validator;

public class Service_department {

	public final DAO_department department_dao;

	public Service_department(DAO_department department_dao) {
		this.department_dao = department_dao;
	}

	public DTO_department getDepartment(String department_id) throws InvalidDepartmentIdException, DataAccessException,
			UnknownDepartmentIdException, DepartmentNotFoundException {

		try {
			Utils_service_department_validator.validateDepartmentId(department_id);

			return this.department_dao.getDepartment(department_id);
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage(), e);
		}
	}

	public List<DTO_department> getAllDepartment() throws DepartmentTableEmptyException, DataAccessException {

		try {
			return this.department_dao.getAllDepartment();
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage(), e);
		}
	}
}
