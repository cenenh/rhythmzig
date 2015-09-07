package com.sgen.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCUtil {
	//
	public static Connection getConnection() 
	{
		String dburl ="jdbc:mysql://localhost:3306/rhythmzig";
		String userid = "root";
		String pw = "chldbwls";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection(dburl, userid, pw);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void close(Connection conn, PreparedStatement pstmt,ResultSet rs)
	{
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		close(conn, pstmt);
	}

	public static void close(Connection conn, PreparedStatement pstmt)
	{
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}