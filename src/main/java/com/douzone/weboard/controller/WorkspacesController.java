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

import com.douzone.weboard.annotation.AuthUser;
import com.douzone.weboard.service.WorkspaceUsersService;
import com.douzone.weboard.service.WorkspacesService;
import com.douzone.weboard.util.ApiResult;
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
		// json에 userNo, name 추가해서 보낼 것
		workspacesService.insert(workspace);
		return new ResponseEntity<ApiResult>(HttpStatus.OK);
	}
	 
	// update
	@PutMapping("/{uno}")
	public ResponseEntity<ApiResult> update(
			@RequestBody Workspaces workspace,
			@PathVariable("uno") Long no){
		// 워크스페이스 관리자가 워크스페이스 수정 가능
		// json에 userNo, name 추가해서 보낼 것
		workspace.setUserNo(no);
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
	
	// 용신님 도와주세요
	// search - 미완성이요. 문제점 발견해써요.... 빠른 수정할게요오...
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
	public ResponseEntity<ApiResult> invite(
			@RequestBody WorkspaceUsers workspaceUsers){
		HashMap<String, Long> map = new HashMap<>();

		Long userNo = workspaceUsers.getUserNo();
		Long workspaceNo = workspaceUsers.getWorkspaceNo();
		
		map.put("userNo", userNo);
		map.put("workspaceNo", workspaceNo);
		map.put("userRole", 1L); // 워크스페이스 생성자면 0L, 초대받은(일반) 유저는 1L
		
		workspaceUsersService.invite(map);
		return new ResponseEntity<ApiResult>(HttpStatus.OK);
	}
	
	@DeleteMapping("/workspace-users/{userNo}/{workspaceNo}")
	public ResponseEntity<ApiResult> leave(
			@PathVariable("workspaceNo") Long workspaceNo,
			@PathVariable("userNo") Long userNo){
		
		Long testUserNo = userNo;
		Long testWorkspaceNo = workspaceNo;
		Long testRole = 1L; // 워크스페이스 생성자면 0L, 초대받은 유저(일반유저)는 1L
		
		HashMap<String, Long> map = new HashMap<>();
		map.put("userNo", testUserNo);
		map.put("workspaceNo", testWorkspaceNo);
		map.put("user", testRole);
		
		// 다시 한 번 고려할 것
		workspaceUsersService.leave(map);
		
		return new ResponseEntity<ApiResult>(HttpStatus.OK);
	}
	
	// 테스트 돌아는 가요... 하지만 좋은 방법 찾고있어요...
	@PutMapping("/workspace-users/change-role/{no}")
	public ResponseEntity<ApiResult> changeRole(@PathVariable("no") Long no, @RequestBody WorkspaceUsers changeData){
		WorkspaceUsers current = new WorkspaceUsers();
		current.setUserNo(no);
		current.setWorkspaceNo(changeData.getWorkspaceNo());

//		Long testAdminNo = 4L; // 로그인한 유저번호
//		Long testUserNo = 21L; // 선택한 유저의 no
//		Long testWorkspaceNo = 126L; // 워크스페이스 no
	
//		HashMap<String, Long> map = new HashMap<>();
//		map.put("adminNo", testAdminNo);
//		map.put("userNo", testUserNo);
//		map.put("workspaceNo", testWorkspaceNo);
		
		workspaceUsersService.changeRole(current, changeData);
		
		return new ResponseEntity<ApiResult>(HttpStatus.OK);
	}
	
}