package com.douzone.weboard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.douzone.weboard.service.ChatroomService;
import com.douzone.weboard.util.ApiResult;
import com.douzone.weboard.vo.Chatroom;

@RestController
@RequestMapping("/workspaces/{wno}/chatroom")
public class ChatroomController {

	@Autowired
	private ChatroomService chatroomService;

	// main
	@GetMapping("")
	public ResponseEntity<ApiResult> main(@PathVariable("wno") Long wno) {
		List<Chatroom> list = chatroomService.findList(wno);
		return new ResponseEntity<ApiResult>(ApiResult.success(list), HttpStatus.OK);
	}

	@PostMapping("")
	public ResponseEntity<ApiResult> insert(@RequestBody Chatroom chatroom) {
		chatroomService.insert(chatroom);
		return new ResponseEntity<ApiResult>(ApiResult.success(chatroom),HttpStatus.OK);	
	}
}