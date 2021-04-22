package com.sbs.untact2.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sbs.untact2.dto.Board;

@Mapper
public interface BoardDao {

	List<Board> getForPrintBoards(@Param("searchKeywordType") String searchKeywordType,
			@Param("searchKeyword") String searchKeyword, @Param("limitStart") int limitStart,
			@Param("limitTake") int limitTake);

	int getBoardsTotalCount(@Param("searchKeywordType") String searchKeywordType,
			@Param("searchKeyword") String searchKeyword);

	void addBoard(Map<String, Object> param);

	Board getBoard(@Param("id") int id);

	void deleteBoard(@Param("id") int id);

	void modifyBoard(Map<String, Object> param);

	Board getBoardByCode(@Param("code")String code);

}
