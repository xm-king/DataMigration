package com.diantu.service;

import java.util.Calendar;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.diantu.model.MigrationJobModel;


@DisallowConcurrentExecution
public class DataMigrationJob implements Job{
	private static final Logger LOG = LoggerFactory.getLogger(DataMigrationJob.class);
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		LOG.info("Migration Job Start"+ Calendar.getInstance().getTime());
		MigrationJobModel jobModel = (MigrationJobModel) context.getMergedJobDataMap().get("");
		LOG.info("Migration Job End"+ Calendar.getInstance().getTime());
	}
}
