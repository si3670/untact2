<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="../part/mainLayoutHead.jspf"%>

<c:set var="fileInputMaxCount" value="10" />
<script>
BoardAdd__fileInputMaxCount = parseInt("${fileInputMaxCount}");
</script>

<script>
BoardAdd__submited = false;
function BoardAdd__checkAndSubmit(form) {
	if ( BoardAdd__submited ) {
		alert('처리중입니다.');
		return;
	}
	
	form.code.value = form.code.value.trim();
	if ( form.code.value.length == 0 ) {
		alert('코드을 입력해주세요.');
		form.code.focus();
		return false;
	}
	form.name.value = form.name.value.trim();
	if ( form.name.value.length == 0 ) {
		alert('이름을 입력해주세요.');
		form.name.focus();
		return false;
	}
	var maxSizeMb = 50;
	var maxSize = maxSizeMb * 1024 * 1024;

	
	for( let inputNo = 1; inputNo <= BoardAdd__fileInputMaxCount; inputNo++){
		const input = form["file__article__0__common__attachment__" + inputNo];
		
		if (input.value) {
			if (input.files[0].size > maxSize) {
				alert(maxSizeMb + "MB 이하의 파일을 업로드 해주세요.");
				input.focus();
				
				return;
			}
		}
	}
	
	const startSubmitForm = function(data) {
		
		if (data && data.body && data.body.genFileIdsStr) {
			form.genFileIdsStr.value = data.body.genFileIdsStr;
		}

		
		for ( let inputNo = 1; inputNo <= BoardAdd__fileInputMaxCount; inputNo++ ) {
			const input = form["file__article__0__common__attachment__" + inputNo];
			input.value = '';
		}
		
		form.submit();
	};
	const startUploadFiles = function(onSuccess) {
		var needToUpload = false;
		
		for ( let inputNo = 1; inputNo <= BoardAdd__fileInputMaxCount; inputNo++ ) {
			const input = form["file__article__0__common__attachment__" + inputNo];
			if ( input.value.length > 0 ) {
				needToUpload = true;
				break;
			}
		}
			
		if (needToUpload == false) {
			onSuccess();
			return;
		}
		
		var fileUploadFormData = new FormData(form);
		
		$.ajax({
			url : '/common/genFile/doUpload',
			data : fileUploadFormData,
			processData : false,
			contentType : false,
			dataType : "json",
			type : 'POST',
			success : onSuccess
		});
	}
	BoardAdd__submited = true;
	startUploadFiles(startSubmitForm);
}
</script>


<section class="section-1">
	<div class="bg-white shadow-md rounded container mx-auto p-8 mt-8">
		<form onsubmit="BoardAdd__checkAndSubmit(this); return false;"
			action="doAdd" method="POST" enctype="multipart/form-data">
			<input type="hidden" name="genFileIdsStr" value="" />
			<input type="hidden" name="boardId" value="${param.boardId}" />
			<div class="form-row flex flex-col lg:flex-row">
				<div class="lg:flex lg:items-center lg:w-28">
					<span>코드</span>
				</div>
				<div class="lg:flex-grow">
					<input type="text" name="code" autofocus="autofocus"
						class="form-row-input w-full rounded-sm" placeholder="코드를 입력해주세요." />
				</div>
			</div>
			<div class="form-row flex flex-col lg:flex-row">
				<div class="lg:flex lg:items-center lg:w-28">
					<span>아름</span>
				</div>
				<div class="lg:flex-grow">
					<textarea name="name" class="form-row-input w-full rounded-sm"
						placeholder="아름을 입력해주세요."></textarea>
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
