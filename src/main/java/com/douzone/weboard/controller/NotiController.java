package com.douzone.weboard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.douzone.weboard.service.NotiService;
import com.douzone.weboard.util.ApiResult;
import com.douzone.weboard.vo.Noti;
import com.douzone.weboard.vo.User;

@RestController
@RequestMapping("/noti/test")
public class NotiController {
	
	@Autowired
	private NotiService notiService;
	
	
	@PostMapping("")
	public ResponseEntity<ApiResult> insertNoti(){
		Noti noti = Noti.builder()
			.sender(1L)
			.contents("noti")
			.type("invite")
			.workspaceNo(1L).build();
		
//		notiService.addNoti(noti, List.of(User.builder().no(2L).build(),User.builder().no(1L).build()));
		
		return new ResponseEntity<ApiResult>(ApiResult.success(noti), HttpStatus.OK);
		
	}
	
	@DeleteMapping("")
	public ResponseEntity<ApiResult> deleteNoti(@RequestParam Long no){
		
		notiService.deleteNotiUser(no);
		return new ResponseEntity<ApiResult>(ApiResult.success(no), HttpStatus.OK);
	}
}
