package com.douzone.weboard.repository;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.weboard.vo.Workspaces;

@Repository
public class WorkspaceUsersRepository {
	
	@Autowired
	private SqlSession sqlSession;
	
	public boolean invite(HashMap<String, Long> map) {
		int count = sqlSession.insert("workspace_users.invite", map);
		return count == 1;
	}	

}
