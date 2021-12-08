package com.douzone.weboard.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.weboard.repository.WorkspaceUsersRepository;

@Service
public class WorkspaceUsersService {

	@Autowired
	private WorkspaceUsersRepository workspaceUsersRepository;

	public void leave(HashMap<String, Long> map) {
		workspaceUsersRepository.leave(map);	
	}
	
	public void invite(HashMap<String, Long> map) {
		workspaceUsersRepository.invite(map);
	}

	public void changeRole(HashMap<String, Long> map) {
		workspaceUsersRepository.changeRole(map);	
	}
	
}
