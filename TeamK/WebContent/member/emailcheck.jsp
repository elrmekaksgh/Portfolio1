<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>TeamK 여행사</title>
<link href="./css/popup.css" rel="stylesheet" type="text/css">
</head>
<%String checknum = (String) request.getAttribute("checknum");%>
<body>
	<div id="emailchk">
		<form action="./MemberEmailAction.me" method="post">
			<input type="hidden" name="checknum" value="<%=checknum%>"> 
			<input type="text" name="number" id="number" 
			placeholder="인증번호를 입력해주세요" maxlength="6">
			<input type="submit" value="확인">
		</form>
	</div>
</body>
</html>