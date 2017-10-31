<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="net.member.db.*"%>
    <%@ page import="java.util.List" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="./js/HuskyEZCreator.js" charset="utf-8"></script>
<link href="./css/inc.css" rel="stylesheet" type="text/css">
<link href="./css/subpage.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="./js/jquery-3.2.0.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
</head>
<body>
<!-- 왼쪽 메뉴 -->
<jsp:include page="../inc/leftMenu.jsp"></jsp:include>
<!-- 왼쪽 메뉴 -->

<div id="wrap">
	<div id="article_head">
		<div id="article_title">카테고리 등록</div>
	<div class="empty"></div>
			<div id="article_script"></div>
		</div>
		<div id="clear"></div>
	<article>
	<div id="category_form">
		<form action="./CategoryWriteAction.bo" id="fr" method="post" >
			<table>
				<tr>
					<th>카테고리명</th>
					<td><input type="text" name="car_name">
						</td>
				</tr>
				<tr>
					<th>카테고리</th>
					<td><select name="car_pt">
							<option value="">선택하세요</option>
							<option value="T">T (상품 페이지)</option>
							<option value="P">P (패키지 페이지)</option>
					</select></td>
				</tr>
				
			</table>
			<input type="submit" id="save" value="등록">
			<input type="button" value="취소" onclick="history.back()">
		</form>
	</div>
	</article>
</div>
<!-- 오른쪽 메뉴 -->
<jsp:include page="../inc/rightMenu.jsp"></jsp:include>
<!-- 오른쪽 메뉴 -->
<!-- 푸터 메뉴 -->
	<jsp:include page="../inc/footer.jsp"></jsp:include>
<!-- 푸터 메뉴 -->
</body>
</html>