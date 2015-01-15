package com.diantu.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CommonController {
	
	@RequestMapping(value="/login")
	public void login(@RequestParam("name")String name,@RequestParam("password")String password){
		
	}
}
