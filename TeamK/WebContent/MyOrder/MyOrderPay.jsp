<%@page import="java.text.DecimalFormat"%>
<%@page import="net.member.db.MemberBean"%>
<%@page import="net.bns.db.PBasketBEAN"%>
<%@page import="net.bns.db.TBasketBEAN"%>
<%@page import="net.mod.db.ReceiveInfoBEAN"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="./css/inc.css" rel="stylesheet" type="text/css">
<link href="./css/subpage.css" rel="stylesheet" type="text/css">
<%
		request.setCharacterEncoding("utf-8");
		List<PBasketBEAN> ModPackList = (ArrayList<PBasketBEAN>) request.getAttribute("ModPackList");
		List<TBasketBEAN> ModThingList = (ArrayList<TBasketBEAN>) request.getAttribute("ModThingList");
		MemberBean mb = (MemberBean)request.getAttribute("mb");
		ReceiveInfoBEAN rib = (ReceiveInfoBEAN)request.getAttribute("rib");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		int cost_sum = 0;
		int thing_cost_sum = 0;
	%>
<script src = "./js/jquery-3.2.0.js"></script>
<script type="text/javascript">

$(document).ready(function() {
	
    $('#o_memo').on('keyup', function() {
        if($(this).val().length >200) {
        	alert("200자를 초과 하였습니다");
            $(this).val($(this).val().substring(0, 200));
        }
    });
    $('input[name=po_receive_check]').change(function(){
    	if(<%=ModThingList.size()%>==0 && $(this).val()=="0")$('#receive_info').hide();
    	else $('#receive_info').show();
    });
    if(<%=ModThingList.size()%>==0)$('#receive_memo').hide();
});
$(function(){
	$('#receive_setting').on('click',receive_setting);
});
function receive_setting(){
	window.open('./Receive_Setting.mo', '배송지 선택', 'left=200, top=100, width=480, height=640, scrollbars=yes, status=no, resizable=no, fullscreen=no, channelmode=no');
}
</script>
</head>
<body>
	<!--왼쪽 메뉴 -->
	<div>
		<jsp:include page="../inc/leftMenu.jsp"></jsp:include>
	</div>
	<!--왼쪽 메뉴 -->
	<div id="wrap">
	<div id="article_head">
			<div id="article_title">결제 페이지</div>
			<div class="empty"></div>
		</div>
	<article>
	<form action ="./MyOrderAddAction.mo" method ="post" name = "fr" onsubmit="return submit_check()" class="gyeolje">
	<div id ="buyer_info">
		<table>
			<caption>구매자 정보</caption>
			<tbody>
				<tr>
					<th>ID</th>
					<td><%=mb.getId() %></td>
				</tr>
				<tr>
					<th>Tel.</th>
					<td><%=mb.getMobile() %></td>
				</tr>
				<tr>
					<th>E-mail</th>
					<td><%=mb.getEmail() %></td>
				</tr>
			</tbody>
		</table>
	</div>
	
	<%
		//if (ModPackList == null && ModThingList == null)
			//response.sendRedirect("../index.jsp");

		if (ModPackList.size() != 0) {
	%>
	<div id = "packList">
	<table id="packtable">
	<caption>예약하신 패키지 상품 정보<br>
		<span>예약하신 패키지 상품의 팜플랫을 받아 보시겠습니까?
			<input name = "po_receive_check" type ="radio" id ="po_receive_check" value="1" checked="checked">예
			<input name = "po_receive_check" type ="radio" value="0">아니오
		</span>
	</caption>
	
	<tbody>
		<%
			for (int i = 0; i < ModPackList.size(); i++) {
					PBasketBEAN pbb = ModPackList.get(i);
					String [] countp = pbb.getCountp();
					cost_sum += pbb.getCost();
					DecimalFormat Commas = new DecimalFormat("#,###");
					String pbb_cost = (String)Commas.format(pbb.getCost());
		%>
		<tr>
			
			<td id="tr2td1"><img src='./upload/<%=pbb.getImg() %>'></td>
			<td><b><center><%=pbb.getSubject()%></center></b><%if(pbb.getPb_num()!=0){ %><input type="hidden" name="pch" value="<%=pbb.getPb_num() %>">
				<%}else{ %><input type="hidden" name="pnum" value="<%=pbb.getOri_num()%>">
							<input type="hidden" name="adult" value="<%=countp[0] %>">
							<input type="hidden" name="child" value="<%=countp[1] %>"><%} %>
			<br><center><%=pbb.getIntro()%></center></td>
			<td id="tdtd">성인: <%=countp[0]%>명
			<br>유아: <%=countp[1]%>명</td>
			<td id="tdtd"><%=pbb_cost%>원</td>
			
			
		</tr>
		<%
			}
		%>
		</tbody>
	</table>
</div>
	<%
		}
		if (ModThingList.size() != 0) {
	%>
	<div id ="thingList">
	<table id="Thingtable">
	<caption>주문하신 상품 정보</caption>
	<tbody>
		<%
			for (int i = 0; i < ModThingList.size(); i++) {
					TBasketBEAN tbb= ModThingList.get(i);
					cost_sum += tbb.getCost();
					thing_cost_sum+=tbb.getCost();
					DecimalFormat Commas = new DecimalFormat("#,###");
					String tbb_cost = (String)Commas.format(tbb.getCost());
		%>
		<tr>
			<td id="tr2td1"><img src='./upload/<%=tbb.getImg() %>'></td>
			<td><b><center><%=tbb.getSubject()%></center></b>
				<%if(tbb.getNum()!=0){ %><input type="hidden" name="tch" value="<%=tbb.getNum() %>">
				<%}else{ %><input type="hidden" name="tnum" value="<%=tbb.getOri_num()%>">
							<input type="hidden" name="count" value="<%=tbb.getCount() %>">
							<input type="hidden" name="color" value="<%=tbb.getColor() %>">
							<input type="hidden" name="size" value="<%=tbb.getSize() %>">
							<input type="hidden" name="cost" value="<%=tbb.getCost() %>"><%} %>
			<br><center><%=tbb.getIntro()%></center></td>
			<td id="tdtd">색상: <%=tbb.getColor()%>
			<br>크기: <%=tbb.getSize()%>
			<br>수량: <%=tbb.getCount()%></td>
			<td id="tdtd"><%=tbb_cost%>원</td>
			
		</tr>
		<%
			}
		%>
		</tbody>
	</table>
	
</div>
<%} %>
<div id="receive_info">
		<table>
			<caption>배송지<input type = "button" value="배송지 설정" id="receive_setting"></caption>
			<tbody>
			<tr>
				<th>성함</th>
				<td id="receive_name"><%if(rib.getId()==null)out.print("등록된 정보가 없습니다!");
					 else out.print(rib.getName());%></td>
			</tr>
			<tr>
				<th>연락처</th>
				<td id="receive_mobile"><%if(rib.getId()==null)out.print("등록된 정보가 없습니다!");
					 else out.print(rib.getMobile());%></td>
			</tr>
			<tr>
				<th>주소</th>
				<td id="receive_address"><%if(rib.getId()==null)out.print("등록된 정보가 없습니다!");
					 else out.print("["+rib.getPostcode()+"] "+rib.getAddress1()+" "+rib.getAddress2());%></td>
			</tr>
			<%if(ModThingList != null){ %>
			<tr id = "receive_memo">
				<th>배송시 요청사항</th>
				<td><textarea cols="50" rows="4" placeholder="200자 이내로 입력해 주세요" id="o_memo" name = "o_memo"></textarea></td>
			</tr>
			<%} %>
			</tbody>
		</table>
