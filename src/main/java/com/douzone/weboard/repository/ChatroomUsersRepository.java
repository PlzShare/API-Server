package com.douzone.weboard.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.douzone.weboard.vo.ChatroomUsers;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class ChatroomUsersRepository {
	private final SqlSession sqlSession;

	public boolean insert(ChatroomUsers chatroomUsers) {
		int count = sqlSession.insert("chatroomUsers.insert", chatroomUsers);
		return count == 1;
	}
}
