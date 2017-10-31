<%@page import="net.mod.db.ModTradeInfoBEAN"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="./css/popup.css" rel="stylesheet" type="text/css">
<title>환불 및 취소 페이지</title>
<%
request.setCharacterEncoding("utf-8");
ModTradeInfoBEAN mtib = (ModTradeInfoBEAN)request.getAttribute("mtib");
int o_num = Integer.parseInt(request.getParameter("num"));
int ti_num = Integer.parseInt(request.getParameter("ti_num"));

%>
<script src = "./js/jquery-3.2.0.js"></script>
	<script type="text/javascript">
	$(document).ready(function(){
		$('.payback').hide();
		$('input[name=status]').change(function(){
			tcount_reset();
			payback_cost();
			if($(this).val()=="Cancel"){
				$('.payback').show();
				$('.Exchange').hide();
				$('textarea[name=memo]').attr('placeholder','환불 사유를 간략히 써주세요');
				$('#submit').val('환불 신청');
			}else{
				$('.payback').hide();
				$('.Exchange').show ();
				$('textarea[name=memo]').attr('placeholder','교환을 원하시는 색상과 사이즈를 입력해 주세요');
				$('#submit').val('교환 신청');
			}
		});
	});
	function tcount_reset(){
		$('#tcount option:eq(0)').prop('selected','selected');
	}
	function payback_cost(){
		var cost = <%=mtib.getCost()%>*$('select[name=tcount] option:selected').val();
		$('input:hidden[name=payback_co]').val(cost);
		$('#payback_cost').html(cost+"원");
	}
	function bankcheck(){
		var radioval=$('input:radio[name=status]:checked').val();
		if(radioval=="Cancel"){
			if("<%=mtib.getTrade_type()%>"=="무통장 입금"){
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
		}
		
		if($('textarea[name=memo]').val().length==0){
			if(radioval=="Exchange"){
				alert("교환 요청사항을 입력해 주세요");
				return false;
			}else{
				alert("환불 사유를 입력해 주세요");
				return false;
			}
		}
	}
	
	
	</script>

</head>
<body>
<div id="to_cancel_or_exchange">
<h4>상품 정보</h4>
<form action = "./TO_Status_Update.mo" method = "post" onsubmit="return bankcheck()">
<input type="radio" checked="checked" value="Exchange" name="status" id="change"><label for="change">교환</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input type="radio" value="Cancel" name="status" id="refund"><label for="refund">환불</label>
<br><br>
<table>
	<tr>
		<th>상품명</th>
		<td><%=mtib.getSubject() %></td>
	</tr>
	<tr>
		<th>사이즈</th>
		<td><%=mtib.getSize() %></td>
	</tr>
	<tr>
		<th>색상</th>
		<td><%=mtib.getColor() %></td>
	</tr>
	<tr>
		<th>수량</th>
		<td>
			<select id="tcount" name="tcount" onchange="payback_cost()">
				<%for(int i =1; i<=mtib.getThing_count();i++){ %>
				<option value="<%=i%>"><%=i %>개</option>
				<%} %>
			</select>
		</td>
	</tr>
	<tr class="payback">
		<th>총 상품 금액</th>
		<td><%=mtib.getTotal_cost() %>원</td>
	</tr>
	<tr class="payback">
		<th>환불 금액</th>
		<td id="payback_cost"></td>
	</tr>
	<tr class="payback">
		<th>환불 방식</th>
		<td><%=mtib.getTrade_type() %></td>
	</tr>
	<%if(mtib.getTrade_type().equals("무통장 입금")){ %>
	<tr class="payback">
		<th colspan="2">환불 받으실 계좌 정보 입력</th>
	</tr>
	<tr class="payback">
		<th>은행명</th><td><input type="text" id="bank_name" name="Cancel_info" 
							placeholder="EX)콩팥 머니 은행"></td>
	</tr>
	<tr class="payback">
		<th>예금주</th><td><input type="text" id="name" name="Cancel_info"
							placeholder="EX)홍길동"></td>
	</tr>
	<tr class="payback">
		<th>계좌 번호</th><td><input type="text" id="bank_number" name="Cancel_info"
							placeholder="EX)12-007-2245-777">
	</td>
	</tr>
	<%} %>
	<tr>
		<td colspan="2">
		<textarea rows="6" cols="45" placeholder="교환을 원하시는 색상과 사이즈를 입력해 주세요"
			name = "memo" style="resize:none;"></textarea>
		</td>
	</tr>
</table>
<span style="color:red; font-size:14px;" class="Exchange">차액 발생시 추가 결제를 요하는 경우가 있습니다!</span><br><br>
<input type="hidden" name="payback_co" value="">
<input type="hidden" name="trade_type" value="<%=mtib.getTrade_type() %>">
<input type="hidden" name="num" value="<%=o_num %>">
<input type ="submit" id="submit" value = "교환 신청">
<input type = "button" value ="닫기" onclick ="window.close()">
 </form> 
</div>  	
</body>
</html>