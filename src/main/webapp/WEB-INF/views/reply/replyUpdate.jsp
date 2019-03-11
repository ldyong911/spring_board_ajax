<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<h1 class="page-header">댓글수정</h1>
<form id="frmUpd" action="${cp}/replyUpdate" method="post">
	<input type="hidden" name="reply_num" value="${replyVo.reply_num}"/>
	<textarea rows="10" cols="150" name="reply_content">${replyVo.reply_content}</textarea> <br>
	<input type="button" id="btnUpd" value="저장"/>
</form>
	
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>

<script src="/SE2/js/HuskyEZCreator.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	//댓글 수정 버튼 클릭이벤트
	$("#btnUpd").on("click", function(){
		$("#frmUpd").submit();
	});
});
</script>