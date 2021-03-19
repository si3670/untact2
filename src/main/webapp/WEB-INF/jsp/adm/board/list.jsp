<%@ page import="com.sbs.untact2.util.Util"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="../part/mainLayoutHead.jspf"%>

<script>
	param.boardId = parseInt("${board.id}");
</script>

<section class="section-1">
	<div class="bg-white shadow-md rounded container mx-auto p-8 mt-8">
		<div class="flex items-center">
			<select class="py-2 select-board-id">
				<option value="1">공지사항</option>
				<option value="2">자유게시판</option>
			</select>
			<script>
				$('.section-1 .select-board-id').val(param.boardId);
				$('.section-1 .select-board-id').change(function() {
					location.href = '?boardId=' + this.value;
				});
			</script>

			<div class="flex-grow"></div>

			<a href="add?boardId=${board.id}"
				class="btn-primary bg-blue-500 hover:bg-blue-dark text-white font-bold py-1 px-4 rounded">게시판
				추가</a>
		</div>
		<div class="ml-1">총 게시물 수 : ${totalItemsCount}</div>
		<div class="articles">
			<c:forEach items="${boards}" var="board">
				<c:set var="detailUrl" value="detail?id=${article.id}" />
				<div class="flex items-center mt-10">
					<a href="${detailUrl}" class="font-bold">No.${board.id}</a>
					<a href="${detailUrl}" class="ml-2 font-light text-gray-600">${board.regDate}</a>
					<div class="flex-grow"></div>

				</div>
				<div class="mt-2">
					<a href="${detailUrl}"
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
		<nav class="flex justify-center rounded-md shadow-sm mt-3"
			aria-label="Pagination">
			<c:if test="${pageMenuStart != 1 }">
				<a href="${Util.getNewUrl(requestUrl, 'page', 1)}"
					class="relative inline-flex items-center px-2 py-2 rounded-l-md border border-gray-300 bg-white text-sm font-medium text-gray-500 hover:bg-gray-50">
					<a href="?page=1"
						class="relative inline-flex items-center px-2 py-2 rounded-l-md border border-gray-300 bg-white text-sm font-medium text-gray-500 hover:bg-gray-50">
						<span class="sr-only">Previous</span>

						<i class="fas fa-chevron-left"></i>
						<path fill-rule="evenodd"
							d="M12.707 5.293a1 1 0 010 1.414L9.414 10l3.293 3.293a1 1 0 01-1.414 1.414l-4-4a1 1 0 010-1.414l4-4a1 1 0 011.414 0z"
							clip-rule="evenodd" />
						</svg>
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
					<path fill-rule="evenodd"
						d="M7.293 14.707a1 1 0 010-1.414L10.586 10 7.293 6.707a1 1 0 011.414-1.414l4 4a1 1 0 010 1.414l-4 4a1 1 0 01-1.414 0z"
						clip-rule="evenodd" />
					</svg>
				</a>
			</c:if>
		</nav>
	</div>
</section>

<%@ include file="../part/mainLayoutFoot.jspf"%>
