package com.douzone.weboard.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
<<<<<<< HEAD
@NoArgsConstructor
@AllArgsConstructor
=======
@ToString
>>>>>>> d2c1315e23a22bbea1bac386b438304793e9c26b
public class User {
	private Long no;
	private String id;
	private String password;
	private String nickname;
	private String regDate;
	private String leaveDate;
	private String profile;
	
}
