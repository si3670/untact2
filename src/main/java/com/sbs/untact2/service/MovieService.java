package com.sbs.untact2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbs.untact2.dao.MovieDao;
import com.sbs.untact2.dto.Movie;

@Service
public class MovieService {
	@Autowired
	private MovieDao movieDao;

	public List<Movie> getForPrintMovies(int boardId, String searchKeyword, String searchKeywordType) {
		return movieDao.getForPrintMovies(boardId, searchKeyword, searchKeywordType);
	}

}
