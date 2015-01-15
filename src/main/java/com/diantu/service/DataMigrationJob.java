package com.diantu.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import net.sf.json.JSONObject;

import org.apache.commons.dbcp.BasicDataSource;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.diantu.model.DataBaseModel;
import com.diantu.model.MigrationJobModel;
import static com.diantu.util.Constants.*;


@DisallowConcurrentExecution
public class DataMigrationJob implements Job{
	private static final Logger LOG = LoggerFactory.getLogger(DataMigrationJob.class);
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		LOG.info("Migration Job Start"+ Calendar.getInstance().getTime());
		MigrationJobModel jobModel = (MigrationJobModel) context.getMergedJobDataMap().get("sheculeJob");
		String description = jobModel.getDescription();
		JSONObject job = JSONObject.fromObject(description);
		
		//Create Source DataSource
		DataBaseModel sourceDB = null;
		BasicDataSource sourceDS = new BasicDataSource();
		sourceDS.setUsername(sourceDB.getUserName());
		sourceDS.setPassword(sourceDB.getPassWord());
		sourceDS.setUrl(sourceDB.getConnectionUrl());
		sourceDS.setDriverClassName("");
		JdbcTemplate sourceTemplate = new JdbcTemplate(sourceDS);
		
		//Create Dest DataSource
		BasicDataSource destDS = new BasicDataSource();
		JdbcTemplate destTemplate = new JdbcTemplate(destDS);
		
		//Create Table if not exists
		String preparationSql = job.getString(JOB_PREPARATION);
		destTemplate.execute(preparationSql);
		
		//Select from source DataBase
		String selectSourceDBSql = job.getString(JOB_SELECT);
		List<Map<String, Object>> results = sourceTemplate.queryForList(selectSourceDBSql);
		
		
		//Insert into Dest DataBase
		String insertDestDBSql = job.getString(JOB_INSERT);
		for(Map<String,Object> args:results ){
			List<String> params = new ArrayList<String>();
			for(Entry<String, Object> entry:args.entrySet()){
				params.add(entry.getValue().toString());
			}
			destTemplate.update(insertDestDBSql, params.toArray());
		}
		
		//Destory DataSource
		try {
			sourceDS.close();
			destDS.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		LOG.info("Migration Job End"+ Calendar.getInstance().getTime());
	}
}
