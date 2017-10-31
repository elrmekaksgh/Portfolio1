<%@page import="java.text.SimpleDateFormat"%>
<%@page import="net.mod.db.ModTradeInfoBEAN"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>패키지 예약 취소 요청 페이지</title>
<%
request.setCharacterEncoding("utf-8");
ModTradeInfoBEAN mtib = (ModTradeInfoBEAN)request.getAttribute("mtib");
String[] countp = mtib.getPack_count().split(",");
int po_num = Integer.parseInt(request.getParameter("num"));
SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
%>
<link href="./css/popup.css" rel="stylesheet" type="text/css">
<script src = "./js/jquery-3.2.0.js"></script>
	<script type="text/javascript">
	var check = 0;
	$(window).ready(function(){
		alert("취소 및 환불 규정을 꼭 확인하시길 바랍니다");
	})
	function cancel_rule_view(){
		check++;
		if(check ==1){
			$('#cancel_rule_view').html("환불 규정▲");
			$('#Cancel_Rule').show();
		}else{
			$('#cancel_rule_view').html("환불 규정▼");
			$('#Cancel_Rule').hide();
			check = 0;
		}
	}
	function bankcheck(){
		if(confirm("환불 규정을 확인하지 않은데에 대한 불이익은 본사에서 책임 지지 않습니다")){
			if("<%=mtib.getTrade_type()%>"=="무통장 입금"&&<%=mtib.getCost()%>!=0){
				if($('#bank_name').val().length==0){
					alert('환불 받을실 은행을 입력해 주세요');
					$('#bank_name').select();
					return false;
				}else if($('#name').val().length==0){
					alert('예금주를 입력해 주세요');
					$('#name').select();
					return false;
				}else if($('#bank_number').val().length==0){
					alert('계좌번호를 입력해 주세요');
					$('#bank_number').select();
					return false;
				}
			}
		}else return false;
	}
	
</script>
</head>

<body>
<div id="res_cancel">
<h4>패키지 정보</h4>
<form action = "./Res_Cancel_Action.mo" method = "post" onsubmit="return bankcheck()">
<table>
	<tr>
		<th>예약번호 </th>
		<td><%=mtib.getTrade_num() %></td>
	</tr>
	<tr>
		<th>상품명</th>
		<td><%=mtib.getSubject() %></td>
	</tr>
	<tr>
		<th>출발 날짜</th>
		<td><%=sdf.format(mtib.getTrade_date()) %></td>
	</tr>
	<tr>
		<th>예약 인원</th>
		<td>성인 : <%=countp[0] %>, 아동 : <%=countp[1] %></td>
	</tr>
	<tr>
		<th>결제 금액</th>
		<td><%=mtib.getTotal_cost()%>원</td>
	</tr>
	<tr>
		<th>공제율</th>
		<td><%=mtib.getMemo()%></td>
	</tr>
	<tr>
		<th>결제 환불 금액</th>
		<td><%=mtib.getCost() %>원<input type="hidden" name="Cancel_info" value="<%=mtib.getCost()%>">
			<input type="hidden" name="Cancel_info" value="<%=mtib.getTrade_type()%>">
			<input type="hidden"  name = "pnum" value ="<%=po_num %>"></td>
	</tr>
	<%if(mtib.getTrade_type().equals("무통장 입금")&&mtib.getCost()!=0){ %>
	<tr>
		<th colspan="2">환불 받으실 계좌 정보 입력</th>
	</tr>
	<tr>
		<th>은행명</th><td><input type="text" id="bank_name" name="Cancel_info"
							placeholder="EX)콩팥 머니 은행"></td>
		<th>예금주</th><td><input type="text" id="name" name="Cancel_info"
							placeholder="EX)홍길동"></td>
	</tr>
	<tr>
		<th>계좌 번호</th><td><input type="text" id="bank_number" name="Cancel_info"
							placeholder="EX)12-007-2245-777">
			</td>
	</tr>
	<%} %>
</table>
<!-- onclick = "cancel_rule_view()" -->
<span id = "cancel_rule_view">※ 환불 규정</span>
 <div id="Cancel_Rule">
	<ul> 
  		<li>출발 7일전 취소시 총 여행경비 10%공제 후 환불</li>
 		<li>출발 5일전 취소시 총 여행경비 20%공제 후 환불</li>
  		<li>출발 3일전 취소시 총 여행경비 30%공제 후 환불</li>
  		<li>출발 1일전 취소시 총 여행경비 50%공제 후 환불</li>
  		<li>당일 취소시 여행경비 환불 불가</li>
	</ul>
</div><br>
 <input type ="submit" value = "예약 취소">
<input type = "button" value ="닫기" onclick ="window.close()">
 </form> 
</div>  	
</body>
</html>