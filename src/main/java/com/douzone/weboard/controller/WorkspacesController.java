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
import org.springframework.web.bind.annotation.RestController;

import com.douzone.weboard.service.WorkspaceUsersService;
import com.douzone.weboard.service.WorkspacesService;
import com.douzone.weboard.util.ApiResult;
import com.douzone.weboard.vo.ChangeUser;
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
	@GetMapping("/{userNo}")
	public ResponseEntity<ApiResult> main(
			@PathVariable("userNo") Long userNo){
		List<Workspaces> list = workspacesService.findAll(userNo);
		return new ResponseEntity<ApiResult>(ApiResult.success(list), HttpStatus.OK); // 리턴 여러개로 정상동작 / 오류동작으로 분기
	}

	// insert
	@PostMapping("")
	public ResponseEntity<ApiResult> insert(
			@RequestBody Workspaces workspace){
		workspacesService.insert(workspace);
		return new ResponseEntity<ApiResult>(HttpStatus.OK);
	}
	 
	// update
	@PutMapping("")
	public ResponseEntity<ApiResult> update(
			@RequestBody Workspaces workspace){
		workspacesService.update(workspace);
		return new ResponseEntity<ApiResult>(HttpStatus.OK);
	}
	
	// delete
	@DeleteMapping("/{workspaceNo}")
	public ResponseEntity<ApiResult> delete(
			@PathVariable("workspaceNo") Long workspaceNo){
		// 워크스페이스 관리자가 워크스페이스 삭제 가능
		workspacesService.delete(workspaceNo);
		return new ResponseEntity<ApiResult>(HttpStatus.OK);
	}
	
	@GetMapping("/search/{userNo}")
	public ResponseEntity<ApiResult> search(
			@PathVariable("userNo") Long userNo){
		
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
	
	@GetMapping("/workspace-users/{userNo}/{workspaceNo}")
	public ResponseEntity<ApiResult> getlist(
			@PathVariable("userNo") Long userNo, 
			@PathVariable("workspaceNo") Long workspaceNo){
		
		WorkspaceUsers workspaceUsers = new WorkspaceUsers();
		workspaceUsers.setUserNo(userNo);
		List<WorkspaceUsers> result = workspaceUsersService.getUser(workspaceNo);
				
		return new ResponseEntity<ApiResult>(ApiResult.success(result), HttpStatus.OK);
	}
	
	@PostMapping("/workspace-users")
	public ResponseEntity<ApiResult> inviteUser(
			@RequestBody WorkspaceUsers workspaceUsers){
		workspaceUsersService.inviteUser(workspaceUsers);
		return new ResponseEntity<ApiResult>(HttpStatus.OK);
	}
	
	@DeleteMapping("/workspace-users/{userNo}/{workspaceNo}")
	public ResponseEntity<ApiResult> leave(
			@PathVariable("workspaceNo") Long workspaceNo,
			@PathVariable("userNo") Long userNo){
		
		WorkspaceUsers workspaceUsers = new WorkspaceUsers();
		workspaceUsers.setUserNo(userNo);
		workspaceUsers.setWorkspaceNo(workspaceNo);
		
		workspaceUsersService.leave(workspaceUsers);
		
		return new ResponseEntity<ApiResult>(HttpStatus.OK);
	}
	
	@PutMapping("/workspace-users/change-role")
	public ResponseEntity<ApiResult> changeRole(
			@RequestBody ChangeUser changeUser){
		
		workspaceUsersService.changeRole(changeUser);
		
		return new ResponseEntity<ApiResult>(HttpStatus.OK);
	}
	
}