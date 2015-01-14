package com.diantu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diantu.dao.MigrationJobMapper;
import com.diantu.model.MigrationJobModel;

@Service("migrationJobService")
public class MigrationJobService {

	@Autowired
	private MigrationJobMapper migrationJobMapper;
	
	public void saveJob(MigrationJobModel jobModel){
		migrationJobMapper.saveJob(jobModel);
	}
	public void updateJob(MigrationJobModel jobModel){
		migrationJobMapper.updateJob(jobModel);
	}
	public List<MigrationJobModel> listJobs(){
		return migrationJobMapper.listJobs();
	}
}
