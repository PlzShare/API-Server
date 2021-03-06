package com.douzone.weboard.controller;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.douzone.weboard.annotation.AuthUser;
import com.douzone.weboard.service.WorkspaceUsersService;
import com.douzone.weboard.service.WorkspacesService;
import com.douzone.weboard.util.ApiResult;
import com.douzone.weboard.vo.ChangeUser;
import com.douzone.weboard.vo.User;
import com.douzone.weboard.vo.WorkspaceUsers;
import com.douzone.weboard.vo.Workspaces;

@RestController // responsebody 다 붙어진 효과
@RequestMapping("/workspaces")
public class WorkspacesController {
	
	@Autowired
	private WorkspacesService workspacesService;

	@Autowired
	private WorkspaceUsersService workspaceUsersService;
	
	// main
	@GetMapping("")
	public ResponseEntity<ApiResult> main(@AuthUser User authUser){
		System.out.println("===============Workspace Get==========");
		System.out.println("어스유저 : " + authUser);
		
		List<Workspaces> list = workspacesService.findAll(authUser.getNo());
		System.out.println("리스트 : " + list);
		return new ResponseEntity<ApiResult>(ApiResult.success(list), HttpStatus.OK); // 리턴 여러개로 정상동작 / 오류동작으로 분기
	}
	
	@GetMapping("/{wno}")
	public ResponseEntity<ApiResult> getWorkspace(@PathVariable("wno") Long wno){
		return new ResponseEntity<ApiResult>(ApiResult.success(workspacesService.find(wno)), HttpStatus.OK);
	}

	// insert
	@PostMapping("")
	public ResponseEntity<ApiResult> insert(@RequestBody Workspaces workspace){
		workspacesService.insert(workspace);
		return new ResponseEntity<ApiResult>(HttpStatus.OK);
	}
	 
	// update
	@PutMapping("")
	public ResponseEntity<ApiResult> update(@RequestBody Workspaces workspace){
		workspacesService.update(workspace);
		return new ResponseEntity<ApiResult>(HttpStatus.OK);
	}
	
	// delete
	@DeleteMapping("")
	public ResponseEntity<ApiResult> delete(
			@RequestParam Long wno, 
			@AuthUser User authUser){

		workspacesService.delete(wno);
		return new ResponseEntity<ApiResult>(HttpStatus.OK);
	}
	
	@GetMapping("/search")
	public ResponseEntity<ApiResult> search(@AuthUser User authUser){
		
		// 테스트용 키워드 입력.
		String test_keyword = "";
		String test_searchType = "id";
		
		// 이 자리에 프론트에서 받아온 keyword, searchType값(ex, 워크스페이스이름(name), 유저아이디(id)) 넣어주세요
		HashMap<String, String> map = new HashMap<>();
		map.put("keyword", "%" + test_keyword + "%");
		map.put("searchType", test_searchType);
		
		workspacesService.search(map);
		return new ResponseEntity<ApiResult>(HttpStatus.OK);
	}
	
	////////////////////////////// /workspace-users /////////////////////////////////////////
	@GetMapping("/workspace-users")
	public ResponseEntity<ApiResult> getlist(
			@RequestParam Long wno){
		
		List<WorkspaceUsers> result = workspaceUsersService.getUser(wno);
				
		return new ResponseEntity<ApiResult>(ApiResult.success(result), HttpStatus.OK);
	}
	
	@PostMapping("/workspace-users")
	public ResponseEntity<ApiResult> inviteUser(
			@RequestBody Workspaces workspaces){
		workspaceUsersService.inviteUser(workspaces);
		return new ResponseEntity<ApiResult>(HttpStatus.OK);
	}
	
	@DeleteMapping("/workspace-users")
	public ResponseEntity<ApiResult> leave(
			@AuthUser User authUser, 
			@RequestParam Long wno){
		
		System.out.println("떠난 사람:" + authUser);
		
		WorkspaceUsers workspaceUsers = 
				WorkspaceUsers.builder()
							  .userNo(authUser.getNo())
							  .workspaceNo(wno)
							  .build();
		
		workspaceUsersService.leave(workspaceUsers);
		
		return new ResponseEntity<ApiResult>(HttpStatus.OK);
	}
	
	@DeleteMapping("/workspace_users/noti")
	public ResponseEntity<ApiResult> deleteNotiUser(
			@RequestParam Long nno,
			@AuthUser User authUser){
		
		WorkspaceUsers workspaceUsers = new WorkspaceUsers();
		workspaceUsers.setUserNo(authUser.getNo());
		
		workspaceUsersService.deleteNotiUser(workspaceUsers, nno);
		return new ResponseEntity<ApiResult>(HttpStatus.OK);
	}
	
	@PutMapping("/workspace-users/change-role")
	public ResponseEntity<ApiResult> changeRole(
			@AuthUser User authUser,
			@RequestBody ChangeUser changeUser){
		changeUser.setAdminNo(authUser.getNo());
		System.out.println(changeUser);
		workspaceUsersService.changeRole(changeUser);
		return new ResponseEntity<ApiResult>(HttpStatus.OK);
	}
	
	
	@PutMapping("/workspace_users")
	public ResponseEntity<ApiResult> changeDate(@RequestBody WorkspaceUsers workspaceUsers){
		workspaceUsersService.changeDate(workspaceUsers);
		return new ResponseEntity<ApiResult>(HttpStatus.OK);
	}
	
	
	
}