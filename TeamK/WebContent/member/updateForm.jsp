
<%@page import="net.member.db.MemberBean"%>
<%@page import="net.member.db.MemberDAO"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="java.awt.event.FocusAdapter"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>TeamK 여행사</title>
<link href="./css/inc.css" rel="stylesheet" type="text/css">
<link href="./css/subpage.css" rel="stylesheet" type="text/css">
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js"></script>
<script type="text/javascript">

$(function(){ 
	  //크롬등에서 ime-mode:disabled 정상작동 되지않으므로 정규식으로 처리
	   $('#id').keyup(function(event){
	   $(this).val($(this).val().replace(/[^a-zA-Z0-9]/g,''));
	   });

	});

function passchange() {
	fid=document.fr.id.value;
	window.open("./member/passchange.jsp?userid="+fid, "", "width=400,height=250");
	
}
function sendmailup() {			
	var email = document.fr.email.value;
	window.open("./MemberEmailUpdate.me?email="+email+"&echeck=0","","width=400,height=200");

}
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
function submit() {
	if (document.fr.eckecknum.value == "") {
		alert("e-mail 인증을 완료해주세요");
		document.fr.email.focus();
		return false;
	}
}

</script>
</head>
<body>
		<%
		String id = (String) session.getAttribute("id");
		if (id == null) {response.sendRedirect("./member/MemberLogin.me");}
		MemberBean mb=(MemberBean)request.getAttribute("mb");
	%>
	<!--왼쪽 메뉴 -->
	<div>
		<jsp:include page="../inc/leftMenu.jsp"></jsp:include>
	</div>
	<!--왼쪽 메뉴 -->
	<div id="wrap">
		<div id="article_head">
			<div id="article_title">회원정보수정</div>
			<div class="empty"></div>
			<div id="article_script">회원님의 정보를 수정합니다.</div>
		</div>
		<div id="clear"></div>
		<article>
			<div id="m_update_form">
				<form action="./MemberUpdateAction.me" method="post" name="fr" onsubmit="return submit()">
					
					<label for="id">아이디</label>
					<input type="text" name="id" id="id" value="<%=mb.getId()%>" readonly><br> 
					
					<label for="pass">비밀번호</label>
					<input type="password" name="pass" id="pass">
					<input type="button" value="비밀번호변경"  onclick="passchange()"><br>
					
					<label for="name">이름</label>
					<input type="text" name="name" id="name" value="<%=mb.getName()%>"><br> 
					
					<label for="address">주소</label>
					<input type="text" id="sample6_postcode" placeholder="우편번호" name="postcode" value="<%=mb.getPostcode()%>">
					<input type="button" onclick="sample6_execDaumPostcode()" value="우편번호 찾기" name="btnPostcode"><br>
					<input type="text" id="sample6_address" placeholder="주소" name="address1" value="<%=mb.getAddress1()%>"><br>
					<input type="text" id="sample6_address2" placeholder="상세주소" name="address2" value="<%=mb.getAddress2()%>"><br>
					
					<label for="mobile">전화번호</label>
					<input type="text" name="mobile" id="mobile" value="<%=mb.getMobile()%>"><br> 
					
					<label for="email">이메일</label>
					<input type="text" name="email" id="email" value="<%=mb.getEmail()%>"  readonly>
					<input type="button" value="변경하기" onclick="sendmailup()">
					<input type="hidden" name="eckecknum" value="1"><br> 
					<input type="submit" value="수정완료">
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