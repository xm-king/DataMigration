package com.diantu.util;

import java.util.ArrayList;
import java.util.List;

public class Constants {
	
	public static final String JOB_PREPARATION = "preparation";
	public static final String JOB_SELECT = "select";
	public static final String JOB_UPDATE = "update";
	public static final String JOB_INSERT  = "insert";
	
	public static final String SCHEDULE_JOB = "sheculeJob";
	public static final String DATABASE_MAPPER = "dataBaseMapper";
	
	//数据库连接类型
	public static final String DB_TYPE_MYSQL = "mysql";
	public static final String DB_TYPE_ORACLE = "oracle";
	public static final List<String> DB_TYPES = new ArrayList<String>();
	static {
		DB_TYPES.add(DB_TYPE_MYSQL);
		DB_TYPES.add(DB_TYPE_ORACLE);
	}
	
	//数据库驱动Class类型
	public static final String DB_DRIVER_MYSQL = "com.mysql.jdbc.Driver";
	public static final String DB_DRIVER_ORACLE = "oracle.jdbc.driver.OracleDriver";
	
}
