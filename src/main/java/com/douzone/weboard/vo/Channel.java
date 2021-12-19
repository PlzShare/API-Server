package com.douzone.weboard.vo;

import java.util.List;

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
@ToString
public class Channel {
	private Long no;
	private String name;
	private String desc;
	private String createdAt;
	private Long workspaceNo;
	
	private List<Long> userNums;
	private String nickname;
	private Long makeUser;
}
