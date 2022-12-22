package com.postgresql.test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Iterator;

import javax.management.Query;

import org.apache.poi.hslf.dev.PPDrawingTextListing;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;

public class ReadExcel {
	
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
	
	/*public static int demData() {
		ResultSet rs = null;
		String x = null;
		int cot = 0;
		try {
			Connection connection = null;
			Statement statement = null;
			x = "select Count(*) as NumberOfRows from MonAn";
			statement = connection.createStatement();
			rs = statement.executeQuery(x);
			System.out.println(
                    ""+ rs.getInt("NumberOfRows") + " rows");
			cot = rs.getInt("NumberOfRows");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cot;
	}
	/*
	public static String demBang(int x) {
		return "Đã tạo được " + x + " bảng";
	}
	
	public static String demDuLieu(int x) {
		return "Đã tạo được " + x + " dữ liệu";
	}*/
	
	private static String getCellType(Cell cell) {
		CellType cellType = cell.getCellType();
		String x = cellType.toString();
		return x;
	}
	/*public static String kieuDuLieu(String x) {
		if (x == "STRING") {
			x = " varchar(30)";
			System.out.println("Kieu string");
		}
		if (x == "NUMERIC") {
			x = " date";
			System.out.println("Kieu date");
		}
		return x;
	}*/
	
	
	public static void main(String string) throws IOException {
		Connection connection = null;
		Statement statement = null;
		connection = ketnoi(connection);
        int soTable = 0;
		int soData = 0;
		try {
			XSSFWorkbook wb = new XSSFWorkbook(string);
			int r = wb.getNumberOfSheets();
			for(int s = 0; s < r; s++) {
				//Chon Sheet
		        XSSFSheet sheet = wb.getSheetAt(s);
		        String tenBang = wb.getSheetName(s);
		        Iterator<Row> rowIterator = sheet.iterator();
		        //rowIterator.next(); // - header
		        String[] b = null;
		        int k = 1;  
				while (rowIterator.hasNext()) {
					Row rows = rowIterator.next();
					Iterator<Cell> cellIterator = rows.cellIterator();
					int dem = 0;
					DataFormatter dataFormatter = new DataFormatter();
					String[] a = null;
					String temp = "";
					while (cellIterator.hasNext()) {
						dem++;
						Cell cell = cellIterator.next();
						String cellValue;
						String x;
						cellValue = getCellValue(cell).toString().replaceAll(" ", "");
						temp += cellValue + " ";
						if (dem == rows.getPhysicalNumberOfCells()) {
							 int n = rows.getPhysicalNumberOfCells();
	                		 System.out.println(temp);	                  		 	     
	                		 a=temp.split(" ");
	                		 String query = "";
	                		 try {
	                			 System.out.println("k = "+ k);
	                			 // k = 1 thi tao bang
	                			 if(k==1) {
	                				 query = "Create table "+tenBang+" ("+Cot(n, a)+")";
	                				 //query = "Create table "+tenBang+" ("+Cot(n, a, getCellType(cell))+")";
	                				 statement = connection.createStatement();
	                					statement.executeUpdate(query);   
	                					System.out.println("Create Table Thanh Cong");
	                				k = k + 1;
	                				soTable++;
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
		                				soData++;
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
		System.out.println("Số bảng đã insert: "+soTable);
		System.out.println("Số dòng dữ liệu đã insert: "+soData);
		//demBang(soTable);
		//demDuLieu(soData);
	}

	private static Object getCellValue(Cell cell) {
		CellType cellType = cell.getCellType();
		Object cellValue = null;
		switch (cellType) {
		case BOOLEAN:
			cellValue = cell.getBooleanCellValue();
			break;
		case FORMULA:
			Workbook workbook = cell.getSheet().getWorkbook();
			FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
			cellValue = evaluator.evaluate(cell).getNumberValue();
			break;
		case NUMERIC: 
			if (DateUtil.isCellDateFormatted(cell)) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				cellValue = dateFormat.format(cell.getDateCellValue());
			} else {
				cellValue = cell.getNumericCellValue();
			}
			break;
		case STRING:
			cellValue = cell.getStringCellValue();
			break;
		case _NONE:
		case BLANK:
		case ERROR:
			break;
		default:
			break;
		}
		return cellValue;
	}

}