package com.sbs.untact2.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbs.untact2.dao.ArticleDao;
import com.sbs.untact2.dto.Article;
import com.sbs.untact2.dto.Board;
import com.sbs.untact2.dto.ResultData;
import com.sbs.untact2.util.Util;

@Service
public class ArticleService {
	@Autowired
	private ArticleDao articleDao;
	@Autowired
	private MemberService memberService;
	@Autowired
	private GenFileService genFileService;

	public Article getArticle(int id) {
		return articleDao.getArticle(id);
	}

	public ResultData addArticle(Map<String, Object> param) {
		articleDao.addArticle(param);
		int id = Util.getAsInt(param.get("id"), 0);
		return new ResultData("P-1", "성공", "id", id);
	}

	public ResultData deleteArticle(int id) {
		articleDao.deleteArticle(id);
		
		genFileService.deleteFiles("article", id);
		
		return new ResultData("P-1", "삭제 성공", "id", id);
	}

	public ResultData modifyArticle(int id, String title, String body) {
		articleDao.modifyArticle(id, title, body);

		return new ResultData("P-1", "성공", "id", id);
	}

	public List<Article> getArticles(String searchKeyword, String searchKeywordType) {
		return articleDao.getArticles(searchKeyword, searchKeywordType);
	}

	public ResultData getActorCanModifyRd(Article article, int actorId) {
		if (article.getMemberId() == actorId) {
			return new ResultData("P-1", "가능합니다.");
		}
		if (memberService.isAdmin(actorId)) {
			return new ResultData("P-2", "가능합니다.");
		}
		return new ResultData("F-1", "권한이 없습니다.");
	}

	public ResultData getActorCanDeleteRd(Article article, int actorId) {
		return getActorCanModifyRd(article, actorId);
	}

	public Article getForPrintArticle(int id) {
		return articleDao.getForPrintArticle(id);
	}

	public List<Article> getForPrintArticles(int boardId, String searchKeyword, String searchKeywordType, int page,
			int itemsInAPage) {
		int limitStart = (page - 1) * itemsInAPage;
		int limitTake = itemsInAPage;

		return articleDao.getForPrintArticles(boardId, searchKeyword, searchKeywordType, limitStart, limitTake);
	}

	public Board getBoard(int id) {
		return articleDao.getBoard(id);
	}

	public ResultData doAddReply(Map<String, Object> param) {
		articleDao.doAddReply(param);
		int id = Util.getAsInt(param.get("id"), 0);
		return new ResultData("P-1", "성공", "id", id);
	}

	public ResultData getActorCanModifyReplyRd(Article article, int actorId) {
		if (article.getMemberId() == actorId) {
			return new ResultData("P-1", "가능합니다.");
		}
		if (memberService.isAdmin(actorId)) {
			return new ResultData("P-2", "가능합니다.");
		}
		return new ResultData("F-1", "권한이 없습니다.");
	}


}
