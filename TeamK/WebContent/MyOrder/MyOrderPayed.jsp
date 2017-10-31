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
<%
request.setCharacterEncoding("utf-8");
int check = Integer.parseInt(request.getParameter("check"));
%>
	<script src = "./js/jquery-3.2.0.js"></script>
	<script type="text/javascript">
	$(window).ready(function(){
		if(<%=check%> ==1)
			if(confirm("여행상품은 대표자 연락처를 반드시 입력해야합니다!\n지금 바로 입력페이지로 이동하시겠습니까?")){
				location.href="./MyPackOrderList.mo";
		}
	})
		
	
	</script>
<body>
	<!--왼쪽 메뉴 -->
	<div>
		<jsp:include page="../inc/leftMenu.jsp"></jsp:include>
	</div>
	<!--왼쪽 메뉴 -->
	<div id="wrap">
	<div id="article_head">
<div id="article_title">결제 완료</div>
<div class="empty"></div>
</div>
<article>
<br><br>
<h1>결제가 정상적으로 완료 되었습니다.</h1><br>
<h3>이용해주셔서 감사합니다</h3><br><br><br>
<input type = "button" value = "내 주문" onclick="location.href='./MyOrderList.mo'">
<input type="button" value="내 패키지 주문 목록" onclick="location.href='./MyPackOrderList.mo'">
<input type="button" value="내 상품 주문 목록" onclick="location.href='./MyThingOrderList.mo'"><br><br><br>
	</article>
	</div>
	<jsp:include page="../inc/footer.jsp"></jsp:include>
	<!--오른쪽 메뉴 -->
	<div>
		<jsp:include page="../inc/rightMenu.jsp"></jsp:include>
	</div>
	<!--오른쪽 메뉴 -->
</body>
</body>
</html>