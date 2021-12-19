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
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Document {
	private Long no;
	private String title;
	private String contents;
	private String createdAt;
	private String updatedAt;
	private Boolean isPinned;
	private Long userNo;
	private Long channelNo;
	
	// noti
	private List<Long> userNums;
	private String nickname;
	private Long makeUser;
	private Long workspaceNo;
}
