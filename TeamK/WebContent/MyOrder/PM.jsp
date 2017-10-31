<%@page import="net.mod.db.PackMemberBEAN"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="./css/popup.css" rel="stylesheet" type="text/css">
<script src = "./js/jquery-3.2.0.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	
});
function leader_check(){
	if($('#leader_name').val().length==0||
		$('#leader_mobile').val().length==0||
		$('#leader_birthday').val().length==0||
		$('#leader_first_name').val().length==0||
		$('#leader_last_name').val().length==0){
		alert("대표자 정보를 정확히 입력해 주십시오");
		return false;
	}
	
}
</script>
<title>여행자 정보 입력</title>
</head>
<%
request.setCharacterEncoding("utf-8");
List<PackMemberBEAN> PM_List = (List<PackMemberBEAN>)request.getAttribute("PM_List");
%>
<body>

<form action="./PM_Info_Update.mo" method = "post"
	name = "fr" onsubmit="return leader_check()" id="pm">
<h3>여행자 정보 입력</h3>
<%
if(PM_List.size()!=0){
	PackMemberBEAN pm = PM_List.get(0);
%>
	<h4>대표자</h4>
	<h5 style="color:red">※ 대표자 정보는 반드시 입력 하셔야합니다!</h5>
	<input type="hidden" value = "<%=pm.getPm_num()%>" name = "pm_num">
	<table>
	<tr>
		<td>이름</td>
		<td><input type = "text" name = "name" id="leader_name"
			<%if(pm.getHangul_name()!=null){ %>
			value="<%=pm.getHangul_name()%>"
			<%}else{%> placeholder="Ex)홍길동"<%} %>>
		</td>
		<td>연락처</td>
		<td><input type = "text" name = "mobile" id="leader_mobile"
			<%if(pm.getMobile()!=null){ %>
			value="<%=pm.getMobile()%>"
			<%}else{%> placeholder="Ex)010-1234-5678"<%} %>>
		</td>
	</tr>
	<tr>
		<td>영문 이름</td>
		<td><input type = "text" name = "First_name" id="leader_first_name"
			<%if(pm.getFirst_name()!=null){ %>
			value="<%=pm.getFirst_name()%>"
			<%}else{%> placeholder="Ex)Gil Dong"<%} %>>
		</td>
		<td>영문 성</td>
		<td><input type = "text" name = "Last_name" id="leader_last_name"
			<%if(pm.getLast_name()!=null){ %>
			value="<%=pm.getLast_name()%>"
			<%}else{%> placeholder="Ex)Hong"<%} %>>
		</td>
	</tr>
	<tr>
		<td>생년 월일</td>
		<td><input type = "text" name = "birthday" id="leader_birthday"
			<%if(pm.getBirth_day()!=0){ %>
			value="<%=pm.getBirth_day()%>"
			<%}else{%> placeholder="Ex)20170131"<%} %>>
		</td>
	</tr>
	</table>
	<%
	int adult =1;
	int child =1;
	for(int i = 1; i<PM_List.size();i++){
		pm = PM_List.get(i);
		%>
	<h5><%if(pm.getAdult_or_child()==1){
			out.print("동행자"+adult);
			adult++;
		}else {
			out.print("어린이"+child);
			child++;
		}%></h5>
		<input type="hidden" value = "<%=pm.getPm_num()%>" name = "pm_num">
	<table>
	<tr>
		<td>이름</td>
		<td><input type = "text" name = "name" id="leader_name"
			<%if(pm.getHangul_name()!=null){ %>
			value="<%=pm.getHangul_name()%>"<%} %>>
		</td>
		<td>연락처</td>
		<td><input type = "text" name = "mobile" id="leader_mobile"
			<%if(pm.getMobile()!=null){ %>
			value="<%=pm.getMobile()%>"<%} %>>
		</td>
	</tr>
	<tr>
		<td>영문 이름</td>
		<td><input type = "text" name = "First_name" id="leader_first_name"
			<%if(pm.getFirst_name()!=null){ %>
			value="<%=pm.getFirst_name()%>"<%} %>>
		</td>
		<td>영문 성</td>
		<td><input type = "text" name = "Last_name" id="leader_last_name"
			<%if(pm.getLast_name()!=null){ %>
			value="<%=pm.getLast_name()%>"<%} %>>
		</td>
	</tr>
	<tr>
		<td>생년 월일</td>
		<td><input type = "text" name = "birthday" id="leader_birthday"
			<%if(pm.getBirth_day()!=0){ %>
			value="<%=pm.getBirth_day()%>"<%} %>>
		</td>
	</tr>
	</table>

<%}
} %>
<br>
<input type="submit" value = "입력">
<input type ="Button" value = "취소" onclick="window.close()">
</form>
</body>
</html>