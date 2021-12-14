package com.douzone.weboard.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.weboard.vo.ChangeUser;
import com.douzone.weboard.vo.WorkspaceUsers;

@Repository
public class WorkspaceUsersRepository {
	
	@Autowired
	private SqlSession sqlSession;
	
	public List<WorkspaceUsers> findUser(Long wno){
		return sqlSession.selectList("workspaceUsers.findUser", wno);
//				("workspaceUsers.findUser", wno);
	}
	
	public boolean inviteAdmin(WorkspaceUsers workspaceUsers) {
		int count = sqlSession.insert("workspaceUsers.inviteAdmin", workspaceUsers);
		return count == 1;
	}	
	
	public boolean inviteUser(WorkspaceUsers workspaceUsers) {
		int count = sqlSession.insert("workspaceUsers.inviteUser", workspaceUsers);
		return count == 1;
	}

	public boolean leave(WorkspaceUsers workspaceUsers) {
		int count = sqlSession.update("workspaceUsers.leave", workspaceUsers);
		return count == 1;
	}
	
	public boolean changeRole(ChangeUser changeUser) {
		int AUCount = sqlSession.update("workspaceUsers.changeRoleAU", changeUser);
		int UACount = sqlSession.update("workspaceUsers.changeRoleUA", changeUser);
		return (AUCount * UACount) == 1;
	}
}