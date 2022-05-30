package com.higradius;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	
	public static Connection con() throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		try {
			  Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/highradius","root","root");
			  return connection;
		}catch(SQLException s) {
		}
		return null;
	}
}