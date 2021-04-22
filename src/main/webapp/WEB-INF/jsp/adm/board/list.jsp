<%@ page import="com.sbs.untact2.util.Util"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="../part/mainLayoutHead.jspf"%>

<!-- 
?board제목 누르면 해당 게시물 리스트 보여주기
 -->

<script>
	param.boardId = parseInt("${board.id}");
</script>

<section class="section-1">
	<div class="bg-white shadow-md rounded container mx-auto p-8 mt-8">
		<div class="flex items-center">
			<div class="ml-1">총 게시물 수 : ${totalItemsCount}</div>
			<div class="flex-grow"></div>

			<a href="add?boardId=${board.id}"
				class="btn-primary bg-blue-500 hover:bg-blue-dark text-white font-bold py-1 px-4 rounded">게시판
				추가</a>
		</div>

		<form class="flex mt-3">
			<select name="searchKeywordType">
				<option value="codeAndName">전체</option>
				<option value="code">코드</option>
				<option value="name">이름</option>
			</select>
			<script>
				if (param.searchKeywordType) {
					$('.section-1 select[name="searchKeywordType"]').val(
							param.searchKeywordType);
				}
			</script>
			<input
				class="ml-3 shadow appearance-none border rounded w-full py-2 px-3 text-grey-darker"
				name="searchKeyword" type="text" placeholder="검색어를 입력해주세요."
				value="${param.searchKeyword}" />
			<input
				class="ml-3 btn-primary bg-blue-500 hover:bg-blue-dark text-white font-bold py-2 px-4 rounded"
				type="submit" value="검색" />
		</form>
		<div class="boards">
			<c:forEach items="${boards}" var="board">
				<c:set var="detailUrl" value="detail?id=${board.id}" />
				<div class="flex items-center mt-10">
					<a href="${detailUrl}" class="font-bold">No.${board.id}</a>
					<a href="${detailUrl}" class="ml-2 font-light text-gray-600">${board.regDate}</a>
					<div class="flex-grow"></div>

				</div>
				<div class="mt-2">
					<a href="../article/list?boardId=${article.boardId}"
						class="text-2xl text-gray-700 font-bold hover:underline">${board.code}</a>
					<a href="${detailUrl}" class="mt-2 text-gray-600 block">${board.name}</a>
				</div>
				<div class="flex items-center mt-4">
					<a href="detail?id=${board.id}"
						class="text-blue-500 hover:underline">자세히 보기</a>
					<a href="modify?id=${board.id}"
						class="ml-2 text-blue-500 hover:underline">수정</a>
					<a onclick="if(!confirm('삭제하시겠습니까?'))return false;"
						href="doDelete?id=${board.id}"
						class="ml-2 text-blue-500 hover:underline">삭제</a>
					<div class="flex-grow"></div>

				</div>
			</c:forEach>
		</div>
		
		<nav class="flex justify-center rounded-md mt-3"
			aria-label="Pagination">
			<c:if test="${pageMenuStart != 1}">
				<a href="${Util.getNewUrl(requestUrl, 'page', 1)}"
					class="relative inline-flex items-center px-2 py-2 rounded-l-md border border-gray-300 bg-white text-sm font-medium text-gray-500 hover:bg-gray-50">
					<span class="sr-only">Previous</span>

					<i class="fas fa-chevron-left"></i>
				</a>
			</c:if>

			<c:forEach var="i" begin="${pageMenuStart}" end="${pageMenuEnd}">
				<c:set var="aClassStr"
					value="relative inline-flex items-center px-4 py-2 border border-gray-300 bg-white text-sm font-medium" />
				<c:if test="${i == page}">
					<c:set var="aClassStr"
						value="${aClassStr} text-red-700 hover:bg-red-50" />
				</c:if>
				<c:if test="${i != page}">
					<c:set var="aClassStr"
						value="${aClassStr} text-gray-700 hover:bg-gray-50" />
				</c:if>
				<a href="${Util.getNewUrl(requestUrl, 'page', i)}"
					class="${aClassStr}">${i}</a>
			</c:forEach>

			<c:if test="${pageMenuEnd != totalPage}">
				<a href="${Util.getNewUrl(requestUrl, 'page', totalPage)}"
					class="relative inline-flex items-center px-2 py-2 rounded-r-md border border-gray-300 bg-white text-sm font-medium text-gray-500 hover:bg-gray-50">

					<span class="sr-only">Next</span>

					<i class="fas fa-chevron-right"></i>
				</a>
			</c:if>
		</nav>
	</div>
</section>

<%@ include file="../part/mainLayoutFoot.jspf"%>
