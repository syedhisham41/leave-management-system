package leave_request_service_runner;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sun.net.httpserver.HttpServer;

import dao.impl.sqlite.DAO_Impl_audit_log_sqlite;
import dao.impl.sqlite.DAO_Impl_get_leave_request_sqlite;
import dao.impl.sqlite.DAO_Impl_leave_balance_sqlite;
import dao.impl.sqlite.DAO_Impl_post_leave_request_sqlite;
import dao.impl.sqlite.DAO_Impl_update_leave_request_sqlite;
import dao.interfaces.DAO_audit_log;
import dao.interfaces.DAO_leave_balance;
import dao.interfaces.DAO_leave_request_get;
import dao.interfaces.DAO_leave_request_post;
import dao.interfaces.DAO_leave_request_update;
import db.DB_initialize;
import exceptions.mapper.AuthExceptionMapper;
import exceptions.mapper.DBExceptionMapper;
import exceptions.mapper.ExceptionMapper;
import exceptions.mapper.ExceptionMapperManager;
import exceptions.mapper.JsonExceptionMapper;
import exceptions.mapper.ParameterExceptionMapper;
import exceptions.mapper.ServiceExceptionMapper;
import handler.Handler_debug_exceptions;
import handler.Handler_delete_leave_balance;
import handler.Handler_get_audit_log;
import handler.Handler_get_leave_balance;
import handler.Handler_get_leave_request;
import handler.Handler_leave_request_approval_action;
import handler.Handler_post_leave_balance;
import handler.Handler_post_leave_request;
import service.Service_leave_audit_log;
import service.Service_leave_balance;
import service.Service_leave_request_approval;
import service.Service_leave_request_get;
import service.Service_leave_request_post;
import service.Service_leave_request_update;
import event.registry.*;
import event.listener.*;

public class Runner {

	public static void main(String[] args) throws IOException {

		DB_initialize.db_initializer();
		System.out.println("Starting leave-request service...");

		List<ExceptionMapper> mapper = List.of(new DBExceptionMapper(), new ParameterExceptionMapper(),
				new ServiceExceptionMapper(), new JsonExceptionMapper(), new AuthExceptionMapper());
		ExceptionMapperManager exceptionInstance = ExceptionMapperManager.getInstance(true, mapper);

		HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);

		DAO_leave_request_post leaveRequestDAO = new DAO_Impl_post_leave_request_sqlite();
		DAO_leave_request_get leaveRequestGetDAO = new DAO_Impl_get_leave_request_sqlite();
		DAO_leave_request_update leaveRequestUpdateDAO = new DAO_Impl_update_leave_request_sqlite();
		DAO_leave_balance leaveBalanceDAO = new DAO_Impl_leave_balance_sqlite();
		DAO_audit_log leaveAuditDAO = new DAO_Impl_audit_log_sqlite();

		Service_leave_request_get leaveRequestGetService = new Service_leave_request_get(leaveRequestGetDAO);
		Service_leave_audit_log leaveAuditLogService = new Service_leave_audit_log(leaveAuditDAO,
				leaveRequestGetService);
		Service_leave_request_post leaveRequestService = new Service_leave_request_post(leaveRequestDAO,
				leaveBalanceDAO, leaveAuditLogService);
		Service_leave_balance leavebalanceService = new Service_leave_balance(leaveBalanceDAO);
		Service_leave_request_update leaveRequestUpdateService = new Service_leave_request_update(
				leaveRequestUpdateDAO);
		Service_leave_request_approval leaveRequestApprovalService = new Service_leave_request_approval(
				leaveRequestGetService, leaveRequestUpdateService, leavebalanceService, leaveAuditLogService);

		server.createContext("/leaverequest", new Handler_post_leave_request(leaveRequestService, exceptionInstance));

		server.createContext("/leaverequest/get",
				new Handler_get_leave_request(leaveRequestGetService, exceptionInstance));

		server.createContext("/leavebalance/create",
				new Handler_post_leave_balance(leavebalanceService, exceptionInstance));

		server.createContext("/leavebalance/get",
				new Handler_get_leave_balance(leavebalanceService, exceptionInstance));

		server.createContext("/leavebalance/delete",
				new Handler_delete_leave_balance(leavebalanceService, exceptionInstance));

		server.createContext("/audit/get", new Handler_get_audit_log(leaveAuditLogService, exceptionInstance));

		server.createContext("/leaveapproval",
				new Handler_leave_request_approval_action(leaveRequestApprovalService, exceptionInstance));

		server.createContext("/debug/exceptions", new Handler_debug_exceptions(exceptionInstance));

		server.setExecutor(null);
		server.start();

		System.out.println("Server started on port 8081");

		new Thread(() -> {
			try {
				EventConsumerRegistry registry = new EventConsumerRegistry(leavebalanceService);

				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.registerModule(new JavaTimeModule());
				objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

				RabbitMQEventListener listener = new RabbitMQEventListener("employee.event.queue.leave.service", // Queue
																													// name
						registry.getRouter(), objectMapper);
				listener.startListening();
				System.out.println("Listening started for queue: " + "employee.event.queue");
			} catch (Exception e) {
				System.err.println("Failed to start RabbitMQ listener: " + e.getMessage());
				e.printStackTrace();
			}
		}).start();

		System.out.println("RabbitMQ Listener started");
	}

	// write leave cancel service TODO
	// write leave request get all service TODO
	// review and rewrite the cancel flow in handler TODO
	// make a map of all the flow and supported functions TODO

}
