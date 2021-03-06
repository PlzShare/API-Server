package com.douzone.weboard.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.weboard.vo.Noti;
import com.douzone.weboard.vo.NotiUser;

@Repository
public class NotiRepository {
	@Autowired
	private SqlSession sqlSession;
	
	// 최초 발생 알림 (최초 발생 하나만)
	public boolean insertNoti(Noti noti) {
		int count = sqlSession.insert("noti.insertnoti", noti);
		return count == 1;
	}
	
	
	// 유저 별 알림 (중복 : 여러개)
	public boolean insertNotiUser(NotiUser notiUser) {
		int count  = sqlSession.insert("noti.insertnotiuser", notiUser);
		return count == 1;
	}
	
	// 개인 유저 별 삭제
	public boolean deleteNoti(Long nno) {
		int count = sqlSession.delete("noti.deleteNoti", nno);
		return count == 1;
	}
	
	public boolean delete(NotiUser notiUser) {
		int count = sqlSession.delete("noti.delete", notiUser);
		return count == 1;
	}
	
	// 분류별 알림
	public List<NotiUser> findinviteNoti(Long uno){
		return sqlSession.selectList("notiuser.findinviteNoti", uno);
	}
	
	public Long findOneNoti(Long nno){
		return sqlSession.selectOne("notiuser.findOneNoti", nno);
	}
	
	public List<NotiUser> findTypeNoti(Map map){
		return sqlSession.selectList("notiuser.findTypeNoti", map);
	}
	
	
}
