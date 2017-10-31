<%@page import="net.member.db.MemberBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, 
maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, target-densitydpi=medium-dpi" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Imagetoolbar" content="no" />
<title>TeamK 여행사</title>
<link href="./css/inc.css" rel="stylesheet" type="text/css">
<link href="./css/subpage.css" rel="stylesheet" type="text/css">
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js"></script>

<%
request.setCharacterEncoding("UTF-8"); 
String ctx = request.getContextPath(); //콘텍스트명 얻어오기.
response.setHeader("Pragma-directive", "no-cache");
response.setHeader("Cache-directive", "no-cache");
response.setHeader("Pragma", "no-cache");
response.setHeader("Cache-Control", "no-cache");
response.setDateHeader("Expires",0); 
String id = (String) session.getAttribute("id");
%>

<script type="text/javascript">

$(function(){ 
	  //크롬등에서 ime-mode:disabled 정상작동 되지않으므로 정규식으로 처리
	   $('#id').keyup(function(event){
	   $(this).val($(this).val().replace(/[^a-zA-Z0-9]/g,''));
	   });

	});


	
	
	
function sample6_execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var fullAddr = ''; // 최종 주소 변수
            var extraAddr = ''; // 조합형 주소 변수

            // 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                fullAddr = data.roadAddress;

            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                fullAddr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 조합한다.
            if(data.userSelectedType === 'R'){
                //법정동명이 있을 경우 추가한다.
                if(data.bname !== ''){
                    extraAddr += data.bname;
                }
                // 건물명이 있을 경우 추가한다.
                if(data.buildingName !== ''){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
                fullAddr += (extraAddr !== '' ? ' ('+ extraAddr +')' : '');
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('sample6_postcode').value = data.zonecode; //5자리 새우편번호 사용
            document.getElementById('sample6_address').value = fullAddr;

            // 커서를 상세주소 필드로 이동한다.
            document.getElementById('sample6_address2').focus();
        }
    }).open();
}

	function idCheck() {
		if (document.fr.id.value == "") {
			alert("id입력하세요");
			document.fr.id.focus();
			return;
		}else{ 
		fid = document.fr.id.value;
		window.open("./member/joinIdCheck.jsp?userid=" + fid, "",
				"width=400,height=200");
		}
		
		//한글입력 불가
		//document.fr.id.style.imeMode = "disabled"
	}
	function check_key() {
		var char_ASCII = event.keyCode;

		//숫자
		if (char_ASCII >= 48 && char_ASCII <= 57)
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
	function nonHangulSpecialKey() {

		if (check_key() != 1 && check_key() != 2) {

			alert("숫자나 영문자만 입력하세요!");
			location.reload();
			return false;
		}
		
	}
	
	function sendmail() {			
		var email = document.fr.email.value;
		
		if(email == ""){alert("입력 된 이메일이 없습니다.");}
		else{
		window.open("./MemberEmail.me?email="+email+"&echeck=0","","width=400,height=200");
		}
	}
	function winopen() {

		if (document.fr.id.value == "") {
			alert("id를 입력하세요");
			document.fr.id.focus();
			return false;
		} else if (document.fr.id.value.length<4 
			|| document.fr.id.value.length>10) {
			alert("id는 4~10자 사이로입력하세요");
			document.fr.id.focus();
			return false;
		}
		
		if(document.fr.idchecknum.value != 1){
			alert("ID중복확인을 해주세요.");
			document.fr.id.focus();
			return false;
		}
		if (document.fr.pass.value == "") {
			alert("비밀번호를 입력하세요");
			document.fr.pass.focus();
			return false;
		} else if (document.fr.pass.value.length<4 
			|| document.fr.pass.value.length>10) {
			alert("비밀번호는 4~10자 사이로입력하세요");
			document.fr.pass.focus();
			return false;
		}
		if (document.fr.pass2.value == "") {
			alert("비밀번호를 다시한번 적어주세요");
			document.fr.pass2.focus();
			return false;
		} else if (document.fr.pass2.value != document.fr.pass.value) {
			alert("비밀번호를 똑같이 적어주세요");
			document.fr.pass2.focus();
			return false;
		}
		if (document.fr.name.value == "") {
			alert("이름을 입력하세요");
			document.fr.name.focus();
			return false;
		}
		if (document.fr.mobile.value == "") {
			alert("전화번호적으세요");
			document.fr.mobile.focus();
			return false;
		}
		if (document.fr.postcode.value == "") {
			alert("우편번호 검색을 해주세요.");
			document.fr.btnPostcode.focus();
			return false;
		}
		if (document.fr.address1.value == "") {
			alert("주소를 입력해주세요");
			document.fr.address1.focus();
			return false;
		}
		if (document.fr.address2.value == "") {
			alert("나머지 주소를 입력해주세요");
			document.fr.address2.focus();
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
		
	}
	document.fr.submit();

</script>
</head>
<body>
<%

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
			<div id="article_title">회원가입</div>
			<div class="empty"></div>
			<div id="article_script"></div>
		</div>
		<div id="clear"></div>
		<article>
			<div id="insert_form"> 
			<form action="./MemberJoinAction.me" method="post" name="fr" onsubmit="return winopen()">
				<label for="id">아이디</label>
				<input type="text" name="id" id="id"
				onkeypress="nonHangulSpecialKey()" placeholder="ID는 영문, 숫자만 가능"  maxlength="10">
				<input type="hidden" name="idchecknum"> 
				<input type="hidden" name="name2" value="0"> 
				<input type="button" value="중복확인" onclick="idCheck()"><br>
				
				<label for="pass">비밀번호</label><input type="password" name="pass" id="pass"><br> 
				<label for="pass2">비밀번호 확인</label><input type="password" name="pass2" id="pass2"><br> 
				
				<label for="name">이름</label><input type="text" name="name" id="name"><br> 
				
				<label for="mobile">전화번호</label><input type="text"name="mobile" id="mobile"><br>
				
				
				<!-- 주소 다음api -->
				<label for="address">주소</label>
				<input type="text" id="sample6_postcode" placeholder="우편번호" name="postcode">
				<input type="button" onclick="sample6_execDaumPostcode()" value="우편번호 찾기" name="btnPostcode"><br>
				<input type="text" id="sample6_address" placeholder="주소" name="address1"><br>
				<input type="text" id="sample6_address2" placeholder="상세주소" name="address2"><br>

				<!-- 이메일체크 -->
				<label for="email">이메일</label>
				<input type="text" name="email" id="email" placeholder="반드시 이메일 인증을 해주세요.">
				<input type="button" value="이메일 인증" onclick="sendmail()">
				<input type="hidden" name="eckecknum"> <br>
				
				<br>
				
				<input type="submit" name="submit" value="가입"> 
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