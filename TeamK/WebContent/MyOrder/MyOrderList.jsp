<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="./css/inc.css" rel="stylesheet" type="text/css">
<link href="./css/subpage.css" rel="stylesheet" type="text/css">
<%request.setCharacterEncoding("utf-8");
String id = (String)session.getAttribute("id"); %>
</head>
<body>
	<!--왼쪽 메뉴 -->
	<div>
		<jsp:include page="../inc/leftMenu.jsp"></jsp:include>
	</div>
	<!--왼쪽 메뉴 -->
	<div id="wrap">
<div>
<h3><%=id %> Order List</h3>
<input type = "button" value="My Basket" onclick ="location.href='./MyBasketList.bns'"><br>
<input type = "button" value="My Interest" onclick ="location.href='./MyInterestList.ins'"><br>
<input type="button" value="PackOrder" onclick = "location.href='./MyPackOrderList.mo'"><br>
<input type="button" value="ThingOrder" onclick="location.href='./MyThingOrderList.mo'"><br>


</div>
	</div>
	<jsp:include page="../inc/footer.jsp"></jsp:include>
	<!--오른쪽 메뉴 -->
	<div>
		<jsp:include page="../inc/rightMenu.jsp"></jsp:include>
	</div>
	<!--오른쪽 메뉴 -->
</body>
</html>