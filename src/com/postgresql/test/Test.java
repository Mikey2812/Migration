package com.postgresql.test;

import java.awt.print.Printable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.sql.Connection;
import java.sql.Statement;

import org.apache.poi.hssf.record.PageBreakRecord.Break;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.Value;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Test {

	public static Connection ketnoi(Connection connection) {
		ConnectDB obj_ConnectDB = new ConnectDB();
		connection = obj_ConnectDB.getConnection();
		return connection;
	}
	public static String soCot(int x) {
		String y = "";
		for(int i = 1; i <= x; i++) {
			if(i != 1) {
				y = y + ",?";
			}
			else {
				y = "?";
			}
		}
		return y;
	}
	public static String Cot(int n, String[] a) {
		String y = "";
		for(int i = 1; i <= n; i++) {
			if(i != 1) {
				y = y + ", "+a[i-1]+" varchar(30)";
			}
			else {
				y = a[i-1]+" varchar(30)";
			}
		}
		return y;
	}
	public static void main(String[] args) throws IOException {
		 Connection connection = null;
		 Statement statement = null;
		 connection = ketnoi(connection);
		try {
			XSSFWorkbook wb = new XSSFWorkbook("Table1.xlsx");
			int r = wb.getNumberOfSheets();
			for(int s = 0; s < r; s++) {
				//Chon Sheet
		        XSSFSheet sheet = wb.getSheetAt(s);
		        String tenBang = wb.getSheetName(s);
		        Iterator<Row> rowIterator = sheet.iterator();
		        //rowIterator.next(); // - header
		        String[] b = null;
		        int k = 1;
		        while(rowIterator.hasNext()) {
		        	Row rows = rowIterator.next();	        	
		                Iterator<Cell> cellIterator = rows.cellIterator();
		                int dem=0;
		                DataFormatter dataFormatter = new DataFormatter();
		                String[] a = null;
		                String temp="";    
		                while(cellIterator.hasNext()) {
		                	dem++;
		                	 Cell cell = cellIterator.next();
		                	 String cellValue = dataFormatter.formatCellValue(cell);
		                	 cellValue=cellValue.replaceAll(" ", "");
		                	 temp+=cellValue+" ";
		                	 if(dem==rows.getPhysicalNumberOfCells()) {
		                		 int n = rows.getPhysicalNumberOfCells();
		                		 System.out.println(temp);	                  		 	     
		                		 a=temp.split(" ");
		                		 String query = "";
		                		 try {
		                			 System.out.println("k ="+ k);
		                			 // k = 1 thi tao bang
		                			 if(k==1) {
		                				 query = "Create table "+tenBang+" ("+Cot(n, a)+")";	     		 
		                				 statement = connection.createStatement();
		                					statement.executeUpdate(query);   
		                					System.out.println("Create Table Thanh Cong");
		                				k = k + 1;
		                			 }
		                			 // k != 1 thi them du lieu
		                			 else {
		                				 query = "insert into "+tenBang+" values ("+soCot(n)+")";
			                				PreparedStatement stm = connection.prepareStatement(query);
			                				for(int i = 1; i <= n; i++) {
			                					stm.setString(i, a[i-1]);
			                					System.out.println(stm);
			   	                		 	}       
			                				stm.executeUpdate();			              				
			                				System.out.println("Insert Value Thanh Cong");
		                			 }
		    
		                		} catch (Exception e) {
		                				e.printStackTrace();
		                		}
		                		 a = new String[0];
		                		 break;	                		 
		                	 }
		                }	              
		        }	 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}