package com.sbs.untact2.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbs.untact2.dao.BoardDao;
import com.sbs.untact2.dto.Article;
import com.sbs.untact2.dto.Board;
import com.sbs.untact2.dto.Member;
import com.sbs.untact2.dto.ResultData;
import com.sbs.untact2.util.Util;

@Service
public class BoardService {
	@Autowired
	private BoardDao boardDao;

	public List<Board> getForPrintBoards(String searchKeywordType, String searchKeyword, int page, int itemsInAPage
			) {
		int limitStart = (page - 1) * itemsInAPage;
		int limitTake = itemsInAPage;
		return boardDao.getForPrintBoards(searchKeyword, searchKeywordType, limitStart, limitTake);
	}

	public int getBoardsTotalCount(String searchKeywordType, String searchKeyword) {
		return boardDao.getBoardsTotalCount(searchKeyword, searchKeywordType);
	}

	public ResultData addBoard(Map<String, Object> param) {
		boardDao.addBoard(param);
		int id = Util.getAsInt(param.get("id"), 0);
		return new ResultData("P-1", "성공", "id", id);
	}

	public Board getBoard(int id) {
		return boardDao.getBoard(id);
	}

	public ResultData deleteBoard(int id) {
		boardDao.deleteBoard(id);

		return new ResultData("P-1", "삭제 성공", "id", id);
	}


	public ResultData modifyboard(Map<String, Object> param) {
		boardDao.modifyBoard(param);

		int id = Util.getAsInt(param.get("id"), 0);

		return new ResultData("P-1", "성공", "id", id);
	}

	public Board getBoardByCode(String code) {
		return boardDao.getBoardByCode(code);
	}





}
