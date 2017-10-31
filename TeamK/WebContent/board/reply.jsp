<%@page import="net.board.db.BoardReplyBean"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<link href="./css/inc.css" rel="stylesheet" type="text/css">
<link href="./css/subpage.css" rel="stylesheet" type="text/css">

<%-- board/content.jsp 페이지의 댓글부분이 밑의 페이지로 대체됨 --%>
<body>
<%
request.setCharacterEncoding("utf-8");
List lrb = null;
int rcount = (int)request.getAttribute("rcount"); //리플갯수 불러오기
if(rcount!=0){lrb=(List)request.getAttribute("lrb");} //리플정보 불러오기
String id = (String)session.getAttribute("id"); 
String pageNum = request.getParameter("pageNum");
String rNum = (String)request.getAttribute("rNum");
%>
<p>댓글(<%=rcount%>개)</p>
<table id="reply">
    <%if(rcount!=0){
    for(int i=0; i<lrb.size(); i++){
    	//자바빈(BoardBean) 변수 =배열한칸 접근  배열변수.get()
    	BoardReplyBean rb = (BoardReplyBean)lrb.get(i);%>
<tr>
<td id="name"><%=rb.getId()%></td>
<td id="content"><%=rb.getContent()%></td>
<td id="delete"><%
if(id!=null){
	if(id.equals(rb.getId())||id.equals("admin")){ 
%>
<form method="post" name="replydel" >
<input type="hidden" name="num" value="<%=rb.getNum()%>">
<input type="button" value="×" onclick="replydelete(<%=rb.getNum()%>)">
</form>
<%}}%></td>
<td id="date"><%=rb.getDate()%></td>
</tr>
    <%
    }}
    %>
</table>
<form method="post" name="fr1" id="reply">
<span><%=id %></span>
<input type="hidden" id="rNum" name="group_del" value="<%=rNum%>">
<input type="hidden" id="pageNum" value="<%=pageNum%>">
<input type="hidden" id="rId" name="id" value="<%=id%>" readonly>
<div id="textarea">
<textarea rows="3" cols="59" id="rContent" name="content" placeholder="타인을 향한 지나친 비방, 욕설은 자제해주세요."></textarea>
</div>
<input type="button" value="댓글달기" onclick="replyupdate()">
</form>
</body>
</html>