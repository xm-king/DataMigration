package com.diantu.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static com.diantu.util.Constants.*;

public class DBUtils {
	public static Map<String,String> DBDrivers = new HashMap<String,String>();
	
	static{
		DBDrivers.put(DB_TYPE_MYSQL,DB_DRIVER_MYSQL);
		DBDrivers.put(DB_TYPE_ORACLE,DB_DRIVER_ORACLE);
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
