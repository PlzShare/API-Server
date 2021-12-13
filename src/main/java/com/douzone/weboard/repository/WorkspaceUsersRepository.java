package com.douzone.weboard.repository;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.weboard.vo.WorkspaceUsers;

@Repository
public class WorkspaceUsersRepository {
	
	@Autowired
	private SqlSession sqlSession;
	
	public List<WorkspaceUsers> findUser(Long wno){
		return sqlSession.selectList("workspaceUsers.findUser", wno);
//				("workspaceUsers.findUser", wno);
	}
	
	public boolean invite(HashMap<String, Long> map) {
		int count = sqlSession.insert("workspaceUsers.invite", map);
		return count == 1;
	}

	public boolean leave(HashMap<String, Long> map) {
		int count = sqlSession.update("workspaceUsers.leave", map);
		return count == 1;
	}
	
	public boolean changeRole(WorkspaceUsers current, WorkspaceUsers changeData) {
		int AUCount = sqlSession.update("workspaceUsers.changeRoleAU", current);
		int UACount = sqlSession.update("workspaceUsers.changeRoleUA", changeData);
		return (AUCount * UACount) == 1;
	}
}