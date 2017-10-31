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
	<!--왼쪽 메뉴 -->
	<div>
		<jsp:include page="../inc/leftMenu.jsp"></jsp:include>
	</div>
	<!--왼쪽 메뉴 -->
	<div id="wrap">
	<div id="article_head">
<div id="article_title">관리자 주문 관리 메뉴</div>
<div class="empty"></div>
</div>
<article>
<%request.setCharacterEncoding("utf-8");
String id = (String)session.getAttribute("id");
if(!(id.equals("admin"))){
%>
<script type="text/javascript">
alert("권한이 없습니다!");
history.back();
</script>
<%} %>
<div id="admin_order_list">
<input type="button"value = "무통장 입금 확인" onclick="location.href='./BankPayCheck.ao'"><br>
<input type="button" value ="여행 주문 확인" onclick ="location.href = './Pack_res.ao'"><br>
<input type="button" value="상품 주문 확인" onclick="location.href='./Admin_Thing_OrderList.ao'"><br>
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