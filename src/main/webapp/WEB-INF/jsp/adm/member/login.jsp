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
<section
	class="section-Login flex items-center min-h-screen bg-white dark:bg-gray-900">
	<div
		class="container mx-auto min-h-screen flex items-center justify-center">
		<div class="max-w-md mx-auto my-10">
			<div class="logo-bar flex justify-center mt-3 mb-3">
				<a href="#" class="logo">
					<span>Cellar Admin</span>
				</a>
			</div>
			<div class="m-7">
				<form
					class="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4 flex flex-col"
					action="doLogin" method="post"
					onsubmit="LoginForm__checkAndSubmit(this); return false;">
					<input type="hidden" name="redirectUrl"
						value="${param.redirectUrl}" />
					<div class="mb-6">
						<label class="block mb-2 text-sm text-gray-600 dark:text-gray-400">Id</label>
						<input
							class="w-full px-3 py-2 placeholder-gray-300 border border-gray-300 rounded-md "
							autofocus="autofocus" type="text" placeholder="id를 입력해주세요."
							name="loginId" maxlength="20" />
					</div>
					<div class="mb-6">
							<label class="block mb-2 text-sm text-gray-600 dark:text-gray-400">Password</label>
							<input class="w-full px-3 py-2 placeholder-gray-300 border border-gray-300 rounded-md "
								autofocus="autofocus" type="password" placeholder="password를 입력해주세요."
								name="loginPw" maxlength="20" />
						</div>
					<div class="mb-6">
						<button
							class="w-full px-2 py-1 text-white bg-indigo-500 rounded-md focus:bg-indigo-600 focus:outline-none"
							type="sumit">login</button>
					</div>
						<p class="text-sm text-center text-gray-400">
						Don't have an account yet?	
						<a href="/adm/member/join"
							class="text-indigo-400 focus:outline-none focus:underline focus:text-indigo-500 dark:focus:border-indigo-800"
							type="sumit">SignUp</a>
					</p>
					<p class="text-sm text-center text-gray-400">
						Don't have an account yet?	
						<a href="/adm/member/findLoginInfo"
							class="text-indigo-400 focus:outline-none focus:underline focus:text-indigo-500 dark:focus:border-indigo-800"
							type="sumit">find Id/Password</a>
					</p>
				</form>
			</div>
		</div>

	</div>
</section>
<%@ include file="../part/foot.jspf"%>
