<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="net.mod.db.ModTradeInfoBEAN"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>교환 처리 페이지</title>
<%request.setCharacterEncoding("utf-8");
ModTradeInfoBEAN mtib = (ModTradeInfoBEAN)request.getAttribute("mtib");
List<String> Color_List = (List<String>)request.getAttribute("Color_List");
DecimalFormat Commas = new DecimalFormat("#,###");
String mtib_cost = (String)Commas.format(mtib.getCost() );
int tnum = Integer.parseInt(request.getParameter("num"));
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
String [] reason = mtib.getO_memo().split("ㅨ");
String []memo=reason[0].split(",");
%>
<script src = "./js/jquery-3.2.0.js"></script>
<script type="text/javascript">
function color_check(){
	if(count_check()!=0){
		var color = $('#color option:selected').val();
		if(color == "none"){
			alert("색상을 먼저 선택해 주세요");
			return false;
		}
	}else{
		alert("이미 교환 요구 수량을 만족하였습니다 \n교환 요구 수량을 확인하세요");
		return false;
	}
}
function count_check(){
	var max_count = parseInt($('#max_count').val()); 
	for(var i =0; i <$('#select').find('.count').length;i++){
		max_count -= $('#select .count').eq(i).val();
	}
	return max_count;
}
function size_call(ori_num){
	if(count_check()==0)alert("이미 교환 요구 수량을 만족하였습니다 \n교환 요구 수량을 확인하세요");
	$("#size").find("option").remove();
	$('#size').append("<option  value = 'none' >사이즈-재고</option>");
	$.ajax({
		type:"POST",
		url:"./size.jc",
		data:{
			color:$('#color option:selected').val(),
			ori_num:<%=mtib.getOri_num()%>
		},
		datatype:"JSON",
		success:function(data){
			$.each(data,function(i,size){
				if(size.stock >0){
					$('#size').append("<option  value = '"+size.num+
							"' >"+size.size+"-"+size.stock+"개</option>");
				}else{
					$('#size').append("<option  value = '"+size.num+
							"' disabled='disabled' >"+size.size+"-"+size.stock+"개 품절</option>");
				}
			});
		}
	});
}
function info_call(){
	var num = $('#size option:selected').val();
	if($('#size option:selected').val()!="none"){
		$.ajax({
			type:"POST",
			url:"./thing_info.jc",
			data:{
				num:num
			},
			datatype:"JSON",
			success:function(data){
				var check = 1;
				for(var i = 0; i<$('#select').find('tr').length;i++){
					if($('#select tr').eq(i).attr('id')=="info"+num)check=0;
				}
					if(check == 1){
						//기존에 있던 count의 옵션 값들 중에 마지막 옵션 삭제
						for(var i = 0; i<$('.count').length;i++){
							if($(".count").eq(i).find('option').length>1&&
									$(".count").eq(i).find('option:selected').val()<
									$(".count").eq(i).find('option:last').val())
								$(".count").eq(i).find('option:last').remove();
						}
						//사이즈 선택시 새로운 tr 추가
						$('#select').append("<tr id='info"+num+"'><td>품번: "+num+
							"</td><td>색상: "+data.color+"</td><td>사이즈: "+data.size+"</td>"+
							"<td>재고: "+data.stock+"</td>"+
							"<td><select id ='count"+num+"'name='count' class='count'"+
							"onchange='count_change("+num+")'></select>"+
							"<input type='hidden' id='stock"+num+"'"+
								"name='stock' value='"+data.stock+"'>"+
							 "<input type='hidden' id='cost"+num+"'"+
								"name='cost' value='"+data.cost+"'>"+
								  "<input type='hidden' name='tnum' value='"+num+"'>"+
								  "<input type='hidden' name='color' value='"+data.color+"'>"+
								  "<input type='hidden' name='size' value='"+data.size+"'>"+
								  "<input type='hidden' id='result"+num+"' name='result' value=''></td>"+
							"<td class='tcost' id='tcost"+num+"'></td>"+
							"<td><img src='./img/delete.png' onclick='trdel("+num+")'></td></tr>");
						$('#count'+num).append("<option value = '1'>1</option>");
						count_change();
					}else{
					alert("이미 리스트에 있는 상품 입니다");
				}
			}		
		});
	}
}
function count_change(){
	var ch = count_check();
	for(var i = 0; i<$('.count').length;i++){
		var selected = $(".count").eq(i).find('option:selected').val();
		var str ="차액 : ";
		var cost_cal = $('#ori_cost').val()-$('input:hidden[name=cost]').eq(i).val();
		if(cost_cal>0)str+="+";
		var result = cost_cal*selected;
		$('input:hidden[name=result]').eq(i).val(result);
		$('.tcost').eq(i).html(str+numberWithCommas(result)+"원");
		var do_count = $(".count").eq(i).find('option:last').val()-selected;
		for(var x =0; x<do_count; x++)$(".count").eq(i).find('option:last').remove();
		var stock=$('input:hidden[name=stock]').eq(i).val();
		var j = parseInt(selected)+parseInt(ch);
		if(stock<j)j=stock;
		for(selected;selected<j;selected++){
			var y = parseInt(selected)+1;
			$(".count").eq(i).append("<option value = '"+y+"'>"+y+"</option>");
		}
	}
	var total_cost =0;
	for(var i = 0; i<$('input:hidden[name=result]').length;i++){
		total_cost+=parseInt($('input:hidden[name=result]').eq(i).val());
	}
	$('#t_cost').val(total_cost);
	$('#total_cost').html(numberWithCommas(total_cost)+"원");
}
function trdel(tnum){
	$('#info'+tnum).remove();
	count_change();
}
function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}
function check(){
	
	if(count_check()==$('#max_count').val()){
		alert('교환 물품을 하나 이상은 선택 하셔야 합니다');
		return false;
	}
	if(count_check()!=0){
		if(!(confirm('교환 요구 수량과 교환 수량이 일치 하지 않습니다\n계속 진행하시겠습니까?'))){
			return false;
		}
	}
	if($('input:text[name=trans_num]').val().length==0){
		alert('송장 번호를 입력해 주세요');
		return false;
	}
	if($('#memo').val().length==0){
		alert('교환 코맨트를 입력해 주세요');
		return false;
	}	
}
</script>
<link href="./css/popup.css" rel="stylesheet" type="text/css">
</head>
<body>
<div id = "thing_exchange">
<form action="./Thing_Exchange_Action.ao" method = "post" onsubmit="return check()">
<table>
	<tr>
		<th>상품 번호
			<input type = "hidden" name = "o_num" value="<%=tnum%>">
			<input type = "hidden" name = "ori_num" value="<%=mtib.getOri_num()%>"></th>
		<td><%=mtib.getOri_num() %></td>
		<td><%=mtib.getSubject() %></td>
		<td>색상: <%=mtib.getColor() %></td>
		<td>사이즈: <%=mtib.getSize() %></td>
		<td>수량: <%=mtib.getThing_count()%>개</td>
		<td><%=mtib_cost%>원<input type="hidden" id = "ori_cost" value="<%=mtib.getTotal_cost()%>"></td>
	</tr>
	<tr>
		<th>교환 요구 사항</th>
		<td colspan="4"><%=reason[1].replace("\r\n", "<br>") %></td>
		<th>교환 요구 수량</th>
		<td><%=memo[1] %>개<input type="hidden" id ="max_count" value = "<%=memo[1] %>"></td>
	</tr>
	<tr>
		<th>거래 정보</th>
		<th>주문자ID</th>
		<td><%=mtib.getId() %></td>
		<th>결제자</th>
		<td><%=mtib.getPayer() %></td>
		<th>결제 방법</th>
		<td colspan="3"><%=mtib.getTrade_type() %></td>
	</tr>
	<tr>
		<th>받으시는 분</th>
		<td><%=mtib.getName() %></td>
		<th>연락처</th>
		<td><%=mtib.getMobile() %></td>
		<th>교환 요구 날짜</th>
		<td colspan="6"><%=sdf.format(mtib.getTrade_date()) %></td>
	</tr>
	<tr>
		<th>주소</th>
		<td colspan="9"><%="["+mtib.getPostcode()+"] "+mtib.getAddress1()+" "+mtib.getAddress2() %></td>
	</tr>
		<%if(mtib.getMemo().length()!=0){ %>
		<tr>
			<th>배송시 요청 사항</th>
			<td colspan="8"><%=mtib.getMemo().replace("\r\n", "<br>") %></td>
		</tr>
		<%} %>
</table>

<table id="select">
	<tr>
		<th>교환할 상품</th>
		<td><select id="color" onchange="size_call(<%=mtib.getOri_num()%>)">
			<option value = "none">색상</option>
			<%for(int i =0; i<Color_List.size();i++){ %>
				<option value="<%=Color_List.get(i) %>"><%=Color_List.get(i) %></option>
			<%} %>
		</select></td>
		<td><select id="size" onclick="return color_check()" onchange="info_call()">
		<option value = "none">사이즈-재고</option>
		</select></td>
		<td colspan="3">총 차액 : <span id="total_cost"></span>
			<input type="hidden"id="t_cost" name="t_cost"></td>
	</tr>
</table>
<table>
	<tr>
		<th>송장 번호</th>
		<td><input type="text" name="trans_num" size="30" placeholder="송장 번호를 입력해 주세요"></td>
	</tr>
	<tr>
		<th>코멘트 입력</th>
		<td colspan ="6"><textarea rows="3" name="comment"
			placeholder="고객에 보낼 메시지를 입력해 주세요"
			id = "memo" style="resize:none;"></textarea>
		</td>
	</tr>
</table>
	<input type="submit" value="교환 완료">
</form>
</div>
</body>
</html>