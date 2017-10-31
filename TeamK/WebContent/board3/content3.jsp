<%@page import="net.board.db.BoardDAO"%>
<%@page import="net.board.db.BoardReplyBean"%>
<%@page import="java.util.List"%>
<%@page import="net.board.db.BoardBean"%>
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
<%
BoardBean bb =(BoardBean)request.getAttribute("bb");
BoardDAO bdao = new BoardDAO();
String pageNum = (String)request.getAttribute("pageNum");
String id = (String)session.getAttribute("id");
int num = Integer.parseInt(request.getParameter("num"));
%>
	<!--왼쪽 메뉴 -->
	<div>
		<jsp:include page="../inc/leftMenu.jsp"></jsp:include>
	</div>
	<!--왼쪽 메뉴 -->
	<div id="wrap">
		<div id="article_head">
			<div id="article_title"><img src="./img/notice2.png" width="30px" style="margin-right: 8px; vertical-align: bottom;">공지사항</div>
			<div class="empty"></div>
			<div id="article_script">공지사항 게시판 입니다.</div>
		</div>
		<div id="clear"></div>
		<article>
		<div id="board_content">
<table id="content">
<tr>
 <td id="num"><%=bb.getRe_ref()%></td>
 <td id="date"> <%=bb.getDate()%></td>
 <td id="readcount">조회수: <%=bb.getReadcount()%></td>
</tr>
<tr>
 <td colspan="2" id="subject"><%=bb.getSubject()%></td>
 <td id="id"><%=bb.getId()%></td>
</tr>
<tr>
 <td colspan="3" id="content_td"><br><br><div id="min"><%=bb.getContent()%></div><br></td>
</tr>

<%--첨부파일이 있을때만 첨부파일 표시--%>
<%if(bb.getFile1()!=null){%>
<tr><td>첨부파일1</td><td colspan="3"><%if(bb.getFile1()!=null){%><a href="./file_down.jsp?file_name=<%=bb.getFile1()%>"><%=bb.getFile1()%></a><%}%></td></tr>
<%}%>
<%if(bb.getFile2()!=null){%>
<tr><td>첨부파일2</td><td colspan="3"><%if(bb.getFile2()!=null){%><a href="./file_down.jsp?file_name=<%=bb.getFile2()%>"><%=bb.getFile2()%></a><%}%></td></tr>
<%}%>
<%if(bb.getFile3()!=null){%>
<tr><td>첨부파일3</td><td colspan="3"><%if(bb.getFile3()!=null){%><a href="./file_down.jsp?file_name=<%=bb.getFile3()%>"><%=bb.getFile3()%></a><%}%></td></tr>
<%}%>
<%if(bb.getFile4()!=null){%>
<tr><td>첨부파일4</td><td colspan="3"><%if(bb.getFile4()!=null){%><a href="./file_down.jsp?file_name=<%=bb.getFile4()%>"><%=bb.getFile4()%></a><%}%></td></tr>
<%}%>
<%if(bb.getFile5()!=null){%>
<tr><td>첨부파일5</td><td colspan="3"><%if(bb.getFile5()!=null){%><a href="./file_down.jsp?file_name=<%=bb.getFile5()%>"><%=bb.getFile5()%></a><%}%></td></tr>
<%}%>
</table><br>
<%
if(id!=null){
	
//자신의 Id일때만 글수정 가능
if(id.equals(bb.getId())){ %>
<input type="button" value="글수정" 
       onclick="location.href= './BoardUpdate3.bo?num=<%=bb.getNum()%>&pageNum=<%=pageNum%>'">     
<%}
//자신의 Id이거나 admin 일때 글삭제 가능
if(id.equals(bb.getId())||id.equals("admin")){ %>
<input type="button" value="글삭제" 
       onclick="location.href= './BoardDelete3.bo?num=<%=bb.getNum()%>&pageNum=<%=pageNum%>'">
<%}}%>
<input type="button" value="글목록" 
       onclick="location.href='./BoardList3.bo?pageNum=<%=pageNum%>'">
</div>
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