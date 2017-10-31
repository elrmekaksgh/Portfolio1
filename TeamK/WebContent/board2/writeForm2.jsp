<%@page import="net.member.db.MemberBean"%>
<%@page import="net.member.db.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
function check() {
	if(document.fr.subject.value==""){
		alert("제목을 입력해주세요");
		document.fr.subject.focus();
		return false;
	}
	if(document.fr.content.value==""){
		alert("내용을 입력해주세요");
		document.fr.content.focus();
		return false;
	}
	if(document.fr.email.value==""){
		alert("이메일을 입력해주세요");
		document.fr.email.focus();
		return false;
	}
}
</script>
<link href="./css/inc.css" rel="stylesheet" type="text/css">
<link href="./css/subpage.css" rel="stylesheet" type="text/css">
<%String id = (String)session.getAttribute("id");%>
<%
MemberDAO mdao = new MemberDAO();
MemberBean mb = mdao.getMember(id);
%>
</head>
<body>
	<!--왼쪽 메뉴 -->
	<div>
		<jsp:include page="../inc/leftMenu.jsp"></jsp:include>
	</div>
	<!--왼쪽 메뉴 -->
	<div id="wrap">
		<div id="article_head">
			<div id="article_title"><img src="./img/qna2.png" width="35px" style="margin-right: 8px; vertical-align: bottom;">Q&A 게시판</div>
			<div class="empty"></div>
			<div id="article_script">궁금한것은 질문해주세요.</div>
		</div>
<div id="clear"></div>
<article>
<div id="board_write">
<form action="./BoardWriteAction2.bo" method="post" name="fr" enctype="multipart/form-data" onsubmit="return check()">
<input type="hidden" value="2" name="type">
<input type="hidden" name="id" value="<%=id%>">
<label for="email">답변 받으실<br>이메일 주소</label><input type="text" name="email" id="email" value="<%=mb.getEmail()%>"><br><br>
<label for="subject">제목</label><input type="text" name="subject" id="subject" maxlength="40"><br><br>
<textarea id="ir1" rows="30" cols="80" name="content" id="content"></textarea><br><br>
<label for="file1">첨부파일1</label><input type="file" name="file1" id="file1"><br>
<label for="file2">첨부파일2</label><input type="file" name="file2" id="file2"><br>
<label for="file3">첨부파일3</label><input type="file" name="file3" id="file3"><br>
<label for="file4">첨부파일4</label><input type="file" name="file4" id="file4"><br>
<label for="file5">첨부파일5</label><input type="file" name="file5" id="file5"><br>
<div class="clear"></div><br>
<input type="submit" id="save" value="글쓰기">
<input type="button" value="글목록" onclick="location.href='./BoardList2.bo?pageNum=1'">
</form>
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