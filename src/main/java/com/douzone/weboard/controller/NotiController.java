package com.douzone.weboard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.douzone.weboard.annotation.AuthUser;
import com.douzone.weboard.service.NotiService;
import com.douzone.weboard.util.ApiResult;
import com.douzone.weboard.vo.NotiUser;
import com.douzone.weboard.vo.User;

@RestController
@RequestMapping("/noti")
public class NotiController {
	
	@Autowired
	private NotiService notiService;
	
	
	@GetMapping("")
	public ResponseEntity<ApiResult> getNoti(
			@AuthUser User authUser){
		List<NotiUser> result = notiService.getNoti(authUser.getNo());
		return new ResponseEntity<ApiResult>(ApiResult.success(result), HttpStatus.OK);
	}
	
	
	
	@GetMapping("/type")
	public ResponseEntity<ApiResult> getTypeNoti(
			@RequestParam("uno") Long uno,
			@RequestParam("type") String type
			){
		List<NotiUser> result = notiService.getTypeNoti(uno, type);
		return new ResponseEntity<ApiResult>(ApiResult.success(result), HttpStatus.OK);
	}
	
	
	@DeleteMapping("")
	public ResponseEntity<ApiResult> deleteNoti(@RequestParam Long no){
		
		notiService.deleteNotiUser(no);
		return new ResponseEntity<ApiResult>(ApiResult.success(no), HttpStatus.OK);
	}
}
