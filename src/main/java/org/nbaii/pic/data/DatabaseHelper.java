package org.nbaii.pic.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseHelper {
	
	private static final Logger logger = LoggerFactory.getLogger(DatabaseHelper.class);
	
	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/pestincidence";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "";
	private Connection connection = null;
	
	public Statement createStatement() {
		connection = getConnection();
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			logger.error("Failed to create statement");
		}
		return statement;
	}
	
	public void closeStatementAndConnection(Statement statement) {
		try {
			statement.close();
			connection.close();
		} catch (SQLException e) {
			logger.info("Error while closing connection");
		}
	}
	
    private Connection getConnection() {
		
		try {
			Class.forName(DB_DRIVER);
			connection = DriverManager
					.getConnection(DB_CONNECTION,DB_USER, DB_PASSWORD);
			if (connection == null) 
				logger.info("Failed to make connection!");
		} catch (ClassNotFoundException e) {
			logger.error("Failed to load driver.");
		} catch (SQLException sqe) {
			logger.error("Failed to establish connection");
		}
	 
		return connection;
	}
}
