<%@page import="net.board.db.BoardDAO"%>
<%@page import="net.board.db.BoardBean"%>
<%@page import="java.util.List"%>
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
List boardList=(List)request.getAttribute("boardList");
String pageNum=(String)request.getAttribute("pageNum");
int count=((Integer)request.getAttribute("count")).intValue();
int pageCount=((Integer)request.getAttribute("pageCount")).intValue();
int pageBlock=((Integer)request.getAttribute("pageBlock")).intValue();
int startPage=((Integer)request.getAttribute("startPage")).intValue();
int endPage=((Integer)request.getAttribute("endPage")).intValue();
String ss = (String)request.getAttribute("ss");
int pNum = Integer.parseInt(pageNum);

String search=request.getParameter("search");

BoardDAO bdao = new BoardDAO();
%>
<script type="text/javascript" src="./js/jquery-3.2.0.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$('#selectSearch').val('<%=ss%>').attr('selected', 'selected');
})
</script>
	<!--왼쪽 메뉴 -->
	<div>
		<jsp:include page="../inc/leftMenu.jsp"></jsp:include>
	</div>
	<!--왼쪽 메뉴 -->
	<div id="wrap">
		<div id="article_head">
			<div id="article_title"><img src="./img/review2.png" width="23px" style="margin-right: 8px; vertical-align: bottom;">리뷰</div>
			<div class="empty"></div>
			<div id="article_script">상품이나 패키지 후기를 쓰는 곳 입니다.<span class="count">[검색된 글의 개수 :<%=count%>]</span></div>
		</div>
		<div id="clear"></div>
		<article>
		<div id="board_list">
<table>
<tr>
 <th id="num">번호</th>
 <th id="cate">분류</th>
 <th id="title">제목</th>
 <th id="name">작성자</th>
 <th id="date">날짜</th>
 <th id="readcount">조회수</th>
</tr>
    <%
    if(count==0){%><tr><td colspan="6">검색결과가 없습니다.</td></tr><%}else{			
    for(int i=0; i<boardList.size(); i++){
    	//자바빈(BoardBean) 변수 =배열한칸 접근  배열변수.get()
    	BoardBean bb = (BoardBean)boardList.get(i);
    	%>
<tr>
 <td><%=bb.getRe_ref()%></td>
 <td id="cate">[<%=bb.getType_select()%>]</td>
 <td id="title"><a href="./BoardContent.bo?num=<%=bb.getNum()%>&pageNum=<%=pageNum%>"><%=bb.getSubject()%>
 <%if(bdao.getBoardReplyCount(bb.getNum())!=0){%>[<%=bdao.getBoardReplyCount(bb.getNum())%>]<%}%></a><%if(bdao.getFile(bb.getNum())!=null){%><img src="./img/disk.png" width="15" height="15>"><%}%></td>
 <td><%=bb.getId()%></td>
 <td><%=bb.getDate()%></td>
 <td><%=bb.getReadcount() %></td>
</tr>
    			<%
    }}
    %>
</table>
<div id="board_menu_bar">
<%
String id = (String)session.getAttribute("id");
if(id!=null){%>
<input type="button" value="글쓰기" 
       onclick="location.href='./BoardWrite.bo'">
    		<%}else{%>
    			<input type="button" value="글쓰기" 
    				   onclick="alert('로그인 해주세요')">
    		<%} %>
<input type="button" value="메인으로" 
       onclick="location.href='./main.fo'">
<%
//페이지 출력
if(count!=0){
	//전체 페이지 수 구하기 게시판 글 50개 한화면에 보여줄 글 개수 10 => 5전체페이지
			//    게시판 글 56개 한화면에 보여줄 글개수 10 =>  5전체페이지 +1 (나머지)=>6		
	// 한 화면에 보여줄 페이지 번호 개수
	// 시작페이지 번호구하기  1~10=>1  11~20=>11  21~30=>21
	// 끝페이지 번호 구하기  
	//이전
	if(startPage>pageBlock){
		%><a href="./listSearch.bo?pageNum=<%=startPage-pageBlock%>&search=<%=search%>">[이전]</a><%
	}
	// 1..10 11..20 21..30
	for(int i=startPage; i<=endPage; i++){
		if(i==pNum){%><span id="i"><%=i%></span><%}else{
		%><a id="i" href="./listSearch.bo?pageNum=<%=i%>&selectSearch=<%=ss%>&search=<%=search%>"><%=i%></a><%
	}}
	// 다음
	if(endPage < pageCount){
		%><a href="./listSearch.bo?pageNum=<%=startPage+pageBlock%>&search=<%=search%>">[다음]</a>
		<%
		}
}
%>
<form action="listSearch.bo" method="get">
<select name="selectSearch" id="selectSearch">
    <option value="id">작성자</option>
    <option value="subject">제목</option>
    <option value="content">내용</option>
    <option value="type_select">분류</option>
</select>
<input type="text" name="search" class="input_box">
<input type="submit" value="검색" class="btn">
</form>
       </div>
		<div class="clear"></div>
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