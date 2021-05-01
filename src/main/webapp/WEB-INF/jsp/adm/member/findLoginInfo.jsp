<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../part/mainLayoutHead.jspf"%>

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
<section class="section-Login flex items-center">
	<div class="container mx-auto flex items-center justify-center">
		<div class="max-w-md mx-auto">
			<div class="logo-bar flex justify-center mt-3 pb-3">
				<span class="text-2xl pt-2">Admin Find Id/PassWord</span>
			</div>
			<div class="m-7">
				<form action="doFindLoginId" method="post"
					onsubmit="FindLoginIdForm__checkAndSubmit(this); return false;">
					<input type="hidden" name="redirectUrl"
						value="${param.redirectUrl}" />
					<div class="mb-2">
						<span>아이디 찾기</span>
						<i aria-hidden="true" class="icon-required"></i>
					</div>
					<div class="mb-6">
						<input class="login-form w-full px-3 py-2" autofocus="autofocus"
							type="text" placeholder="name를 입력해주세요." name="name"
							maxlength="20" />
						<input class="login-form brt w-full px-3 py-2"
							autofocus="autofocus" type="email" placeholder="email를 입력해주세요."
							name="email" maxlength="20" />
					</div>

					<div class="mb-6">
						<button
							class="w-full px-2 py-1 text-white bg-red-400 focus:bg-red-600 focus:outline-none"
							type="sumit">아이디 찾기</button>
					</div>
				</form>

			</div>
			</form>
		</div>
	</div>
	</div>
</section>

<script>
	const FindLoginPwForm__checkAndSubmitDone = false;
	function FindLoginPwForm__checkAndSubmit(form) {
		if (FindLoginPwForm__checkAndSubmitDone) {
			return;
		}

		form.loginId.value = form.loginId.value.trim();

		if (form.FindLoginPwFormId.value.length == 0) {
			alert('id 입력해주세요');
			form.loginId.focus();
			return;
		}

		if (form.email.value.length == 0) {
			alert('email 입력해주세요');
			form.email.focus();
			return;
		}
		form.submit();
		FindLoginPwForm__checkAndSubmitDone = true;
	}
</script>

<section
	class="section-Login flex items-center bg-white dark:bg-gray-900">

	<div class="container mx-auto flex items-center justify-center">
		<div class="max-w-md mx-auto">

			<div class="m-7">
				<form action="doFindLoginPw" method="post"
					onsubmit="FindLoginPwForm__checkAndSubmit(this); return false;">
					<input type="hidden" name="redirectUri" value="../member/login" />

					<div class="mb-2">
						<span>비밀번호 찾기</span>
						<i aria-hidden="true" class="icon-required"></i>
					</div>

					<div class="mb-6">
						<input class="login-form w-full px-3 py-2" autofocus="autofocus"
							type="text" placeholder="id를 입력해주세요." name="loginId"
							maxlength="20" />
						<input class="login-form brt w-full px-3 py-2"
							autofocus="autofocus" type="email" placeholder="email를 입력해주세요."
							name="email" maxlength="20" />
					</div>

					<div class="mb-6">
						<button
							class="w-full px-2 py-1 text-white bg-red-400 focus:bg-red-600 focus:outline-none"
							type="sumit">비밀번호 찾기</button>
					</div>

					<a onclick="history.back();"
						class="focus:bg-yellow-700 focus:outline-none text-xs cursor-pointer">뒤로가기</a>
				</form>
			</div>
		</div>
	</div>
</section>







<%@ include file="../part/mainLayoutFoot.jspf"%>
