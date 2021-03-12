package com.sbs.untact2.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sbs.untact2.dto.Member;
import com.sbs.untact2.dto.ResultData;
import com.sbs.untact2.service.MemberService;

@Controller
public class UsrMemberController {
	@Autowired
	private MemberService memberService;
	
	@PostMapping("/usr/member/doJoin")
	@ResponseBody
	public ResultData doJoin(@RequestParam Map<String, Object> param) {
		if(param.get("loginId") == null) {
			return new ResultData("F-1", "loginId을 입력해주세요.");
		}
		Member originMember = memberService.getMemberByLoginId((String) param.get("loginId"));
		
		if(param.get("loginPw") == null) {
			return new ResultData("F-1", "loginPw을 입력해주세요.");
		}
		if(param.get("name") == null) {
			return new ResultData("F-1", "name을 입력해주세요.");
		}
		if(param.get("nickname") == null) {
			return new ResultData("F-1", "nickName을 입력해주세요.");
		}
		if(param.get("cellphoneNo") == null) {
			return new ResultData("F-1", "cellphoneNo을 입력해주세요.");
		}
		if(param.get("email") == null) {
			return new ResultData("F-1", "email을 입력해주세요.");
		}
		return memberService.addMember(param);
	}
	
	@PostMapping("/usr/member/doLogin")
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
		
		session.setAttribute("loginedMemberId", originMember.getId());
		
		return new ResultData("P-1", String.format("%s님 환영", originMember.getName()));
	}
	
	@PostMapping("/usr/member/doLogout")
	@ResponseBody
	public ResultData doLogout(HttpSession session) {
		session.removeAttribute("loginedMemberId");
		
		return new ResultData("P-1", "로그아웃 성공");
	}
	
	@PostMapping("/usr/member/doModify")
	@ResponseBody
	public ResultData doModify(@RequestParam Map<String, Object> param, HttpSession session) {
		if(param.isEmpty()) {
			return new ResultData("F-2", "수정할 사항을 입력해주세요");
		}
		
		int loginedMemberId = (int)session.getAttribute("loginedMemberId");
		param.put("id", loginedMemberId);
		return memberService.modifyMember(param);
	}
	
	@GetMapping("/usr/member/authKey")
	@ResponseBody
	public ResultData showAuthKey(String loginId, String loginPw) {
		
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
		
		return new ResultData("P-1", String.format("%s님 환영", originMember.getName()), "authKey", originMember.getAuthKey(), "id", originMember.getId(), "name", originMember.getName(),"nickName", originMember.getNickname());
	}
	
	@GetMapping("/usr/member/memberByAuthKey")
	@ResponseBody
	public ResultData showByAuthKey(String authKey) {
		
		if(authKey == null) {
			return new ResultData("F-1", "authKey를 입력해주세요.");
		}
		
		Member originMember = memberService.getMemberByAuthKey(authKey);
		if(originMember == null) {
			return new ResultData("F-1", "유효하지 않는 authKey 입니다.");
		}
		
		return new ResultData("P-1", String.format("유효한 회원입니다."), "member : ", originMember);
	}
}
