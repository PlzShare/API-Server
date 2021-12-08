package com.douzone.weboard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.weboard.repository.NotiRepository;
import com.douzone.weboard.vo.Noti;
import com.douzone.weboard.vo.NotiUser;
import com.douzone.weboard.vo.User;

@Service
public class NotiService {
	@Autowired
	private NotiRepository notiRepository;
	
	
	// 최초 노티 & 유저 노티
	public void addNoti(Noti noti, List<User> userList) {
		notiRepository.insertNoti(noti);
		
		for(User user : userList) {
			NotiUser notiUser = NotiUser.builder()
									.notiNo(noti.getNo())
									.sendTo(user.getNo())
									.build();
			
			notiRepository.insertNotiUser(notiUser);			
		}
		return;		
	}
	
	public boolean deleteNotiUser(Long no) {
		NotiUser notiUser = new NotiUser();
		notiUser.setNo(no);
		
		return notiRepository.delete(notiUser);
	}
	

}