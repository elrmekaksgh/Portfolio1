<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="./css/popup.css" rel="stylesheet" type="text/css">
<%
	request.setCharacterEncoding("utf-8");
	String id = (String) session.getAttribute("id");
%>
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script>
	function code() {
		new daum.Postcode(
				{
					oncomplete : function(data) {
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
						if (data.userSelectedType === 'R') {
							//법정동명이 있을 경우 추가한다.
							if (data.bname !== '') {
								extraAddr += data.bname;
							}
							// 건물명이 있을 경우 추가한다.
							if (data.buildingName !== '') {
								extraAddr += (extraAddr !== '' ? ', '
										+ data.buildingName : data.buildingName);
							}
							// 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
							fullAddr += (extraAddr !== '' ? ' (' + extraAddr
									+ ')' : '');
						}

						// 우편번호와 주소 정보를 해당 필드에 넣는다.
						document.getElementById('postcode').value = data.zonecode; //5자리 새우편번호 사용
						document.getElementById('address1').value = fullAddr;

						// 커서를 상세주소 필드로 이동한다.
						document.getElementById('address2').focus();
					}
				}).open();
	}
</script>
<script src = "./js/jquery-3.2.0.js"></script>
<script type="text/javascript">
function check(){
	if($('#name').val()==""){
		alert("name empty");
		return false;
	}
	if($('#mobile').val()==""){
		alert("mobile empty");
		return false;
	}
	if($('#postcode').val()==""||$('#address1').val()==""||$('#address2').val()==""){
		alert("Address empty");
		return false;
	}
}
</script>
</head>
<body>
	<div id="receive_add">
	<form action ="./Add_AddressAction.mo" method="post" name="fr" onsubmit="return check()">
		<table>
			<tbody>
			<tr>
			<th>이름</th>
			<td><input id="name" type="text" name ="name"></td></tr>
			<tr>
			<th>연락처</th>
			<td><input id="mobile" type="tel" name="mobile" placeholder="'-'를 제외하고 입력해 주세요" maxlength="11"
					title="'-'를 제외한 10~11자리 숫자를 입력해 주세요" pattern="[0-9]{10}[0-9]?"></td></tr>
			<tr>
			<th>우편번호</th>
			<td><input type="text" id="postcode" name="postcode" placeholder="우편번호">
				<input type="button" class="dup" onclick="code()" value="우편번호 찾기"></td>
			</tr>
			<tr>
			<th>주소</th>
			<td><input type="text" name="address1" id="address1" placeholder="주소"></td></tr>
			<tr>
			<th>상세 주소</th>
			<td>
			<input type="text" id="address2" name="address2" placeholder="상세주소"></td>
			</tr>
			</tbody>
		</table><br>
		위 배송지를 기본 배송지로 설정 하시겠습니까?<br>
		<input type="radio" value="1" name="basic_setting" checked="checked">예
		<input type="radio" value="0" name="basic_setting">아니오<br><br>
		<input type = "submit" value="배송지 추가">
		<input type = "button" value="배송지 목록" onclick="history.back()">
	</form>
		</div>
</body>
</html>