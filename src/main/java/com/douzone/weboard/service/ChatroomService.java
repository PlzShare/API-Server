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
		
		chatroomUsers.setChatroomNo(chatroom.getNo());
		chatroom.getUserNums().forEach((userNo) -> {
			chatroomUsers.setUserNo(userNo);
			chatroomUsersRepository.insert(chatroomUsers);
		});

	}
}