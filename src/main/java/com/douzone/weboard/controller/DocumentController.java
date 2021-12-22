package com.douzone.weboard.controller;

import java.util.Optional;

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

import com.douzone.weboard.annotation.AuthUser;
import com.douzone.weboard.service.DocumentService;
import com.douzone.weboard.util.ApiResult;
import com.douzone.weboard.vo.Document;
import com.douzone.weboard.vo.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/workspaces/{wsNo}/channels/{chNo}/documents")
public class DocumentController {
	private final DocumentService documentService;
	
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
	public ResponseEntity<ApiResult> insert(@RequestBody Document doc, @AuthUser User authUser){	
		doc.setUserNo(authUser.getNo());
		doc.setNickname(authUser.getNickname());
		boolean result = documentService.add(doc);		
		
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
