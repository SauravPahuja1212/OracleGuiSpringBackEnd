package com.oraclegui.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
	
	@RequestMapping({ "/", "/hello" })
	public String healthCheck() {
		return "Ok, Working !";
	}
}
