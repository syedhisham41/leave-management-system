package dao.interfaces;

import java.sql.SQLException;
import java.util.List;

import exceptions.exception.DepartmentManagerTableEmptyException;
import model.DepartmentManager;

public interface DAO_manager {
	
	public List<DepartmentManager> getManagerByDept(String department_id) throws SQLException, DepartmentManagerTableEmptyException;

	public List<DepartmentManager> getAllManagers() throws SQLException, DepartmentManagerTableEmptyException;

}
