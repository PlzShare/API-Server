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
		int count = sqlSession.insert("workspace_users.invite", map);
		return count == 1;
	}

	public boolean leave(HashMap<String, Long> map) {
		int count = sqlSession.update("workspace_users.leave", map);
		return count == 1;
	}
	
	public boolean changeRole(HashMap<String, Long> map) {
		System.out.println(map);
		int AUCount = sqlSession.update("workspace_users.changeRoleAU", map);
		int UACount = sqlSession.update("workspace_users.changeRoleUA", map);
		return (AUCount * UACount) == 1;
	}
}
