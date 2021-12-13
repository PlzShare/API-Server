package com.douzone.weboard.controller;

import java.util.List;
import java.util.Optional;

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

import com.douzone.weboard.service.ChannelService;
import com.douzone.weboard.util.ApiResult;
import com.douzone.weboard.vo.Channel;

@RestController  // responsebody 생략가능
@RequestMapping("/workspaces/{wno}")
public class ChannelController {
	
	@Autowired
	private ChannelService channelService;
	
	
	@GetMapping({"/channels", "/channels/{cno}"})
	public ResponseEntity<ApiResult> getList(@PathVariable("wno") Long wno, @PathVariable(value = "cno",required = false) Long cno){
//		if(cno == null) cno = 
		
		List<Channel> list = channelService.getList(wno);
		
		return new ResponseEntity<ApiResult>(ApiResult.success(list), HttpStatus.OK);
	}
	
	@PostMapping("/channels")
	public ResponseEntity<ApiResult> addChannel(@PathVariable("wno") Long wno, @RequestBody Channel channel){
		channel.setWorkspaceNo(wno);
		channelService.addChannel(channel);
		
		return new ResponseEntity<ApiResult>(ApiResult.success(channel), HttpStatus.OK);
	}
	
	
	@DeleteMapping("/channels/{cno}")
	public ResponseEntity<ApiResult> deleteChannel(@PathVariable("cno") Long cno){
		channelService.deleteChannel(cno);
		return new ResponseEntity<ApiResult>(ApiResult.success(cno), HttpStatus.OK);
	}
	
	@PutMapping("/channels/{cno}")
	public ResponseEntity<ApiResult> updateChannel(@PathVariable("cno") Long cno, @RequestBody Channel channel){
		channel.setNo(cno);
		channelService.updateChannel(channel);
		return new ResponseEntity<ApiResult>(ApiResult.success(cno), HttpStatus.OK);
	}
	
	
	
}
