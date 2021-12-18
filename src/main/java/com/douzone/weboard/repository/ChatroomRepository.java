package com.douzone.weboard.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.douzone.weboard.vo.Chatroom;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class ChatroomRepository {
	private final SqlSession sqlSession;

	public List<Chatroom> findList(Long workspaceNo) {
		return sqlSession.selectList("chatroom.findList", workspaceNo);
	}

	public boolean insert(Chatroom chatroom) {
		int count = sqlSession.insert("chatroom.insert", chatroom);
		return count == 1;
	}
}
