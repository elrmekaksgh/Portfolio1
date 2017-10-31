<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
    <%@ page import="net.pack.db.CategoryBean" %>
    <%@ page import="java.util.List" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="./css/inc.css" rel="stylesheet" type="text/css">
<link href="./css/subpage.css" rel="stylesheet" type="text/css">
<script src="./js/jquery-3.2.0.js"></script>
</head>
<body>

<%
	List CategoryList = (List)request.getAttribute("CategoryList");
%>
<script type="text/javascript">
	//패키지 검색 시 지역 선택
	function input_chk()
	{
		var val = $("#area option:selected").val(); 
		if (val == "")
		{
			alert("지역을 선택해주세요");
	    		return false;
		}
		return true;
	}
</script>
	<!--왼쪽 메뉴 -->
	<jsp:include page="../inc/leftMenu.jsp"></jsp:include>
	<!--왼쪽 메뉴 -->
	<!--지도 들어갈 부분 -->
	<div id="map">
		<object type="image/svg+xml" data="./img/Map_of_South_Korea-blank.svg">Your browser does not support SVGs</object>
		<center>원하는 지역을 선택해주세요.</center>
		<div id="seoul"><img alt="seoul" src="./img/home.png"><h2>서울</h2><p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum sapien nisi, condimentum ac lorem et, blandit iaculis quam. Nunc mattis facilisis nisl nec efficitur. Maecenas tincidunt orci vel euismod condimentum. Duis nec nisi tincidunt est interdum porttitor. Donec et ante sapien.</p></div>
		<div id="busan"><img alt="seoul" src="./img/home.png"><h2>부산</h2><p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum sapien nisi, condimentum ac lorem et, blandit iaculis quam. Nunc mattis facilisis nisl nec efficitur. Maecenas tincidunt orci vel euismod condimentum. Duis nec nisi tincidunt est interdum porttitor. Donec et ante sapien.</p></div>
		<div id="gyeonggi"><img alt="seoul" src="./img/home.png"><h2>경기도</h2><p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum sapien nisi, condimentum ac lorem et, blandit iaculis quam. Nunc mattis facilisis nisl nec efficitur. Maecenas tincidunt orci vel euismod condimentum. Duis nec nisi tincidunt est interdum porttitor. Donec et ante sapien.</p></div>
		<div id="gangwon"><img alt="seoul" src="./img/home.png"><h2>강원도</h2><p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum sapien nisi, condimentum ac lorem et, blandit iaculis quam. Nunc mattis facilisis nisl nec efficitur. Maecenas tincidunt orci vel euismod condimentum. Duis nec nisi tincidunt est interdum porttitor. Donec et ante sapien.</p></div>
		<div id="chungbuk"><img alt="seoul" src="./img/home.png"><h2>충청북도</h2><p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum sapien nisi, condimentum ac lorem et, blandit iaculis quam. Nunc mattis facilisis nisl nec efficitur. Maecenas tincidunt orci vel euismod condimentum. Duis nec nisi tincidunt est interdum porttitor. Donec et ante sapien.</p></div>
		<div id="chungnam"><img alt="seoul" src="./img/home.png"><h2>충청남도</h2><p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum sapien nisi, condimentum ac lorem et, blandit iaculis quam. Nunc mattis facilisis nisl nec efficitur. Maecenas tincidunt orci vel euismod condimentum. Duis nec nisi tincidunt est interdum porttitor. Donec et ante sapien.</p></div>
		<div id="jeonbuk"><img alt="seoul" src="./img/home.png"><h2>전라북도</h2><p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum sapien nisi, condimentum ac lorem et, blandit iaculis quam. Nunc mattis facilisis nisl nec efficitur. Maecenas tincidunt orci vel euismod condimentum. Duis nec nisi tincidunt est interdum porttitor. Donec et ante sapien.</p></div>
		<div id="jeonnam"><img alt="seoul" src="./img/home.png"><h2>전라남도</h2><p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum sapien nisi, condimentum ac lorem et, blandit iaculis quam. Nunc mattis facilisis nisl nec efficitur. Maecenas tincidunt orci vel euismod condimentum. Duis nec nisi tincidunt est interdum porttitor. Donec et ante sapien.</p></div>
		<div id="gyeongbuk"><img alt="seoul" src="./img/home.png"><h2>경상북도</h2><p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum sapien nisi, condimentum ac lorem et, blandit iaculis quam. Nunc mattis facilisis nisl nec efficitur. Maecenas tincidunt orci vel euismod condimentum. Duis nec nisi tincidunt est interdum porttitor. Donec et ante sapien.</p></div>
		<div id="gyeongnam"><img alt="seoul" src="./img/home.png"><h2>경상남도</h2><p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum sapien nisi, condimentum ac lorem et, blandit iaculis quam. Nunc mattis facilisis nisl nec efficitur. Maecenas tincidunt orci vel euismod condimentum. Duis nec nisi tincidunt est interdum porttitor. Donec et ante sapien.</p></div>
		<div id="jeju"><img alt="seoul" src="./img/home.png"><h2>제주도</h2><p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum sapien nisi, condimentum ac lorem et, blandit iaculis quam. Nunc mattis facilisis nisl nec efficitur. Maecenas tincidunt orci vel euismod condimentum. Duis nec nisi tincidunt est interdum porttitor. Donec et ante sapien.</p></div>
	</div>
	<!--지도 들어갈 부분 -->
	
	<!--검색하는 부분 -->
	<div id="wrap">
	<div id="search">
		<form action="./PackSearchAction.po" method="get" name="fr" id="search" onsubmit="return input_chk();">
<!-- 			<input type="text" name="keyword"> -->
			<select id="area" name="area">
				<option value="">선택하세요</option>
				<%
					CategoryBean cb;
					for (int i = 0; i < CategoryList.size(); i++)
					{
						cb =(CategoryBean)CategoryList.get(i);
				%>	
					<option value="<%=cb.getCar_name() %>"><%=cb.getCar_name() %></option>
				<%
					}
				%>
			</select>
			<input type="submit" value="검색">
		</form>
	</div>
	<!--검색하는 부분 -->
	<div id="index_wrap">
		<div id="main_menu_box">
		<%
		String id = (String) session.getAttribute("id");
		if(id==null || id==""){ %>
		<input type="button" value="　로그인" onclick="location.href='./MemberLogin.me'" class="login">
		<input type="button" value="들어가기" onclick="location.href='./main.fo'" class="enter">
		<input type="button" value="회원가입" onclick="location.href='./MemberJoin.me'" class="join">
		<%}else if(id!=null){%>	
		<input type="button" value="로그아웃" onclick="location.href='./MemberLogout.me'" class="login">
		<input type="button" value="들어가기" onclick="location.href='./main.fo'" class="enter">
		<input type="button" value="회원정보" onclick="location.href='./MemberInfo.me'" class="join">
		<%} %>
		</div>
	</div>
	
	</div>
	<!--오른쪽 메뉴 -->
	<jsp:include page="../inc/rightMenu.jsp"></jsp:include>
	<!--오른쪽 메뉴 -->
	<jsp:include page="../inc/footer.jsp"></jsp:include>
</body>
</html>