package weka.web.data;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHelper {
	
//	private static final Logger logger = LoggerFactory.getLogger(DatabaseHelper.class);
	
	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/pestincidence";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "mysql";
	private Connection connection = null;
	
	public Statement createStatement(boolean autoCommit) {
		connection = getConnection();
		Statement statement = null;
		try {
			connection.setAutoCommit(autoCommit);
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return statement;
	}
	
	public DatabaseMetaData getMetaData() {
		try {
			connection = getConnection();
			return connection.getMetaData();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void commit() {
		try {
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void closeStatement(Statement statement) {
		try {
			statement.close();
		} catch (SQLException e) {
//			logger.info("Error while closing connection");
		}
	}
	
    private Connection getConnection() {
		
		try {
			Class.forName(DB_DRIVER);
			connection = DriverManager
					.getConnection(DB_CONNECTION,DB_USER, DB_PASSWORD);
//			if (connection == null) 
//				logger.info("Failed to make connection!");
		} catch (ClassNotFoundException e) {
//			logger.error("Failed to load driver.");
		} catch (SQLException sqe) {
//			logger.error("Failed to establish connection");
		}
	 
		return connection;
	}
}
