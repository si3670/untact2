package com.sbs.untact2.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartRequest;

import com.sbs.untact2.dto.Article;
import com.sbs.untact2.dto.Board;
import com.sbs.untact2.dto.GenFile;
import com.sbs.untact2.dto.Member;
import com.sbs.untact2.dto.Movie;
import com.sbs.untact2.dto.ResultData;
import com.sbs.untact2.service.ArticleService;
import com.sbs.untact2.service.GenFileService;
import com.sbs.untact2.service.MovieService;
import com.sbs.untact2.util.Util;

@Controller
public class AdmMovieController extends BaseController {
	@Autowired
	private ArticleService articleService;
	@Autowired
	private MovieService movieService;
	@Autowired
	private GenFileService genFileService;

	//영화카테고리 시작
	@RequestMapping("/adm/movie/list")
	public String showList(HttpServletRequest req, @RequestParam(defaultValue = "10") int boardId,
			@RequestParam(defaultValue = "1") int page, String searchKeyword,
			@RequestParam(defaultValue = "titleAndBody") String searchKeywordType) {
		
		Board board = articleService.getBoard(boardId);

		if (board == null) {
			return msgAndBack(req, "존재하지않는 게시판 입니다.");
		}

		if (searchKeywordType != null) {
			searchKeywordType = searchKeywordType.trim();
		}

		if (searchKeywordType == null || searchKeywordType.length() == 0) {
			searchKeywordType = "titleAndBody";
		}

		if (searchKeyword != null && searchKeyword.length() == 0) {
			searchKeyword = null;
		}

		if (searchKeyword != null) {
			searchKeyword = searchKeyword.trim();
		}

		if (searchKeyword == null) {
			searchKeywordType = null;
		}

		req.setAttribute("board", board);

		List<Movie> movies = movieService.getForPrintMovies(boardId, searchKeyword, searchKeywordType);

		req.setAttribute("movies", movies);

		return "adm/movie/list";
	}

}