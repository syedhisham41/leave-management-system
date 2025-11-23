package employee_service_runner;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

import com.sun.net.httpserver.HttpServer;

import dao.factory.*;
import dao.interfaces.*;
import db.*;
import exceptions.exception.DbNotFoundException;
import exceptions.mapper.AuthExceptionMapper;
import exceptions.mapper.DBExceptionMapper;
import exceptions.mapper.ExceptionMapper;
import exceptions.mapper.ExceptionMapperManager;
import exceptions.mapper.JsonExceptionMapper;
import exceptions.mapper.ParameterExceptionMapper;
import exceptions.mapper.ServiceExceptionMapper;
import handler.*;
import handler.swagger_ui.ClassPathFileHandler;
import service.*;

public class Main {

	public static void main(String[] args) throws IOException {

		System.setProperty("EVENT_PUBLISH_MODE", "MQ"); // or hTTP

		DB_initialize.db_initializer();
		System.out.println("Starting employee service...");

		List<ExceptionMapper> mapper = List.of(new DBExceptionMapper(), new ParameterExceptionMapper(),
				new ServiceExceptionMapper(), new JsonExceptionMapper(), new AuthExceptionMapper());

		ExceptionMapperManager exceptionInstance = ExceptionMapperManager.getInstance(true, mapper);

		HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

		DAO_factory factory = null;
		try {
			factory = DAO_factory_selector.dao_factory_selector(DB_Parameters.DB_TYPE);
		} catch (DbNotFoundException e) {
			e.printStackTrace();
		}

		DAO_employee_select dao_select = factory.getEmployeeSelectDAO();
		DAO_employee_insert dao_insert = factory.getEmployeeInsertDAO();
		DAO_employee_update dao_update = factory.getEmployeeUpdateDAO();
		DAO_employee_delete dao_delete = factory.getEmployeeDeleteDAO();

		DAO_department dao_department = factory.getDepartmentDAO();

		DAO_manager dao_manager = factory.getManagerDAO();

		Service_employee_get emp_get = new Service_employee_get(dao_select);
		Service_employee_add emp_add = new Service_employee_add(dao_insert);
		Service_employee_update emp_update = new Service_employee_update(dao_update);
		Service_employee_delete emp_delete = new Service_employee_delete(dao_delete);

		Service_department department_service = new Service_department(dao_department);

		Service_manager manager_service = new Service_manager(dao_manager, department_service, emp_get);

		// server.createContext("/employee/get", new Handler_get_employee_by_id(emp_get,
		// exceptionInstance));
		// server.createContext("/employee/getall", new
		// Handler_get_all_employees(emp_get));
		server.createContext("/employee/get", new Handler_get_employee(emp_get, manager_service, exceptionInstance));
		server.createContext("/employee/add", new Handler_insert_employee(emp_add, exceptionInstance));
		server.createContext("/employee/update", new Handler_update_employee(emp_update, exceptionInstance));
		server.createContext("/employee/delete", new Handler_delete_employee(emp_delete, exceptionInstance));
		// server.createContext("/departments", new
		// Handler_get_all_departments_with_query(emp_get));
		server.createContext("/department", new Handler_get_department(department_service, exceptionInstance));
		server.createContext("/manager", new Handler_get_manager(manager_service, exceptionInstance));
		server.createContext("/manager/check", new Handler_get_manager_check(manager_service, exceptionInstance));
		server.createContext("/debug/exceptions", new Handler_debug_exceptions(exceptionInstance));
		server.createContext("/docs", new ClassPathFileHandler("/swagger-ui"));
		server.createContext("/specs", new ClassPathFileHandler("/specs"));
		server.setExecutor(null);
		server.start();
		System.out.println("Server started on port 8080");

	}

}
