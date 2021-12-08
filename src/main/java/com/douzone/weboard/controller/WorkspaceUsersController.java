package com.douzone.weboard.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.douzone.weboard.service.WorkspaceUsersService;
import com.douzone.weboard.util.ApiResult;

@RestController // responsebody 다 붙어진 효과
@RequestMapping("/workspace-users")
public class WorkspaceUsersController {

	@Autowired
	private WorkspaceUsersService workspaceUsersService;
	
	@PostMapping("/invite")
	public ResponseEntity<ApiResult> invite(){
		HashMap<String, Long> map = new HashMap<>();

		Long testUserNo = 2L;
		Long testWorkspaceNo = 31L;
		
		map.put("userNo", testUserNo);
		map.put("workspaceNo", testWorkspaceNo);
		map.put("userRole", 1L); // 워크스페이스 생성자면 0L, 초대받은(일반) 유저는 1L
		
		workspaceUsersService.invite(map);
		return new ResponseEntity<ApiResult>(HttpStatus.OK);
	}
	
	@PutMapping("/leave")
	public ResponseEntity<ApiResult> leave(){
		
		Long testUserNo = 2L;
		Long testWorkspaceNo = 31L;
		Long testRole = 1L; // 일반유저로 테스트
		
		HashMap<String, Long> map = new HashMap<>();
		map.put("userNo", testUserNo);
		map.put("workspaceNo", testWorkspaceNo);
		map.put("user", testRole); // 워크스페이스 생성자면 0, 초대받은 유저는 1
		
		// 미완성상태. admin 인증 확실해지고 나서 다시 할 것
		workspaceUsersService.leave(map);
		
		return new ResponseEntity<ApiResult>(HttpStatus.OK);
	}
	
	@PutMapping("/change-role")
	public ResponseEntity<ApiResult> changeRole(){
		
		Long testAdminNo = 2L;
		Long testUserNo = 1L;
		Long testWorkspaceNo = 3L;
		
		HashMap<String, Long> map = new HashMap<>();
		map.put("adminNo", testAdminNo);
		map.put("userNo", testUserNo);
		map.put("workspaceNo", testWorkspaceNo);
		
		workspaceUsersService.changeRole(map);
		
		return new ResponseEntity<ApiResult>(HttpStatus.OK);
	}
}