package com.higradius;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Read {
	public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
//read
		FileInputStream read = new FileInputStream(new File("WebContent/data/final.csv"));
		// store
		BufferedReader store = new BufferedReader(new InputStreamReader(read));
		String i = "";
		String arr[];
		Connection var = DatabaseConnection.con();
		int c = 0;
		store.readLine();
		String query = "INSERT INTO Kshitij values(default,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
		PreparedStatement prep = var.prepareStatement(query);
		try {
			while ((i = store.readLine()) != null) {
				arr = i.split(",");
				//System.out.println(i);
				// c++;
				prep.setString(1, arr[0]);
				prep.setString(2, arr[1]);
				prep.setString(3, (arr[2]));
				prep.setString(4, arr[3]);
				prep.setDouble(5, Double.parseDouble(arr[4]));
				prep.setDouble(6, Double.parseDouble(arr[5]));
				prep.setDate(7, java.sql.Date.valueOf(arr[6]));
				prep.setDate(8, java.sql.Date.valueOf(arr[7]));
				prep.setDate(9, java.sql.Date.valueOf(arr[8]));
				prep.setDate(10, java.sql.Date.valueOf(arr[9]));
				prep.setString(11, arr[10]);
				prep.setString(12, arr[11]);
				prep.setDouble(13, Double.parseDouble(arr[12]));
				prep.setDouble(14, Double.parseDouble(arr[13]));
				prep.setDate(15, java.sql.Date.valueOf(arr[14]));
				prep.setString(16, arr[15]);
				prep.setDouble(17, Double.parseDouble(arr[16]));
				prep.setInt(18, Integer.parseInt(arr[17]));
				prep.setDate(19, java.sql.Date.valueOf(arr[18]));
				prep.setString(20, "");
				prep.addBatch();
				c++;
				//System.out.println(c);
				if (c % 100 == 0) {
					prep.executeBatch();
					System.out.print(c);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//	public static Date convert_date(String date) throws ParseException {
//		Date newdate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
//		return newdate;
//	}
}
