package com.diantu.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.diantu.model.MigrationJobModel;

@Component("migrationJobMapper")
public interface MigrationJobMapper {
	
	//查询出所有的定时任务
	public List<MigrationJobModel> listJobs();
	//保存定时任务到数据库
	public void saveJob(MigrationJobModel jobModel);
	//根据ID查询出定时任务对象
	public MigrationJobModel getJob(int id);
	//更新定时任务到数据库
	public void updateJob(MigrationJobModel jobModel);
	
}
