package com.sbs.untact2.dto;

import java.util.Map;

import com.sbs.untact2.util.Util;

import lombok.Data;

@Data
public class ResultData {
	private String resultCode;
	private String msg;
	private Map<String, Object> body;
	
	public ResultData(String resultCode, String msg, Object... args) {
		this.resultCode = resultCode;
		this.msg = msg;
		this.body = Util.mapOf(args);
	}
	public boolean isSuccess() {
		return resultCode.startsWith("P-");
	}
	public boolean isFail() {
		return resultCode.startsWith("F-");
	}
}
