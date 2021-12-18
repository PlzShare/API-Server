package com.douzone.weboard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.weboard.repository.ChatroomRepository;
import com.douzone.weboard.repository.ChatroomUsersRepository;
import com.douzone.weboard.vo.Chatroom;
import com.douzone.weboard.vo.ChatroomUsers;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatroomService {

	@Autowired
	private ChatroomRepository chatroomRepository;
	
	@Autowired
	private ChatroomUsersRepository chatroomUsersRepository;

	public List<Chatroom> findList(Long wno) {
		return chatroomRepository.findList(wno);
	}

	public void insert(Chatroom chatroom) {
		ChatroomUsers chatroomUsers = new ChatroomUsers();
		chatroomRepository.insert(chatroom);
		
		Long chatroomNo = chatroom.getNo();
		Long UserNo = chatroom.getUserNums().get(0);
		
		chatroomUsers.setChatroomNo(chatroomNo);
		chatroomUsers.setUserNo(UserNo);
		chatroomUsersRepository.insert(chatroomUsers);
				
		for(int i=1; i<chatroom.getUserNums().size(); i++) {
			// 초대되었다고 알람을 뿌리는 구간
			chatroomUsers.setUserNo(chatroom.getUserNums().get(i));
			chatroomUsersRepository.insert(chatroomUsers);
		}	
	}
}
