package com.diantu.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CommonController {
	
	@RequestMapping(value="/v1/login",method=RequestMethod.POST)
	public String login(@RequestParam("name")String name,@RequestParam("password")String password){
		//if login success redirect index.html
		if("admin".equals(name) && "admin".equals(password)){
			return "redirect:/index.html";
		}else{
			return "redirect:/login.html"; 
		}
	}
}
