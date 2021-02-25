package com.sbs.untact2.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sbs.untact2.dto.Reply;

@Mapper
public interface ReplyDao {

	List<Reply> getForPrintReplise(@Param("relTypeCode")String relTypeCode, @Param("relId")int relId);

	Reply getReply(@Param("relId")int relId);

	void deleteReply(@Param("relId")int relId);

	void modifyReply(@Param("relId")int relId, @Param("body")String body);

}
