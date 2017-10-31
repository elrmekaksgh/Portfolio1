<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Trans Number Search</title>
<%int trans_num = Integer.parseInt(request.getParameter("num")); %>
</head>
<body>
<h3>송장 번호 조회 서비스</h3>
송장 번호  [<%=trans_num %>] 조회 페이지<br>
API없음...<br>
조회 안되니 다음 기회에...
<input type= "button" value="닫기" onclick="window.close()">
</body>
</html>