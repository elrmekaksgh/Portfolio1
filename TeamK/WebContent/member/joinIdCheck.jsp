<%@page import="net.member.db.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>TeamK 여행사</title>
<link href="../css/popup.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js"></script>
<%request.setCharacterEncoding("UTF-8"); %>


<script type="text/javascript">
	function result() {
		if(document.wfr.userid.value<4){
			alert("아이디는 4~10글자로 정해주세요");
			document.wfr.userid.focus();
			return false;
		}else{
			opener.document.fr.id.value = document.wfr.userid.value;
			opener.document.fr.idchecknum.value = document.wfr.idchecknum.value;
			window.close(); 
		}
	}
	
	
	$(function(){ 
		  //크롬등에서 ime-mode:disabled 정상작동 되지않으므로 정규식으로 처리
		   $('#id').keyup(function(){
		   $(this).val($(this).val().replace(/[^a-zA-Z0-9]/g,''));
		   });

		});
	
	
	function check_key() {
		var char_ASCII = event.keyCode;

		//숫자
		if (char_ASCII >= 48 && char_ASCII <= 57)
			return 1;
		//엔터키 esc키
		else if(char_ASCII == 13 || char_ASCII == 27)
			return 1;
		//영어
		else if ((char_ASCII >= 65 && char_ASCII <= 90)
				|| (char_ASCII >= 97 && char_ASCII <= 122))
			return 2;
		//특수기호
		else if ((char_ASCII >= 33 && char_ASCII <= 47)
				|| (char_ASCII >= 58 && char_ASCII <= 64)
				|| (char_ASCII >= 91 && char_ASCII <= 96)
				|| (char_ASCII >= 123 && char_ASCII <= 126))
			return 4;
		//한글
		else
			return 0;
	}

	//텍스트 박스에 숫자와 영문만 입력할수있도록
	function nonHangulSpecialKey() {

		if (check_key() != 1 && check_key() != 2) {

			alert("숫자나 영문자만 입력하세요!");
			location.reload();
			return false;
		}
		//한글입력 불가
		//document.wfr.userid.style.imeMode = "disabled"
	}

</script>
</head>
<body>
	<!-- WebContent/member/joinIdCheck.jsp -->
	<%
		String id = request.getParameter("userid");
		MemberDAO mdao = new MemberDAO();
	%>
	<div id="idcheck">
	<form action="joinIdCheck.jsp" method="post" name="wfr">
		<input type="text" name="userid" value="<%=id%>" id="id" onkeypress="nonHangulSpecialKey()" 
		style="ime-mode:disabled" maxlength="10">

		<input type="submit" value="중복확인">
		<p>
		<%if(id.length()>=4 && id.length()<=10){
			int check = mdao.joinIdCheck(id);
			if (check == 1) {
				%>
				<script type="text/javascript">
				document.wfr.userid.focus();
				</script>
				<%
				out.println(id + "는 사용 불가능한<br>아이디 입니다.");
				%>
				<%
			} else if (check == 0) {
				%>
				<script type="text/javascript">
				document.wfr.select.focus();
				</script>
				<%
				out.println(id + "는 사용 가능한<br>아이디 입니다.");
		%></p>
		<input type="button" name="select" value="아이디 선택" onclick="return result()">
		<input type="hidden" name="idchecknum" value="1">
		<%
			}
		}else if(id.length()<4 || id.length()>10){
			out.println("아이디는 4~10글자<br>영문 + 숫자로 정해주세요.");
			%>
			<script type="text/javascript">
			document.wfr.userid.focus();
			</script>
			<%
		}
		%>
		<br>
		
	</form>
	</div>
</body>
</html>
