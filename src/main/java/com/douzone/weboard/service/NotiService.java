package com.douzone.weboard.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.weboard.repository.NotiRepository;
import com.douzone.weboard.vo.NotiUser;

@Service
public class NotiService {
	@Autowired
	private NotiRepository notiRepository;
	
	public List<NotiUser> getNoti(Long uno) {
		List<NotiUser> notiList = notiRepository.findinviteNoti(uno);
		
		return notiList;
	}
	
	public List<NotiUser> getTypeNoti(Long uno, String type) {
		Map<String, Object> map = new HashMap();
		map.put("sendTo", uno);
		map.put("type", type);
		List<NotiUser> notiList = notiRepository.findTypeNoti(map);
		
		return notiList;
	}
	
	public boolean deleteNotiUser(Long no) {
		NotiUser notiUser = new NotiUser();
		notiUser.setNo(no);
		
		return notiRepository.delete(notiUser);
	}
	

}
