package com.sbs.untact2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbs.untact2.dao.ReplyDao;
import com.sbs.untact2.dto.Member;
import com.sbs.untact2.dto.Reply;
import com.sbs.untact2.dto.ResultData;

@Service
public class ReplyService {
	@Autowired
	private ReplyDao replyDao;
	@Autowired
	private MemberService memberService;
	
	public List<Reply> getForPrintReplise(String relTypeCode, int relId) {
		return replyDao.getForPrintReplise(relTypeCode, relId);
	}

	public Reply getReply(int id) {
		return replyDao.getReply(id);
	}

	public ResultData getActorCanDeleteRd(Reply reply, Member actor) {
		if (reply.getMemberId() == actor.getId()) {
			return new ResultData("P-1", "가능합니다.");
		}
		if (memberService.isAdmin(actor)) {
			return new ResultData("P-2", "가능합니다.");
		}
		return new ResultData("F-1", "권한이 없습니다.");
	}

	public ResultData deleteReply(int relId) {
		replyDao.deleteReply(relId);

		return new ResultData("P-1", "삭제 성공", "relId", relId);
	}

	public ResultData getActorCanModifyRd(Reply reply, Member actor) {
		return getActorCanDeleteRd(reply, actor);
	}

	public ResultData modifyReply(int relId, String body) {
		replyDao.modifyReply(relId, body);

		return new ResultData("P-1", "성공", "relId", relId);
	}

}
