<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="../part/mainLayoutHead.jspf"%>

<script>
	MemberModify__submited = false;
	function MemberModify__checkAndSubmit(form) {
		if (MemberModify__submited) {
			alert('처리중입니다.');
			return;
		}

		if (JoinForm__checkAndSubmitDone) {
			return;
		}
		form.loginId.value = form.loginId.value.trim();
		if (form.loginId.value.length == 0) {
			alert('로그인아이디를 입력해주세요.');
			form.loginId.focus();
			return;
		}

		if (form.loginPw.value) {
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
		MemberModify__submited = true;
	}
</script>


<section class="section-1">
	<div class="bg-white shadow-md rounded container mx-auto p-8 mt-8">
		<form onsubmit="MemberModify__checkAndSubmit(this); return false"
			action="doModify" method="POST">
			<input type="hidden" name="genfileIdsStr" value="" />
			<input type="hidden" name="id" value="${member.id}" />
			<div class="form-row flex flex-col lg:flex-row">
				<div class="lg:flex lg:items-center lg:w-28">
					<span>Id</span>
				</div>
				<div class="lg:flex-grow">
					<input value="${member.loginId}" type="text" name="loginId"
						autofocus="autofocus" class="form-row-input w-full rounded-sm"
						placeholder="아이디를 입력해주세요." />
				</div>
			</div>
			<div class="form-row flex flex-col lg:flex-row">
				<div class="lg:flex lg:items-center lg:w-28">
					<span>Password</span>
				</div>
				<div class="lg:flex-grow">
					<input type="password" name="loginPw" autofocus="autofocus"
						class="form-row-input w-full rounded-sm"
						placeholder="비밀번호를 입력해주세요." />
				</div>
			</div>
			<div class="form-row flex flex-col lg:flex-row">
				<div class="lg:flex lg:items-center lg:w-28">
					<span>Password 확인</span>
				</div>
				<div class="lg:flex-grow">
					<input type="password" name="loginPwConfirm" autofocus="autofocus"
						class="form-row-input w-full rounded-sm"
						placeholder="비밀번호 확인을 입력해주세요." />
				</div>
			</div>
			<div class="form-row flex flex-col lg:flex-row">
				<div class="lg:flex lg:items-center lg:w-28">
					<span>Name</span>
				</div>
				<div class="lg:flex-grow">
					<input value="${member.name}" type="text" name="name"
						autofocus="autofocus" class="form-row-input w-full rounded-sm"
						placeholder="이름을 입력해주세요." />
				</div>
			</div>
			<div class="form-row flex flex-col lg:flex-row">
				<div class="lg:flex lg:items-center lg:w-28">
					<span>NickName</span>
				</div>
				<div class="lg:flex-grow">
					<input value="${member.nickname}" type="text" name="nickname"
						autofocus="autofocus" class="form-row-input w-full rounded-sm"
						placeholder="닉네임을 입력해주세요." />
				</div>
			</div>
			<div class="form-row flex flex-col lg:flex-row">
				<div class="lg:flex lg:items-center lg:w-28">
					<span>CellPhonNo</span>
				</div>
				<div class="lg:flex-grow">
					<input value="${member.cellphoneNo}" type="tel" name="cellphoneNo"
						autofocus="autofocus" class="form-row-input w-full rounded-sm"
						placeholder="전화번호를 입력해주세요." />
				</div>
			</div>
			<div class="form-row flex flex-col lg:flex-row">
				<div class="lg:flex lg:items-center lg:w-28">
					<span>E-mail</span>
				</div>
				<div class="lg:flex-grow">
					<input value="${member.email}" type="text" name="email"
						autofocus="autofocus" class="form-row-input w-full rounded-sm"
						placeholder="email를 입력해주세요." />
				</div>
			</div>
			<div class="form-row flex flex-col lg:flex-row">
				<div class="lg:flex lg:items-center lg:w-28">
					<span>권한레벨</span>
				</div>
				<div class="lg:flex-grow">
					<input value="${member.authLevel}" type="text" name="authLevel"
						autofocus="autofocus" class="form-row-input w-full rounded-sm" />
				</div>
			</div>
			<div class="form-row flex flex-col lg:flex-row">
				<div class="lg:flex lg:items-center lg:w-28">
					<span>작성</span>
				</div>
				<div class="lg:flex-grow">
					<div class="btns">
						<input type="submit"
							class="btn-primary bg-blue-500 hover:bg-blue-dark text-white font-bold py-2 px-4 rounded"
							value="작성">
						<input onclick="history.back();" type="button"
							class="btn-info bg-red-500 hover:bg-red-dark text-white font-bold py-2 px-4 rounded"
							value="취소">
					</div>
				</div>
			</div>
		</form>
	</div>
</section>

<%@ include file="../part/mainLayoutFoot.jspf"%>
