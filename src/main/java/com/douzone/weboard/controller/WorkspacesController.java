package com.douzone.weboard.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.douzone.weboard.service.WorkspacesService;
import com.douzone.weboard.util.ApiResult;
import com.douzone.weboard.vo.Workspaces;

@RestController // responsebody 다 붙어진 효과
@RequestMapping("/workspaces")
public class WorkspacesController {
	
	@Autowired
	private WorkspacesService workspacesService;
	
	@GetMapping("")
	public ResponseEntity<ApiResult> main(){
		// 테스트로 유저넘버 1L 지정
		// 병합개발 시 수정할 것(테스트용)
		Long test_userNo = 1L;
		List<Workspaces> list = workspacesService.findAll(test_userNo);
		System.out.println(list);
		return new ResponseEntity<ApiResult>(ApiResult.success(list), HttpStatus.OK); // 리턴 여러개로 정상동작 / 오류동작으로 분기
	}
	
	@PostMapping("/insert")
	public ResponseEntity<ApiResult> insert(){
		Workspaces workspace = Workspaces.builder()
				.name("DB_insert_test1")
		        .userNo(1L)
		        .build();
		workspacesService.insert(workspace);
		return new ResponseEntity<ApiResult>(HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<ApiResult> update(){
		// 워크스페이스 관리자가 워크스페이스 수정 가능
		// 일반 사용자는 수정 불가
		Workspaces workspace = Workspaces.builder()
				.name("DB_update_test1")
		        .userNo(3L)
		        .build();
		workspacesService.update(workspace);
		return new ResponseEntity<ApiResult>(HttpStatus.OK);
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<ApiResult> delete(){
		// 워크스페이스 관리자가 워크스페이스 삭제 가능
		// 일반 사용자는 삭제 불가
		Long test_workspaceNo = 2L;
		workspacesService.delete(test_workspaceNo);
		return new ResponseEntity<ApiResult>(HttpStatus.OK);
	}
	
	@PostMapping("/search")
	public ResponseEntity<ApiResult> search(){
		// 테스트용 키워드 입력.
		// test할 이름으로 find 테스트.
		String test_keyword = "";
		// String test_searchType = "name";
		String test_searchType = "id";
		
		// 이 자리에 프론트에서 받아온 keyword값 넣어주세요
		// 이 자리에 프론트에서 받아온 searchType값(ex, 워크스페이스이름(name), 유저아이디(id)) 넣어주세요
		HashMap<String, String> map = new HashMap<>();
		map.put("keyword", "%" + test_keyword + "%");
		map.put("searchType", test_searchType);
		
		System.out.println(workspacesService.search(map));
		return new ResponseEntity<ApiResult>(HttpStatus.OK);
	}	
}