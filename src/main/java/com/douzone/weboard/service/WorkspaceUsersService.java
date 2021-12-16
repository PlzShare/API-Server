package com.douzone.weboard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.weboard.repository.WorkspaceUsersRepository;
import com.douzone.weboard.vo.ChangeUser;
import com.douzone.weboard.vo.WorkspaceUsers;
import com.douzone.weboard.vo.Workspaces;

@Service
public class WorkspaceUsersService {

	@Autowired
	private WorkspaceUsersRepository workspaceUsersRepository;

	public List<WorkspaceUsers> getUser(Long wno) {
		List<WorkspaceUsers> workspaceUserList = workspaceUsersRepository.findUser(wno);
		System.out.println(wno);
		System.out.println(workspaceUserList);
		
		return workspaceUserList;
	}

	public void leave(WorkspaceUsers workspaceUsers) {
		System.out.println("떠난 사람(어드먼일때 고려할 것):" + workspaceUsers);
		workspaceUsersRepository.leave(workspaceUsers);
	}

	public void inviteAdmin(WorkspaceUsers workspaceUsers) {
		workspaceUsersRepository.inviteAdmin(workspaceUsers);
	}
	public void inviteUser(Workspaces workspaces) {
		WorkspaceUsers workspaceUsers = new WorkspaceUsers();
		
		workspaceUsers.setWorkspaceNo(workspaces.getNo());
		workspaces.getUserNums().forEach((userNo) -> {
			workspaceUsers.setUserNo(userNo);
			workspaceUsersRepository.inviteUser(workspaceUsers);
		});
	}

	public void changeRole(ChangeUser chUser) {
		workspaceUsersRepository.changeRole(chUser);
	}
}