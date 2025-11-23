package dao.factory.impl;

import dao.factory.DAO_factory;
import dao.impl.sqlite.*;
import dao.interfaces.*;

public class DAO_factory_sqlite_impl implements DAO_factory {

	@Override
	public DAO_employee_select getEmployeeSelectDAO() {
		return new DAO_Impl_employee_select_sqlite();
	}

	@Override
	public DAO_employee_insert getEmployeeInsertDAO() {
		return new DAO_Impl_employee_insert_sqlite();
	}

	@Override
	public DAO_employee_update getEmployeeUpdateDAO() {
		return new DAO_Impl_employee_update_sqlite();
	}

	@Override
	public DAO_employee_delete getEmployeeDeleteDAO() {
		return new DAO_Impl_employee_delete_sqlite();
	}

	@Override
	public DAO_department getDepartmentDAO() {
		return new DAO_Impl_department_sqlite();
	}

	@Override
	public DAO_manager getManagerDAO() {
		return new DAO_Impl_department_manager_sqlite();
	}

}
