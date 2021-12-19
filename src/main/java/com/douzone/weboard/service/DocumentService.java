package com.douzone.weboard.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.weboard.repository.ChannelRepository;
import com.douzone.weboard.repository.DocumentRepository;
import com.douzone.weboard.repository.NotiRepository;
import com.douzone.weboard.vo.Document;
import com.douzone.weboard.vo.Noti;
import com.douzone.weboard.vo.NotiUser;
import com.douzone.weboard.vo.WorkspaceUsers;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocumentService {
	private final DocumentRepository documentRepository;
	
	@Autowired
	private ChannelRepository channelRepository;
	
	@Autowired
	private WorkspaceUsersService workspaceUsersRepository;
	
	@Autowired
	private NotiRepository notiRepository;
	
	public boolean add(Document document) {
		Noti noti = new Noti();
		NotiUser notiUser = new NotiUser();
		List<WorkspaceUsers> work = workspaceUsersRepository.getUser(document.getWorkspaceNo());
		
		ArrayList<Long> member = new ArrayList<>();
		
		for(int i=0;i<work.size();i++) {
			member.add(work.get(i).getUserNo());
		} // 유저넘버리스트
		
		noti.setType("doc");
		noti.setWorkspaceNo(document.getWorkspaceNo());
		
		noti.setContents(document.getNickname() + "님이 "
							+ document.getTitle() + " 문서를 추가하셨습니다.");
		noti.setSender(document.getMakeUser());
		
		notiRepository.insertNoti(noti);
		
		notiUser.setNotiNo(noti.getNo());
		member.forEach((userNo) -> {
			notiUser.setSendTo(userNo);
			notiRepository.insertNotiUser(notiUser);
		});
		
		
		return documentRepository.insert(document);
	}
	
	public boolean update(Document document) {
		return documentRepository.update(document);
	}
	
	public boolean delete(Long no) {
		return documentRepository.delete(no);
	}
	
	public Optional<Document> find(Long no) {
		return documentRepository.find(no);
	}
	
	public List<Document> findAll(Document document){
		return documentRepository.findAll(document);
	}
}
