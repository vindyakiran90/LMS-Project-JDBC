package com.tyss.lmsjdbc.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.tyss.lmsjdbc.exception.LMSException;

public class JdbcUtility {
	
	private static Connection connection = null;
	
	public static Connection getConnection() throws LMSException {
		
		File file = new File("resources/jdbc.properties");
		FileInputStream inputStream = null;
		
		try {
			inputStream = new FileInputStream(file);
			
			Properties properties = new Properties();
			properties.load(inputStream);
			
			String driver = properties.getProperty("db.driver");
			String url = properties.getProperty("db.url");
			String username = properties.getProperty("db.username");
			String password = properties.getProperty("db.password");
			
			Class.forName(driver).newInstance();
			connection = DriverManager.getConnection(url, username, password);
		} catch (FileNotFoundException e) {
			throw new LMSException("File does not exists");
		} catch (IOException e) {
			throw new LMSException("unable to read the data from the file");
		} catch (ClassNotFoundException e) {
			throw new LMSException("Class not loaded");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new LMSException("Connection issue");
		} catch (InstantiationException e) {
			throw new LMSException("Instantiation issue");
		} catch (IllegalAccessException e) {
			throw new LMSException("Instantiation issue");
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				throw new LMSException("Unable to close the file");
			}
			
		}
		return connection;
	}
}
