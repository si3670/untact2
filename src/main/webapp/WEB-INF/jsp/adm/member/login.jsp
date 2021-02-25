<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../part/head.jspf"%>
<script>
	const LoginForm__checkAndSubmitDone = false;
	function LoginForm__checkAndSubmit(form) {
		if (LoginForm__checkAndSubmitDone) {
			return;
		}

		form.loginId.value = form.loginId.value.trim();

		if (form.loginId.value.length == 0) {
			alert('아이디를 입력해주세요');
			form.loginId.focus();
			return;
		}

		if (form.loginPw.value.length == 0) {
			alert('비밀번호를 입력해주세요');
			form.loginPw.focus();
			return;
		}
		form.submit();
		LoginForm__checkAndSubmitDone = true;
	}
</script>
<section class="section-Login">
	<div class="container mx-auto min-h-screen flex items-center justify-center">
		<form class="bg-white w-full shadow-md rounded px-8 pt-6 pb-8 mb-4 flex flex-col" action="doLogin" method="post"
			onsubmit="LoginForm__checkAndSubmit(this); return false;">
			<div class="flex flex-col md:flex-row pb-3">
				<div class="p-1 block text-grey-darker font-bold md:w-36 md:flex md:items-center"  >
					<span>아이디</span>
				</div>
				<div class="p-1 md:flex-grow">
					<input class="shadow appearance-none border rounded w-full py-2 px-3 text-grey-darker md:flex-row" autofocus="autofocus" type="text"
						placeholder="아이디를 입력해주세요" name="loginId" maxlength="20" />
				</div>
			</div>
			<div class="flex flex-col md:flex-row pb-3">
				<div class="p-1 block text-grey-darker font-bold md:w-36 md:flex md:items-center">
					<span>비밀번호</span>
				</div>
				<div class="p-1 md:flex-grow">
					<input class="shadow appearance-none border rounded w-full py-2 px-3 text-grey-darker" autofocus="autofocus" type="password"
						placeholder="비밀번호를 입력해주세요" name="loginPw" maxlength="20" />
				</div>
			</div>
			<div class="flex items-center justify-between">
				<button class="bg-blue-500 hover:bg-blue-dark text-white font-bold py-2 px-4 rounded" type="sumit">로그인</button>
			</div>
		</form>
	</div>
</section>
<%@ include file="../part/foot.jspf"%>
