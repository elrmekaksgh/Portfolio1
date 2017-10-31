<%@page import="com.sun.org.apache.xerces.internal.impl.dv.util.Base64"%>
<%@page import="net.member.db.MemberBean"%>
<%@page import="net.member.db.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>TeamK 여행사</title>
<link href="../css/popup.css" rel="stylesheet" type="text/css">
</head>
<body>
	<%
		String id = request.getParameter("userid");
		MemberDAO mdao = new MemberDAO();
		MemberBean mb = mdao.getMember(id);
		String pass = mb.getPass();		
	%>
	<div id="passchange">
	<form action="../MemberPasschangeAction.me" method="post" name="fr">
			
			<label>아이디</label>			<input type="text" value="<%=id%>" name="id" readonly="readonly"><br>
					
			<!-- 기존의 비밀번호값 -->
			<input type="hidden" name="pass" value="<%=pass%>"> 					
			 
			<label>기존 비밀번호</label>		<input type="password" name="pass2"><br>
			
			<label>변경할 비밀번호</label>	<input type="password" name="changepass"><br>
			  
			<label>변경할 비밀번호 확인</label>	<input type="password" name="changepass2"><br>
										<input type="submit" value="변경">
										<input type="button" value="취소" onclick="window.close()">
		</form>
			</div>
</body>
</html>