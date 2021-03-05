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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.sbs.untact2.dto.Article;
import com.sbs.untact2.dto.Board;
import com.sbs.untact2.dto.GenFile;
import com.sbs.untact2.dto.ResultData;
import com.sbs.untact2.service.ArticleService;
import com.sbs.untact2.service.GenFileService;
import com.sbs.untact2.util.Util;

@Controller
public class AdmArticleController extends BaseController{
	@Autowired
	private ArticleService articleService;
	@Autowired
	private GenFileService genFileService;

	@RequestMapping("/adm/article/detail")
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

	@RequestMapping("/adm/article/list")
	public String showlist(HttpServletRequest req,@RequestParam(defaultValue = "1") int boardId, String searchKeyword, String searchKeywordType, @RequestParam(defaultValue = "1") int page) {
		Board board = articleService.getBoard(boardId);
		req.setAttribute("board", board);
		if ( board == null ) {
			return msgAndBack(req, "존재하지 않는 게시판 입니다.");
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
		
		for(Article article : articles) {
			GenFile genFile = genFileService.getGenFile("article", article.getId(), "common", "attachment", 1);
			
			if(genFile != null) {
				article.setExtra__thumbImg(genFile.getForPrintUrl());
			}
			
			
		}
		
		req.setAttribute("articles", articles);
		return "adm/article/list";
	}
	
	@RequestMapping("/adm/article/add")
	public String showAdd(@RequestParam Map<String, Object> param, HttpServletRequest req) {
		return "adm/article/add";
	}

	@RequestMapping("/adm/article/doAdd")
	public String doAdd(@RequestParam Map<String, Object> param, HttpServletRequest req,
			MultipartRequest multipartRequest) {		
		int loginedMemberId = (int)req.getAttribute("loginedMemberId");
		
		if(param.get("title") == null) {
			return msgAndBack(req, "title을 입력해주세요.");
		}
		if(param.get("body") == null) {
			return msgAndBack(req, "body을 입력해주세요.");
		}
	
		param.put("memberId", loginedMemberId);
		ResultData addArticleRd = articleService.addArticle(param);
		
		int newArticleId = (int)addArticleRd.getBody().get("id");
		
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (String fileInputName : fileMap.keySet()) {
			MultipartFile multipartFile = fileMap.get(fileInputName);
			
			
			if(multipartFile.isEmpty() == false) {
				genFileService.save(multipartFile, newArticleId);
			}
			

		}

		return msgAndReplace(req, "게시물 작성 완료", "../article/detail?id=" + newArticleId);
	}

	@RequestMapping("/adm/article/doDelete")
	@ResponseBody
	public ResultData doDelete(int id, HttpServletRequest req) {
		int loginedMemberId = (int)req.getAttribute("loginedMemberId");
		
		Article article = articleService.getArticle(id);
		if(article == null) {
			return new ResultData("F-1", "해당 게시물이 존재하지 않습니다.");
		}
		
		ResultData actorCanDeleteRd = articleService.getActorCanDeleteRd(article, loginedMemberId);
		if(actorCanDeleteRd.isFail()) {
			return actorCanDeleteRd;
		}
		
		return articleService.deleteArticle(id);
	}
	
	@RequestMapping("/adm/article/modify")
	public String showModify(Integer id, HttpServletRequest req) {
		if(id == null) {
			return msgAndBack(req, "id를 입력해주세요.");
		}
		Article article = articleService.getForPrintArticle(id);
		req.setAttribute("article", article);
		
		if(article == null) {
			return msgAndBack(req, "존재하지 않는 게시물입니다.");
		}
		
		return "adm/article/modify";
	}
	
	@RequestMapping("/adm/article/doModify")
	@ResponseBody
	public ResultData doModify(int id, String title, String body, HttpSession session) {
		int loginedMemberId = Util.getAsInt(session.getAttribute("loginedMemberId"), 0);

		Article article = articleService.getArticle(id);
		if(article == null) {
			return new ResultData("F-1", "해당 게시물이 존재하지 않습니다.");
		}
		ResultData actorCanModifyRd = articleService.getActorCanModifyRd(article, loginedMemberId);
		if(actorCanModifyRd.isFail()) {
			return actorCanModifyRd;
		}
		
		return articleService.modifyArticle(id, title, body);
	}
	
	@RequestMapping("/adm/article/doAddReply")
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