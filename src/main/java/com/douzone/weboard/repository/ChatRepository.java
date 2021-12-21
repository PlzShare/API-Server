package com.douzone.weboard.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.douzone.weboard.vo.Chatroom;
import com.douzone.weboard.vo.ChatroomUsers;
import com.douzone.weboard.vo.WorkspaceUsers;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class ChatRepository {
	private final SqlSession sqlSession;

	public List<Chatroom> findList(WorkspaceUsers workspaceUsers) {
		return sqlSession.selectList("chatroom.findList", workspaceUsers);
	}

	public boolean insert(Chatroom chatroom) {
		int count = sqlSession.insert("chatroom.insert", chatroom);
		return count == 1;
	}

	public List<Chatroom> findChatList(Long chatroomNo) {
		return sqlSession.selectList("chat.findChatList", chatroomNo);
	}

	public boolean leave(ChatroomUsers chatroomUsers) {
		int count = sqlSession.delete("chatroom.leave", chatroomUsers);
		return count == 1;
	}
	
}
