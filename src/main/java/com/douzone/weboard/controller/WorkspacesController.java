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
import com.douzone.weboard.service.WorkspacesService;
import com.douzone.weboard.util.ApiResult;
import com.douzone.weboard.vo.Workspaces;
@RestController // responsebody 다 붙어진 효과
@RequestMapping("/workspaces")
public class WorkspacesController {
	@Autowired
	private WorkspacesService workspacesService;
	
	// main
	@GetMapping("")
	public ResponseEntity<ApiResult> main(
			Workspaces workspace){
		Long userNo = workspace.getUserNo();
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
	@PutMapping("")
	public ResponseEntity<ApiResult> update(
			@RequestBody Workspaces workspace){
		// 워크스페이스 관리자가 워크스페이스 수정 가능
		// json에 userNo, name 추가해서 보낼 것
		workspacesService.update(workspace);
		return new ResponseEntity<ApiResult>(HttpStatus.OK);
	}
	
	// delete
	@DeleteMapping("")
	public ResponseEntity<ApiResult> delete(
			@RequestBody Workspaces workspace){
		// 워크스페이스 관리자가 워크스페이스 삭제 가능
		Long workspaceNo = workspace.getNo();
		System.out.println(workspaceNo);
		workspacesService.delete(workspaceNo);
		return new ResponseEntity<ApiResult>(HttpStatus.OK);
	}
	
	// 용신님 도와주세요
	// search
	@GetMapping("/search")
	public ResponseEntity<ApiResult> search(
			@PathVariable("userNo") Long userNo){
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
		
		workspacesService.search(map);
		return new ResponseEntity<ApiResult>(HttpStatus.OK);
	}
}