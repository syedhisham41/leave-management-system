package auth_service_runner;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sun.net.httpserver.HttpServer;

import dao.impl.sqlite.DAO_impl_login_sqlite;
import dao.impl.sqlite.DAO_impl_signup_sqlite;
import dao.interfaces.DAO_Login;
import dao.interfaces.DAO_Signup;
import db.DB_initialize;
import event.listener.RabbitMQEventListener;
import event.registry.EventConsumerRegistry;
import exceptions.mapper.DBExceptionMapper;
import exceptions.mapper.ExceptionMapper;
import exceptions.mapper.ExceptionMapperManager;
import exceptions.mapper.JSONExceptionMapper;
import exceptions.mapper.ParameterExceptionMapper;
import exceptions.mapper.ServiceExceptionMapper;
import handler.Handler_debug_exceptions;
import handler.swagger_ui.ClassPathFileHandler;
import handler.Handler_login;
import handler.Handler_signup;
import service.Service_login;
import service.Service_signup;

public class Auth_Runner {

	public static void main(String[] args) throws IOException {

		DB_initialize.db_initializer();
		System.out.println("Starting auth service...");

		List<ExceptionMapper> mapper = List.of(new DBExceptionMapper(), new ParameterExceptionMapper(),
				new ServiceExceptionMapper(), new JSONExceptionMapper());

		ExceptionMapperManager exceptionInstance = ExceptionMapperManager.getInstance(true, mapper);

		HttpServer server = HttpServer.create(new InetSocketAddress(8082), 0);

		DAO_Signup signup_dao = new DAO_impl_signup_sqlite();
		DAO_Login login_dao = new DAO_impl_login_sqlite();

		Service_signup signup_service = new Service_signup(signup_dao);
		Service_login login_service = new Service_login(login_dao);

		server.createContext("/auth/signup", new Handler_signup(signup_service, exceptionInstance));
		server.createContext("/auth/login", new Handler_login(login_service, exceptionInstance));
		server.createContext("/debug/exceptions", new Handler_debug_exceptions(exceptionInstance));
		server.createContext("/docs", new ClassPathFileHandler("/swagger-ui"));
		server.createContext("/specs", new ClassPathFileHandler("/specs"));

		server.setExecutor(null);
		server.start();
		System.out.println("Server started on port 8082");

		new Thread(() -> {
			try {
				EventConsumerRegistry registry = new EventConsumerRegistry(signup_service);

				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.registerModule(new JavaTimeModule());
				objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

				RabbitMQEventListener listener = new RabbitMQEventListener("employee.event.queue.auth.service", // Queue
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
}
