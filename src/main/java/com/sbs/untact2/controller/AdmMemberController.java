package com.sbs.untact2.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sbs.untact2.dto.GenFile;
import com.sbs.untact2.dto.Member;
import com.sbs.untact2.dto.ResultData;
import com.sbs.untact2.service.GenFileService;
import com.sbs.untact2.service.MemberService;
import com.sbs.untact2.util.Util;

@Controller
public class AdmMemberController extends BaseController {
	@Autowired
	private MemberService memberService;
	@Autowired
	private GenFileService genFileService;
	

	@GetMapping("/adm/member/getLoginIdDup")
	@ResponseBody
	public ResultData getLoginIdDup(String loginId) {
		if (loginId == null) {
			return new ResultData("F-5", "loginId를 입력해주세요.");
		}

		if (Util.allNumberString(loginId)) {
			return new ResultData("F-3", "로그인아이디는 숫자만으로 구성될 수 없습니다.");
		}

		if (Util.startsWithNumberString(loginId)) {
			return new ResultData("F-4", "로그인아이디는 숫자로 시작할 수 없습니다.");
		}

		if (loginId.length() < 5) {
			return new ResultData("F-5", "로그인아이디는 5자 이상으로 입력해주세요.");
		}

		if (loginId.length() > 20) {
			return new ResultData("F-6", "로그인아이디는 20자 이하로 입력해주세요.");
		}

		if (Util.isStandardLoginIdString(loginId) == false) {
			return new ResultData("F-1", "로그인아이디는 영문소문자와 숫자의 조합으로 구성되어야 합니다.");
		}

		Member existingMember = memberService.getMemberByLoginId(loginId);

		if (existingMember != null) {
			return new ResultData("F-2", String.format("%s(은)는 이미 사용중인 로그인아이디 입니다.", loginId));
		}

		return new ResultData("S-1", String.format("%s(은)는 사용가능한 로그인아이디 입니다.", loginId), "loginId", loginId);
	}
	
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

	@RequestMapping("/adm/member/modify")
	public String showModify(Integer id, HttpServletRequest req) {
		if (id == null) {
			return msgAndBack(req, "id를 입력해주세요.");
		}
		Member member = memberService.getForPrintMember(id);
		
		List<GenFile> files = genFileService.getGenFiles("member", member.getId(), "common", "attachment");

		Map<String, GenFile> filesMap = new HashMap<>();

		for (GenFile file : files) {
			filesMap.put(file.getFileNo() + "", file);
		}

		member.getExtraNotNull().put("file__common__attachment", filesMap);
		
		req.setAttribute("member", member);

		if (member == null) {
			return msgAndBack(req, "존재하지 않는 회원번호입니다.");
		}

		return "adm/member/modify";
	}

	@RequestMapping("/adm/member/doModify")
	@ResponseBody
	public String doModify(@RequestParam Map<String, Object> param, HttpServletRequest req) {
		int loginedMemberId = (int) req.getAttribute("loginedMemberId");
		param.put("id", loginedMemberId);

		ResultData modifyMemberRd = memberService.modifyMember(param);
		String redirectUrl = "/adm/member/list";
		return Util.msgAndReplace(modifyMemberRd.getMsg(), redirectUrl);
	}

	@RequestMapping("/adm/member/list")
	public String showList(HttpServletRequest req, @RequestParam(defaultValue = "1") int boardId,
			String searchKeywordType, String searchKeyword, @RequestParam(defaultValue = "1") int page,
			@RequestParam Map<String, Object> param) {
		if (searchKeywordType != null) {
			searchKeywordType = searchKeywordType.trim();
		}

		if (searchKeywordType == null || searchKeywordType.length() == 0) {
			searchKeywordType = "name";
		}

		if (searchKeyword != null && searchKeyword.length() == 0) {
			searchKeyword = null;
		}

		if (searchKeyword != null) {
			searchKeyword = searchKeyword.trim();
		}

		if (searchKeyword == null) {
			searchKeywordType = null;
		}

		int itemsInAPage = 20;

		List<Member> members = memberService.getForPrintMembers(searchKeywordType, searchKeyword, page, itemsInAPage,
				param);

		req.setAttribute("members", members);

		return "adm/member/list";
	}
	
	//아이디, 비번 찾기
	@RequestMapping("/adm/member/findLoginInfo")
	public String showFindLoginInfo() {
		return "adm/member/findLoginInfo";
	}
	
	@RequestMapping("/adm/member/doFindLoginId")
	public String doFindLoginId(String name, String email, HttpServletRequest req, String redirectUrl) {
		Member member = memberService.getMemberByNameAndEmail(name, email);
		
		if(member == null) {
			return msgAndBack(req, "존재하지 않는 회원입니다.");
		}
		
		String msg = String.format("아이디는 %s 입니다.", member.getLoginId());

		redirectUrl = Util.ifEmpty(redirectUrl, "../member/login");

		return Util.msgAndReplace(msg, redirectUrl);
	}
	
	
	
	
	
	
	
	
	
}
