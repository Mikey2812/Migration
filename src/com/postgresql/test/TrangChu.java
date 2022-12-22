package com.postgresql.test;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class TrangChu extends JFrame {

	private JPanel contentPane;
	private JTextField txtTb;
	private java.io.File fileExcel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TrangChu frame = new TrangChu();
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
	public TrangChu() {
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
		
		final JButton btnChonFile = new JButton("Browser");
		btnChonFile.setFont(new Font("Times New Roman", Font.BOLD, 20));
		btnChonFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        JFileChooser fc = new JFileChooser();
		        fc.showOpenDialog(null);
		        //String tenFile = fc.getSelectedFile().getPath();
		        int ReturnVal = fc.showOpenDialog(btnChonFile);
		        if(ReturnVal == JFileChooser.APPROVE_OPTION) {
		        	fileExcel = fc.getSelectedFile();
		        	txtTb.setText(fileExcel.getPath());
		        }else {
		        	txtTb.setText("File không hợp lệ!!");
		        }
			}
		});
		btnChonFile.setBounds(502, 191, 134, 31);
		contentPane.add(btnChonFile);
		
		txtTb = new JTextField();
		txtTb.setBounds(55, 191, 422, 31);
		contentPane.add(txtTb);
		txtTb.setColumns(10);
		
		JButton btnExport = new JButton("Export");
		btnExport.setFont(new Font("Times New Roman", Font.BOLD, 20));
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ReadExcel.main(fileExcel.getPath());
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnExport.setBounds(255, 264, 127, 44);
		contentPane.add(btnExport);
	}
}
