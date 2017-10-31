<%@page import="net.member.db.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>TeamK 여행사</title>
<link href="./css/popup.css" rel="stylesheet" type="text/css">
</head>
<body>
<%-- 	<%String checknum = (String) request.getAttribute("checknum");%> --%>
	<div id="emailchkup">
	<form action="./MemberEmailUp.me" method="post">
		<label for="email">이메일</label>
		<input type="text" name="email" id="email"> 
		<input type="hidden" name="echeck" value="0"> 
		<input type="hidden" name="checknum" value="checknum"><br>
		<input type="submit" value="인증번호 받기">
	</form>
	</div>
</body>
</html>