</div>
<div id="pay_info">
<script type="text/javascript">

$(document).ready(function() {
	$('#cost_sum').html(numberWithCommas(<%=cost_sum%>)+"원");
	if(<%=ModThingList.size()%>!=0){
		if(<%=thing_cost_sum%>>=20000){
			$('#trans_cost').prepend("0원");	
			$('#total_cost').val(<%=cost_sum%>);
			$('#t_cost').html(numberWithCommas(<%=cost_sum%>)+"원");
		}
		else {
			$('#trans_cost').prepend("3,000원");
			$('#total_cost').val(<%=cost_sum%>+3000);
			$('#t_cost').html(numberWithCommas(<%=cost_sum%>)+"원");
		}
	}else{
		$('#total_cost').val(<%=cost_sum%>);
		$('#t_cost').html(numberWithCommas(<%=cost_sum%>)+"원");
	}
	for(var i = 1; i<5;i++){
		$('.trade_type'+i).hide();	
	}	
	$('.trade_type1').show();
	
	$('input[name=t_type]').change(function(){
		selectreset();
		var trade_id=$(this).attr('id');
		for(var i = 1; i<4;i++){
			$('.trade_type'+i).hide();	
		}
		$('.'+trade_id).show();
	});
	$('#monthly_pay_disable').hide();
	if(<%=ModPackList.size()%>==0){
		if(<%=thing_cost_sum%><50000){
			$('#monthly_pay').hide();
			$('#monthly_pay_disable').show();
		}
	}
	
	$('#select_card').change(function(){
		selected_val = $(this).val();
		if($(this).val()=="none"){
			$('#select_card_check').show();
		}else {
			$('#select_card_check').hide();
		}
	});

	$('#cash_receipt_check').attr('disabled','disabled');
	$('#select_bank').change(function(){
		select_val = $(this).val();
		if($(this).val()=="none"){
			$('#select_bank_check').show();
			$('#cash_receipt_check').prop('checked',false);
			$('#cash_receipt_check').attr('disabled','disabled');
		}else {
			$('#select_bank_check').hide();
			$('#cash_receipt_check').attr('disabled',false);
		}
	});
	$('#cash_receipt').hide();
	$('#cash_receipt_check').on('click',function(){
		var check = $(this).is(":checked");
		if(check){
			$('#cash_receipt').show();
		}else{
			$('#cash_receipt').hide();
			$('#cash_receipt_number').val("");
		}
	});	
	$('input[name=cash_receipt_type]').change(function(){
	
		var thisval = $(this).val();
		if(thisval=="소득 공제"){
			$('#cash_receipt_type_select').html("휴대전화 번호");
			$('#cash_receipt_type_select').val("휴대전화번호");
		}else if(thisval ="지출 증빙"){
			$('#cash_receipt_type_select').html("사업자 번호");
			$('#cash_receipt_type_select').val("사업자번호");
		}
	});
	selectreset();
	$('#card_agree_ul_select').on('click',function(){
		$('#card_agree_ul_selected').show();
		$(this).hide();
		$('#card_agree_ul').show();
	});
	$('#card_agree_ul_selected').on('click',function(){
		$('.agree_li_view').hide();
		$('#card_agree_ul').hide();
		$('#mobile_agree_ul').hide();
		$('.agree_ul_selected').hide();
		$('.agree_li_selected').hide();
		$('.agree_li_select').show();
		$('.agree_ul_select').show();
		$('#select_bank option:eq(0)').prop('selected',true);
		$('#cash_receipt_check').prop('checked',false);
		$('#cash_receipt').hide();
	});
	$('#mobile_agree_ul_select').on('click',function(){
		$('#mobile_agree_ul_selected').show();
		$(this).hide();
		$('#mobile_agree_ul').show();
	});
	$('#mobile_agree_ul_selected').on('click',function(){
		selectreset();
	});
});
function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}
function submit_check(){
	if($("#po_receive_check").is(':checked')&&
			<%=ModThingList.size()%>==0){
		if($('#o_receive_name').val()=="null"){
			alert('배송지 정보를 입력해 주세요');
			return false;
		}
	}
	
	if($('#trade_type1').is(":checked")){
		if($('#select_card option:eq(0)').prop('selected')){
			alert("카드사를 선택해 주세요");
			return false;
		}
		if(!($('input:checkbox[name=card_agree]').prop('checked'))){
			alert("결제 대행 서비스 약관에 동의 하셔야 합니다!");			
			return false;
		}
		else alert("카드 결제");
	}else if($('#trade_type3').is(":checked")){
		if($('#select_bank option:eq(0)').prop('selected')){
			alert("은행을 선택해 주세요");
			return false;
		}else{
			if($('#cash_receipt_check').is(':checked')){
				if($('#cash_receipt_number').val()==""){
					alert("failed");
					return false;
				}else alert("successed");
			}else alert("successed");
		}
	}else{
		if(!($('input:checkbox[name=mobile_agree]').prop('checked'))){
			alert("결제 대행 서비스 약관에 동의 하셔야 합니다!");			
			return false;
		}
		alert("모바일 결제");
	}
}


