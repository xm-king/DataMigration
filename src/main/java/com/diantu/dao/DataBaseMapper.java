package com.diantu.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.diantu.model.DataBaseModel;


@Component("dataBaseMapper")
public interface DataBaseMapper {
	
	//��ѯ�����б�������ݿ�����
	public List<DataBaseModel> listDataBases();
	//�������ݿ�����
	public void saveDataBaseModel(DataBaseModel dbModel);
	//����ID��ѯ���ݿ�������Ϣ
	public DataBaseModel getDataBaseModel(int id);
	//����IDɾ�����ݿ�������Ϣ
	public void deleteDataBaseModel(int id);
	//�������ݿ�����
	public void updateDataBaseModel(DataBaseModel dbModel);
	
}
