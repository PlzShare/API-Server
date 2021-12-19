package com.douzone.weboard.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.weboard.repository.WorkspaceUsersRepository;
import com.douzone.weboard.repository.WorkspacesRepository;
import com.douzone.weboard.vo.WorkspaceUsers;
import com.douzone.weboard.vo.Workspaces;

@Service
public class WorkspacesService {

	@Autowired
	private WorkspacesRepository workspacesRepository;
	
	@Autowired
	private WorkspaceUsersRepository workspaceUsersRepository;
	
	public List<Workspaces> findAll(Long userNo) {
		return workspacesRepository.findAll(userNo);
	}
	public Workspaces find(Long wno) {
		return workspacesRepository.find(wno);
	}
	public void insert(Workspaces workspace) {
		
		WorkspaceUsers workspaceUsers = new WorkspaceUsers();
		workspace.setUserNo(workspace.getUserNums().get(0));
		workspacesRepository.insert(workspace);
		
		Long workspaceNo = workspace.getNo();
		Long userNo = workspace.getUserNo();
		
		workspaceUsers.setUserNo(userNo);
		workspaceUsers.setWorkspaceNo(workspaceNo);
		
		workspaceUsersRepository.inviteAdmin(workspaceUsers);		
				
		for(int i=1; i<workspace.getUserNums().size(); i++) {
			// UserNums에서 하나씩 추출해서 번호만 바뀌면서 새로 추가
			workspaceUsers.setUserNo(workspace.getUserNums().get(i));
			workspaceUsersRepository.inviteUser(workspaceUsers);
		}	

	}	
	
	public void update(Workspaces workspace) {
		workspacesRepository.update(workspace);
	}
	
	public void delete(Long workspaceNo) {
		// 어드민이어야만 지울 수 있게 생각
		workspacesRepository.delete(workspaceNo);
	}	
	
	public List<Workspaces> search(HashMap<String, String> map) {
		return workspacesRepository.search(map);
	}
}