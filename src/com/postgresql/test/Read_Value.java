package com.postgresql.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Read_Value {
	public static void main(String[] args) {
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		ConnectDB obj_ConnectDB = new ConnectDB();
		connection = obj_ConnectDB.getConnection();
		
		try {
			/*String query = "select * from MonAn";
			statement = connection.createStatement();
			rs = statement.executeQuery(query);
			while(rs.next()) {
				System.out.print(rs.getString("MaMon")+" ");
				System.out.print(rs.getString("TenMon")+" ");
				System.out.print(rs.getString("Gia")+" ");
				System.out.println();
			}*/
			String query = "select count(*) from monan";
			statement = connection.createStatement();
			rs = statement.executeQuery(query);
		      //Retrieving the result
		      rs.next();
		      int count = rs.getInt(1);
		      System.out.println("Number of records table: "+count);
		} catch (Exception e) {
			e.printStackTrace();
	 	}
	}
}
