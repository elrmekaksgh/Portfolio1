
<%@page import="net.member.action.MemberList"%>
<%@page import="net.member.db.MemberBean"%>
<%@page import="net.member.db.MemberDAO"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="./css/inc.css" rel="stylesheet" type="text/css">
<link href="./css/subpage.css" rel="stylesheet" type="text/css">
<title>관리자가 보는 회원 정보</title>
</head>
<body>
	<!--왼쪽 메뉴 -->
	<div>
		<jsp:include page="../inc/leftMenu.jsp"></jsp:include>
	</div>
	<!--왼쪽 메뉴 -->
	<div id="wrap">
			<div id="article_head">
			<div id="article_title">고객 정보 관리</div>
			<div class="empty"></div>
			<div id="article_script"></div>
		</div>
	<%
		String id = (String) session.getAttribute("id");

		if (id != null && !id.equals("admin")) {
			response.sendRedirect("./main.fo");
		}

		List memberList = (List) request.getAttribute("memberList");
	%>
	<article>
	<table id="memberList">
		<tr>
			<th>아이디</th>
			<th>이름</th>
			<th>주소 / 상세 주소</th>
			<th>전화번호 / 이메일</th>
		</tr>
		<%
			for (int i = 0; i < memberList.size(); i++) {
				MemberBean mb = (MemberBean) memberList.get(i);
		%>
		<tr>
			<td rowspan="2"><%=mb.getId()%></td>
			<td rowspan="2"><%=mb.getName()%></td>
			<td id="imsi"><%=mb.getAddress1()%></td>
			<td id="imsi"><%=mb.getMobile()%></td>
		</tr>
		<tr>
			<td><%=mb.getAddress2()%></td>
			<td><%=mb.getEmail()%></td>
		</tr>

		<%
			}
		%>
	</table>
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