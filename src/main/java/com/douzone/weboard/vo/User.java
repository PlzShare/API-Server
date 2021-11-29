package com.douzone.weboard.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class User {
	private Long no;
	private String id;
	private String password;
	private String nickname;
	private String regDate;
	private String leaveDate;
	private String profile;
	
}
