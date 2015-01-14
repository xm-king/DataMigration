package com.diantu.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.diantu.model.MigrationJobModel;

@Component("migrationJobMapper")
public interface MigrationJobMapper {
	
	//��ѯ�����еĶ�ʱ����
	public List<MigrationJobModel> listJobs();
	//���涨ʱ�������ݿ�
	public void saveJob(MigrationJobModel jobModel);
	//���¶�ʱ�������ݿ�
	public void updateJob(MigrationJobModel jobModel);
	
}
