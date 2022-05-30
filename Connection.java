package com.higradius;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Connection {
	
	public static Connection con() throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		try {
			  Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/project","root","root");
			  return connection;
		}catch(SQLException s) {
		}
		return null;
	}
}