package com.douzone.weboard.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.douzone.weboard.util.ApiResult;
import com.douzone.weboard.vo.User;

@RestController // responsebody 다 붙어진 효과
public class TestController {
   
   // 이런식으로 앞으론 객체로 던짐
   // ResponseEntity : 응답이랑 상태코드를 던짐
   @GetMapping("/test")
   public ResponseEntity<User> test() {
      User user = User.builder()
                  .nickname("test")
                  .id("JDK")
                  .password("1234")
                  .build();
      return new ResponseEntity<User>(user, HttpStatus.OK); // 리턴 여러개로 정상동작 / 오류동작으로 분기
      
   }
   
   // form 데이터 받는 법
   @PostMapping("/test")
   public ResponseEntity<ApiResult> postTest(User user){
      return new ResponseEntity<ApiResult>(HttpStatus.OK); // 리턴 여러개로 정상동작 / 오류동작으로 분기
   }
   
   // json data
   // json으로 받기 위해서 RequestBody 사용
   @PutMapping("/test")
   public ResponseEntity<ApiResult> updateUser(@RequestBody User user){
      return new ResponseEntity<ApiResult>(ApiResult.success(user), HttpStatus.OK);
   }
   
   @DeleteMapping("/test/{no}")
   public ResponseEntity<ApiResult> deleteeUser(@PathVariable("no") Long no){
      return new ResponseEntity<ApiResult>(ApiResult.success(no), HttpStatus.OK);
   }
   
   
}