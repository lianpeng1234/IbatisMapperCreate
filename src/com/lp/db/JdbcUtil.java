package com.lp.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcUtil {
	
	private static Object obj = new Object();
	
	private JdbcUtil() {}
	
	private static Connection defaultConn;
	
	private static Connection openConn;
	
	public static Connection getDefaultConnection() {
		try {
			String jdbcUrl = "jdbc:mysql://" + DbFile.singleton.getJdbc().getUrl() + "/" + DbFile.singleton.getJdbc().getDbname();
			if(defaultConn == null) {
				synchronized (obj) {
					if(defaultConn == null) {
						Class.forName("com.mysql.jdbc.Driver");
						defaultConn = DriverManager.getConnection(jdbcUrl, DbFile.singleton.getJdbc().getUsername(), DbFile.singleton.getJdbc().getPassword());//��ȡ����
					}
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return defaultConn;
	}
	
	public static Connection getOpenConnection() {
		//
		try {
			String jdbcUrl = "jdbc:mysql://" + DbFile.singleton.getJdbc().getUrl();
			if(openConn == null) {
				synchronized (obj) {
					if(openConn == null) {
						Class.forName("com.mysql.jdbc.Driver");
						openConn = DriverManager.getConnection(jdbcUrl, DbFile.singleton.getJdbc().getUsername(), DbFile.singleton.getJdbc().getPassword());//��ȡ����
					}
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return openConn;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
