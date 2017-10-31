<%@page import="java.text.SimpleDateFormat"%>
<%@page import="net.bns.db.TBasketBEAN"%>
<%@page import="net.bns.db.PBasketBEAN"%>
<%@page import="java.util.List"%>
<%@ page import="java.text.DecimalFormat" %>
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

	function people_Calc(cost,num){
		var val1 = $("#adult"+num+" option:selected").val();
   		var val2 = $("#child"+num+" option:selected").val();
   		var val3 =String((val1 * cost) + (val2 * (cost/2)));
   		var val3a = val3.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');  // 금액 자릿수 ,를 붙인다
   		$('#pcost'+num).html(val3a);  
   		
    	
	} 
	function thing_cal(cost, num) {
		var val1 = $("#tcount"+num+" option:selected").val();
		$('#ttcount'+num).val(val1);
		var tval1 = String(cost*val1);
		var tval2 = tval1.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');  // 금액 자릿수 ,를 붙인다
		$('#tcost'+num).html(tval2);
		$('#ttcost'+num).val(cost*val1);
	}
	function TBasket_Update(num){
		$.ajax({
			type:"post",
			url:"./ThingBasketUpdate.bns",
			data:{
				tcount:$("#tcount"+num+" option:selected").val(),
				tcost:$("#tcost"+num).html().replace(/[^\d]+/g, ''),
				num:$("#tch"+num).val(),
				success:function(){
					alert("변경되었습니다.");
				}
			}
		});
	}
	function PBasket_Update(num){
		$.ajax({
			type:"post",
			url:"./PackBasketUpdate.bns",
			data:{
				adult_count:$("#adult"+num+" option:selected").val(),
				child_count:$("#child"+num+" option:selected").val(),
				pcost:$("#pcost"+num).html().replace(/[^\d]+/g, ''),
				num:$("#pch"+num).val(),
				success:function(){
					alert("변경되었습니다");
				}
			}
		});
	}
	function basket_delete(){
		
		if(confirm("정말 삭제하시겠습니까?")){
			if(check()==0){
				alert("선택된 항목이 없습니다!");
				return false;
			}else{
				alert($('input:checkbox[name=tch]').length);
				document.fr.action = "./MyBasketDelete.bns";
				document.fr.method="post";
				document.fr.submit();
				return false;
			}
		}else return false;
	}
	function basket_submit(){
		if(check()==0){
			alert("선택된 항목이 없습니다!");
			return false;
		}else{
			document.fr.action = "./MyOrderPay.mo";
			document.fr.method="post";
			document.fr.submit();
		}
	}
	function check(){
		return $('input:checkbox[name=pch]:checked').length+
				 $('input:checkbox[name=tch]:checked').length;
	}
	// 장바구니에서 패키지 상품 클릭 시 해당 패키지 정보로 이동
	function pack_numchk(i)
	{
		var numchk = $("#pori_num" + i).val();
		location.href="./PackContent.po?num=" + numchk;
	}
	// 장바구니에서 상품 클릭 시 해당 상품 정보로 이동
	function thing_numchk(i)
	{
		var numchk = $("#tch11" + i).val();
		var carnumchk = $("#tch1" + i).val();
		location.href="./ProductContent.bo?num=" + numchk + "&car_num=" + carnumchk;
	}
	
</script>
<%
	request.setCharacterEncoding("utf-8");
	
	int packcount = (int)request.getAttribute("packcount");
	int thingcount = (int)request.getAttribute("thingcount");
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	//List<PBasketBEAN> OrderThing = (List<PBasketBEAN>) request.getAttribute("OrderThing");
	 
%>
</head>
<body>
	<!--왼쪽 메뉴 -->
	<div>
		<jsp:include page="../inc/leftMenu.jsp"></jsp:include>
	</div>
	<!--왼쪽 메뉴 -->
	<div id="wrap">
<div id="article_head">
<div id="article_title">
	장바구니
