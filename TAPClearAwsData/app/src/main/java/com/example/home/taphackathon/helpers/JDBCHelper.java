package com.example.home.taphackathon.helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCHelper {
	public Connection getConnection() {
		// JDBC driver name and database URL
		final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
		final String DB_URL = "jdbc:mysql://hackathondb.cqcpwlsqyqz8.us-east-1.rds.amazonaws.com/hackathondb";

		final String USER = "muneerahmed";
		final String PASS = "1a2b3c4d5e";

		Connection conn = null;

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return conn;
	}

	public boolean insertUser(Connection conn, String dbName, String tableName, String username, int noOfPhotosEnrolled) {
		try {
			String sql = "INSERT INTO " + dbName + "." + tableName + "(username, enrolledphotoscount) VALUES (?, ?);";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, username);
			pst.setInt(2, noOfPhotosEnrolled);
			int x = pst.executeUpdate();
			return x == 1 ? true: false; 
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public int getEnrolledPhotosCount(Connection conn, String databaseName, String tableName, String username) {
		int enrolledPhotosCount = 0;
		try {
			String sql = "SELECT * FROM " + databaseName + "." + tableName + " WHERE username = ?;";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, username);
			ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				enrolledPhotosCount = rs.getInt(2);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return enrolledPhotosCount;
	}

	public boolean incrementEnrolledPhotosCount(Connection conn, String databaseName, String tableName,
			String username) {
		// TODO Auto-generated method stub
		int noOfRowsUpdated = 0;
		String sql = "UPDATE " + databaseName + "." + tableName + " SET enrolledphotoscount = enrolledphotoscount + 1 WHERE username = ?;";
		PreparedStatement pst;
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, username);
			noOfRowsUpdated = pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return noOfRowsUpdated == 1 ? true : false; 
	}
}
