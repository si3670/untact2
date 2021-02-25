package com.sbs.untact2.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sbs.untact2.dto.Article;
import com.sbs.untact2.dto.Board;
//데이터 저장 수정 삭제 등
@Mapper
public interface ArticleDao {
	public Article getArticle(@Param(value = "id")int id);
	public List<Article> getArticles(@Param(value = "searchKeywordType")String searchKeywordType, @Param(value = "searchKeyword")String searchKeyword);
//select가 아닌건 void
	public void addArticle(Map<String, Object> param);
	public void deleteArticle(@Param(value = "id")int id);
	public void modifyArticle(@Param(value = "id")int id, @Param(value = "title")String title, @Param(value = "body")String body);
	public Article getForPrintArticle(@Param(value = "id")int id);
	public List<Article> getForPrintArticles(@Param("boardId") int boardId, @Param("searchKeywordType")String searchKeywordType, @Param("searchKeyword")String searchKeyword,  @Param("limitStart") int limitStart,
			@Param("limitTake") int limitTake);
	public Board getBoard(@Param(value = "id")int id);
	public void doAddReply(Map<String, Object> param);
}
