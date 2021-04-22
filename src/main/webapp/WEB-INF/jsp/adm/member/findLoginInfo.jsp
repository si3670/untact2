<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../part/head.jspf"%>

<script>
	const FindLoginIdForm__checkAndSubmitDone = false;
	function FindLoginIdForm__checkAndSubmit(form) {
		if (FindLoginIdForm__checkAndSubmitDone) {
			return;
		}

		form.name.value = form.name.value.trim();

		if (form.FindLoginIdFormId.value.length == 0) {
			alert('name 입력해주세요');
			form.name.focus();
			return;
		}

		if (form.email.value.length == 0) {
			alert('email 입력해주세요');
			form.email.focus();
			return;
		}
		form.submit();
		FindLoginIdForm__checkAndSubmitDone = true;
	}
</script>
<section
	class="section-Login flex items-center min-h-screen bg-white dark:bg-gray-900">
	<div
		class="container mx-auto min-h-screen flex items-center justify-center">
		<div class="max-w-md mx-auto my-10">
			<div class="logo-bar flex justify-center mt-3 mb-3">
				<a href="#" class="logo">
					<span class="text-2xl">Cellar find Id/PassWord</span>
				</a>
			</div>
			<div class="m-7">
				<form
					class="bg-white shadow-md rounded w-max px-10 pt-6 pb-8 mb-4 flex flex-col"
					action="doFindLoginId" method="post"
					onsubmit="FindLoginIdForm__checkAndSubmit(this); return false;">
					<input type="hidden" name="redirectUrl"
						value="${param.redirectUrl}" />

					<h2 class="con">아이디 찾기</h2>

					<div class="mb-6">
						<label class="block mb-2 text-sm text-gray-600 dark:text-gray-400">이름</label>
						<input
							class="w-full px-3 py-2 placeholder-gray-300 border border-gray-300 rounded-md "
							autofocus="autofocus" type="text" placeholder="name를 입력해주세요."
							name="name" maxlength="20" />
					</div>
					<div class="mb-6">
						<label class="block mb-2 text-sm text-gray-600 dark:text-gray-400">email</label>
						<input
							class="w-full px-3 py-2 placeholder-gray-300 border border-gray-300 rounded-md "
							autofocus="autofocus" type="email" placeholder="email를 입력해주세요."
							name="email" maxlength="20" />
					</div>
					<div class="mb-6">
						<button
							class="w-full px-2 py-1 text-white bg-indigo-500 rounded-md focus:bg-indigo-600 focus:outline-none"
							type="sumit">아이디 찾기</button>
					</div>

					<hr />

					<h2 class="con">비밀번호 찾기</h2>
					
					<a onclick="history.back();"
							class="btn-info bg-green-500 hover:bg-blue-dark text-white font-bold py-2 px-2 rounded inline-block">뒤로가기</a>
				</form>
			</div>
		</div>

	</div>
</section>
<%@ include file="../part/foot.jspf"%>
