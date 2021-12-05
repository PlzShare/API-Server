package com.douzone.weboard.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.douzone.weboard.repository.DocumentRepository;
import com.douzone.weboard.vo.Document;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocumentService {
	private final DocumentRepository documentRepository;
	
	public boolean add(Document document) {
		return documentRepository.insert(document);
	}
	
	public boolean update(Document document) {
		return documentRepository.update(document);
	}
	
	public boolean delete(Long no) {
		return documentRepository.delete(no);
	}
	
	public Optional<Document> find(Long no) {
		return documentRepository.find(no);
	}
	
	public List<Document> findAll(Document document){
		return documentRepository.findAll(document);
	}
}
