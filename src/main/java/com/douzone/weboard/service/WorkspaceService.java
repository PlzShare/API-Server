package com.douzone.weboard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.weboard.repository.WorkspaceRepository;
import com.douzone.weboard.vo.Workspace;

@Service
public class WorkspaceService {
	
	@Autowired
	private WorkspaceRepository workspaceRepository;
	
	public List<Workspace> findAll(Long userNo) {
		System.out.println("통과");
//		Workspace d1 = Workspace.builder().no(1L).name("JDK").createdAt("생성일자").userNo(1L).build();
//		Workspace d2 = Workspace.builder().no(2L).name("JDK2").createdAt("생성일자2").userNo(2L).build();
//		List<Workspace> list = new ArrayList<>();
//		list.add(d1);
//		list.add(d2);
//		System.out.println(list);
		return workspaceRepository.findAll(userNo);
	}
	

}
