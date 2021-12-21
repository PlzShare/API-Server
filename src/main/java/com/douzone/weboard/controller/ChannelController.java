package com.douzone.weboard.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
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
import org.springframework.web.client.RestTemplate;

import com.douzone.weboard.annotation.AuthUser;
import com.douzone.weboard.service.ChannelService;
import com.douzone.weboard.service.WorkspaceUsersService;
import com.douzone.weboard.util.ApiResult;
import com.douzone.weboard.vo.Channel;
import com.douzone.weboard.vo.NotiType;
import com.douzone.weboard.vo.User;

@RestController // responsebody 생략가능
@RequestMapping("/workspaces/{wno}/channels")
public class ChannelController {

	@Autowired
	private ChannelService channelService;
	
	@Autowired
	private WorkspaceUsersService workspaceUsersService;

	@GetMapping({ "", "/{cno}" })
	public ResponseEntity<ApiResult> getList(@PathVariable("wno") Long wno,
			@PathVariable(value = "cno", required = false) Long cno) {
		List<Channel> list = channelService.getList(wno);
		return new ResponseEntity<ApiResult>(ApiResult.success(list), HttpStatus.OK);
	}

	@PostMapping("")
	public ResponseEntity<ApiResult> addChannel(@PathVariable Long wno, @RequestBody Channel channel, @AuthUser User authUser) {
		channel.setWorkspaceNo(wno);
		channelService.addChannel(channel);
		System.out.println(channel + "ㅎㅎ");
		List<Long> memberNoList = workspaceUsersService.getUserList(wno);
		List<Long> notifyList = new ArrayList<Long>();
		for (int i=0;i < memberNoList.size();i++) {
			if( authUser.getNo() != memberNoList.get(i)) {
				notifyList.add(memberNoList.get(i));
			}
		}

		String message = authUser.getNickname() + "님이" + channel.getName() + "채널을 추가하셨습니다.";
		
		NotiType notiType = new NotiType();
		notiType.setMemberList(notifyList);
		notiType.setContent(message);
		notiType.setNickname(authUser.getNickname());
		notiType.setType("channel");
//	
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity requestEntity = new HttpEntity(notiType);
		restTemplate.exchange("http://localhost:8888/noti", HttpMethod.POST, requestEntity, String.class);
		return new ResponseEntity<ApiResult>(ApiResult.success(channel), HttpStatus.OK);
	}

	@DeleteMapping("/{cno}")
	public ResponseEntity<ApiResult> deleteChannel(@PathVariable Long wno, @PathVariable Long cno) {
		channelService.deleteChannel(cno);
		return new ResponseEntity<ApiResult>(ApiResult.success(cno), HttpStatus.OK);
	}


	@PutMapping("/{cno}")
	public ResponseEntity<ApiResult> updateChannel(@PathVariable Long wno, @PathVariable Long cno,
			@RequestBody Channel channel) {
		channel.setNo(cno);
		channelService.updateChannel(channel);
		return new ResponseEntity<ApiResult>(ApiResult.success(cno), HttpStatus.OK);
	}

}
