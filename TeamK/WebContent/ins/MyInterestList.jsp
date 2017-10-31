<%@page import="net.ins.db.interestBEAN"%>
<%@page import="java.util.List"%>
<%@ page import="java.text.DecimalFormat" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="./css/inc.css" rel="stylesheet" type="text/css">
<link href="./css/subpage.css" rel="stylesheet" type="text/css">
<script src="./js/jquery-3.2.0.js"></script>
<%
request.setCharacterEncoding("utf-8");
int []count = (int[])request.getAttribute("count");
List<interestBEAN> InterestPack = (List<interestBEAN>) request.getAttribute("InterestPack");
List<interestBEAN> InterestThing = (List<interestBEAN>) request.getAttribute("InterestThing"); %>
</head>
<script type="text/javascript">
	function inter_pack_move(select)
	{
		var num = $("#num" + select).val();
		location.href="./PackContent.po?num=" + num;
	}
	
	function inter_thing_move(select)
	{
		var num = $("#thingnum" + select).val();
		var carnum = $("#carnum" + select).val();
		location.href="./ProductContent.bo?num=" + num + "&car_num=" + carnum;
	}
</script>
<body>
	<!--왼쪽 메뉴 -->
	<div>
		<jsp:include page="../inc/leftMenu.jsp"></jsp:include>
	</div>
	<!--왼쪽 메뉴 -->
	<div id="wrap">
<div id="article_head">
<div id="article_title">
	찜 리스트
</div>
<div class="empty"></div>
</div>
<article>
		<h1 style="color:#06c; font-size: 23px;">패키지 찜 리스트</h1><br>
		<div id="packInterest_list">
		<%
			if (InterestPack.size() == 0) {
		%>
		찜해둔 패키지가 없습니다!
		<%
			} else {
		%>
		<table>
			<tr>
				<th class="inb_img"></th>
				<th id="num">상품명</th>
				<th id="num">가격</th>
				<th></th>
			</tr>
			<%
				for (int i = 0; i < InterestPack.size(); i++) {
					interestBEAN inb = InterestPack.get(i);
					DecimalFormat Commas = new DecimalFormat("#,###");
					String cost = (String)Commas.format(inb.getCost());
			%>
			<tr>
				<input type="hidden" id="num<%=i %>" value="<%=inb.getOri_num() %>">
				<td class="inb_img ev_hover" onclick="inter_pack_move(<%=i %>);"><img id="inb_img" alt="" src="./upload/<%=inb.getImg() %>"></td>
				<td onclick="inter_pack_move(<%=i %>);" class="ev_hover"><%=inb.getSubject()%><br>
				<%=inb.getIntro() %></td>
				<td><%=cost%>원</td>
				<td>
				<input type="button" value="찜 취소" onclick = "location.href='./MyInterestDel.ins?n=<%=inb.getInter_num()%>'"></td>
				</tr>
			<%
				}
			%>
		</table>
		<%if(count[0]>5); %><a href = "./MyInterest.ins?TY=P&pageNum=1">더 보기 +</a><%; %>
		<%
			}
		%><br><br>
		<hr><br>
		<h1 style="color:#06c; font-size: 23px;">상품 찜 리스트</h1><br>

		<%
			if (InterestThing.size() == 0) {
		%>
		찜해둔 상품이 없습니다!
		<%
			} else {
		%>
		<table>
			<tr>
				<th class="inb_img"></th>
				<th id="num">상품명</th>
				<th id="num">가격</th>
				<th></th>
			</tr>
			<%
				for (int i = 0; i < InterestThing.size(); i++) {
						DecimalFormat Commas = new DecimalFormat("#,###");
						interestBEAN inb = InterestThing.get(i);
						String cost = (String)Commas.format(inb.getCost());
			%>
			<tr>
			<input type="hidden" id="thingnum<%=i %>" value="<%=inb.getOri_num() %>">
			<input type="hidden" id="carnum<%=i %>" value="<%=inb.getCar_num() %>">
			<td class="inb_img ev_hover" onclick="inter_thing_move(<%=i %>);"><img id="inb_img" alt="" src="./upload/<%=inb.getImg() %>"></td>
			<td class="inb_img ev_hover" onclick="inter_thing_move(<%=i %>);"><%=inb.getSubject()%><br>
				<%=inb.getIntro() %></td>
			<td><%=cost%>원</td>
			<td>
				<input type="button" value="찜 취소" onclick = "location.href='./MyInterestDel.ins?n=<%=inb.getInter_num()%>'"></td>
			</tr>
			<%
				}
			%>
		</table>
		<%if(count[1]>5); %><a href = "./MyInterest.ins?TY=T&pageNum=1">더 보기 +</a><%; %>
		<%
			}
		%>
		<br>
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