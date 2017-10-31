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

<%
request.setCharacterEncoding("utf-8");
session.setAttribute("id", "admin");
String id = (String)session.getAttribute("id");
%>
.Main.jsp<br>

<%=id %> is Connected<br>
<div>
<h3>Client</h3>
<input type = "button" value="My Basket" onclick ="location.href='./MyBasketList.bns'"><br>
<input type = "button" value="My Interest" onclick ="location.href='./MyInterestList.ins'"><br>
<input type="button" value="PackOrder" onclick = "location.href='./MyPackOrderList.mo'"><br>
<input type="button" value="ThingOrder" onclick="location.href='./MyThingOrderList.mo'"><br>
</div>
<div>
<h3>Admin</h3>
<input type="button"value = "adminmenu" onclick="location.href='./BankPayCheck.ao'"><br>
<input type="button" value ="Admin_pack_Order" onclick ="location.href = './Pack_res.ao'"><br>
<input type="button" value="Admin_thing_Order" onclick="location.href='./Admin_Thing_OrderList.ao'"><br>
<input type="button" value="test" onclick="location.href='./test1.jc'"><br>
<!-- <input type="button" value="thing_completed" onclick="location.href='./Thing_Exchange.ao'"><br> -->


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