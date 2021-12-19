package com.douzone.weboard.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.weboard.repository.NotiRepository;
import com.douzone.weboard.repository.WorkspaceUsersRepository;
import com.douzone.weboard.repository.WorkspacesRepository;
import com.douzone.weboard.vo.Noti;
import com.douzone.weboard.vo.NotiUser;
import com.douzone.weboard.vo.WorkspaceUsers;
import com.douzone.weboard.vo.Workspaces;

@Service
public class WorkspacesService {

	@Autowired
	private WorkspacesRepository workspacesRepository;

	@Autowired
	private WorkspaceUsersRepository workspaceUsersRepository;

	@Autowired
	private NotiRepository notiRepository;

	public List<Workspaces> findAll(Long userNo) {
		return workspacesRepository.findAll(userNo);
	}

	public Workspaces find(Long wno) {
		return workspacesRepository.find(wno);
	}

	public void insert(Workspaces workspace) {

		// 워크스페이스 생성
		WorkspaceUsers workspaceAdmin = new WorkspaceUsers();
		WorkspaceUsers workspaceUsers = new WorkspaceUsers();
		workspacesRepository.insert(workspace);

		// 초대알림
		Noti noti = new Noti();
		NotiUser notiUser = new NotiUser();

		notiUser.setNotiNo(noti.getNo());
		workspace.getUserNums().forEach((userNo) -> {
			notiUser.setSendTo(userNo);
			notiRepository.insertNotiUser(notiUser);
		});

		// 워크스페이스 생성자 - Admin 설정
		workspaceAdmin.setWorkspaceNo(workspace.getNo());
		workspaceAdmin.setUserNo(workspace.getUserNo());
		workspaceUsersRepository.inviteAdmin(workspaceAdmin);

		// 워크스페이스 유저 초대 - Users설정
		workspaceUsers.setWorkspaceNo(workspace.getNo());
		workspaceUsers.setUserNums(workspace.getUserNums());
		if (workspaceUsers.getUserNums().size() != 0) {
			workspaceUsers.getUserNums().forEach((userNo) -> {
				workspaceUsers.setUserNo(userNo);
				workspaceUsersRepository.inviteUser(workspaceUsers);
			});
			noti.setType("workspace");
			noti.setWorkspaceNo(workspace.getNo());

			noti.setContents(workspace.getInviteMember() + "님이 " + (workspace.getName()) + " 워크스페이스로 초대하셨습니다.");
			noti.setSender(workspace.getUserNo());

			notiRepository.insertNoti(noti);
			
		} else {
			return;
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