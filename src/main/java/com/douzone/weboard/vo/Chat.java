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
@ToString
public class Chat {
	private Long no;
	private Long userNo;
	private Long chatroomNo;
	private String userName;
	private String contents;
	private String createdAt;
}
