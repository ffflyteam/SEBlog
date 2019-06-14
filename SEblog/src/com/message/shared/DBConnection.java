package com.message.shared;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DBConnection {
		private static final String DRIVE_NAME="com.mysql.cj.jdbc.Driver";
		public static final DBConnection instance = new DBConnection();
		private static DataSource ds;
		static {
			try {
				Class.forName(DRIVE_NAME);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			ds = new ComboPooledDataSource(); 
		}
		public static DataSource getDatasSource() {
			return ds; 
		}  
		/*public static ComboPooledDataSource cpds;
		static {
			//1.初始化C3P0数据源
			  cpds = new ComboPooledDataSource();
			// 设置连接数据库需要的配置信息
			try {
				cpds.setDriverClass(DRIVE_NAME);
				cpds.setJdbcUrl(URL);
				cpds.setUser(NAME);
				cpds.setPassword(PASSWORD);
				//2.设置连接池的参数
				cpds.setInitialPoolSize(100);
				cpds.setMaxPoolSize(1500);
			} catch (Exception e) {
				throw new ExceptionInInitializerError(e);
			}
		}*/
		/*static {
			try {
				Class.forName(DRIVE_NAME);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}*/
		
		private DBConnection() {
		}
		
		private Connection getConnection() {
			Connection con = null;
			try {
				con = ds.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return con; 
		}
		
		public ResultSet executeCommand(String sql, Object[] params) {
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			Connection conn = null;
			try {
				conn = getConnection();
				pstmt = conn.prepareStatement(sql);
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
			Connection conn = null;
			try {
				conn = getConnection();
				pstmt = conn.prepareStatement(sql);
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
