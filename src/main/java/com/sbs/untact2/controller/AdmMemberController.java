package com.sbs.untact2.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sbs.untact2.dto.Article;
import com.sbs.untact2.dto.Member;
import com.sbs.untact2.dto.ResultData;
import com.sbs.untact2.service.MemberService;

@Controller
public class AdmMemberController {
	@Autowired
	private MemberService memberService;
	
	@RequestMapping("/adm/member/login")
	public String login() {
		return "adm/member/login";
	}
	
	
	@RequestMapping("/adm/member/doLogin")
	@ResponseBody
	public ResultData doLogin(String loginId, String loginPw, HttpSession session) {
		
		if(loginId == null) {
			return new ResultData("F-1", "loginId을 입력해주세요.");
		}
		Member originMember = memberService.getMemberByLoginId(loginId);
		if(originMember == null) {
			return new ResultData("F-2", "존재하지 않는 아이디입니다.");
		}
		
		if(loginPw == null) {
			return new ResultData("F-1", "loginPw을 입력해주세요.");
		}
		if(originMember.getLoginPw().equals(loginPw) == false) {
			return new ResultData("F-3", "비밀번호를 확인해주세요");
		}
		
		if(memberService.isAdmin(originMember)==false) {
			return new ResultData("F-3", "관리자만 이용 가능합니다.");
		}
		
		session.setAttribute("loginedMemberId", originMember.getId());
		
		return new ResultData("P-1", String.format("%s님 환영", originMember.getName()));
	}
	
	@RequestMapping("/adm/member/doModify")
	@ResponseBody
	public ResultData doModify(@RequestParam Map<String, Object> param, HttpSession session) {
		if(param.isEmpty()) {
			return new ResultData("F-2", "수정할 사항을 입력해주세요");
		}
		
		int loginedMemberId = (int)session.getAttribute("loginedMemberId");
		param.put("id", loginedMemberId);
		return memberService.modifyMember(param);
	}
}
