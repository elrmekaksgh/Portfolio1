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
			<div id="article_title">ID 찾기</div>
			<div class="empty"></div>
			<div id="article_script"></div>
		</div>
		<div id="clear"></div>
		<article>
			<div id="findid_form">
				<form action="./MemberFindIdAction.me" method="post">
					<label for="name">이름</label><input type="text" name="name" id="name"><br>
					<label for="email">이메일</label><input type="text" name="email" id="email"><br>
					<input type="submit" value="확인">
					<input type="button" value="돌아가기" onclick="history.back()">
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