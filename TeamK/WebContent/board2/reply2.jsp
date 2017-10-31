<%@page import="net.board.db.BoardBean"%>
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

<%-- board2/content2.jsp 페이지의 댓글부분이 밑의 페이지로 대체됨 --%>
<body>
<%
request.setCharacterEncoding("utf-8");
List lrb = null;
int rcount = (int)request.getAttribute("rcount"); //리플갯수 rcount에 불러오기
if(rcount!=0){lrb=(List)request.getAttribute("lrb");} //rcount가 0이 아니면 리플정보 불러오기
String id = (String)session.getAttribute("id");
String pageNum = request.getParameter("pageNum");
String rNum = (String)request.getAttribute("rNum"); // rNum에 글 번호 불러오기
String wEmail = (String)request.getAttribute("wEmail"); //wEmail에 작성자의 메일주소 불러오기
String wContent = (String)request.getAttribute("wContent"); //wContent에 작성한 댓글 내용 불러오기
%>
<p>답변(<%=rcount%>개)</p>
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
	<input type="hidden" id="wContent" value="<%=wContent%>"><%--글작성자 글내용 넘기기  --%>
	<input type="hidden" id="wEmail" value="<%=wEmail%>"><%--글작성자 메일주소 넘기기 --%>
	<div id="textarea">
	<textarea rows="3" cols="59" id="rContent" name="content"></textarea><%--리플내용 넘기기 --%>
	</div>
	<input type="button" value="댓글달기" onclick="replyupdate()">
</form>
</body>
</html>