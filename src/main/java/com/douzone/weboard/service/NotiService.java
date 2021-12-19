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
	
	public List<NotiUser> getNoti(Long uno) {
		List<NotiUser> notiList = notiRepository.findNoti(uno);
		
		return notiList;
	}
	
	public boolean deleteNotiUser(Long no) {
		NotiUser notiUser = new NotiUser();
		notiUser.setNo(no);
		
		return notiRepository.delete(notiUser);
	}
	

}
