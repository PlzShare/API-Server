package com.douzone.weboard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.weboard.repository.NotiRepository;
import com.douzone.weboard.repository.WorkspaceUsersRepository;
import com.douzone.weboard.repository.WorkspacesRepository;
import com.douzone.weboard.vo.ChangeUser;
import com.douzone.weboard.vo.Noti;
import com.douzone.weboard.vo.NotiUser;
import com.douzone.weboard.vo.WorkspaceUsers;
import com.douzone.weboard.vo.Workspaces;

@Service
public class WorkspaceUsersService {

	@Autowired
	private WorkspaceUsersRepository workspaceUsersRepository;
	@Autowired
	private WorkspacesRepository workspacesRepository;
	
	@Autowired
	private NotiRepository notiRepository;

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
		System.out.println(workspaces + "으아아아아앙ㅇ아아악");
		
		// 알림 서비스
		Noti noti = new Noti();
		NotiUser notiUser = new NotiUser();
		
		workspaceUsers.setWorkspaceNo(workspaces.getNo());
		workspaces.getUserNums().forEach((userNo) -> {
			workspaceUsers.setUserNo(userNo);
			workspaceUsersRepository.inviteUser(workspaceUsers);
			
		});
		
		
		
		noti.setType("invite");
		noti.setWorkspaceNo(workspaces.getNo());
		
		noti.setContents(workspaces.getInviteMember() + "님이 " 
					+ (workspacesRepository.find(workspaces.getNo()).getName())
					+ " 워크스페이스로 초대하셨습니다.");
		noti.setSender(workspaces.getUserNo());
		
		notiRepository.insertNoti(noti);
		
		notiUser.setNotiNo(noti.getNo());
		workspaces.getUserNums().forEach((userNo) -> {
			notiUser.setSendTo(userNo);
			notiRepository.insertNotiUser(notiUser);
		});
		
		
	}

	public void changeRole(ChangeUser chUser) {
		workspaceUsersRepository.changeRole(chUser);
	}
}