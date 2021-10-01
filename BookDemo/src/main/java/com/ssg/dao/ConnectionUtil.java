package com.ssg.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
	
	
	
	public static   Connection getconnection() throws SQLException, ClassNotFoundException {
		
		Class.forName("org.postgresql.Driver");

		Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "root");

		if (con != null) {

			System.out.println("Connection OK");
		} else {
			System.out.println("Connection Failed");
		}
		return con;
	}

}
