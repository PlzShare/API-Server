package com.douzone.weboard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.weboard.repository.ChatRepository;
import com.douzone.weboard.repository.ChatroomUsersRepository;
import com.douzone.weboard.vo.Chatroom;
import com.douzone.weboard.vo.ChatroomUsers;
import com.douzone.weboard.vo.WorkspaceUsers;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {

	@Autowired
	private ChatRepository chatRepository;

	@Autowired
	private ChatroomUsersRepository chatroomUsersRepository;

	public List<Chatroom> findList(WorkspaceUsers workspaceUsers) {
		return chatRepository.findList(workspaceUsers);
	}

	public void insert(Chatroom chatroom) {
		ChatroomUsers chatroomUsers = new ChatroomUsers();
		chatRepository.insert(chatroom);

		chatroomUsers.setChatroomNo(chatroom.getNo());
		chatroom.getUserNums().forEach((userNo) -> {
			chatroomUsers.setUserNo(userNo);
			chatroomUsersRepository.insert(chatroomUsers);
		});

	}

	public List<Chatroom> findChatList(Long ctno) {
		return chatRepository.findChatList(ctno);
	}

	public void leave(ChatroomUsers chatroomUsers) {
		chatRepository.leave(chatroomUsers);
	}
}