</div>
<div class="empty"></div>
</div>
<article>
	<form name="fr">
	<div id="my_basket_list">
		<h1 style="color:#06c; font-size: 23px;">패키지</h1>
		<%
			if (packcount == 0) {
		%>
		<br><br>장바구니에 담긴 패키지가 없습니다!<br><br><br>
		<%
			} else {
				
				List<PBasketBEAN> PackBasket = (List<PBasketBEAN>) request.getAttribute("PackBasket");
				 
		%>
		<table id ="ptable">
			<tr>
				<th id="num"></th>
				<th id="num">이미지</th>
				<th id="num">상품명</th>
				<th id="num">성인</th>
				<th id="num">유아</th>
				<th id="num">가격</th>
				<th id="num">출발일</th>
				<th id="num">비고</th>
			</tr>
			<%
				for (int i = 0; i < PackBasket.size(); i++) {
						PBasketBEAN pbb = PackBasket.get(i);
						String [] countp = pbb.getCountp();
						DecimalFormat Commas = new DecimalFormat("#,###");
						String pbbcost = (String)Commas.format(pbb.getCost());
			%>
			<tr>
				<input type="hidden" id="pori_num<%=i %>" value="<%=pbb.getOri_num() %>">
				<td class="chkbx"><input type="checkbox" id="pch<%=i %>" name="pch" value="<%=pbb.getPb_num()%>"></td>
				<td id="cate" onclick="pack_numchk(<%=i %>)"><img src ="./upload/<%=pbb.getImg()%>"></td>
				<td id="title" onclick="pack_numchk(<%=i %>)"><%=pbb.getSubject()%><br>
				<%=pbb.getIntro() %></td>
				
				<td><select id = "adult<%=i%>" name="adult<%=i%>" onchange="people_Calc(<%=pbb.getOri_cost()%>,<%=i%>)">
				<%for(int j =1 ; j<11; j++){ %>
				<option value="<%=j%>" 
				<%if(j==Integer.parseInt(countp[0])){%>selected <%} %>><%=j %></option>
				<%} %>
				</select>
				</td>
				<td><select id = "child<%=i%>" name="child<%=i%>" onchange="people_Calc(<%=pbb.getOri_cost()%>,<%=i%>)">
				<%for(int j =0 ; j<11; j++){ %>
				<option value="<%=j%>" 
				<%if(j==Integer.parseInt(countp[1])){%>selected <%} %>><%=j %></option>
				<%} %>
				</select></td>
				<td id="pcost<%=i %>"><%=pbbcost%></td>
				<td><%=sdf.format(pbb.getOri_date()) %></td>
				<td>
				<input type = "button" value="변경"  onclick="PBasket_Update(<%=i %>)" >
				</td>
				</tr>

			<%
				}
			%>
		</table><br>
		<%if(packcount>5) %><a href = "./MyPackBasketList.bns?pageNum=1">더 보기 +</a><br><br><%; %>
		<%
			}
		%>
	<hr>
	<br>
	<h1 style="color:#06c; font-size: 23px;">상품</h1>
			<%
			if (thingcount == 0) {
		%>
		<br><br>장바구니에 담긴 상품이 없습니다!<br><br><br>
		<%
			} else {
				List<TBasketBEAN> ThingBasket = (List<TBasketBEAN>) request.getAttribute("ThingBasket");
		%>
		
		<table id ="ttable">
			<tr>
				<th id="num"></th>
				<th id="num">이미지</th>
				<th id="num">상품명</th>
				<th id="num">색상</th>
				<th id="num">사이즈</th>
				<th id="num">수량</th>
				<th id="num">가격</th>
				<th id="num">등록일</th>
				<th id="num">비고</th>
			</tr>
			<%
				for (int i = 0; i <ThingBasket.size(); i++) {
						TBasketBEAN tbb = ThingBasket.get(i);
						DecimalFormat Commas = new DecimalFormat("#,###");
						String tbbcost = (String)Commas.format(tbb.getCost());
						
			%>
			<tr>
				<td class="chkbx">
					<input type="checkbox" id="tch<%=i %>"name="tch" value="<%=tbb.getNum()%>">
					<input type="hidden" id="tch11<%=i %>" value="<%=tbb.getOri_num()%>">
					<input type="hidden" id="tch1<%=i %>" value="<%=tbb.getCar_num()%>">
				</td>
				<td onclick="thing_numchk(<%=i %>)" class="tr1td0_popup"><img src ="./upload/<%=tbb.getImg()%>"  height="70"></td>
				<td onclick="thing_numchk(<%=i %>)" class="tr1td0_popup"><%=tbb.getSubject() %><br>
				<%=tbb.getIntro() %></td>
				
				<td><%=tbb.getColor() %></td>
				<td><%=tbb.getSize() %></td>
				<td><select id = "tcount<%=i%>" name="tcount<%=i%>" onchange="thing_cal(<%=tbb.getOri_cost()%>,<%=i%>)">
				<%for(int j =1 ; j<tbb.getMaxcount()+1; j++){ %>
				<option value="<%=j%>" 
				<%if(j==tbb.getCount()){%>selected <%} %>><%=j %></option>
				<%} %>
				</select>
				</td>
				<td id="tcost<%=i%>"><%=tbbcost%></td>
				<td><%=sdf.format(tbb.getDate()) %></td>
				<td>
				
				<input type = "button" value="변경"  onclick="TBasket_Update(<%= i %>)" >
				</td>
				</tr>

			<%
				}
			%>
		</table><br>
		<%if(thingcount>5) %><a href = "./MyThingBasketList.bns?pageNum=1">더 보기 +</a><br><br><%; %>
		<%
			}
		%>
		
	</div>
	<input type="button" value="구입" onclick = "return basket_submit()">
	<input type="button" value ="삭제" onclick = "return basket_delete()">
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