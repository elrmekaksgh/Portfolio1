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
<link href="./css/inc.css" rel="stylesheet" type="text/css">
<link href="./css/subpage.css" rel="stylesheet" type="text/css">
<script src = "./js/jquery-3.2.0.js"></script>
<script type="text/javascript">

$(document).ready(function(){
	order_hide();
})
function order_view(i){
	order_hide();
	$('#order_select'+i).hide();
	$('#order_view'+i).show();
	$('#order_selected'+i).show();
}
function order_hide(){
	$('.order_selected').hide();
	$('.order_view').hide();
	$('.order_select').show();
}
function select_check(){
	if($("input[name='tich']:checked").length==0){
		alert("선택 된 주문이 없습니다!");
		return false;	
	}
}
function Trade_Info_Delete(){
	var tich = [];
	$("input[name='tich']:checked").each(function(i){
		tich.push($(this).val());
	})
	$.ajax({
		url:'./Trade_Info_Delete.ao',
		type:'post',
		data:{
			tich:tich,
		},success:function(){
			alert("삭제 완료");
			window.location.reload(true);
        }
	});
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
<div id="article_title">[관리자]고객 무통장 입금확인</div>
<div class="empty"></div>
</div>
<article>
<div id="bank_pay_check">
<%	
		request.setCharacterEncoding("utf-8");
		int pblock = ((Integer) request.getAttribute("pblock")).intValue();
		int endpage = ((Integer) request.getAttribute("endpage")).intValue();
		int startp = ((Integer) request.getAttribute("startp")).intValue();
		int pcount = ((Integer) request.getAttribute("pcount")).intValue();
		int count = ((Integer) request.getAttribute("count")).intValue();
		String pagenum = (String) request.getAttribute("pageNum");
		int pNum = Integer.parseInt(pagenum);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<Vector> ModList = (List<Vector>) request.getAttribute("ModList");
		%>
		<form action="./BankPayChecked.ao" method="post" name="fr" onsubmit="return select_check()">
		<%
		if(ModList.size()==0&&count!=0)response.sendRedirect("./BankPayCheck.ao");
		if (ModList.size() != 0) {
			for(int i = 0; i < ModList.size(); i++){
				Vector v = ModList.get(i);		
				ModTradeInfoBEAN mtib = (ModTradeInfoBEAN)v.get(0);
				List<ModTradeInfoBEAN> mpbList = (List<ModTradeInfoBEAN>)v.get(1);
				List<ModTradeInfoBEAN> mtbList = (List<ModTradeInfoBEAN>)v.get(2);
				DecimalFormat Commas = new DecimalFormat("#,###");
				String mtib_cost = (String)Commas.format(mtib.getTotal_cost() );			
	%>
	
		<fieldset>
		<legend>주문 번호 : <%=mtib.getTi_num() %></legend>
		<h4>결제일 : <%=sdf.format(mtib.getTrade_date())%></h4><br>
		<table border="1">
			<tr>
				<td class="chkbx"><input type = "checkbox" name ="tich" 
							value = "<%=mtib.getTi_num() %>"></td>
				<td><%=mtib.getPayer() %></td>
				<td><%=mtib.getTrade_type() %></td>
				<td><%=mtib_cost%>원</td>
				<td><%=sdf.format(mtib.getTrade_date()) %></td>
			</tr>
		</table>
		<br><center id="order_select<%=i%>" class="order_select"
			onclick = "order_view(<%=i%>)">주문 품목▼</center>
		<center id="order_selected<%=i%>" class="order_selected" onclick="order_hide()">주문 품목▲</center>
		<div id="order_view<%=i%>" class="order_view">
		<%if(mpbList.size()!=0){ %>
				<br><h5>주문한 여행 패키지 목록</h5><br>
				<table border = "1">
					<%for(int j =0; j< mpbList.size();j++){
						ModTradeInfoBEAN mpb = mpbList.get(j);
						String []pack_count = mpb.getPack_count().split(",");
						Commas = new DecimalFormat("#,###");
						String mpb_cost = (String)Commas.format(mpb.getCost() );
						%>
					<tr onclick="location.href='#'">
						<td><%=mpb.getTrade_num() %></td>
						<td><%=mpb.getImg() %></td>
						<td><%=mpb.getSubject() %></td>
						<td><%=mpb.getIntro() %></td>
						<td>성인 : <%=pack_count[0] %>, 아동 : <%=pack_count[1] %></td>
						<td><%=mpb_cost%>원</td>
						<td><%if(mpb.getPo_receive_check()==1){ %>Yes
						<%}else{%>NO<%} %></td>
					</tr>						
					<%} %>
				</table>				
			<%} 
			if(mtbList.size()!=0){%>
				<br><h5>주문한 상품 목록</h5><br>
				<table border = "1">
					<%for(int j =0; j< mtbList.size();j++){
						ModTradeInfoBEAN mtb = mtbList.get(j);
						Commas = new DecimalFormat("#,###");
						String mtb_cost = (String)Commas.format(mtb.getCost() );
						%>
					<tr onclick="location.href='#'">
						<td><%=mtb.getTrade_num() %></td>
						<td><%=mtb.getImg() %></td>
						<td><%=mtb.getSubject() %></td>
						<td><%=mtb.getIntro() %></td>
						<td><%=mtb.getColor() %></td>
						<td><%=mtb.getSize() %></td>
						<td><%=mtb.getThing_count()%>개</td>
						<td><%=mtb_cost%>원</td> 
					</tr>
					<%} %>
				</table>
				<%} %>
		</div>
		
		<br></fieldset>
	<%	
			}
			%>
		
		<input type="submit" value="입금 확인  완료">
		<input type="button" value="주문 삭제" onclick="Trade_Info_Delete()"> 
	<%
	}else{
		 %>
		 <div>새로운 무통장 입금 내역이 없습니다!</div>
		 <%
	}
		%>
		</form>
		<%
	if (count != 0) {
		if (endpage > pcount)endpage = pcount;
		if (startp > pblock) {
	%><a href="./BankPayCheck.ao?pageNum=<%=startp - 1%>" id="i">이전</a>
	<%
		}
			for (int i = startp; i <= endpage; i++) {
	if(i==pNum){%><span id="i"><%=i%></span><%}else{			
	%><a href="./BankPayCheck.ao?pageNum=<%=i%>" id="i"><%=i%></a><%
		}}
			if (endpage < pcount) {
	%><a href="./BankPayCheck.ao?pageNum=<%=endpage + 1%>" id="i">다음</a>
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