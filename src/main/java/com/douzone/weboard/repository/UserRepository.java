package com.douzone.weboard.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.douzone.weboard.vo.SearchInfo;
import com.douzone.weboard.vo.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class UserRepository {
	private final SqlSession sqlSession;
	
	public boolean insert(User user) {
		return sqlSession.insert("user.insert",user) == 1;
	}

	public User find(User user) {
		return sqlSession.selectOne("user.find",user);
	}

	public User findNo(Long userNo) {
		return sqlSession.selectOne("user.findNo", userNo);
	}

	public boolean update(User user) {
		int count =  sqlSession.update("user.update", user);
		return count == 1; 
	}

	public boolean delete(Long userNo) {
		int count = sqlSession.delete("user.delete", userNo);
		return count == 1;
	}

	public List<User> findList(SearchInfo searchInfo) {
	
		return sqlSession.selectList("user.findList", searchInfo);
	}
	
	public Long checkUser(String userId) {
		return sqlSession.selectOne("user.checkUser", userId);
	}
}
