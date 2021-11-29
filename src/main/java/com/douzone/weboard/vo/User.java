package com.douzone.weboard.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
	private Long no;
	private String id;
	private String password;
	private String nickname;
	private String regDate;
	private String leaveDate;
	private String profile;
	
}
