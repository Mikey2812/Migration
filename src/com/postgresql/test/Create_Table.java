package com.postgresql.test;

import java.sql.Statement;
import java.sql.Connection;

public class Create_Table {
	public static void main(String[] args) {
		Connection connection = null;
		Statement statement = null;
		ConnectDB obj_ConnectDB = new ConnectDB();
		connection = obj_ConnectDB.getConnection();
		try {
			String query = "Create table MonAn(MaMon varchar(10) primary key, TenMon varchar(30), gia int)";
			String query2 = "Create table KhachHang(MaKH varchar(10) primary key, HoTenKH varchar(30), DiaChi varchar(30), SDT int)";
			String query3 = "Create table NhanVien(MaNV varchar(10) primary key, TenMon varchar(30), NgaySinh date, SDT int, DiaChi varchar(30))";
			String query4 = "Create table DoanhThu(MaDT varchar(10) primary key, TenMon varchar(30), gia int)";
			String query5 = "Create table HoaDon(MaHD varchar(10) primary key, MaNV varchar(10), MaKH varchar(10), MaMon varchar(10), TenMon varchar(30),"
					+ "			 NgayNhap date, NgayXuat date, SoLuong int, TongTien int)";
			statement = connection.createStatement();
			statement.executeUpdate(query);
			statement.executeUpdate(query2);
			statement.executeUpdate(query3);
			statement.executeUpdate(query4);
			statement.executeUpdate(query5);
			System.out.println("Finish");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
 