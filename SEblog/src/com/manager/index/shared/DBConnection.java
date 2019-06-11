package com.manager.index.shared;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnection {
		private static final String URL="jdbc:mysql://localhost:3306/user?userSSL=false&serverTimezone=UTC";
		private static final String DRIVE_NAME="com.mysql.cj.jdbc.Driver";
		private static final String NAME = "root";
		private static final String PASSWORD = "123456";
		public static final DBConnection instance = new DBConnection();
		static {
			try {
				Class.forName(DRIVE_NAME);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		private DBConnection() {
		}
		
		private Connection getConnection() {
			try {
				Connection conn = DriverManager.getConnection(URL, NAME, PASSWORD);
				return conn;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		
		public ResultSet executeCommand(String sql, Object[] params) {
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				pstmt = getConnection().prepareStatement(sql);
				for(int i = 0; i < params.length; i++) {
					pstmt.setObject(i + 1, params[i]);
				}
				rs = pstmt.executeQuery();
				return rs;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		
		public int executeQuery(String sql, Object[] params) {
			PreparedStatement pstmt = null;
			try {
				pstmt = getConnection().prepareStatement(sql);
				for(int i = 0; i < params.length; i++) {
					pstmt.setObject(i + 1, params[i]);				
				}
				return pstmt.executeUpdate();
			} catch (Throwable t) {
				t.printStackTrace();
				return ResultConst.EXECUTE_SQL_ERROR.getId();
			}
		}
}
