<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="./css/inc.css" rel="stylesheet" type="text/css">
<link href="./css/subpage.css" rel="stylesheet" type="text/css">
</head>
<body>
<%
int num = Integer.parseInt(request.getParameter("num"));
String pageNum=request.getParameter("pageNum"); 
String id = (String)session.getAttribute("id");
%>
	<!--왼쪽 메뉴 -->
	<div>
		<jsp:include page="../inc/leftMenu.jsp"></jsp:include>
	</div>
	<!--왼쪽 메뉴 -->
	<div id="wrap">
		<div id="article_head">
			<div id="article_title"><img src="./img/qna2.png" width="35px" style="margin-right: 8px; vertical-align: bottom;">Q&A 게시판</div>
			<div class="empty"></div>
			<div id="article_script">궁금한것은 질문해주세요.</div>
		</div>
		<div id="clear"></div>
		<article>
		<div id="article_delete">
<form action="./BoardDeleteAction2.bo?pageNum=<%=pageNum%>" method="post" name="fr">
<input type="hidden" name="id" value="<%=id%>">
<input type="hidden" name="num" value="<%=num%>">
<p>정말로 글을 삭제하시겠습니까?</p>
<input type="submit" value="삭제">
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