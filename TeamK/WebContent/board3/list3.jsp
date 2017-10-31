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
<link href="../css/inc.css" rel="stylesheet" type="text/css">
<link href="../css/subpage.css" rel="stylesheet" type="text/css">
<%
//세션 id값 불러오기
String id = (String)session.getAttribute("id");
%>

</head>
<body>
<%
List boardList3=(List)request.getAttribute("boardList3");
String pageNum=(String)request.getAttribute("pageNum");
int count=((Integer)request.getAttribute("count")).intValue();
int pageCount=((Integer)request.getAttribute("pageCount")).intValue();
int pageBlock=((Integer)request.getAttribute("pageBlock")).intValue();
int startPage=((Integer)request.getAttribute("startPage")).intValue();
int endPage=((Integer)request.getAttribute("endPage")).intValue();
int pNum = Integer.parseInt(pageNum);

BoardDAO bdao = new BoardDAO();
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
			<div id="article_script">공지사항 게시판 입니다.<span class="count">[전체글 개수 :<%=count%>]</span></div>
		</div>
		<div id="clear"></div>
		<article>
		<div id="board_list">
<br>
<table>
<tr>
 <th id="num">번호</th>
 <th id="cate"></th>
 <th id="title">제목</th>
 <th id="name">작성자</th>
 <th id="date">날짜</th>
 <th id="readcount">조회수</th>
</tr>
    <%
    if(count==0){%><tr><td colspan="6">글이 없습니다.</td></tr><%}else{
    for(int i=0; i<boardList3.size(); i++){
    	//자바빈(BoardBean) 변수 =배열한칸 접근  배열변수.get()
    	BoardBean bb = (BoardBean)boardList3.get(i);
    			%>
<tr>
 <td><%=bb.getRe_ref()%></td> <%--글 번호 --%>
 <td id="cate"><a href="./BoardContent3.bo?num=<%=bb.getNum()%>&pageNum=<%=pageNum%>">[<%=bb.getType_select()%>]</a></td><%--글 타입 --%>
 <td id="title"><a href="./BoardContent3.bo?num=<%=bb.getNum()%>&pageNum=<%=pageNum%>"><%=bb.getSubject()%></a><%--글 제목 --%>
 <%if(bdao.getFile(bb.getNum())!=null){%><img src="./img/disk.png" width="15" height="15>"><%}%></td><%--파일 첨부했으면 파일 이미지 나옴 --%>
 <td>관리자</td><%--작성자 Id --%>
 <td><%=bb.getDate()%></td><%--작성 날짜 --%>
 <td><%=bb.getReadcount() %></td><%--조회수 --%>
</tr>
    			<%
    }}
    %>
</table>
<div id="board_menu_bar">
<%
if(id!=null){
if(id.equals("admin")){
%>
<input type="button" value="글쓰기" 
       onclick="location.href='./BoardWrite3.bo'">	
    		<%}}%>
<input type="button" value="메인으로" 
       onclick="location.href='./main.fo'">
<%
//페이지 출력
if(count!=0){ 
	//이전
	if(startPage>pageBlock){
		%><a href="./BoardList3.bo?pageNum=<%=startPage-pageBlock%>">[이전]</a><%
	}
	// 현재페이지 빨간색, 클릭 안됨
	for(int i=startPage; i<=endPage; i++){
		if(i==pNum){%><span id="i"><%=i%></span><%}else{
		%><a id="i" href="./BoardList3.bo?pageNum=<%=i%>"><%=i%></a><%
	}}
	// 다음
	if(endPage < pageCount){
		%><a href="./BoardList3.bo?pageNum=<%=startPage+pageBlock%>">[다음]</a>
		<%
		}
}
%>
<form action="listSearch3.bo" method="get">
<select name="selectSearch">
    <option value="subject">제목</option>
    <option value="content">내용</option>
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