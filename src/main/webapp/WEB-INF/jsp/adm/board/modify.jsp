<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.sbs.untact2.util.Util"%>

<%@ include file="../part/mainLayoutHead.jspf"%>

<c:set var="fileInputMaxCount" value="2" />
<script>
	const boardId = parseInt("${board.id}");
</script>

<section class="section-1">
	<div class="bg-white shadow-md rounded container mx-auto mt-8">
		<div class="card-title">
			<span>게시판 수정</span>
		</div>
		<form class="p-8 " action="doModify" method="POST" enctype="multipart/form-data">
			<input type="hidden" name="id" value="${board.id}" />
			<div class="form-row flex flex-col lg:flex-row mt-4">
				<div class="lg:flex lg:items-center lg:w-28">
					<span>코드</span>
				</div>
				<div class="lg:flex-grow">
					<input value="${board.code}" type="text" name="code"
						autofocus="autofocus" class="form-row-input w-full rounded-sm"
						placeholder="코드를 입력해주세요." />
				</div>
			</div>
			<div class="form-row flex flex-col lg:flex-row">
				<div class="lg:flex lg:items-center lg:w-28">
					<span>이름</span>
				</div>
				<div class="lg:flex-grow">
					<textarea name="name" class="form-row-input w-full rounded-sm"
						placeholder="이름을 입력해주세요.">${board.name}</textarea>
				</div>
			</div>
			
			<div class="form-row flex flex-col lg:flex-row">
				<div class="lg:flex-grow">
					<div class="btns">
						<input type="submit"
							class="btn btn-accent btn-sm mb-1 text-white"
							value="수정">
						<input onclick="history.back();" type="button"
							class="btn btn-sm mb-1"
							value="취소">
					</div>
				</div>
			</div>
		</form>
	</div>
</section>

<%@ include file="../part/mainLayoutFoot.jspf"%>
