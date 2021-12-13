package com.douzone.weboard.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.weboard.repository.WorkspaceUsersRepository;
import com.douzone.weboard.vo.WorkspaceUsers;

@Service
public class WorkspaceUsersService {

	@Autowired
	private WorkspaceUsersRepository workspaceUsersRepository;
	
	public List<WorkspaceUsers> getUser(Long wno){
		List<WorkspaceUsers> workspaceUserList = workspaceUsersRepository.findUser(wno);
		
		return workspaceUserList;
	}
	

	public void leave(HashMap<String, Long> map) {
		workspaceUsersRepository.leave(map);	
	}
	
//	public void inviteAdmin(WorkspaceUsers workspaceUsers) {
//		workspaceUsersRepository.inviteAdmin(workspaceUsers);
//	}
//
//	public void inviteUser(WorkspaceUsers workspaceUsers) {
//		workspaceUsersRepository.inviteUser(workspaceUsers);
//	}
	
	public void invite(HashMap<String, Long> map) {
		workspaceUsersRepository.invite(map);
	}

	public void changeRole(WorkspaceUsers current, WorkspaceUsers changeData) {
		workspaceUsersRepository.changeRole(current, changeData);	
	}
	
}