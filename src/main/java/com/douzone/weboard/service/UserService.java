package com.douzone.weboard.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.douzone.weboard.repository.UserRepository;
import com.douzone.weboard.vo.SearchInfo;
import com.douzone.weboard.vo.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;	
	
	@Transactional
	public boolean join(User user) {
		//비밀번호 암호화
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		return userRepository.insert(user);
	}
	

	public User login(User user) {
		
		String userps = userRepository.findPassword(user.getId());
	
		boolean check = passwordEncoder.matches(user.getPassword(), userps);
		
		System.out.println(check);
		if(check) {
			return userRepository.find(user);
		}else {
			return null;
		}
	}

	public User getUser(Long userNo) {
		return userRepository.findNo(userNo);
	}

	public boolean update(User user) {
		return userRepository.update(user);
	}

	public boolean delete(Long userNo) {
		return userRepository.delete(userNo);
	}


	public List<User> getList(SearchInfo searchInfo) {
		List<User> list = userRepository.findList(searchInfo);
		return list;
	}

	public Long checkUser(String userId) {
		return userRepository.checkUser(userId);
	}
	
}
