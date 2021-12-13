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
		
		// [0, 1, 2, 3] 배열에서 첫번째는 admin
		
		// 0번째 userAdmin
		workspace.setUserNo(workspace.getUserNums().get(0));
		
		// admin
		workspacesRepository.insert(workspace);
		Long workspaceNo = workspace.getNo();
		HashMap<String, Long> map = new HashMap<>();
		map.put("userNo", workspace.getUserNo());
		map.put("workspaceNo", workspaceNo);
		map.put("userRole", 0L); // 워크스페이스 생성자면 0L, 초대받은 유저는 1L
		workspaceUsersRepository.invite(map);
		map.clear();
		
		for(int i=1; i<workspace.getUserNums().size(); i++) {
			workspace.setUserNo(workspace.getUserNums().get(i));
			System.out.println(workspace);
			map.put("userNo", workspace.getUserNo());
			map.put("workspaceNo", workspaceNo);
			map.put("userRole", 1L); // 워크스페이스 생성자면 0L, 초대받은 유저는 1L
			System.out.println(workspace);
			workspaceUsersRepository.invite(map);
			map.clear();
		}	

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