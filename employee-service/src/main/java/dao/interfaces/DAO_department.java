package dao.interfaces;

import java.sql.SQLException;
import java.util.List;

import dto.DTO_department;
import exceptions.exception.DepartmentNotFoundException;
import exceptions.exception.DepartmentTableEmptyException;

public interface DAO_department {

	public DTO_department getDepartment(String department_id) throws SQLException, DepartmentNotFoundException;
	
	public List<DTO_department> getAllDepartment() throws SQLException, DepartmentTableEmptyException;
}
