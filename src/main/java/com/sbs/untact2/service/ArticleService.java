package com.sbs.untact2.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbs.untact2.dao.ArticleDao;
import com.sbs.untact2.dto.Article;
import com.sbs.untact2.dto.Board;
import com.sbs.untact2.dto.Member;
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
		String genFileIdsStr = Util.ifEmpty((String)param.get("genFileIdsStr"), null);

		if ( genFileIdsStr != null ) {
			List<Integer> genFileIds = Util.getListDividedBy(genFileIdsStr, ",");

			// 파일이 먼저 생성된 후에, 관련 데이터가 생성되는 경우에는, file의 relId가 일단 0으로 저장된다.
			// 그것을 뒤늦게라도 이렇게 고처야 한다.
			for (int genFileId : genFileIds) {
				genFileService.changeRelId(genFileId, id);
			}
		}
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

	public ResultData getActorCanModifyRd(Article article, Member actor) {
		if (article.getMemberId() == actor.getId()) {
			return new ResultData("P-1", "가능합니다.");
		}
		if (memberService.isAdmin(actor)) {
			return new ResultData("P-2", "가능합니다.");
		}
		return new ResultData("F-1", "권한이 없습니다.");
	}

	public ResultData getActorCanDeleteRd(Article article, Member actor) {
		return getActorCanModifyRd(article, actor);
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

	public ResultData getActorCanModifyReplyRd(Article article, Member actor) {
		if (article.getMemberId() == actor.getId()) {
			return new ResultData("P-1", "가능합니다.");
		}
		if (memberService.isAdmin(actor)) {
			return new ResultData("P-2", "가능합니다.");
		}
		return new ResultData("F-1", "권한이 없습니다.");
	}


}
