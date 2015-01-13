package com.diantu.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.diantu.model.DataBaseModel;
import com.diantu.service.DataBaseService;
import com.diantu.util.DBUtils;

@Controller
public class DataBaseController {

	@Autowired
	private DataBaseService dataBaseService;
	
	@RequestMapping(value = "/v1/database/check", method = RequestMethod.POST)
	@ResponseBody
	public String checkDataBase(@RequestParam("type") String type,
			@RequestParam("userName") String userName, 
			@RequestParam("passWord")String passWord,
			@RequestParam("connectionUrl")String connectionUrl){
		if(DBUtils.checkConnection(type, userName, passWord, connectionUrl))
			return "SUCCESS";
		else
			return "FAILED";
	}
	
	@RequestMapping(value = "/v1/database/invoke", method = RequestMethod.POST)
	@ResponseBody
	public String saveDataBase(@RequestParam("type") String type,
			@RequestParam("name") String name,
			@RequestParam("userName") String userName, 
			@RequestParam("passWord")String passWord,
			@RequestParam("connectionUrl")String connectionUrl) {
		
		DataBaseModel model = new DataBaseModel();
		model.setType(type);
		model.setName(name);
		model.setUserName(userName);
		model.setPassWord(passWord);
		model.setConnectionUrl(connectionUrl);
		dataBaseService.saveDataBase(model);
		return "SUCCESS";
	}
}