function card_agree_select(i){
	$('#card_agree_li_selected'+i).show();
	$('#card_agree_li_select'+i).hide();
	$('#card_agree_li_view'+i).show();
}
function card_agree_selected(i){
	$('#card_agree_li_selected'+i).hide();
	$('#card_agree_li_select'+i).show();
	$('#card_agree_li_view'+i).hide();
}
function mobile_agree_select(i){
	$('#mobile_agree_li_selected'+i).show();
	$('#mobile_agree_li_select'+i).hide();
	$('#mobile_agree_li_view'+i).show();
}
function mobile_agree_selected(i){
	$('#mobile_agree_li_selected'+i).hide();
	$('#mobile_agree_li_select'+i).show();
	$('#mobile_agree_li_view'+i).hide();
}
function selectreset(){
	$('#select_card_check').show();
	$('.agree_li_view').hide();
	$('#card_agree_ul').hide();
	$('#mobile_agree_ul').hide();
	$('.agree_ul_selected').hide();
	$('.agree_li_selected').hide();
	$('.agree_li_select').show();
	$('.agree_ul_select').show();
	$('#select_card option:eq(0)').prop('selected',true);
	$('#select_bank option:eq(0)').prop('selected',true);
	$('#cash_receipt_check').prop('checked',false);
	$('#cash_receipt').hide();
}
</script>
<table>
	<caption>결제 정보</caption>
	<tbody>
		<tr>
			<th>총 상품 가격</th>
			<td id="cost_sum"></td>
		</tr>
		<%if(ModThingList.size()!=0){ %>
		<tr>
			<th>배송료</th>
			<td id="trans_cost"> 상품에 해당하는 금액이 2만원 이상일 경우 무료입니다</td>
		</tr><%} %>
		<tr>
			<th>결제 금액</th>
			<td id="t_cost"></td>
		</tr>
		<tr>
			<th rowspan="2">결제 방법</th>
			<td colspan="2">
				<label><input type="radio" name="t_type" id="trade_type1" value="카드 결제" checked="checked">카드 결제</label>
				<label><input type="radio" name="t_type" id="trade_type2" value="모바일 결제">모바일 결제</label>
				<label><input type="radio" name="t_type" id="trade_type3" value="무통장 입금">무통장 입금</label>
			</td>
			<tr>
				<td colspan="2" class="trade_type1" >
					<div>
						<ul>
							<li><label><span style="float: left;">카드 종류</span> 
								<select id ="select_card" name="select_card" style="margin-right: 104px;">
									<option value="none">선택</option>
									<option value="신한 카드">신한 카드</option>
									<option value="비씨카드">비씨카드</option>
									<option value="국민카드">국민카드</option>
									<option value="현대카드">현대카드</option>
									<option value="롯데카드">롯데카드</option>
									<option value="삼성카드">삼성카드</option>
									<option value="농협카드">농협카드</option>
									<option value="하나은행카드">하나은행카드</option>
									<option value="씨티은행카드">씨티은행카드</option>
									<option value="우리은행카드">우리은행카드</option>
									<option value="제주은행카드">제주은행카드</option>
									<option value="전북은행카드">전북은행카드</option>
									<option value="광주은행카드">광주은행카드</option>
									<option value="수협카드">수협카드</option>
									<option value="KDB외환은행카드">KDB외환은행카드</option>
									<option value="우체국카드">우체국카드</option>
									<option value="신협카드">신협카드</option>
								</select>
								</label><br>
								<span id="select_card_check" style="color: red;">! 카드사를 선택해 주세요</span>
								<span id = "monthly_pay_disable">할부는 결제 금액이 5만원 이상인 경우에 가능합니다 </span>
							</li>
							<li  id="monthly_pay">
							<label><span  style="float: left;">할부 기간</span>
								<select name="monthly_pay" style="margin-right: 170px;">
									<option value ="1">일시불</option>
									<%for(int i = 2; i<13;i++){ %>
									<option value="<%=i %>"><%=i %>개월</option>
									<%} %>
								</select>
							</label>
							</li>
							
						</ul>	
					</div>
				</td>
				<td colspan="2" class="trade_type2" >
					<div>모바일
					</div>
				</td>
				<td colspan="2" class="trade_type3" >
					<div>
						<ul>
							<li>
							<span style="float: left;">입금 은행</span>
								<div>
								<select name="select_bank" id="select_bank" style="margin-right: 130px;">
									<option value="none">선택</option>
									<option value="신한은행">신한은행</option>
									<option value="국민은행">국민은행</option>
									<option value="농협">농협</option>
									<option value="하나은행">하나은행</option>
									<option value="씨티은행">씨티은행</option>
									<option value="우리은행">우리은행</option>
									<option value="제주은행">제주은행</option>
									<option value="전북은행">전북은행</option>
									<option value="광주은행">광주은행</option>
									<option value="수협">수협</option>
									<option value="외환은행">KDB외환은행</option>
									<option value="우체국">우체국</option>
									<option value="신협">신협</option>								
								</select>
								</div>
							<span id="select_bank_check" style="color: red">! 은행을 선택해 주세요</span>
							</li>
							<li><span style="float: left;">입금기한</span><div></div>
							</li>
							<li><span style="float: left;">현금 영수증</span><br>
							<label><input type="checkbox" value="1" 
							name = "cash_receipt_check" id="cash_receipt_check">현금 영수증 발급</label>
							<div id="cash_receipt">
								<ul>
									<li>용도
									<label><input type="radio" id="cash_receipt_type" 
										name="cash_receipt_type" value="소득 공제" checked="checked">소득 공제</label>
									<label><input type="radio" id="cash_receipt_type" 
										name="cash_receipt_type" value="지출 증빙">지출 증빙</label>
									</li>
									<li>발급 방법
									<select name="cash_receipt_type_select">
										<option id="cash_receipt_type_select" value="휴대전화번호">휴대전화</option>
										<option value="현금영수증 카드">현금영수증카드</option>
									</select>
									<input type="text" name="cash_receipt_number" id="cash_receipt_number" placeholder="숫자만 입력하세요">
									</li>									
								</ul>
							</div>
							</li>
						</ul>
						<dl>
							<dt>무통장 입금시 유의 사항</dt>
							<dd>입금 완료 후 상품 품절로 인해 자동 취소된 상품은 환불 처리해 드립니다</dd>
							<dd>은행 이체 수수료가 발생될 수 있습니다. 입금시 수수료를 확인해 주세요</dd>
						</dl>
					</div>
				</td>
			</tr>
			
	</tbody>
