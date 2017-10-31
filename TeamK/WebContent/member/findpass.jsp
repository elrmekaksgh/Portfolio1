<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>TeamK 여행사</title>
<link href="./css/inc.css" rel="stylesheet" type="text/css">
<link href="./css/subpage.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js"></script>
<script type="text/javascript">


$(function(){ 
	  //크롬등에서 ime-mode:disabled 정상작동 되지않으므로 정규식으로 처리
	   $('#id').keyup(function(event){
	   $(this).val($(this).val().replace(/[^a-zA-Z0-9]/g,''));
	   });

	});

function winopen(){
	if (document.fr.name.value == "") {
		alert("이름을 입력하세요");
		document.fr.name.focus();
		return false;
	}
	if (document.fr.id.value == "") {
		alert("id를 입력하세요"); 
		document.fr.id.focus();
		return false;
	}
	if (document.fr.email.value == "") {
		alert("e-mail을 입력하세요");
		document.fr.email.focus();
		return false;
	}
	if (document.fr.eckecknum.value == "") {
		alert("e-mail 인증을 완료해주세요");
		document.fr.email.focus();
		return false;
	}
	document.fr.id.style.imeMode = "disabled"
}
function sendmail() {			
	var email = document.fr.email.value;
	
	if(email == ""){alert("입력 된 이메일이 없습니다.");}
	else{
	window.open("./MemberEmail.me?email="+email+"&echeck=0","","width=400,height=200");
	}
}
</script>
</head>
<body>
<%
String id = (String) session.getAttribute("id");
if (id != null) {
	response.sendRedirect("./main.fo");
}
%>
	<!--왼쪽 메뉴 -->
	<div>
		<jsp:include page="../inc/leftMenu.jsp"></jsp:include>
	</div>
	<!--왼쪽 메뉴 -->
	<div id="wrap">
		<div id="article_head">
			<div id="article_title">비밀번호 찾기</div>
			<div class="empty"></div>
			<div id="article_script"></div>
		</div>
		<div id="clear"></div>
		<article>
			<div id="findpass_form">
				<form action="./MemberFindPassAction.me" method="post" name="fr" onsubmit="return winopen()">
					<label for="name">이름</label><input type="text" name="name" id="name"><br>
					<label for="id">아이디</label><input type="text" name="id" id="id"><br>					
					<label for="email">이메일</label><input type="text" name="email" 
					id="email" placeholder="반드시 이메일 인증을 해주세요.">
					<input type="button" value="이메일 인증" onclick="sendmail()">
					<input type="hidden" name="eckecknum"><br>
					<p>※ 회원 가입시 작성하신 이름, ID, 이메일 주소가 일치해야<br>이메일 인증을 받으실 수 있습니다.<br>
					※ 이메일 인증을 완료하시면 해당 이메일 주소로 임시 비밀번호가 발송됩니다. 이후 회원정보에서 비밀번호를 변경해주세요.<br></p>
					<input type="submit" value="확인">
					<input type="button" value="돌아가기" onclick="history.back()">
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