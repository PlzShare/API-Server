package com.douzone.weboard.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.douzone.weboard.repository.UserRepository;
import com.douzone.weboard.vo.SearchInfo;
import com.douzone.weboard.vo.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	private final UserRepository userRepository;
	
	public boolean join(User user) {
		return userRepository.insert(user);
	}

	public User login(User user) {
		return userRepository.find(user);
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



}
