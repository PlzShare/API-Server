package com.douzone.weboard.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.douzone.weboard.util.ApiResult;
import com.douzone.weboard.vo.User;


@RestController
public class TestController {
	
	// 조회
	@GetMapping("/test")
	public ResponseEntity<ApiResult> test() {
		User user = User.builder()
						.nickname("test")
						.id("json response")
						.password("aaaa")
						.build();

		return new ResponseEntity<ApiResult>(ApiResult.success(user),HttpStatus.OK);
	}
  
	// 삽입
	// form data 받는 법
	@PostMapping("/test")
	public ResponseEntity<ApiResult> postTest(User user){
		System.out.println(user);
		return new ResponseEntity<ApiResult>(HttpStatus.OK);
	}
  
	// 수정
	// json data  
	@PutMapping("/test")
	public ResponseEntity<ApiResult> updateUser(@RequestBody User user){
		return new ResponseEntity<ApiResult>(ApiResult.success(user),HttpStatus.OK);
	}
	
	// 삭제
	@DeleteMapping("/test/{no}")
	public ResponseEntity<ApiResult> deleteUser(@PathVariable("no") Long no){
		return new ResponseEntity<ApiResult>(ApiResult.success(no),HttpStatus.OK);
	}
	
}




