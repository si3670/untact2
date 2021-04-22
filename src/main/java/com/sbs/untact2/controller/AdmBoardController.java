package com.sbs.untact2.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartRequest;

import com.sbs.untact2.dto.Article;
import com.sbs.untact2.dto.Board;
import com.sbs.untact2.dto.GenFile;
import com.sbs.untact2.dto.Member;
import com.sbs.untact2.dto.ResultData;
import com.sbs.untact2.service.BoardService;
import com.sbs.untact2.service.MemberService;
import com.sbs.untact2.util.Util;

@Controller
public class AdmBoardController extends BaseController {
	@Autowired
	private BoardService boardService;

	@RequestMapping("/adm/board/list")
	public String showList(HttpServletRequest req, String searchKeywordType, String searchKeyword,
			@RequestParam(defaultValue = "1") int page, @RequestParam Map<String, Object> param) {
		if (searchKeywordType != null) {
			searchKeywordType = searchKeywordType.trim();
		}

		if (searchKeywordType == null || searchKeywordType.length() == 0) {
			searchKeywordType = "codeAndName";
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

		int itemsInAPage = 5;

		int totalItemsCount = boardService.getBoardsTotalCount(searchKeywordType, searchKeyword);

		int totalPage = (int) Math.ceil(totalItemsCount / (double) itemsInAPage);
		int pageMenuArmSize = 1;
		int pageMenuStart = page - pageMenuArmSize;

		if (pageMenuStart < 1) {
			pageMenuStart = 1;
		}

		int pageMenuEnd = page + pageMenuArmSize;
		if (pageMenuEnd > totalPage) {
			pageMenuEnd = totalPage;
		}

		List<Board> boards = boardService.getForPrintBoards(searchKeywordType, searchKeyword, page,
				itemsInAPage);
		req.setAttribute("totalItemsCount", totalItemsCount);
		req.setAttribute("boards", boards);
		req.setAttribute("page", page);
		req.setAttribute("totalPage", totalPage);
		req.setAttribute("pageMenuArmSize", pageMenuArmSize);
		req.setAttribute("pageMenuStart", pageMenuStart);
		req.setAttribute("pageMenuEnd", pageMenuEnd);

		return "adm/board/list";
	}

	@RequestMapping("/adm/board/add")
	public String showAdd(@RequestParam Map<String, Object> param, HttpServletRequest req) {
		return "adm/board/add";
	}

	@RequestMapping("/adm/board/doAdd")
	public String doAdd(@RequestParam Map<String, Object> param, HttpServletRequest req,
			MultipartRequest multipartRequest, String code) {
		int loginedMemberId = (int) req.getAttribute("loginedMemberId");

		if (param.get("code") == null) {
			return msgAndBack(req, "code을 입력해주세요.");
		}
		if (param.get("name") == null) {
			return msgAndBack(req, "name을 입력해주세요.");
		}
		
		Board existingBoard = boardService.getBoardByCode(code);

		if (existingBoard != null) {
			return msgAndBack(req, "이미 사용 중인 code입니다.");
		}

		param.put("memberId", loginedMemberId);
		ResultData addBoardRd = boardService.addBoard(param);

		int newBoardId = (int) addBoardRd.getBody().get("id");

		return msgAndReplace(req, String.format("%d번 게시물이 작성되었습니다.", newBoardId), "../board/list?id=" + newBoardId);
	}

	@RequestMapping("/adm/board/doDelete")
	@ResponseBody
	public ResultData doDelete(int id, HttpServletRequest req) {
		Member loginedMember = (Member) req.getAttribute("loginedMember");

		Board board = boardService.getBoard(id);
		if (board == null) {
			return new ResultData("F-1", "해당 게시물이 존재하지 않습니다.");
		}

		return boardService.deleteBoard(id);
	}
	
	@RequestMapping("/adm/board/modify")
	public String showModify(Integer id, HttpServletRequest req) {
		if (id == null) {
			return msgAndBack(req, "id를 입력해주세요.");
		}

		Board board = boardService.getBoard(id);

		req.setAttribute("board", board);

		if (board == null) {
			return msgAndBack(req, "존재하지 않는 게시물입니다.");
		}

		return "adm/board/modify";
	}

	@RequestMapping("/adm/board/doModify")
	@ResponseBody
	public ResultData doModify(@RequestParam Map<String, Object> param, HttpServletRequest req) {
		Member loginedMember = (Member) req.getAttribute("loginedMember");

		int id = Util.getAsInt(param.get("id"), 0);

		if (id == 0) {
			return new ResultData("F-1", "id를 입력해주세요.");
		}
		if (Util.isEmpty(param.get("code"))) {
			return new ResultData("F-1", "code을 입력해주세요.");
		}
		if (Util.isEmpty(param.get("name"))) {
			return new ResultData("F-1", "name를 입력해주세요.");
		}

		Board board = boardService.getBoard(id);
		
		if (board == null) {
			return new ResultData("F-1", "해당 게시물이 존재하지 않습니다.");
		}
		
		return boardService.modifyboard(param);
	}

}
