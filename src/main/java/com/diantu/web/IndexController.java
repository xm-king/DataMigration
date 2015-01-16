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
	 * ��̨������ҳ
	 * @return
	 */
	@RequestMapping(value="index.html",method=RequestMethod.GET)
	public String index(){
		return "index";
	}
	
	/**
	 * ���ݿ�����
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
	 * �����µ����ݿ�����
	 * @return
	 */
	@RequestMapping(value="insert.html",method=RequestMethod.GET)
	public String createDB(ModelMap model){
		//���ݿ�����
		model.addAttribute("dbTypes", DB_TYPES);
		return "insert";
	}

	/**
	 * �������ݿ�����
	 * @param id
	 * @return
	 */
	@RequestMapping(value="update.html",method=RequestMethod.GET)
	public String updateDB(@RequestParam("id") int id,ModelMap model){
		//���ݿ����
		DataBaseModel dbModel = dataBaseService.getDataBase(id);
		model.addAttribute("model",dbModel);
		//���ݿ�����
		model.addAttribute("dbTypes", DB_TYPES);
		return "update";
	}
}
