package com.sbs.untact2.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sbs.untact2.dto.Article;
import com.sbs.untact2.dto.Board;
import com.sbs.untact2.dto.Member;
import com.sbs.untact2.dto.ResultData;
import com.sbs.untact2.service.ArticleService;
import com.sbs.untact2.util.Util;

@Controller
public class UsrArticleController {
	@Autowired
	private ArticleService articleService;

	@GetMapping("/usr/article/detail")
	@ResponseBody
	public ResultData showdetail(Integer id) {
		if(id == null) {
			return new ResultData("F-1", "id를 입력해주세요.");
		}
		Article article = articleService.getForPrintArticle(id);
		if(article == null) {
			return new ResultData("F-1", "존재하지 않는 게시물입니다.");
		}
		return new ResultData("P-1", "성공", "article", article);
	}

	@GetMapping("/usr/article/list")
	@ResponseBody
	public ResultData showlist(@RequestParam(defaultValue = "1") int boardId, String searchKeyword, String searchKeywordType, @RequestParam(defaultValue = "1") int page) {
		Board board = articleService.getBoard(boardId);		
		if ( board == null ) {
			return new ResultData("F-1", "존재하지 않는 게시판 입니다.");
		}
		
		if(searchKeywordType == null || searchKeywordType.length() == 0) {
			searchKeywordType = "titleAndbody";
		}
		if(searchKeywordType != null) {
			searchKeywordType = searchKeywordType.trim();
		}
		if(searchKeyword != null && searchKeyword.length() == 0) {
			searchKeyword = null;
		}
		if(searchKeyword != null) {
			searchKeyword = searchKeyword.trim();
		}
		
		int itemsInAPage = 10;
		List<Article> articles = articleService.getForPrintArticles(boardId, searchKeyword, searchKeywordType, page, itemsInAPage);
		return new ResultData("P-1", "성공", "articles", articles);
	}

	@PostMapping("/usr/article/doAdd")
	@ResponseBody
	public ResultData doAdd(@RequestParam Map<String, Object> param, HttpSession session) {
		int loginedMemberId = Util.getAsInt(session.getAttribute("loginedMemberId"), 0);
		
		if(param.get("title") == null) {
			return new ResultData("F-1", "title을 입력해주세요.");
		}
		if(param.get("body") == null) {
			return new ResultData("F-1", "body을 입력해주세요.");
		}
		
		param.put("memberId", loginedMemberId);
		
		return articleService.addArticle(param);
	}

	@PostMapping("/usr/article/doDelete")
	@ResponseBody
	public ResultData doDelete(int id, HttpServletRequest req) {
		Member loginedMember = (Member)req.getAttribute("loginedMember");
		
		Article article = articleService.getArticle(id);
		if(article == null) {
			return new ResultData("F-1", "해당 게시물이 존재하지 않습니다.");
		}
		
		ResultData actorCanDeleteRd = articleService.getActorCanDeleteRd(article, loginedMember);
		if(actorCanDeleteRd.isFail()) {
			return actorCanDeleteRd;
		}
		
		return articleService.deleteArticle(id);
	}

	@PostMapping("/usr/article/doModify")
	@ResponseBody
	public ResultData doModify(int id, String title, String body, HttpServletRequest req) {
		Member loginedMember = (Member)req.getAttribute("loginedMember");

		Article article = articleService.getArticle(id);
		if(article == null) {
			return new ResultData("F-1", "해당 게시물이 존재하지 않습니다.");
		}
		ResultData actorCanModifyRd = articleService.getActorCanModifyRd(article, loginedMember);
		if(actorCanModifyRd.isFail()) {
			return actorCanModifyRd;
		}
		
		return articleService.modifyArticle(id, title, body);
	}
	
	@PostMapping("/usr/article/doAddReply")
	@ResponseBody
	public ResultData doAddReply(@RequestParam Map<String, Object> param, HttpSession session) {
		int loginedMemberId = Util.getAsInt(session.getAttribute("loginedMemberId"), 0);

		if(param.get("body") == null) {
			return new ResultData("F-1", "body을 입력해주세요.");
		}
		if(param.get("articleId") == null) {
			return new ResultData("F-1", "articleId를 입력해주세요.");
		}
		
		param.put("memberId", loginedMemberId);
		
		return articleService.doAddReply(param);
	}


}