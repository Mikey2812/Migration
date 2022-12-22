package com.postgresql.test;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.apache.commons.io.filefilter.AndFileFilter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Export extends JFrame {

	private JPanel contentPane;
	private JTextField txtTb;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Export frame = new Export();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	
	
	public Export() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 725, 462);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitle = new JLabel("Convert Excel to PostgreSQL");
		lblTitle.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		lblTitle.setBounds(156, 34, 387, 52);
		contentPane.add(lblTitle);
		
		txtTb = new JTextField();
		txtTb.setBounds(55, 191, 562, 31);
		contentPane.add(txtTb);
		txtTb.setColumns(10);
		
		JButton btnExport = new JButton("Export");
		btnExport.setFont(new Font("Times New Roman", Font.BOLD, 20));
		btnExport.addActionListener(new ActionListener() {
			public Connection ketnoi(Connection connection) {
				ConnectDB obj_ConnectDB = new ConnectDB();
				connection = obj_ConnectDB.getConnection();
				return connection;
			}
			
			//dem so cot insert value
			public String soCot(int x) {
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
			
			//ep kieu du lieu insert value
			public String Cot(int n, String[] a) {
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
			
			//lay kieu du lieu
			private String getCellType(Cell cell) {
				CellType cellType = cell.getCellType();
				String x = cellType.toString();
				return x;
			}
			
			//lay data
			private Object getCellValue(Cell cell) {
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
					break;
				case BLANK:
					break;
				case ERROR:
					break;
				default:
					break;
				}
				return cellValue;
			}
			
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
		        fc.showOpenDialog(null);
		        java.io.File file = fc.getSelectedFile();
				Connection connection = null;
				Statement statement = null;
				connection = ketnoi(connection);
		        int soTable = 0;
				int soData = 0;
				int all = 0;
				try {
					
					XSSFWorkbook wb = new XSSFWorkbook(file.getPath());	
					txtTb.setText(file.getPath());
					//XSSFWorkbook wb = new XSSFWorkbook("Table1.xlsx");
					int r = wb.getNumberOfSheets();
					
					for(int s = 0; s < r; s++) {
						//Chon Sheet
				        XSSFSheet sheet = wb.getSheetAt(s);
				        String tenBang = wb.getSheetName(s);
				        Iterator<Row> rowIterator = sheet.iterator();
				        //rowIterator.next(); // - header
				        String[] b = null;
				        int k = 1;  
				        soData = 0;
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
								/*if (cellValue == "") {
									if(dem==1 && s==0) {
										cellValue = "BAD01";
									}
								}*/
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
			    
			                		} catch (Exception e1) {
			                				e1.printStackTrace();
			                		}
									a = new String[0];
									break;
								}
							}
						}
						all = all + soData;
						JFrame frame = new JFrame("JOptionPane showMessageDialog example");
        		        JOptionPane.showMessageDialog(frame,
        		        		"Bảng: "+tenBang+"\n"+
        		        		"Số dòng dữ liệu đã insert: "+soData,
        		        		"Report",
        		                JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
//				System.out.println("Số bảng đã insert: "+soTable);
//				System.out.println("Số dòng dữ liệu đã insert: "+soData);

				JFrame frame = new JFrame("JOptionPane showMessageDialog example");
		        JOptionPane.showMessageDialog(frame,
		        		"Số bảng đã insert: "+soTable+"\n"+
		        		"Số dòng dữ liệu đã insert: "+all,
		        		"Report",
		                JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnExport.setBounds(282, 262, 127, 44);
		contentPane.add(btnExport);
	}
}
