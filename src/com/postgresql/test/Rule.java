package com.postgresql.test;
import java.beans.Statement;
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
public class Rule {

	public static void main(String[] args) throws IOException {
		int all = 0;
		try {
			
			XSSFWorkbook wb = new XSSFWorkbook("Table3.xlsx");
			
			int r = wb.getNumberOfSheets();
			for(int s = 0; s < r; s++) {
	        XSSFSheet sheet = wb.getSheetAt(s);

	        Iterator<Row> rowIterator = sheet.iterator();
	        
	      
	        int bad=0;
	        int k=0;
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
	                	 if(k==0) {  
	                	 }
	                	 else {
	                	 String result = cellValue.replaceAll("[-+.^:,#$!@%^&*()_=]","");
	                	 if(result.equals(cellValue)) { 
	                	 }else {
	                		 bad++;
	                	 }
	                	 result=result.replaceAll(" ", "");
//	                	 //check sdt đủ 10 so ko
	                	 if(dem==4 && s==0 ) {
	                		 if(result.length()!=10 &&result.length()>0) {
	                			 result="9999999999";
		                		 bad++;	 
	                		 } 
	                	 }
	                	 if(dem==4 && s==1 ) {
	                		 if(result.length()!=10 &&result.length()>0) {result="9999999999";
	                		 bad++;} 
	                    
	                	 }
	                	 if(result=="") {
	                		 //sdt
	                		 if(dem==4 && s==0) {result="9999999999"; bad++;}  if(dem==4 &&  s==1) {result="9999999999"; bad++;} 
	                		 //if(dem==4 &&) {result="000"; bad++;} 
	                		 //Ngaysinh
	                		 if(dem==3 && s==0) {result="01/01/1970"; bad++;}if(dem==4 && s==2) {result="01/01/1970"; bad++;} if(dem==5 && s==2) {result="01/01/1970"; bad++;}
	                		 //ten
	                		 if(dem==2 && s==0) {result="AAA "; bad++;} if(dem==2 && s==1) {result="AAA "; bad++;} if(dem==2  && s==3) {result="AAA "; bad++;}
	                		 //diachi
	                		 if(dem==5 && s==0) {result="Viet Nam "; bad++;} if(dem==3 && s==1) {result="VietNam "; bad++;}
	                		 //Tien
	                		 if(dem==6 && s==2) {result="999 "; bad++;} if(dem==4 && s==3  ) {result="999 "; bad++;}if(dem==4 &&  s==4 ) {result="999 "; bad++;} if(dem==3 && s==4) {result="999 "; bad++;}
	                		 //Ma
	                		 if(dem==1 && s==0) {result="BAD01 "; bad++;} if(dem==1 && s==1) {result="BAD01 "; bad++;}if(dem==1 && s==2) {result="BAD01 "; bad++;} if(dem==1 && s==3) {result="BAD01 "; bad++;}if(dem==1 && s==4) {result="BAD01 "; bad++;}
	                		 if(dem==2 && s==2  ) {result="BAD01 "; bad++;}if(dem==2 &&  s==4 ) {result="BAD01 "; bad++;}if(dem==3 &&  s==2 ) {result="BAD01 "; bad++;}
	            }
	                	 temp+=result+" ";
	                	 a=temp.split(" ");
	                	if(dem==rows.getPhysicalNumberOfCells()) {
	                		System.out.println(temp);
	                		  a=temp.split(" ");
	                		 a = new String[0];
	                		 break;
	                	}
	            	 
	                }
	               
	                }
	                k++;
	               
	        }
	        System.out.println("Số lỗi: "+bad);
	        all = all + bad;
	        
			}			
		} catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println("Tổng số lỗi: "+all);
	}}


