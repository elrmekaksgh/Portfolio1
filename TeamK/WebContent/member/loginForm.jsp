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
</head>
<script type="text/javascript">

$(function(){ 
	  //크롬등에서 ime-mode:disabled 정상작동 되지않으므로 정규식으로 처리
	   $('#id').keyup(function(event){
	   $(this).val($(this).val().replace(/[^a-zA-Z0-9]/g,''));
	   });

	});
</script>
<body>
<%
String id = (String) session.getAttribute("id");
if (id != null) {
	response.sendRedirect("./main.fo"); 
}
%>
	<!--왼쪽 메뉴 -->
	<div>
		<jsp:include page="../inc/leftMenu.jsp"></jsp:include>
	</div>
	<!--왼쪽 메뉴 -->
	<div id="wrap"> 
		<div id="article_head">
			<div id="article_title">로그인</div>
			<div class="empty"></div>
			<div id="article_script">로그인이 필요합니다.</div>
		</div>
		<div id="clear"></div>
		<article>
			<div id="login_form">
				<form action="./MemberLoginAction.me" method="post" name="fr">
					<label for="id">아이디</label><input type="text" name="id" id="id" autofocus><br> 
					<label for="pass">비밀번호</label><input type="password" name="pass" id="pass"><br> 
					<input type="submit" value="로그인" id="login_submit">
					<input type="button" value="돌아가기" onclick="location.href='./main.fo'">
					<input type="hidden" value="<%=request.getHeader("referer")%>" name="exurl">
				</form>
				<table>
					<tr id="hr">
						<td>아직 회원이 아니신가요?</td>
						<td><input type="button" value="회원가입"
							onclick="location.href='./MemberJoin.me'"></td>
					</tr>
					<tr>
						<td rowspan="2">ID 혹은 비밀번호를<br>잊어버리셨나요?
						</td>
						<td><input type="button" value="아이디 찾기"
							onclick="location.href='./MemberFindId.me'"></td>
					</tr>
					<tr>
						<td><input type="button" value="비밀번호 찾기 "
							onclick="location.href='./MemberFindPass.me'"></td>
					</tr>
				</table>
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