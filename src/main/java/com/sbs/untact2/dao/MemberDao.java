package com.sbs.untact2.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.Mapping;

import com.sbs.untact2.dto.Member;

@Mapper
public interface MemberDao {

	public void addMember(Map<String, Object> param);

	public Member getMemberByLoginId(@Param(value = "loginId")String loginId);

	public void modifyMember(Map<String, Object> param);

	public Member getMember(@Param(value = "id")int id);


}
