package com.douzone.weboard.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.douzone.weboard.service.DocumentService;
import com.douzone.weboard.util.ApiResult;
import com.douzone.weboard.vo.Document;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/documents")
public class DocumentController {
	private final DocumentService documentService;
	
	@GetMapping("")
	public ResponseEntity<ApiResult> getList(){
		Document d1 = Document.builder().no(1L).title("doc1").contents("test").build();
		Document d2 = Document.builder().no(2L).title("doc2").contents("test2").build();
		List<Document> list = List.of(d1, d2);		
//		list = documentService.getList();
		
		return new ResponseEntity<ApiResult>(ApiResult.success(list), HttpStatus.OK);
	}
	
	@GetMapping("/{no}")
	public ResponseEntity<ApiResult> get(@PathVariable("no") Long no){
//		Document doc = documentService.get(no);
		Document doc = Document.builder().no(no).title("doc1").contents("test1").build();
		return new ResponseEntity<ApiResult>(ApiResult.success(doc), HttpStatus.OK);
	}
	
	
//	@PostMapping("")
//	public ResponseEntity<ApiResult> insert(@RequestBody Document doc){	
//		Document doc = documentService.add(doc);
//		return new ResponseEntity<ApiResult>(documentService.add(doc)? ApiResult.success(doc) : ApiResult.fail("생성 실패") )
//	}
}
