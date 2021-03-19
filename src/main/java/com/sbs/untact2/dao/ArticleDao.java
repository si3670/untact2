package com.sbs.untact2.dao;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sbs.untact2.dto.Article;
import com.sbs.untact2.dto.Board;
@Mapper
public interface ArticleDao {
	Article getArticle(@Param("id") int id);
	void addArticle(Map<String, Object> param);
	void deleteArticle(@Param("id") int id);
	void modifyArticle(Map<String, Object> param);
	List<Article> getArticles(@Param("searchKeywordType") String searchKeywordType,
			@Param("searchKeyword") String searchKeyword);
	Article getForPrintArticle(@Param("id") int id);
	List<Article> getForPrintArticles(@Param("boardId") int boardId,
			@Param("searchKeywordType") String searchKeywordType, @Param("searchKeyword") String searchKeyword,
			@Param("limitStart") int limitStart, @Param("limitTake") int limitTake);
	Board getBoard(@Param("id") int id);

	void doAddReply(Map<String, Object> param);
	int getArticlesTotalCount(@Param("boardId") int boardId,
			@Param("searchKeywordType") String searchKeywordType, @Param("searchKeyword") String searchKeyword);
	void increaseArticleHit(@Param("id") int id);
	

}