package com.diantu.web;

import java.util.List;

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
@RequestMapping(value = "/v1/database")
public class DataBaseController {

	@Autowired
	private DataBaseService dataBaseService;

	/**
	 * 查询数据库连接是否正常
	 * 
	 * @param type
	 * @param userName
	 * @param passWord
	 * @param connectionUrl
	 * @return TRUE if DB connection is OK
	 */
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ResponseBody
	public String checkDataBase(@RequestParam("type") String type, @RequestParam("userName") String userName, @RequestParam("passWord") String passWord,
			@RequestParam("connectionUrl") String connectionUrl) {
		if (DBUtils.checkConnection(type, userName, passWord, connectionUrl))
			return "SUCCESS";
		else
			return "FAILED";
	}

	/**
	 * 保存数据库连接信息到数据库
	 * 
	 * @param type
	 * @param name
	 * @param userName
	 * @param passWord
	 * @param connectionUrl
	 * @return TRUE if save success
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public String saveDataBase(@RequestParam("type") String type, @RequestParam("name") String name, @RequestParam("userName") String userName, @RequestParam("passWord") String passWord,
			@RequestParam("connectionUrl") String connectionUrl) {

		DataBaseModel model = new DataBaseModel();
		model.setType(type);
		model.setName(name);
		model.setUserName(userName);
		model.setPassWord(passWord);
		model.setConnectionUrl(connectionUrl);
		dataBaseService.saveDataBase(model);
		return "SUCCESS";
	}

	/**
	 * 查询出所有的数据库连接
	 * 
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public String listAllConnections() {
		List<DataBaseModel> dataBases = dataBaseService.listDataBases();
		return "SUCCESS";
	}
}
