package com.douzone.weboard.controller;

import java.util.List;

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
import org.springframework.web.multipart.MultipartFile;

import com.douzone.weboard.service.FileuploadService;
import com.douzone.weboard.service.UserService;
import com.douzone.weboard.util.ApiResult;
import com.douzone.weboard.vo.SearchInfo;
import com.douzone.weboard.vo.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
	private final UserService userService;
	private final FileuploadService FileuploadService;
	
	//회원가입
	@PostMapping("/join")
	public ResponseEntity<ApiResult> joinUser(@RequestBody User user){
		userService.join(user);
		return new ResponseEntity<ApiResult>(ApiResult.success(user),HttpStatus.OK);
		
	}
	
	// 로그인
	@PostMapping("/login")
	public ResponseEntity<ApiResult> login(@RequestBody User user){
		User login = userService.login(user);
		if(login == null) {
			return new ResponseEntity<ApiResult>(ApiResult.fail("id, password 불일치"),HttpStatus.UNAUTHORIZED);			
		}
		
		return new ResponseEntity<ApiResult>(ApiResult.success(user),HttpStatus.OK);
	}
	
	// userNo 가져오기
	@GetMapping("/{userNo}")
	public ResponseEntity<ApiResult> getUser(@PathVariable("userNo") Long userNo){
		User user = userService.getUser(userNo);
		if(user == null) {
		return new ResponseEntity<ApiResult>(ApiResult.fail("없음"),HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ApiResult>(ApiResult.success(user),HttpStatus.OK);
	}
	
	// 회원정보 수정
	@PutMapping("/{userNo}")
	public ResponseEntity<ApiResult> update(User user, @PathVariable Long userNo , @RequestParam("file") MultipartFile file) {
		
		String url = FileuploadService.restoreImage(file, "/user/profile");
		
		user.setNo(userNo);
		user.setProfile(url);
		
		userService.update(user);
		
		return new ResponseEntity<ApiResult>(ApiResult.success(user),HttpStatus.OK);
	}
	
	// 회원탈퇴
	@DeleteMapping("/{userNo}")
	public ResponseEntity<ApiResult> delete(@PathVariable Long userNo){
	
		userService.delete(userNo);
		
		return new ResponseEntity<ApiResult>(ApiResult.success(true),HttpStatus.OK);
	}
	
	// 유저 검색 
	@GetMapping("")
	public ResponseEntity<ApiResult> getList(SearchInfo searchInfo){
		// System.out.println(searchInfo);
		List<User> list = userService.getList(searchInfo);
		return new ResponseEntity<ApiResult>(ApiResult.success(list),HttpStatus.OK);
	}
	
	// 멤버 확인
	@PostMapping("/checkUser")
	public ResponseEntity<ApiResult> checkUser(
			@RequestBody User user){
		Long userNo = userService.checkUser(user.getId());
		System.out.println(userNo);		
		return new ResponseEntity<ApiResult>(ApiResult.success(userNo), HttpStatus.OK);

	}
}
