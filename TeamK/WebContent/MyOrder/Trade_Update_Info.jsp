<%@page import="java.text.DecimalFormat"%>
<%@page import="net.mod.db.ModTradeInfoBEAN"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="./css/popup.css" rel="stylesheet" type="text/css">
<%
request.setCharacterEncoding("utf-8");
ModTradeInfoBEAN mtib = (ModTradeInfoBEAN)request.getAttribute("mtib");
String [] reason = mtib.getMemo().split("ㅨ");
int length = reason.length;
String []memo=reason[0].split(",");
DecimalFormat Commas = new DecimalFormat("#,###");

%>
<title><%=memo[0] %> 정보</title>
<script src = "./js/jquery-3.2.0.js"></script>
<script type="text/javascript">
function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}
</script>

</head>
<body>
<div id="trade_update_info">
<h3 align="center"><%=memo[0] %> 정보</h3>
	<table>
		<tr>
			<th>상품명</th>
			<td colspan ="3"><%=mtib.getSubject() %></td>
		</tr>
		<tr>
			<th>색상</th>
			<td><%=mtib.getColor() %></td>
			<th>사이즈</th>
			<td><%=mtib.getSize() %></td>
		</tr>
<%	if(memo[0].equals("교환")){%>
		<tr>
			<th>수량</th>
			<td><%=memo[1] %>개</td>
			<th>가격</th>
			<td><%=Commas.format(mtib.getCost()) %>원</td>
		</tr>
		<tr>
			<th colspan="4">교환 요청 사항</th>
		</tr>
		<tr>
			<td colspan="4"><%=reason[1].replace("\r\n", "<br>")%></td>
		</tr>
		<tr>
		</tr>
		
<%		if(mtib.getStatus()==3){
		%>
		<tr>
			<th colspan="4">교환 처리 결과</th>
		</tr>
		<%
		for(int i = 2; i<length-2;i++){
			String[] info=reason[i].split(",");%>
		<tr>
			<td>색상 : <%=info[0] %></td>
			<td>사이즈 : <%=info[1] %></td>
			<td>수량 : <%=info[2] %>개</td>
			<td>차액 : <%=Commas.format(Integer.parseInt(info[3])) %>원</td>
		</tr>
		<%} %>
		<tr>
			<th colspan="2">
			<%if(Integer.parseInt(reason[length-2])<0){ %>
			환불 금액
			<%}else{ %>
			추가 결제 금액
			<%} %>
			</th>
			<td colspan = "2"><%=Commas.format(Math.abs(Integer.parseInt(reason[length-2]))) %>원</td>
		</tr>
		
		<tr>
			<th colspan="4">판매자 코멘트</th>
		</tr>
		<tr>
			<td colspan="4"><%=reason[length-1].replace("\r\n", "<br>")%></td>
		</tr>
	<%}
	}else{%>
		<tr>
			<th>수량</th>
			<td colspan ="3"><%=memo[2] %></td>
		</tr>
		<tr>
			<th>환불 금액</th>
			<td colspan ="3"><%=memo[3] %></td>
		</tr>
		<tr>
			<th>환불 방법</th>
			<td colspan="3"><%=memo[1] %></td>
		</tr>
		<%if(memo[1].equals("무통장 입금")){ %>
		<tr>
			<th>은행명</th>
			<td><%=memo[4] %></td>
			<th>예금주</th>
			<td><%=memo[5] %></td>
		</tr>
		<tr>
			<th>계좌</th>
			<td colspan="3"><%=memo[6] %></td>
		</tr>
		<%} %>
		<tr>
			<th colspan ="4">환불 사유</th>
		</tr>
		<tr>
			<td colspan="4"><%=reason[1].replace("\r\n", "<br>") %></td>
		</tr>
	<%
	}%>
	</table>

	<center><input type="button" value="닫기" onclick="window.close()"></center>
</div>
</body>
</html>