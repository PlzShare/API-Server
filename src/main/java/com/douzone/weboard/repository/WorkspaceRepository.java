package com.douzone.weboard.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.weboard.vo.Workspace;

@Repository
public class WorkspaceRepository {
	
	@Autowired
	private SqlSession sqlSession;
	
	public List<Workspace> findAll(Long userNo) {
		return sqlSession.selectList("workspace.findAll", userNo);
	}

}
