package com.douzone.weboard.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.douzone.weboard.vo.User;

@RestController
public class TestController {
	@GetMapping("/test")
	public String test() {
		User user = new User();
	
		return "test";
	}
}
