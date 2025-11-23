package db;

import java.sql.*;

public class DB_Connection {

	public static Connection getConnection() throws SQLException {
		String url = "jdbc:sqlite:leave-request-service/data/Leave-management.db";
		return DriverManager.getConnection(url);
	}

}
