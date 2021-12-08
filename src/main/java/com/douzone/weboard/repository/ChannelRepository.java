package com.douzone.weboard.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.weboard.vo.Channel;

//@AllArgsConstructor
//위 아래중 하나만 써주고 final해주면 @Autowired sql안해줘도된다.
@Repository
public class ChannelRepository {
	@Autowired
	private SqlSession sqlSession;
	
	public List<Channel> findAll(Long wno){
		return sqlSession.selectList("channel.findAll", wno);
	}
	
	public boolean insert(Channel channel) {
		int count = sqlSession.insert("channel.insert", channel);
		return count == 1;
	}
	
	public boolean delete(Channel channel) {
		int count = sqlSession.delete("channel.delete", channel);
		return count == 1;
	}
	
	public int update(Channel channel) {
		return sqlSession.update("channel.update", channel);
	}
}
