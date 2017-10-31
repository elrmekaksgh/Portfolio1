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
int ti_num = Integer.parseInt(request.getParameter("ti_num"));
int  num =Integer.parseInt(request.getParameter("num"));

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
function receive_change(num,ra_num){
	$.ajax({
        type:"post",
        url:"./receive_changeAction.mo",
        data:{
           ra_num:ra_num,
           name:$('#name'+num).html(),
           mobile: $('#mobile'+num).html(),
           postcode:$('#postcode'+num).val(),
           address1:$('#address1'+num).val(),
           address2:$('#address2'+num).val()
        },
        success:function(){
            alert("배송지가 변경 되었습니다!");
        	opener.window.location.reload(true);
        	window.close();
        }
     });
}
</script>
</head>
<body>
<div id="receive_change">
<h3>배송지 변경</h3>
<%if(ribList.size() != 0){
	for(int i=0; i<ribList.size(); i++){
		ReceiveInfoBEAN rib = ribList.get(i);%>
	<input type="hidden" value="<%=rib.getPostcode() %>" id = "postcode<%=rib.getRa_num() %>">
	<input type="hidden" value="<%=rib.getAddress1() %>" id = "address1<%=rib.getRa_num() %>">
	<input type="hidden" value="<%=rib.getAddress2() %>" id = "address2<%=rib.getRa_num() %>">
	<h5><%=i+1 %>번째 배송지</h5>
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
	<input type = "button" value="선택" onclick ="receive_change(<%=rib.getRa_num() %>,<%=ti_num %>)">
	<%if(rib.getBasic_setting()==0){ %><input type="button" value="기본 배송지로 설정" onclick="basic_change(<%=rib.getRa_num() %>)" >
	<%} %>
<%	}
}else{ %>등록 된 정보가 없습니다<%} %><br><br>
<input type = "button" id="Add_address" value = "배송지 추가">
</div>
</body>
</html>