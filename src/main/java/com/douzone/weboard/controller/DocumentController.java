package com.douzone.weboard.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.douzone.weboard.annotation.AuthUser;
import com.douzone.weboard.service.DocumentService;
import com.douzone.weboard.service.WorkspaceUsersService;
import com.douzone.weboard.util.ApiResult;
import com.douzone.weboard.vo.Document;
import com.douzone.weboard.vo.NotiType;
import com.douzone.weboard.vo.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/workspaces/{wsNo}/channels/{chNo}/documents")
public class DocumentController {
	private final DocumentService documentService;
	private final WorkspaceUsersService workspaceUsersService;
	
	@GetMapping("")
	public ResponseEntity<ApiResult> getList(@PathVariable("chNo") Long channelNo, @RequestBody Optional<Document> document){
		Document doc = document.orElse(new Document());
		doc.setChannelNo(channelNo);
		return new ResponseEntity<ApiResult>(ApiResult.success(documentService.findAll(doc)), HttpStatus.OK);
	}
	
	@GetMapping("/{docNo}")
	public ResponseEntity<ApiResult> get(@PathVariable("docNo") Long documentNo){
		Optional<Document> doc = documentService.find(documentNo);
		
		if(doc.isPresent()) return new ResponseEntity<ApiResult>(ApiResult.success(doc), HttpStatus.OK);

		return new ResponseEntity<ApiResult>(ApiResult.fail("문서를 찾지 못했습니다"), HttpStatus.NOT_FOUND);
	}
	
	
	@PostMapping("")
	public ResponseEntity<ApiResult> insert(@RequestBody Document doc, @AuthUser User authUser, @PathVariable("wsNo") Long wno){	
		doc.setUserNo(authUser.getNo());
		doc.setNickname(authUser.getNickname());
		boolean result = documentService.add(doc);	
		
		// noti서버 연결 (문서오류나면 지울 부분)
		List<Long> memberNoList = workspaceUsersService.getUserList(wno);
		List<Long> notifyList = new ArrayList<Long>();
		for (int i=0;i < memberNoList.size();i++) {
			if( authUser.getNo() != memberNoList.get(i)) {
				notifyList.add(memberNoList.get(i));
			}
		}
		
		String message = authUser.getNickname() + "님이 [ " + doc.getTitle() + " ] 문서를 추가하셨습니다.";
		
		NotiType notiType = new NotiType();
		notiType.setMemberList(notifyList);
		notiType.setContent(message);
		notiType.setNickname(authUser.getNickname());
		notiType.setType("doc");
//	
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity requestEntity = new HttpEntity(notiType);
		restTemplate.exchange("http://localhost:8888/noti", HttpMethod.POST, requestEntity, String.class);
		
		ResponseEntity<ApiResult> response = result? new ResponseEntity<>(ApiResult.success(true), HttpStatus.OK)
													: new ResponseEntity<>(ApiResult.fail("삽입 실패"), HttpStatus.BAD_REQUEST);
		
		return response;
	}
	
	@PutMapping("/{docNo}")
	public ResponseEntity<ApiResult> update(@RequestBody Document doc, @PathVariable Long docNo, @PathVariable("chNo") Long channerNo){
		doc.setChannelNo(channerNo);
		doc.setNo(docNo);
		boolean result = documentService.update(doc);
		
		ResponseEntity<ApiResult> response = result? new ResponseEntity<>(ApiResult.success(true), HttpStatus.OK)
				: new ResponseEntity<>(ApiResult.fail("수정 실패"), HttpStatus.BAD_REQUEST);
		return response;
	}
	
	@DeleteMapping("/{docNo}")
	public ResponseEntity<ApiResult> update(@PathVariable("docNo") Long docNo, @AuthUser User authUser){
		Document doc = Document.builder().no(docNo).userNo(authUser.getNo()).build();
		boolean result = documentService.delete(doc);
		
		ResponseEntity<ApiResult> response = result? new ResponseEntity<>(ApiResult.success(true), HttpStatus.OK)
				: new ResponseEntity<>(ApiResult.fail("삭제 실패"), HttpStatus.BAD_REQUEST);
		return response;
	}
	
	
}
