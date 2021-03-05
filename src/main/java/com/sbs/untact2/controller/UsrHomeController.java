package com.sbs.untact2.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sbs.untact2.util.Util;

@Controller
public class UsrHomeController {
	@RequestMapping("/usr/home/main")
	@ResponseBody
	public String showMain() {
		return "untact-2";
	}
	
	@RequestMapping("/usr/home/doFormTest")
	@ResponseBody
	public Map<String, Object> doFormTest(String name, int age) {
		return Util.mapOf("name", name, "age", age);
	}
}
