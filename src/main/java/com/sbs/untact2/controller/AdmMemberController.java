package com.sbs.untact2.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sbs.untact2.dto.Member;
import com.sbs.untact2.dto.ResultData;
import com.sbs.untact2.service.MemberService;
import com.sbs.untact2.util.Util;

@Controller
public class AdmMemberController {
	@Autowired
	private MemberService memberService;

	@RequestMapping("/adm/member/login")
	public String showLogin() {
		return "adm/member/login";
	}

	@RequestMapping("/adm/member/join")
	public String showJoin() {
		return "adm/member/join";
	}

	@RequestMapping("/adm/member/doJoin")
	@ResponseBody
	public String doJoin(@RequestParam Map<String, Object> param) {
		if (param.get("loginId") == null) {
			return Util.msgAndBack("loginId을 입력해주세요.");
		}
		Member originMember = memberService.getMemberByLoginId((String) param.get("loginId"));
		if (originMember != null) {
			return Util.msgAndBack("이미 사용중인 아이디입니다.");
		}

		if (param.get("loginPw") == null) {
			return Util.msgAndBack("loginPw을 입력해주세요.");
		}
		if (param.get("name") == null) {
			return Util.msgAndBack("name을 입력해주세요.");
		}
		if (param.get("nickname") == null) {
			return Util.msgAndBack("nickname을 입력해주세요.");
		}
		if (param.get("cellphoneNo") == null) {
			return Util.msgAndBack("cellphoneNo을 입력해주세요.");
		}
		if (param.get("email") == null) {
			return Util.msgAndBack("email을 입력해주세요.");
		}
		memberService.addMember(param);

		String msg = String.format("%s님 환영합니다.", param.get("nickname"));

		String redirectUrl = Util.ifEmpty((String) param.get("redirectUrl"), "../member/login");

		return Util.msgAndReplace(msg, redirectUrl);
	}

	@RequestMapping("/adm/member/doLogin")
	@ResponseBody
	public String doLogin(String loginId, String loginPw, String redirectUrl, HttpSession session) {

		if (loginId == null) {
			return Util.msgAndBack("loginId을 입력해주세요.");
		}
		Member originMember = memberService.getMemberByLoginId(loginId);
		if (originMember == null) {
			return Util.msgAndBack("존재하지 않는 아이디입니다.");
		}

		if (loginPw == null) {
			return Util.msgAndBack("loginPw을 입력해주세요.");
		}
		if (originMember.getLoginPw().equals(loginPw) == false) {
			return Util.msgAndBack("비밀번호를 확인해주세요");
		}

		if (memberService.isAdmin(originMember) == false) {
			return Util.msgAndBack("관리자만 이용 가능합니다.");
		}
		session.setAttribute("loginedMemberId", originMember.getId());

		String msg = String.format("%s님 환영합니다.", originMember.getName());

		redirectUrl = Util.ifEmpty(redirectUrl, "../home/main");

		return Util.msgAndReplace(msg, redirectUrl);
	}

	@RequestMapping("/adm/member/doLogout")
	@ResponseBody
	public String doLogout(HttpSession session) {
		session.removeAttribute("loginedMemberId");

		return Util.msgAndReplace("로그아웃 되었습니다.", "../member/login");
	}

	@RequestMapping("/adm/member/doModify")
	@ResponseBody
	public ResultData doModify(@RequestParam Map<String, Object> param, HttpSession session) {
		if (param.isEmpty()) {
			return new ResultData("F-2", "수정할 사항을 입력해주세요");
		}

		int loginedMemberId = (int) session.getAttribute("loginedMemberId");
		param.put("id", loginedMemberId);
		return memberService.modifyMember(param);
	}
	
	
	@RequestMapping("/adm/member/list")
	public String list() {
		return "adm/member/list";
	}

	@RequestMapping("/adm/member/showList")
	@ResponseBody
	public String showList(HttpServletRequest req, String loginId) {
		List<Member> members = memberService.getForPrintMembers(loginId);
		req.setAttribute("members", members);
		
		return "adm/article/list";
		
	}
}
