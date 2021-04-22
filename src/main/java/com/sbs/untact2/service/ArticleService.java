package com.sbs.untact2.service;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbs.untact2.dao.ArticleDao;
import com.sbs.untact2.dto.Article;
import com.sbs.untact2.dto.Board;
import com.sbs.untact2.dto.GenFile;
import com.sbs.untact2.dto.Member;
import com.sbs.untact2.dto.Movie;
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
		genFileService.changeInputFileRelIds(param, id);
		return new ResultData("P-1", "성공", "id", id);
	}

	public ResultData deleteArticle(int id) {
		articleDao.deleteArticle(id);
		
		genFileService.deleteGenFiles("article", id);
		
		return new ResultData("P-1", "삭제 성공", "id", id);
	}

	public ResultData modifyArticle(Map<String, Object> param) {
		articleDao.modifyArticle(param);

		int id = Util.getAsInt(param.get("id"), 0);

//		changeInputFileRelIds(param, id);

		return new ResultData("P-1", "성공", "id", id);
	}
			
	public List<Article> getArticles(String searchKeyword, String searchKeywordType) {
		return articleDao.getArticles(searchKeyword, searchKeywordType);
	}

	public ResultData getActorCanModifyRd(Article article, Member actor) {
		if (article.getMemberId() == actor.getId()) {
			return new ResultData("S-1", "가능합니다.");
		}

		if (memberService.isAdmin(actor)) {
			return new ResultData("S-2", "가능합니다.");
		}

		return new ResultData("F-1", "권한이 없습니다.");
	}

	public ResultData getActorCanDeleteRd(Article article, Member actor) {
		return getActorCanModifyRd(article, actor);
	}

	public Article getForPrintArticle(int id) {
		return articleDao.getForPrintArticle(id);
	}

	public List<Article> getForPrintArticles(int boardId, String searchKeywordType, String searchKeyword, int page,
			int itemsInAPage) {
		
		int limitStart = (page - 1) * itemsInAPage;
		int limitTake = itemsInAPage;

		List<Article> articles = articleDao.getForPrintArticles(boardId, searchKeywordType, searchKeyword, limitStart, limitTake);
		List<Integer> articleIds = articles.stream().map(article -> article.getId()).collect(Collectors.toList());
		Map<Integer, Map<String, GenFile>> filesMap = genFileService.getFilesMapKeyRelIdAndFileNo("article", articleIds, "common", "attachment");

		for (Article article : articles) {
			Map<String, GenFile> mapByFileNo = filesMap.get(article.getId());

			if (mapByFileNo != null) {
				article.getExtraNotNull().put("file__common__attachment", mapByFileNo);
			}
		}

		
		return articles;
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

	public int getArticlesTotalCount(int boardId, String searchKeyword, String searchKeywordType) {
		return articleDao.getArticlesTotalCount(boardId, searchKeyword, searchKeywordType);
	}

	public void increaseArticleHit(Integer id) {
		articleDao.increaseArticleHit(id);
	}


}
