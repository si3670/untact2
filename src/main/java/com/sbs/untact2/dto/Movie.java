package com.sbs.untact2.dto;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie extends EntityDto{
	private int id;
	private String regDate;
	private String updateDate;
	private int boardId;
	private int memberId;
	private String title;
	private String body;
	private int hit;
	
	private String extra__writer;
	private String extra__boardName;
	private String extra__thumbImg;
	

	public String getWriterThumbImgUrl() {
		return "/common/genFile/file/member/" + memberId + "/common/attachment/1";
	}
}
