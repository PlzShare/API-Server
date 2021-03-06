package com.douzone.weboard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.douzone.weboard.annotation.AuthUser;
import com.douzone.weboard.service.ChatService;
import com.douzone.weboard.util.ApiResult;
import com.douzone.weboard.vo.Chatroom;
import com.douzone.weboard.vo.ChatroomUsers;
import com.douzone.weboard.vo.User;
import com.douzone.weboard.vo.WorkspaceUsers;

@RestController
@RequestMapping("/workspaces/{wno}/chat")
public class ChatController {

	@Autowired
	private ChatService chatService;

	// main
	@GetMapping("")
	public ResponseEntity<ApiResult> main(
			@PathVariable("wno") Long wno,
			@AuthUser User authUser) {
		
		WorkspaceUsers workspaceUsers = new WorkspaceUsers();
		workspaceUsers.setUserNo(authUser.getNo());
		workspaceUsers.setWorkspaceNo(wno);
		
		List<Chatroom> list = chatService.findList(workspaceUsers);
		return new ResponseEntity<ApiResult>(ApiResult.success(list), HttpStatus.OK);
	}
	
	@PostMapping("")
	public ResponseEntity<ApiResult> insert(@RequestBody Chatroom chatroom) {
		chatService.insert(chatroom);
		return new ResponseEntity<ApiResult>(ApiResult.success(chatroom),HttpStatus.OK);	
	}

	@DeleteMapping("")
	public ResponseEntity<ApiResult> leave(
			@AuthUser User authUser,
			@RequestParam Long ctno){
		ChatroomUsers chatroomUsers = new ChatroomUsers();
		chatroomUsers.setChatroomNo(ctno);
		chatroomUsers.setUserNo(authUser.getNo());
		chatService.leave(chatroomUsers);
		return new ResponseEntity<ApiResult>(HttpStatus.OK);
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////
	
	// ??? ????????? ????????????
	@GetMapping("/{ctno}")
	public ResponseEntity<ApiResult> chatMain(@PathVariable("ctno") Long ctno) {
		List<Chatroom> list = chatService.findChatList(ctno);
		return new ResponseEntity<ApiResult>(ApiResult.success(list), HttpStatus.OK);
	}
	
	// ??????
	@PostMapping("/{ctno}")
	public ResponseEntity<ApiResult> invite(
			@RequestBody ChatroomUsers chatroomUsers) {
		chatService.invite(chatroomUsers);
		return new ResponseEntity<ApiResult>(ApiResult.success(chatroomUsers),HttpStatus.OK);	
	}
	
	// ?????? ?????? ??????????????? ?????????
	@GetMapping("/{ctno}/findChatMembers")
	public ResponseEntity<ApiResult> findChatMembers(
			@PathVariable("ctno") Long ctno){
		System.out.println("===========================");
		System.out.println("????????? ??????" + ctno);
		List<ChatroomUsers> list = chatService.findChatMembers(ctno);
		return new ResponseEntity<ApiResult>(ApiResult.success(list), HttpStatus.OK);
	}
	
	// Noti ????????? ??????????????? ????????? ?????? ?????????
	@GetMapping("/noti/{cno}")
	public ResponseEntity<ApiResult> getMemberList(@PathVariable("cno") Long cno){
		List<Long> UserList = chatService.findChatUserList(cno);
		System.out.println(UserList + "?????????????????????????????????????????????????????????????????????");
		return new ResponseEntity<ApiResult>(ApiResult.success(UserList), HttpStatus.OK);
	}
	
}
