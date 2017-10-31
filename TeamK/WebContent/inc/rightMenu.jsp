<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link href="./css/inc.css" rel="stylesheet" type="text/css">
<link href="./css/subpage.css" rel="stylesheet" type="text/css">
<div id="submenu">
	<ul>
		<%
		String id = (String)session.getAttribute("id");
		if(id==null || id==""){
		%>
		<li><a href="./MemberLogin.me">로그인</a></li>
		<li><a href="./MemberJoin.me">회원가입</a></li>
		<br> 
		<%}else if(id!=null || id!=""){%>
		<li><a href="./MemberLogout.me">로그아웃</a></li>
		<li><a href="./MemberInfo.me">내 정보</a></li>
		<br>
		<br>
		<li><a href="./MyBasketList.bns">장바구니</a></li>
		<li><a href="./MyInterestList.ins">찜 리스트</a></li>
		<li><a href="./MyThingOrderList.mo">상품 주문목록</a></li>
		<li><a href="./MyPackOrderList.mo">패키지 주문목록</a></li>
		<br><br>
		<%if(id.equals("admin")){%>
			<li><a href="./AdminOrderList.ao">고객 주문 관리</a></li> 
			<li><a href="./MemberList.me">고객 정보 관리</a></li>
			<li><a href="./Category.bo">카테고리추가</a></li>
		<%}
		}
		 %>
	</ul>
	<div id="map">
	<p>원하는 지역을<br>선택해주세요.</p>
	<object type="image/svg+xml" data="./img/Map_of_South_Korea-blank.svg">Your browser does not support SVGs</object>
	</div>
<!-- 	<embed height="250px" width="150px" src="http://www.gagalive.kr/livechat1.swf?chatroom=~~~new_TeamK"></embed> -->
	<a href='http://itwillbs6.cafe24.com/Main.me' id = "banner1" target="top">
	<img src='./img/tomcat_banner.gif' style="width: 150px; height:70px;"></a>
</div>