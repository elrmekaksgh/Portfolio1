<%@page import="net.member.db.MemberBean"%>
<%@page import="net.member.db.MemberDAO"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>TeamK 여행사</title>
<link href="./css/inc.css" rel="stylesheet" type="text/css">
<link href="./css/subpage.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js"></script>
<script type="text/javascript">


$(function(){ 
	  //크롬등에서 ime-mode:disabled 정상작동 되지않으므로 정규식으로 처리
	   $('#id').keyup(function(event){
	   $(this).val($(this).val().replace(/[^a-zA-Z0-9]/g,''));
	   });

	});

function passck() {
	if(document.fr.pass.value==null || document.fr.pass.value==""){
	alert("비밀번호를 입력해주세요.");
	return false;
	}
	return;
}
</script>
</head>
<body>
	<%
		String id = (String) session.getAttribute("id");
		if (id == null) {
			response.sendRedirect("./MemberLogin.me");
		}
	%>
	<!--왼쪽 메뉴 -->
	<div>
		<jsp:include page="../inc/leftMenu.jsp"></jsp:include>
	</div>
	<!--왼쪽 메뉴 -->
	<div id="wrap">
		<div id="article_head">
			<div id="article_title">회원 탈퇴</div>
			<div class="empty"></div>
			<div id="article_script"></div>
		</div>
		<div id="clear"></div>
		<article>
			<div id="m_delete_form">
			<form action="./MemberDeleteAction.me" method="post" name="fr" onsubmit="return passck()">
				<label for="id">아이디</label>
				<input type="text" name="id" id="id" value="<%=id%>" readonly="readonly"><br>
				<label for="pass">비밀번호</label>
				<input type="password" name="pass" id="pass"><br> 
				<input type="submit" value="회원탈퇴">
				<input type="button" value="돌아가기" onclick="location.href='./main.fo'"> 
			</form>
			</div>
		</article>
	</div>
	<jsp:include page="../inc/footer.jsp"></jsp:include>
	<!--오른쪽 메뉴 -->
	<div>
		<jsp:include page="../inc/rightMenu.jsp"></jsp:include>
	</div>
	<!--오른쪽 메뉴 -->
</body>
</html>