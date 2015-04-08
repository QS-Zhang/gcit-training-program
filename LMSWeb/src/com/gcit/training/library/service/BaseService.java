package com.gcit.training.library.service;

import java.sql.Connection;
import java.sql.DriverManager;

public class BaseService {

	protected Connection getConnection() throws Exception {
		try {
			String driver = "com.mysql.jdbc.Driver";
			Class.forName(driver);
			
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/library", "root", "");
			conn.setAutoCommit(false);
			
			return conn;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
}
