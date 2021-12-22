package com.douzone.weboard.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.weboard.repository.ChannelRepository;
import com.douzone.weboard.repository.NotiRepository;
import com.douzone.weboard.vo.Channel;
import com.douzone.weboard.vo.Noti;
import com.douzone.weboard.vo.NotiUser;
import com.douzone.weboard.vo.WorkspaceUsers;

@Service
public class ChannelService {
	
	@Autowired
	private ChannelRepository channelRepository;
	
	@Autowired
	private WorkspaceUsersService workspaceUsersRepository;
	
	@Autowired
	private NotiRepository notiRepository;
	
	public List<Channel> getList(Long wno){
		List<Channel> channelList = channelRepository.findAll(wno);
		
		return channelList;
	}
	
	public boolean addChannel(Channel channel) {
		Noti noti = new Noti();
		NotiUser notiUser = new NotiUser();
		List<WorkspaceUsers> work = workspaceUsersRepository.getUser(channel.getWorkspaceNo());
		
		ArrayList<Long> member = new ArrayList<>();
		
		for(int i=0;i<work.size();i++) {
			member.add(work.get(i).getUserNo());
		} // 유저넘버리스트
		
		noti.setType("channel");
		noti.setWorkspaceNo(channel.getWorkspaceNo());
		
		noti.setContents(channel.getNickname() + "님이 "
							+ channel.getName() + " 채널을 추가하셨습니다.");
		noti.setSender(channel.getMakeUser());
		
		System.out.println(noti + "우에에에에에엥에에에에에ㅔ에에에에에ㅔ에엥!!");
		
		notiRepository.insertNoti(noti);
		
		notiUser.setNotiNo(noti.getNo());
		member.forEach((userNo) -> {
			notiUser.setSendTo(userNo);
			notiRepository.insertNotiUser(notiUser);
		});
		
		System.out.println(notiUser + "우에에에에에엥에에에에에ㅔ에에에에에ㅔ에엥!!");
		
		return channelRepository.insert(channel);
	}
	
	public boolean deleteChannel(Long cno) {
		Channel channel = new Channel();
		channel.setNo(cno);
		
		return channelRepository.delete(channel);
	}
	
	public boolean updateChannel(Channel channel) {
		int count = channelRepository.update(channel);
		return count == 1;
	}
}
