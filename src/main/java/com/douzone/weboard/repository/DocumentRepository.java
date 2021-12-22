package com.douzone.weboard.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.douzone.weboard.vo.Document;

@Repository
@Mapper
public interface DocumentRepository {
	public boolean insert(Document document);
	public boolean update(Document document);
	public boolean delete(Long no);
	public Optional<Document> find(Long no);
	public List<Document> findAll(Document document);
	public boolean validate(Document doc);
}