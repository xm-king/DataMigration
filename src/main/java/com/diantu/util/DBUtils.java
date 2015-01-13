package com.diantu.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DBUtils {
	public static Map<String,String> DBDrivers = new HashMap<String,String>();
	
	static{
		DBDrivers.put("oracle","oracle.jdbc.driver.OracleDriver");
		DBDrivers.put("mysql","com.mysql.jdbc.Driver");
	}
	
	public static boolean checkConnection(String type,String userName,String passWord,String connectionUrl){
		Connection connection = null;
		boolean isConnected = false;
		try {
			//Register JDBC Driver
			Class.forName(DBDrivers.get(type));
			connection = DriverManager.getConnection(connectionUrl, userName,passWord);
			if(connection != null)
				isConnected = true;
		} catch (Exception e) {
			e.printStackTrace();
			isConnected = false;
		}finally{
			
				try {
					if(connection != null){
					connection.close();
				} 
				}catch (SQLException e) {
					e.printStackTrace();
				}
				return isConnected;
			}
	}
}
