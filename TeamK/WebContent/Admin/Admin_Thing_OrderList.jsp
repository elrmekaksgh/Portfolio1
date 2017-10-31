<%@page import="java.text.DecimalFormat"%>
<%@page import="net.mod.db.ModTradeInfoBEAN"%>
<%@page import="java.util.Vector"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%	
		request.setCharacterEncoding("utf-8");
		int pblock = ((Integer) request.getAttribute("pblock")).intValue();
		int endpage = ((Integer) request.getAttribute("endpage")).intValue();
		int startp = ((Integer) request.getAttribute("startp")).intValue();
		int pcount = ((Integer) request.getAttribute("pcount")).intValue();
		int count = ((Integer) request.getAttribute("count")).intValue();
		String pagenum = (String) request.getAttribute("pageNum");
		String status = (String)request.getAttribute("status");
		String status2 = (String)request.getAttribute("status2");
		int pNum = Integer.parseInt(pagenum);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<Vector> Thing_Order_List = (List<Vector>) request.getAttribute("Thing_Order_List");
		%>
<link href="./css/inc.css" rel="stylesheet" type="text/css">
<link href="./css/subpage.css" rel="stylesheet" type="text/css">
<script src = "./js/jquery-3.2.0.js"></script>
<script type="text/javascript">

