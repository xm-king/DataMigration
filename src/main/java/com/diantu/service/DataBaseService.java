package com.diantu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diantu.dao.DataBaseMapper;
import com.diantu.model.DataBaseModel;

@Service("dataBaseService")
public class DataBaseService {
	
	@Autowired
	private DataBaseMapper dataBaseMapper;
	
	public void saveDataBase(DataBaseModel model){
		dataBaseMapper.saveDataBaseModel(model);
	}
	
	public DataBaseModel getDataBase(int id){
		return dataBaseMapper.getDataBaseModel(id);
	}
	
	public void updateDataBase(DataBaseModel dbModel){
		dataBaseMapper.updateDataBaseModel(dbModel);
	}
	
	public void deleteDataBase(int id){
		dataBaseMapper.deleteDataBaseModel(id);
	}
	
	public List<DataBaseModel> listDataBases(){
		return dataBaseMapper.listDataBases();
	}
}
