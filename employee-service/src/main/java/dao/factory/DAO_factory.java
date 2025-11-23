package dao.factory;

import dao.interfaces.*;

public interface DAO_factory {

	DAO_employee_select getEmployeeSelectDAO();
	
	DAO_employee_insert getEmployeeInsertDAO();
	
	DAO_employee_update getEmployeeUpdateDAO();
	
	DAO_employee_delete getEmployeeDeleteDAO();
	
	DAO_department getDepartmentDAO();
	
	DAO_manager getManagerDAO();
}
