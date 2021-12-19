package com.douzone.weboard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.weboard.repository.ChannelRepository;
import com.douzone.weboard.vo.Channel;

@Service
public class ChannelService {
	
	@Autowired
	private ChannelRepository channelRepository;
	
	public List<Channel> getList(Long wno){
		List<Channel> channelList = channelRepository.findAll(wno);
		
		return channelList;
	}
	
	public boolean addChannel(Channel channel) {
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
