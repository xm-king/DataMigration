package com.diantu.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.diantu.model.DataBaseModel;
import com.diantu.service.DataBaseService;

import static com.diantu.util.Constants.*;

@Controller
@RequestMapping(value="/")
public class IndexController {
	
	@Autowired
	private DataBaseService dataBaseService;
	
	/**
	 * 后台管理首页
	 * @return
	 */
	@RequestMapping(value="index.html",method=RequestMethod.GET)
	public String index(){
		return "index";
	}
	
	/**
	 * 数据库连接
	 * @param model
	 * @return
	 */
	@RequestMapping(value="databases.html",method=RequestMethod.GET)
	public String listDataBases(ModelMap model){
		List<DataBaseModel> dbList = dataBaseService.listDataBases();
		model.addAttribute("list", dbList);
		return "databases";
	}
	
	/**
	 * 创建新的数据库连接
	 * @return
	 */
	@RequestMapping(value="insert.html",method=RequestMethod.GET)
	public String createDB(ModelMap model){
		//数据库类型
		model.addAttribute("dbTypes", DB_TYPES);
		return "insert";
	}

	/**
	 * 更新数据库连接
	 * @param id
	 * @return
	 */
	@RequestMapping(value="update.html",method=RequestMethod.GET)
	public String updateDB(@RequestParam("id") int id,ModelMap model){
		//数据库对象
		DataBaseModel dbModel = dataBaseService.getDataBase(id);
		model.addAttribute("model",dbModel);
		//数据库类型
		model.addAttribute("dbTypes", DB_TYPES);
		return "update";
	}
}
