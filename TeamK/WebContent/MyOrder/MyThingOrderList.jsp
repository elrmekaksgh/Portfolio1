<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.ArrayList"%>
<%@page import="net.mod.db.ModTradeInfoBEAN"%>
<%@page import="com.sun.org.apache.xpath.internal.operations.Mod"%>
<%@page import="java.util.Vector"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
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
		int pageNum = Integer.parseInt(pagenum);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<Vector> ModList = (List<Vector>) request.getAttribute("ModList");
		String status = (String)request.getAttribute("status");	
		int pNum = Integer.parseInt(pagenum);
%>
<link href="./css/inc.css" rel="stylesheet" type="text/css">
<link href="./css/subpage.css" rel="stylesheet" type="text/css">
<script src = "./js/jquery-3.2.0.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$('#status').val('<%=status%>').attr('selected','selected');
})
function complet(num, ti_num){
	if(confirm('구매를 완료하시겠습니까?')){
		$.ajax({
	        type:"post",
	        url:"./TO_Status_Update.mo",
	        data:{
	           num:num,
	           status:10,
	           ti_num:ti_num
	        },
	        success:function(){
	            window.location.reload(true);
	        }
	     });
	}
}
function receive_change(i,ti_num){
	window.open('./Receive_Change.mo?num='+i+"&ti_num="+ti_num, '배송지 선택', 'left=200, top=100, width=480, height=640, scrollbars=yes');
}
function status_change(){
	location.href="./MyThingOrderList.mo?status="+$('#status').val();
}
function thing_exchange(num, ti_num){
	window.open("./TO_Cancel_or_Exchange.mo?num="+num+"&ti_num="+ti_num,''
			,'left=600, top=100, width=600, height=640');
}
function Trade_Update_Info(o_num) {
	window.open("./Trade_Update_Info.mo?num="+o_num,''
			,'left=600, top=150, width=600, height=700, scrollbars=yes');
}

//상품 내용 팝업창
function thing_popup(select)
{
	var select_num = $("#num" + select).html();
// 	alert(select_num);
	win = window.open("./MyThingPopup.mo?num=" + select_num, "MyThingPopup.jsp",
	"width=850, height=900, left=500, top=50 scrollbars=yes");	
}
</script>
<style type="text/css">
.update_info:HOVER {
 cursor: pointer;
 color: red;
}
</style>
</head>
<body>
	<!--왼쪽 메뉴 -->
	<div>
		<jsp:include page="../inc/leftMenu.jsp"></jsp:include>
	</div>
	<!--왼쪽 메뉴 -->
	<div id="wrap">
	<div id="article_head">
