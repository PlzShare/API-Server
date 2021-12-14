package com.douzone.weboard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.weboard.repository.WorkspaceUsersRepository;
import com.douzone.weboard.vo.ChangeUser;
import com.douzone.weboard.vo.WorkspaceUsers;

@Service
public class WorkspaceUsersService {

	@Autowired
	private WorkspaceUsersRepository workspaceUsersRepository;
	
	public List<WorkspaceUsers> getUser(Long wno){
		List<WorkspaceUsers> workspaceUserList = workspaceUsersRepository.findUser(wno);
		return workspaceUserList;
	}
	

	public void leave(WorkspaceUsers workspaceUsers) {
		System.out.println("떠난 사람(어드먼일때 고려할 것):" + workspaceUsers);
		workspaceUsersRepository.leave(workspaceUsers);	
	}
	
	public void inviteAdmin(WorkspaceUsers workspaceUsers) {
		workspaceUsersRepository.inviteAdmin(workspaceUsers);
	}
	
	public void inviteUser(WorkspaceUsers workspaceUsers) {
		workspaceUsersRepository.inviteUser(workspaceUsers);
	}
	
	public void changeRole(ChangeUser changeUser) {
		workspaceUsersRepository.changeRole(changeUser);	
	}
	
}