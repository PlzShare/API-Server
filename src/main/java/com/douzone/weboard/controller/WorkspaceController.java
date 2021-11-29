package com.douzone.weboard.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.douzone.weboard.service.WorkspaceService;
import com.douzone.weboard.util.ApiResult;
import com.douzone.weboard.vo.Workspace;

@RestController // responsebody 다 붙어진 효과
@RequestMapping("/workspace")
public class WorkspaceController {
	
	@Autowired
	private WorkspaceService workspaceService;

	@PutMapping("")
	public ResponseEntity<ApiResult> main(){

		Long userno = 1L;	
		List<Workspace> list = new ArrayList<>();
		list = workspaceService.findAll(userno);
		return new ResponseEntity<ApiResult>(HttpStatus.OK); // 리턴 여러개로 정상동작 / 오류동작으로 분기

	}
}