package com.douzone.weboard.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.douzone.weboard.service.WorkspaceUsersService;
import com.douzone.weboard.util.ApiResult;
import com.douzone.weboard.vo.WorkspaceUsers;

@RestController // responsebody 다 붙어진 효과
@RequestMapping("/workspace-users")
public class WorkspaceUsersController {

	@Autowired
	private WorkspaceUsersService workspaceUsersService;
	
	@GetMapping("/{wno}/{uno}")
	public ResponseEntity<ApiResult> getlist(@PathVariable("wno") Long wno, @PathVariable("uno") Long uno){
		
		WorkspaceUsers workspaceUsers = new WorkspaceUsers();
		workspaceUsers.setUserNo(uno);
		List<WorkspaceUsers> result = workspaceUsersService.getUser(wno);

		
		System.out.println(result);
		
		return new ResponseEntity<ApiResult>(ApiResult.success(result), HttpStatus.OK);
	}

	
	@PostMapping("")
	public ResponseEntity<ApiResult> invite(
			@RequestBody WorkspaceUsers workspaceUsers){
		HashMap<String, Long> map = new HashMap<>();

		System.out.println(workspaceUsers);
		Long userNo = workspaceUsers.getUserNo();
		Long workspaceNo = workspaceUsers.getWorkspaceNo();
		
		map.put("userNo", userNo);
		map.put("workspaceNo", workspaceNo);
		map.put("userRole", 1L); // 워크스페이스 생성자면 0L, 초대받은(일반) 유저는 1L
		
		workspaceUsersService.invite(map);
		return new ResponseEntity<ApiResult>(HttpStatus.OK);
	}
	
	@PutMapping("")
	public ResponseEntity<ApiResult> leave(
			@RequestBody WorkspaceUsers workspaceUsers){
		
		Long testUserNo = workspaceUsers.getUserNo();
		Long testWorkspaceNo = workspaceUsers.getWorkspaceNo();
		Long testRole = 1L; // 일반유저만 이 방을 나갈 수 있게. 승현아 미안해 ㅠ
		
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
		
		Long testAdminNo = 3L;
		Long testUserNo = 4L;
		Long testWorkspaceNo = 78L;
		
		HashMap<String, Long> map = new HashMap<>();
		map.put("adminNo", testAdminNo);
		map.put("userNo", testUserNo);
		map.put("workspaceNo", testWorkspaceNo);
		
		workspaceUsersService.changeRole(map);
		
		return new ResponseEntity<ApiResult>(HttpStatus.OK);
	}
}
