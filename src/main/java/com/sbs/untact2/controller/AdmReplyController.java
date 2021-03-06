package com.sbs.untact2.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sbs.untact2.dto.Article;
import com.sbs.untact2.dto.Member;
import com.sbs.untact2.dto.Reply;
import com.sbs.untact2.dto.ResultData;
import com.sbs.untact2.service.ArticleService;
import com.sbs.untact2.service.ReplyService;
import com.sbs.untact2.util.Util;

@Controller
public class AdmReplyController {
	@Autowired
	private ReplyService replyService;
	@Autowired
	private ArticleService articleService;

	@RequestMapping("/adm/reply/list")
	@ResponseBody
	public ResultData showlist(String relTypeCode, int relId) {
		if (relTypeCode == null) {
			return new ResultData("F-1", "게시판 번호를 입력해주세요.");
		}
		if (relId == 0) {
			return new ResultData("F-1", "게시판 번호를 입력해주세요.");
		}

		if (relTypeCode.equals("article")) {
			Article article = articleService.getArticle(relId);
			if (article == null) {
				return new ResultData("F-1", "존재하지 않는 게시물 입니다.");
			}
		}

		List<Reply> replise = replyService.getForPrintReplise(relTypeCode, relId);
		return new ResultData("P-1", "성공", "replise", replise);
	}
	
	@RequestMapping("/adm/reply/doDelete")
	@ResponseBody
	public ResultData doDelete(int relId, HttpServletRequest req) {
		Member loginedMember = (Member)req.getAttribute("loginedMember");
		
		Reply reply = replyService.getReply(relId);
		if(reply == null) {
			return new ResultData("F-1", "해당 댓글이 존재하지 않습니다.");
		}
		
		ResultData actorCanDeleteRd = replyService.getActorCanDeleteRd(reply, loginedMember);
		if(actorCanDeleteRd.isFail()) {
			return actorCanDeleteRd;
		}
		
		return replyService.deleteReply(relId);
	}
	
	@RequestMapping("/adm/reply/doModify")
	@ResponseBody
	public ResultData doModify(int relId, String body, HttpServletRequest req) {
		Member loginedMember = (Member)req.getAttribute("loginedMember");
		
		Reply reply = replyService.getReply(relId);
		if(reply == null) {
			return new ResultData("F-1", "해당 댓글이 존재하지 않습니다.");
		}
		ResultData actorCanModifyRd = replyService.getActorCanModifyRd(reply, loginedMember);
		if(actorCanModifyRd.isFail()) {
			return actorCanModifyRd;
		}
		
		return replyService.modifyReply(relId, body);
	}

}