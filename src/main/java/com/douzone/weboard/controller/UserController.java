package com.douzone.weboard.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.weboard.annotation.AuthUser;
import com.douzone.weboard.service.FileuploadService;
import com.douzone.weboard.service.UserService;
import com.douzone.weboard.util.ApiResult;
import com.douzone.weboard.vo.SearchInfo;
import com.douzone.weboard.vo.User;

@RestController
@RequestMapping("/users")
public class UserController {
	private final UserService userService;
	private final FileuploadService FileuploadService;	
	
	@Value("${authServer.url}")
	private String authServerUrl;
	
	
	public UserController(UserService userService, FileuploadService fileuploadService) {
		this.userService = userService;
		FileuploadService = fileuploadService;
	}
	
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
		
		// get-token
		RestTemplate restTemplate = new RestTemplate();
		
		ResponseEntity<String> entity = restTemplate.postForEntity(authServerUrl + "/get-token", login, String.class);
		String token = entity.getHeaders().get("Authorization").get(0);
		
		System.out.println(entity);
		System.out.println("=========================");
		System.out.println(token);
		System.out.println("=========================");
		
		return ResponseEntity.ok()
				.header("Authorization", token)
				.body(ApiResult.success(login));
	}
	
	
	
	
	// userNo 가져오기
	@GetMapping("")
	public ResponseEntity<ApiResult> getUser(@AuthUser User authUser){
		User user = userService.getUser(authUser.getNo());
		if(user == null) {
		return new ResponseEntity<ApiResult>(ApiResult.fail("없음"),HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ApiResult>(ApiResult.success(user),HttpStatus.OK);
	}
	
	// 회원정보 수정
	@PutMapping("")
	public ResponseEntity<ApiResult> update(
			User user,
			@AuthUser User authUser,
			@RequestParam(value="file",required = false) MultipartFile file) {
		

		String url = FileuploadService.restoreImage(file, "/user/profile");		
		user.setNo(authUser.getNo());
		user.setProfile(url);
		
		String nickname = user.getNickname() == null? null : user.getNickname().trim();
		String password = user.getPassword() == null? null : user.getPassword().trim();
		user.setNickname(nickname);
		user.setPassword(password);
		
		System.out.println("-0-9-07-0897690758967");
		System.out.println(user);
		if(userService.update(user)) {
			if(nickname != null && !nickname.isEmpty()) {
				authUser.setNickname(nickname);				
			}
			if(password != null && password.isEmpty()) {
				authUser.setPassword(password);				
			}
			if(user.getProfile() != null && !user.getProfile().isEmpty()) {
				authUser.setProfile(user.getProfile());				
			}
			
			
			// get-token
			RestTemplate restTemplate = new RestTemplate();
			
			ResponseEntity<String> entity = restTemplate.postForEntity(authServerUrl + "/get-token", authUser, String.class);
			String token = entity.getHeaders().get("Authorization").get(0);
					
			return ResponseEntity.ok()
					.header("Authorization", token)
					.body(ApiResult.success(authUser));
		}
		
		return new ResponseEntity<ApiResult>(HttpStatus.BAD_REQUEST);
	}
	
	// 회원탈퇴
	@DeleteMapping("")
	public ResponseEntity<ApiResult> delete(@AuthUser User authUser){
	
		userService.delete(authUser.getNo());
		
		return new ResponseEntity<ApiResult>(ApiResult.success(true),HttpStatus.OK);
	}
	
	// 유저 검색 
	@GetMapping("/search")
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
		return new ResponseEntity<ApiResult>(ApiResult.success(userNo), HttpStatus.OK);
	}
}