</table>

<div class="trade_type1">
	<label><input type ="checkbox" name = "card_agree" checked="checked">
		결제 대행 서비스 약관에 모두 동의 합니다.</label><br>
		<span id="card_agree_ul_select" class = "agree_ul_select">내용 보기▼</span>
			<span id="card_agree_ul_selected" class="agree_ul_selected">내용보기▲</span>
			<ul class="agree_ul" id="card_agree_ul">
				<li class="card_agree_li" >서비스 이용약관 동의<span id="card_agree_li_select1" class="agree_li_select" 
					onclick="card_agree_select(1)">내용보기▼</span>
						<span id="card_agree_li_selected1" class="agree_li_selected" onclick="card_agree_selected(1)">내용보기▲</span>
						<div id="card_agree_li_view1" class="agree_li_view">카드 약관1</div></li>
				<li>개인정보 수집 및 이용 동의<span id="card_agree_li_select2" class="agree_li_select" 
					onclick="card_agree_select(2)">내용보기▼</span>
						<span id="card_agree_li_selected2" class="agree_li_selected" onclick="card_agree_selected(2)">내용보기▲</span>
						<div id="card_agree_li_view2" class="agree_li_view">카드 약관2</div></li>
				<li>개인정보 제공 및 위탁 동의<span id="card_agree_li_select3" class="agree_li_select"
					onclick="card_agree_select(3)">내용보기▼</span>
						<span id="card_agree_li_selected3" class="agree_li_selected" onclick="card_agree_selected(3)">내용보기▲</span>
						<div id="card_agree_li_view3" class="agree_li_view">카드 약관3</div></li>
			</ul>
		
