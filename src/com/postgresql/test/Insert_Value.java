package com.postgresql.test;

import java.sql.Connection;
import java.sql.Statement;

public class Insert_Value {
	public static void main(String[] args) {
		Connection connection = null;
		Statement statement = null;
		ConnectDB obj_ConnectDB = new ConnectDB();
		connection = obj_ConnectDB.getConnection();
		try {
			String query = "insert into MonAn values('MA01','Súp cua','100000')";
			String query2 = "insert into MonAn values('MA02','Bò bít tết','250000')";
			String query3 = "insert into MonAn values('MA03','Tôm Alaska sốt bơ tỏi','2000000')";
			String query4 = "insert into MonAn values('MA04','Lẩu hải sản','400000')";
			String query5 = "insert into MonAn values('MA05','Gà rang muối','300000')";
			String query6 = "insert into MonAn values('MA06','Món 6','300000')";
			statement = connection.createStatement();
			statement.executeUpdate(query);
			statement.executeUpdate(query2);
			statement.executeUpdate(query3);
			statement.executeUpdate(query4);
			statement.executeUpdate(query5);
			statement.executeUpdate(query6);
			System.out.println("Insert Value Thanh Cong");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
