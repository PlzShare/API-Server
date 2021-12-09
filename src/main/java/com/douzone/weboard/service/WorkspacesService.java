package com.douzone.weboard.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.weboard.repository.WorkspaceUsersRepository;
import com.douzone.weboard.repository.WorkspacesRepository;
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
	
	public void insert(Workspaces workspace) {
		workspacesRepository.insert(workspace);
		Long workspaceNo = workspace.getNo();
		System.out.println(workspace);
		HashMap<String, Long> map = new HashMap<>();
		map.put("userNo", workspace.getUserNo());
		map.put("workspaceNo", workspaceNo);
		map.put("userRole", 0L); // 워크스페이스 생성자면 0L, 초대받은 유저는 1L
		
		workspaceUsersRepository.invite(map);
	}	
	
	public void update(Workspaces workspace) {
		workspacesRepository.update(workspace);
	}
	
	public void delete(Long workspaceNo) {
		workspacesRepository.delete(workspaceNo);
	}	
	
	public List<Workspaces> search(HashMap<String, String> map) {
		return workspacesRepository.search(map);
	}
}