</div>
<div class="trade_type2">
	<label><input type ="checkbox" name = "mobile_agree" checked="checked">
		결제 대행 서비스 약관에 모두 동의 합니다.</label><br>
		<span id="mobile_agree_ul_select" class = "agree_ul_select">내용 보기▼</span>
		<span id="mobile_agree_ul_selected" class="agree_ul_selected">내용보기▲</span>
		<ul class="agree_ul" id="mobile_agree_ul">
				<li>서비스 이용약관 동의<span id="mobile_agree_li_select1" class="agree_li_select"
					onclick="mobile_agree_select(1)">내용보기▼</span>
						<span id="mobile_agree_li_selected1" class="agree_li_selected" onclick="mobile_agree_selected(1)">내용보기▲</span>
						<div id="mobile_agree_li_view1" class="agree_li_view">모바일 약관1</div></li>
				<li>개인정보 수집 및 이용 동의<span id="mobile_agree_li_select2" class="agree_li_select" 
					onclick="mobile_agree_select(2)">내용보기▼</span>
						<span id="mobile_agree_li_selected2" class="agree_li_selected" onclick="mobile_agree_selected(2)">내용보기▲</span>
						<div id="mobile_agree_li_view2" class="agree_li_view">모바일 약관2</div></li>
				<li>개인정보 제공 및 위탁 동의<span id="mobile_agree_li_select3" class="agree_li_select"
					onclick="mobile_agree_select(3)">내용보기▼</span>
						<span id="mobile_agree_li_selected3" class="agree_li_selected" onclick="mobile_agree_selected(3)">내용보기▲</span>
						<div id="mobile_agree_li_view3" class="agree_li_view">모바일 약관3</div></li>
			</ul>


</div>

<input type="hidden" value="" name="total_cost" id="total_cost">
<input type="hidden" value ="<%= rib.getName()%>" name="o_receive_name" id="o_receive_name">
<input type="hidden" value ="<%= rib.getMobile()%>" name="o_receive_mobile" id="o_receive_mobile">
<input type="hidden" value ="<%= rib.getAddress1()%>" name="o_receive_address1" id="o_receive_address1">
<input type="hidden" value ="<%= rib.getAddress2()%>" name="o_receive_address2" id="o_receive_address2">
<input type="hidden" value ="<%= rib.getPostcode()%>" name="o_receive_postcode" id="o_receive_postcode">

</div>

<input type = "submit" value="구매하기" id="fr_submit"><input type="button" value="취소" onclick="history.back();">
</form>
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