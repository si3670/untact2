<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../part/head.jspf"%>
<script>
	const JoinForm__checkAndSubmitDone = false;
	function JoinForm__checkAndSubmit(form) {
		if (JoinForm__checkAndSubmitDone) {
			return;
		}
		form.loginId.value = form.loginId.value.trim();
		if (form.loginId.value.length == 0) {
			alert('로그인아이디를 입력해주세요.');
			form.loginId.focus();
			return;
		}
		form.loginPw.value = form.loginPw.value.trim();
		if (form.loginPw.value.length == 0) {
			alert('로그인비번을 입력해주세요.');
			form.loginPw.focus();
			return;
		}
		if (form.loginPwConfirm.value.length == 0) {
			alert('로그인비번 확인을 입력해주세요.');
			form.loginPwConfirm.focus();
			return;
		}
		if (form.loginPw.value != form.loginPwConfirm.value) {
			alert('로그인비번이 일치하지 않습니다.');
			form.loginPwConfirm.focus();
			return;
		}
		form.name.value = form.name.value.trim();
		if (form.name.value.length == 0) {
			alert('이름을 입력해주세요.');
			form.name.focus();
			return;
		}
		form.nickname.value = form.nickname.value.trim();
		if (form.nickname.value.length == 0) {
			alert('별명을 입력해주세요.');
			form.nickname.focus();
			return;
		}
		form.email.value = form.email.value.trim();
		if (form.email.value.length == 0) {
			alert('이메일을 입력해주세요.');
			form.email.focus();
			return;
		}
		form.cellphoneNo.value = form.cellphoneNo.value.trim();
		if (form.cellphoneNo.value.length == 0) {
			alert('휴대전화번호를 입력해주세요.');
			form.cellphoneNo.focus();
			return;
		}
		form.submit();
		JoinForm__checkAndSubmitDone = true;
	}
</script>
<section class="section-login">
	<div
		class="container mx-auto min-h-screen flex items-center justify-center">
		<div class="w-full">
			<div class="logo-bar flex justify-center mt-3 mb-3">
				<a href="#" class="logo">
					<span>
						<i class="fas fa-people-arrows"></i>
					</span>
					<span>UNTACT JOIN</span>
				</a>
			</div>
			<form
				class="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4 flex flex-col"
				action="doJoin" method="post"
				onsubmit="JoinForm__checkAndSubmit(this); return false;">
				<input type="hidden" name="redirectUrl" value="${param.redirectUrl}" />
				<div class="flex flex-col md:flex-row pb-3">
					<div
						class="p-1 block text-grey-darker font-bold md:w-36 md:flex md:items-center">
						<span>아이디</span>
					</div>
					<div class="p-1 md:flex-grow">
						<input
							class="shadow appearance-none border rounded w-full py-2 px-3 text-grey-darker md:flex-row"
							autofocus="autofocus" type="text" placeholder="아이디를 입력해주세요"
							name="loginId" maxlength="20" />
					</div>
				</div>
				<div class="flex flex-col md:flex-row pb-3">
					<div
						class="p-1 block text-grey-darker font-bold md:w-36 md:flex md:items-center">
						<span>비밀번호</span>
					</div>
					<div class="p-1 md:flex-grow">
						<input
							class="shadow appearance-none border rounded w-full py-2 px-3 text-grey-darker"
							autofocus="autofocus" type="password" placeholder="비밀번호를 입력해주세요"
							name="loginPw" maxlength="20" />
					</div>
				</div>
				<div class="flex flex-col md:flex-row pb-3">
					<div
						class="p-1 block text-grey-darker font-bold md:w-36 md:flex md:items-center">
						<span>비밀번호 확인</span>
					</div>
					<div class="p-1 md:flex-grow">
						<input
							class="shadow appearance-none border rounded w-full py-2 px-3 text-grey-darker"
							autofocus="autofocus" type="password" placeholder="비밀번호를 확인해주세요"
							name="loginPwConfirm" maxlength="20" />
					</div>
				</div>
				<div class="flex flex-col md:flex-row pb-3">
					<div
						class="p-1 block text-grey-darker font-bold md:w-36 md:flex md:items-center">
						<span>회원 이름</span>
					</div>
					<div class="p-1 md:flex-grow">
						<input
							class="shadow appearance-none border rounded w-full py-2 px-3 text-grey-darker md:flex-row"
							autofocus="autofocus" type="text" placeholder="회원이름을 입력해주세요"
							name="name" maxlength="20" />
					</div>
				</div>
				<div class="flex flex-col md:flex-row pb-3">
					<div
						class="p-1 block text-grey-darker font-bold md:w-36 md:flex md:items-center">
						<span>닉네임</span>
					</div>
					<div class="p-1 md:flex-grow">
						<input
							class="shadow appearance-none border rounded w-full py-2 px-3 text-grey-darker md:flex-row"
							autofocus="autofocus" type="text" placeholder="닉네임을 입력해주세요"
							name="nickname" maxlength="20" />
					</div>
				</div>
				<div class="flex flex-col md:flex-row pb-3">
					<div
						class="p-1 block text-grey-darker font-bold md:w-36 md:flex md:items-center">
						<span>전화번호</span>
					</div>
					<div class="p-1 md:flex-grow">
						<input
							class="shadow appearance-none border rounded w-full py-2 px-3 text-grey-darker md:flex-row"
							autofocus="autofocus" type="tel"
							placeholder="전화번호를 입력해주세요(- 없이 입력해주세요)" name="cellphoneNo"
							maxlength="11" />
					</div>
				</div>
				<div class="flex flex-col md:flex-row pb-3">
					<div
						class="p-1 block text-grey-darker font-bold md:w-36 md:flex md:items-center">
						<span>E-Mail</span>
					</div>
					<div class="p-1 md:flex-grow">
						<input
							class="shadow appearance-none border rounded w-full py-2 px-3 text-grey-darker md:flex-row"
							autofocus="autofocus" type="text" placeholder="E-Mail을 입력해주세요"
							name="email" maxlength="20" />
					</div>
				</div>
				<div class="flex items-center">
					<input
						class="btn-primary bg-blue-500 hover:bg-blue-dark text-white font-bold py-2 px-4 rounded"
						type="submit" value="회원가입" />
				</div>
			</form>
		</div>

	</div>
</section>
<%@ include file="../part/foot.jspf"%>