$(document).ready(function(){
	$('#status').val('<%=status%>').attr('selected','selected');
	$('#status2').val('<%=status2%>').attr('selected','selected');
	if("<%=status%>"=="completed"){
		$('select[name="status2"]').hide();
	 }
	if("<%=status2%>"!="ready"){
		$('.readycheck').hide();
	 }
	$('.Trans_Num_Fix').hide();
	search_type_check();
	$("#search").keyup(function(){
		if($('#search_type').val()=="trade_num"){
			$(this).val( $(this).val().replace(/[^0-9]/g,"") );
		}
	});
});
function Trans_Num_Insert(num){
	if($('#trans_num'+num).val().length==0){
		alert("송장 번호를 입력해 주세요");
		return false;
	}
	$('#Trans_Num_Insert_View'+num).hide();
	$('#Trans_No'+num).html($('#trans_num'+num).val());
	$('#Trans_Num_Fix'+num).show();
}
function TransNum_Insert_Reset(){
	$('.Trans_Num_Fix').hide();
	$('.Trans_Num_Insert_View').show();
}
function Trans_Num_Fix(num){
	$('#Trans_Num_Insert_View'+num).show();
	$('#Trans_Num_Fix'+num).hide();
	$('#trans_num'+num).select();
}
function status_change(){
	location.href="./Admin_Thing_OrderList.ao?status="+$('#status').val();
}
function status2_change(){
	location.href="./Admin_Thing_OrderList.ao?status="+$('#status').val()+
	"&status2="+$('#status2').val();
}
function search(){
	if($('#search').val().length==0){
		alert('검색어를 입력해 주세요');
		return false;
	}else{
		location.href="./Admin_Thing_OrderList_Search.ao?status="+$('#status').val()+
		"&status2="+$('#status2').val()+"&search_type="+$('#search_type').val()+
		"&search="+$('#search').val();
	}
	
}
function trans_num_search(trans_num){
	window.open("./trans_num_search.ao?num="+trans_num,''
			,'left=600, top=150, width=400, height=400');
}
function status_update(o_num,status){
	if(status==3){
		if($('#Trans_num'+o_num).val().length==0){
			alert('송장 번호를 입력해 주세요');
			$('#Trans_num'+o_num).focus();
			return false;
		}
		if($('#memo'+o_num).val().length==0){
			alert('판매자 코멘트를 입력해 주세요');
			$('#memo'+o_num).focus();
			return false;
		}
	   $.ajax({
	        url:'./TO_Status_Update.ao',
	        type:'post',
	        data:{
	        	o_num:o_num,
	            status:status,
		        trans_num:$('#Trans_num'+o_num).val(),
		        trans_memo:$('#memo'+o_num).val()
	        },success:function(){
	        	alert('교환 발송 처리 되었습니다');
	        	location.reload();
			}
		});
	}else if(status==9){
		if(conform('정말 환불 처리 하십니까?')){
			$.ajax({
		        url:'./TO_Status_Update.ao',
		        type:'post',
		        data:{
		        	o_num:o_num,
		            status:status,
		            ti_num:$('#ti_num'+o_num).val()
		        },success:function(){
		        	alert('환불 처리 완료 되었습니다');
		        	location.reload();
				}
			});
		}else{
			return false;
		}
	}else if(status==5){
		window.open("./Thing_Exchange.ao?num="+o_num,''
				,'left=600, top=150, width=600, height=600, scrollbars=yes');
	}
}
function Trade_Update_Info(o_num) {
	window.open("./Trade_Update_Info.mo?num="+o_num,''
			,'left=600, top=150, width=600, height=600, scrollbars=yes');
}
function search_type_check(){
	if($('#search_type').val()=="trade_num"){
		$('#search').attr('placeholder','숫자로만 검색 가능 합니다');
	}else{
		$('#search').removeAttr('placeholder');
	}
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
			<div id="article_title">[관리자]고객 상품 주문 확인</div>
			<div class="empty"></div>
			<div id="article_script"></div>
		</div>
		<div id="empty"></div>
		<article>
		<div id="admin_thing_order">
		<select name="status" id="status" onchange="status_change()">
			<option value="ing">현재 주문 리스트</option>
			<option value="completed">과거 주문 리스트</option>
		</select>
		<select name="status2" id="status2"  onchange="status2_change()">
			<option value="none">전체</option>
			<option value="bank">입금 확인 중</option>
			<option value="ready">배송 준비 중</option>
			<option value="sending">배송 중</option>
			<option value="arrived">배송 완료</option>
			<option value="exchange">교환 요청</option>
			<option value="cancel">환불 요청</option>
		</select>
		<div align="right">	
		<select id="search_type" name="search_type"
			onchange="search_type_check()">
			<option value="trade_num">주문번호</option>
			<option value="trans_num">송장 번호</option>
			<option value="id">아이디</option>
			<option value="payer">결제자</option>
			<option value="name">받으시는 분</option>
			<option value="mobile">연락처</option>	
		</select>
		<input type="text" id="search" name="search">
		<input type="button" value="검색" onclick="return search()">
		</div>
		<form action="./TO_Status_Update.ao?status=2" method="post" name="fr">
		
		<%
		if (Thing_Order_List.size() != 0) {
			for(int i = 0; i < Thing_Order_List.size(); i++){
				Vector v = Thing_Order_List.get(i);	
				ModTradeInfoBEAN mtib = (ModTradeInfoBEAN)v.get(0);
				List<ModTradeInfoBEAN> mtbList = (List<ModTradeInfoBEAN>)v.get(1);
	
				
			if(mtbList.size()!=0){%>
				<fieldset>
				<legend>주문 번호 : <%=mtib.getTi_num() %></legend>
				<table>
					<%for(int j =0; j< mtbList.size();j++){
						ModTradeInfoBEAN mtb = mtbList.get(j);
						DecimalFormat Commas = new DecimalFormat("#,###");
						String mtb_cost = (String)Commas.format(mtb.getCost() );
						%>
					<tr>
						<th>상품 번호
							<input type = "hidden" name = "o_num" value="<%=mtb.getNum()%>"></th>
						<td><%=mtb.getOri_num() %></td>
						<td><%=mtb.getSubject() %></td>
						<td>색상 : <%=mtb.getColor() %></td>
						<td>사이즈 : <%=mtb.getSize() %></td>
						<td><%=mtb.getThing_count()%>개</td>
						<td><%=mtb_cost%>원</td>
						<td><%
					//교환 상품 배송중일때 배송정보 조회 가능하게 링크
						if(mtb.getStatus()==3){
							if(mtb.getO_memo()!=null&&mtb.getO_memo().length()!=0){%>
								<span class="update_info" onclick="Trade_Update_Info(<%=mtb.getNum() %>)" >교환 배송 중</span>
						<%}else out.print(mtb.getStatus_text());
						}else if(mtb.getStatus()==9){//환불 조건 찾기
							String[]paybackinfo = mtb.getO_memo().split(",");
									//일부만 환불 되었을 경우
							if((mtb.getThing_count()-Integer.parseInt(paybackinfo[2]))!=0){%>
								구매 완료<br>
								<span style="font-size:13px;" class="update_info" 
									onclick="Trade_Update_Info(<%=mtb.getNum() %>)" >
									일부 환불 처리</span>
							<%}else out.print(mtb.getStatus_text());
						}else out.print(mtb.getStatus_text());
					if(mtb.getStatus()==3&&!(status2.equals("none"))){ %></td>
					<td>송장번호<input type="button" value="조회"
							onclick="trans_num_search(<%=mtb.getTrans_num() %>)">
							<br><%=mtb.getTrans_num() %>
						<%} %></td>
					<%if(status2.equals("ready")||status2.equals("exchange")){ %>
						<td id="Trans_Num_Insert_View<%=mtb.getNum()%>" class= "Trans_Num_Insert_View readycheck">
							<input type = "text" placeholder="송장번호를 입력해 주세요" 
								name = "Trans_Num" id="trans_num<%=mtb.getNum()%>">
							<input type="button" value="입력" 
								onclick = "return Trans_Num_Insert(<%=mtb.getNum()%>)"></td>
						<td id="Trans_Num_Fix<%=mtb.getNum()%>" class= "Trans_Num_Fix  readycheck">
							<span id="Trans_No<%=mtb.getNum()%>"></span>
							<input type = "Button" value="수정" 
								onclick="Trans_Num_Fix(<%=mtb.getNum()%>)">
							</td>
					<%} %>
						</tr>
					<%if(status2.equals("exchange")){
						String [] reason = mtb.getO_memo().split("ㅨ");
						String []memo=reason[0].split(",");%>
					
					<tr>
						<th>교환 요구 사항</th>
						<td colspan="4"><%=reason[1].replace("\r\n", "<br>") %></td>
						<th>교환 요구 수량</th>
						<td><%=memo[1] %>개</td>
						<td><input type="button" value="교환 정보 입력"
								onclick="status_update(<%=mtb.getNum()%>,5)"></td>
					</tr>
					<%}else if(status2.equals("cancel")){
						String [] reason = mtb.getO_memo().split("ㅨ");
						String []memo=reason[0].split(",");%>
						<tr>
						<th>환불 사유</th>
						<td colspan="6"><%=reason[1].replace("\r\n", "<br>") %></td>
					</tr>
					<tr>
						<th>환불 방식</th>
						<td><%=memo[1] %></td>
						<th>환불 수량</th>
						<td><%=memo[2] %>개</td>
						<th>환불 금액</th>
						<td><%=memo[3] %>원</td>
					
						<%if(memo[1].equals("무통장 입금")){ %>
						</tr>
						<tr>
							<th>은행명</th>
							<td><%=memo[4] %></td>
							<th>예금주</th>
							<td><%=memo[5] %></td>
							<th>계좌 번호</th>
							<td><%=memo[6] %></td>
							
						<%} %>
						
						<td>
							<input type="hidden" id="ti_num<%=mtb.getNum()%>"
								name="ti_num<%=mtb.getNum()%>" value="<%=mtib.getTi_num()%>">
							<input type="button" value="환불 완료"
								onclick="return status_update(<%=mtb.getNum()%>,9)"></td>
					</tr>
					<%}
				}%>
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
					<th>주문 날짜</th>
					<td colspan="6"><%=sdf.format(mtib.getTrade_date()) %></td>
				</tr>
				<tr>
					<th>주소</th>
					<td colspan="9	"><%="["+mtib.getPostcode()+"] "+mtib.getAddress1()+" "+mtib.getAddress2() %></td>
				</tr>
					<%if(mtib.getMemo().length()!=0){ %>
						<tr>
							<th>배송시 요청 사항</th>
							<td colspan="8"><%=mtib.getMemo().replace("\r\n", "<br>") %></td>
						</tr>
					<%} %>
				</table>
				</fieldset>
				<%} 
			}
			
			if(status2.equals("ready")){%>
				
				<input type="submit" value="발송">
			<%						
			}
	}else{
		 %>
		 <div>주문 내역이 없습니다!</div>
	 <%}%>
	 </form>
	 <%
		if (count != 0) {
			if (endpage > pcount)endpage = pcount;
			if (startp > pblock){
	%><a href="./Admin_Thing_OrderList.ao?status=<%=status %>&status2=<%=status2 %>&pageNum=<%=startp-1%>" id="i">이전</a>
	<%
		}
			for (int i = startp; i <= endpage; i++) {
	if(i==pNum){%><span id="i"><%=i%></span><%}else{			
	%><a href="./Admin_Thing_OrderList.ao?status=<%=status %>&status2=<%=status2 %>&pageNum=<%=i%>" id="i"><%=i%></a><%
		}}
			if (endpage < pcount) {
	%><a href="./Admin_Thing_OrderList.ao?status=<%=status %>&status2=<%=status2 %>&pageNum=<%=endpage+1%>" id="i">다음</a>
	<%
		}
		}
		%>
	
	<br><input type = "button" value = "주문 관리" onclick="location.href='./AdminOrderList.ao'">
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