<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="net.member.db.ProductBean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="./css/subpage.css" rel="stylesheet" type="text/css">
<script src="./js/jquery-3.2.0.js"></script>
<title>Insert title here</title>
</head>
<%
	ProductBean pdb = (ProductBean)request.getAttribute("pdb");
%>
<body>
<center>
<h3><%=pdb.getName() %></h3>
<p><%=pdb.getContent() %></p>
</center>

<br><br>
</body>
</html>