<div id="article_title">내 상품 주문 목록</div>
<div class="empty"></div>
<div id="article_script"></div>
</div>
<article>
	<select id ="status" onchange="status_change()">
		<option value="ing">구매 중인 상품</option>
		<option value="completed">지난 주문 상품</option>
	</select>
	<%
			if (ModList.size() !=0) {
				for(int i = 0; i < ModList.size(); i++){
					Vector v = ModList.get(i);		
					ModTradeInfoBEAN mtib = (ModTradeInfoBEAN)v.get(0);
					List<ModTradeInfoBEAN> mtbList = (List<ModTradeInfoBEAN>)v.get(1); %>
	<div id="my_thing_order_list">
	<fieldset>
		<legend align="left">주문 번호 : <%=mtib.getTi_num() %></legend>
		<table>
			<%for(int j =0; j< mtbList.size();j++){
				ModTradeInfoBEAN mtb = mtbList.get(j);
				DecimalFormat Commas = new DecimalFormat("#,###");
				String cost = (String)Commas.format(mtb.getCost());
				
				%>
				<tr>
					<td id="num<%=i %>" style="display:none;"><%=mtb.getOri_num()%></td>
					<td class="tr1td1, tr1td0_popup" id="tr2td1" onclick="thing_popup(<%=i %>);"><img src='./upload/<%=mtb.getImg() %>'></td>
					<td class="tr1td2, tr1td0_popup" onclick="thing_popup(<%=i %>);"><b><%=mtb.getSubject() %></b><br>
						<%=mtb.getIntro() %></td>
					<td class="tr1td3"><%=mtb.getColor() %> / <%=mtb.getSize() %></td>
					<td class="tr1td4"><%=mtb.getThing_count()%>개</td>
					<td class="tr1td5"><%=cost%>원</td>
					<td class="tr1td6"><%
					//교환 상품 배송중일때 배송정보 조회 가능하게 링크
						if(mtb.getStatus()==3){
							if(mtb.getMemo().length()!=0){%>
								<span class="update_info" onclick="Trade_Update_Info(<%=mtb.getNum() %>)" >교환 배송 중</span>
						<%}else out.print(mtb.getStatus_text());
						}else if(mtb.getStatus()==9){//환불 조건 찾기
							String[]paybackinfo = mtb.getMemo().split(",");
									//일부만 환불 되었을 경우
							if((mtb.getThing_count()-Integer.parseInt(paybackinfo[2]))!=0){%>
								구매 완료<br>
								<span style="font-size:13px;" class="update_info" 
									onclick="Trade_Update_Info(<%=mtb.getNum() %>)" >
									일부 환불 처리</span>
							<%}else out.print(mtb.getStatus_text());
						}else if(mtb.getStatus()==5||mtb.getStatus()==6){
							
							%><span class="update_info" 
									onclick="Trade_Update_Info(<%=mtb.getNum() %>)" >
									<%=mtb.getStatus_text() %></span>
					<%}else out.print(mtb.getStatus_text());
					if(mtb.getStatus()==3){ %></td>
					<td class="tr1td7">송장번호<br><%=mtb.getTrans_num() %><%} %></td>
					<%if(mtb.getStatus()==4){ %>
					<td class="tr1td7">
						<input type="button" value="구매 완료" 
							onclick="complet(<%=mtb.getNum()%>,<%=mtib.getTi_num()%>)"><br>
						<input type="button" value="교환 및 환불" onclick="thing_exchange(<%=mtb.getNum()%>,<%=mtib.getTi_num()%>)">
					</td>
					<%} %>
				</tr>
				<%} 
			DecimalFormat Commas = new DecimalFormat("#,###");
			String Total_cost = (String)Commas.format(mtib.getTotal_cost());
				%>
				<tr>
					<td class="tr2td1">주문 정보 </td>
					<td class="tr2td2">받으시는 분</td>
					<td id="receive_name<%=i%>" class="tr2td3"><%=mtib.getName() %></td>
					<td class="tr2td4">연락처</td>
					<td id="receive_mobile<%=i%>" class="tr2td5"><%=mtib.getMobile() %></td>
					<td class="tr2td6">주문 날짜</td>
					<td class="tr2td7"><%=sdf.format(mtib.getTrade_date()) %></td>
				</tr>
				<tr>
					<td class="tr3td1">배송지</td>
					<td id="receive_addr<%=i%>" class="tr3td2" colspan="5">[<%=mtib.getPostcode() %>]
						<%=mtib.getAddress1() %> <%=mtib.getAddress2() %></td>
					<%if(mtib.getStatus()==1||mtib.getStatus()==2){ %>
					<td id="receive_change<%=i %>" class="tr3td3">
						<input type= "button" value="배송지 변경" onclick = "receive_change(<%=i%>,<%=mtib.getTi_num()%>)"></td>
					<%} %>
				</tr>
				<%if(mtib.getMemo().length()!=0){ %>
					<tr>
						<td class="tr4td1">배송 요청사항</td>
						<td id="receive_memo<%=i%>" colspan="6" class="tr4td2"><%=mtib.getMemo().replace("\r\n", "<br>") %></td>
					</tr>
				<%} %>
			</table>
		</fieldset>
		</div>
		
	<%		}	
		} else {
	%>
	<br><br>주문 내역 없음
	<%
		}
	%>
	<%
		if (count != 0) {

			if (endpage > pcount)endpage = pcount;
			if (startp > pblock) {
	%><a href="./MyThingOrderList.mo?pageNum=<%=startp - 1%>" id="i">이전</a>
	<%
		}
			for (int i = startp; i <= endpage; i++) {
	if(i==pNum){%><span id="i"><%=i%></span><%}else{
	%><a href="./MyThingOrderList.mo?pageNum=<%=i%>" id="i"><%=i%></a><%
		}}
			if (endpage < pcount) {
	%><a href="./MyThingOrderList.mo?pageNum=<%=endpage + 1%>" id="i">다음</a>
	<%
		}
		}
	%> 
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