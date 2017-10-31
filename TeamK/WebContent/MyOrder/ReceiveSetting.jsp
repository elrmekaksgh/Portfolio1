<%@page import="net.mod.db.ReceiveInfoBEAN"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
request.setCharacterEncoding("utf-8");
List<ReceiveInfoBEAN>ribList = (List<ReceiveInfoBEAN>)request.getAttribute("ribList");
String id = (String)session.getAttribute("id");

%>
<title>Insert title here</title>
<link href="./css/popup.css" rel="stylesheet" type="text/css">
<script src = "./js/jquery-3.2.0.js"></script>
<script type="text/javascript">
$(function(){
	$('#Add_address').on('click',function(){
		location.href="./Add_Address.mo";
	});	
	
});
function basic_change(ra_num) {
	$.ajax({
        type:"post",
        url:"./Basic_change.mo",
        data:{
           ra_num:ra_num,
           id:"<%=id%>"
        },
        success:function(){
            alert("기본 배송지가 변경 되었습니다!");
            window.location.reload(true);
        }
     });
}
function address_select(ra_num){
	var name = $('#name'+ra_num).html();
	var mobile = $('#mobile'+ra_num).html();
	$('#receive_name',opener.document).html(name);
	$('#receive_mobile',opener.document).html(mobile);
	$('#receive_address',opener.document).html($('#address'+ra_num).html());
	$('#o_receive_name',opener.document).val(name);
	$('#o_receive_mobile',opener.document).val(mobile);
	$('#o_receive_postcode',opener.document).val($('#postcode'+ra_num).val());
	$('#o_receive_address1',opener.document).val($('#address1'+ra_num).val());
	$('#o_receive_address2',opener.document).val($('#address2'+ra_num).val());
	window.close();
}
</script>
</head>
<body>
	<div id="receive_setting">
	<h3>배송지 설정</h3>
<%if(ribList.size() != 0){
	for(int i=0; i<ribList.size(); i++){
		ReceiveInfoBEAN rib = ribList.get(i);%>
	<div>
	<input type="hidden" value="<%=rib.getPostcode() %>" id = "postcode<%=rib.getRa_num() %>">
	<input type="hidden" value="<%=rib.getAddress1() %>" id = "address1<%=rib.getRa_num() %>">
	<input type="hidden" value="<%=rib.getAddress2() %>" id = "address2<%=rib.getRa_num() %>">
	<h5><%=i %>번째 배송지</h5>
	<table>
		<tr>
			<th>이름</th>
			<td id="name<%=rib.getRa_num()%>"><%=rib.getName() %></td>
		</tr>
		<tr>
			<th>연락처</th>
			<td id="mobile<%=rib.getRa_num()%>"><%=rib.getMobile() %></td>
		</tr>
		<tr>
			<th>주소</th>
			<td id="address<%=rib.getRa_num()%>">
			[<%=rib.getPostcode() %>] <%=rib.getAddress1() %> <%=rib.getAddress2() %></td>
		</tr>
	</table>
	<input type = "button" value="선택" onclick ="address_select(<%=rib.getRa_num() %>)">
	<%if(rib.getBasic_setting()==0){ %><input type="button" value="기본 배송지로 설정" onclick="basic_change(<%=rib.getRa_num() %>)" >
	<%} %>
	</div>
<%	}
}else{ %>등록 된 정보가 없습니다<%} %>
<br>
<input type = "button" id="Add_address" value = "배송지 추가">
	</div>
</body>
</html>