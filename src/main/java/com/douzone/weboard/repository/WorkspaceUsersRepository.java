package com.douzone.weboard.repository;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class WorkspaceUsersRepository {
	
	@Autowired
	private SqlSession sqlSession;
	
	public boolean invite(HashMap<String, Long> map) {
		int count = sqlSession.insert("workspaceUsers.invite", map);
		return count == 1;
	}

	public boolean leave(HashMap<String, Long> map) {
		int count = sqlSession.update("workspaceUsers.leave", map);
		return count == 1;
	}
	
	public boolean changeRole(HashMap<String, Long> map) {
		System.out.println(map);
		int AUCount = sqlSession.update("workspaceUsers.changeRoleAU", map);
		int UACount = sqlSession.update("workspaceUsers.changeRoleUA", map);
		return (AUCount * UACount) == 1;
	}
}
