package db;

import java.sql.*;

import config.PropertyConfig;

public class DB_Connection {

	public static Connection getConnection() throws SQLException {
		String url = PropertyConfig.get("DB_URL");
		return DriverManager.getConnection(url);
	}

}
