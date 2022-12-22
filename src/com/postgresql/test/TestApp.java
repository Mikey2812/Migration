package com.postgresql.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestApp {
	
	private static String url = "jdbc:postgresql://localhost:5432/testdb";
	private static String username = "postgres";
	private static String password = "12345";
	
	Connection getConnection() {
		Connection cnt = null;
		try {
			cnt = DriverManager.getConnection(url, username, password);
			System.out.println("Connected to PostgreSQL!");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return cnt;
	}
	
	public static void main(String[] args) {
		TestApp app = new TestApp();
		app.getConnection();
	}
}
