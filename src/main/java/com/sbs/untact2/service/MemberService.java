package com.sbs.untact2.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbs.untact2.dao.MemberDao;
import com.sbs.untact2.dto.Member;
import com.sbs.untact2.dto.ResultData;
import com.sbs.untact2.util.Util;

@Service
public class MemberService {
	@Autowired
	private MemberDao memberDao;
	
	public ResultData addMember(Map<String, Object> param) {
		memberDao.addMember(param);
		int id = Util.getAsInt(param.get("id"), 0);
		return new ResultData("P-1","가입 성공", "id", id);
	}
	
	public Member getMember(int id) {
		return memberDao.getMember(id);
	}
	public Member getMemberByLoginId(String loginId) {
		return memberDao.getMemberByLoginId(loginId);
	}

	public ResultData modifyMember(Map<String, Object> param) {
		memberDao.modifyMember(param);
		return new ResultData("P-1", "수정 성공");
	}

	public boolean isAdmin(int actorId) {
		return actorId == 1;
	}

	public boolean isAdmin(Member actor) {
		return isAdmin(actor.getId());
	}

	public Member getMemberByAuthKey(String authKey) {
		return memberDao.getMemberByAuthKey(authKey);
		
	}




}
