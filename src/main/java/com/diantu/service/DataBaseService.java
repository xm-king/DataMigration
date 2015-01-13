package com.diantu.service;

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
}
