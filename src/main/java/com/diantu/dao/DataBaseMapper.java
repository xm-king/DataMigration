package com.diantu.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.diantu.model.DataBaseModel;


@Component("dataBaseMapper")
public interface DataBaseMapper {
	
	//查询出所有保存的数据库连接
	public List<DataBaseModel> listDataBases();
	//保存数据库连接
	public void saveDataBaseModel(DataBaseModel dbModel);
	//根据ID查询数据库连接信息
	public DataBaseModel getDataBaseModel(int id);
	//根据ID删除数据库连接信息
	public void deleteDataBaseModel(int id);
	//更新数据库连接
	public void updateDataBaseModel(DataBaseModel dbModel);
	
}
