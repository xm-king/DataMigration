package com.diantu.dao;

import org.springframework.stereotype.Component;

import com.diantu.model.DataBaseModel;


@Component("dataBaseMapper")
public interface DataBaseMapper {
	public void saveDataBaseModel(DataBaseModel dbModel);
}
