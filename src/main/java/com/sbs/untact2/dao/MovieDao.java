package com.sbs.untact2.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sbs.untact2.dto.Movie;

@Mapper
public interface MovieDao {

	List<Movie> getForPrintMovies(@Param("boardId") int boardId, @Param("searchKeyword") String searchKeyword,
			@Param("searchKeywordType") String searchKeywordType);

}
