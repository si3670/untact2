package com.sbs.untact2.dao;

import java.util.List;
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

	public Member getMemberByAuthKey(@Param("authKey")String authKey);

	List<Member> getForPrintMembers(Map<String, Object> param);

	public Member getForPrintMember(@Param(value = "id")int id);

	public Member getMemberByNameAndEmail(@Param(value = "name")String name, @Param(value = "email")String email);



}
