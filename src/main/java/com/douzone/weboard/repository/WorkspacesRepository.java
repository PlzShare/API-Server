package com.douzone.weboard.repository;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.weboard.vo.Workspaces;

@Repository
public class WorkspacesRepository {
	
	@Autowired
	private SqlSession sqlSession;
	
	public List<Workspaces> findAll(Long userNo) {
		return sqlSession.selectList("workspace.findAll", userNo);
	}

	public boolean insert(Workspaces workspace) {
		int count = sqlSession.insert("workspace.insert", workspace);
		return count == 1;
	}	
	
	public Long findInsertFirst(Workspaces workspace) {
		return sqlSession.selectOne("workspace.findInsertOne", workspace);
	}

	public boolean update(Workspaces workspace) {
		int count = sqlSession.update("workspace.update", workspace);
		return count == 1;
	}
	
	public boolean delete(Long no) {
		int count = sqlSession.delete("workspace.delete", no);
		return count == 1;
	}
	
	public List<Workspaces> search(HashMap<String, String> map) {
		return sqlSession.selectList("workspace.search", map);
	}
	
	public boolean invite(Long userNo) {
		int count = sqlSession.update("workspace.invite", userNo);
		return count == 1;
	}

}
