package com.douzone.weboard.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter // 게터
@Setter // 세터
@NoArgsConstructor // 빈 생성자를 만들겠다.
@AllArgsConstructor // 전부 다 있는 생성자를 만들겠다.
//@RequiredArgsConstructor // final이 붙은 필요한 생성자만 만들겠다.
@Builder // 빌더 사용
@ToString // 투스트링 사용
public class Workspaces {
	private Long no;
	private String name;
	private String createdAt;
	private Long userNo;
	// 테스트중
	private List<Long> userNums;
}
