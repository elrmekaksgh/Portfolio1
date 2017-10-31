<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="net.bns.db.TBasketBEAN"%>
<%@page import="net.bns.db.PBasketBEAN"%>
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
<script src = "./js/jquery-3.2.0.js"></script>
<script type="text/javascript">
function thing_cal(cost, num) {
	//var num = num;
	//var val1 = $("#tcount"+num+" option:selected").val();
	//$('#tcost'+num).html(cost*val1);

	var val1 = $("#tcount"+num+" option:selected").val();
	$('#ttcount'+num).val(val1);
	var tval1 = String(cost*val1);
	var tval2 = tval1.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');  // 금액 자릿수 ,를 붙인다
	$('#tcost'+num).html(tval2);
}
function Basket_Update(num){
	$.ajax({
		type:"post",
		url:"./ThingBasketUpdate.bns",
		data:{
			tcount:$("#tcount"+num+" option:selected").val(),
			tcost:$("#tcost"+num).html(),
			num:$('#tch'+num).val(),
			success:function(){
				alert("sucess");
			}
		}
	});
}
function basket_delete(){
	
	if(confirm("정말 삭제하시겠습니까?")){
		if($('input:checkbox[name=tch]:checked').length==0){
			alert("선택된 항목이 없습니다!");
			return false;
		}else{
			document.fr.action = "./MyBasketDelete.bns";
			document.fr.method="post";
			document.fr.submit();
		}
	}else return false;
}
function basket_submit(){
	if($('input:checkbox[name=tch]:checked').length==0){
		alert("선택된 항목이 없습니다!");
		return false;
	}else{
		document.fr.action = "./MyOrderAddAction.mo";
		document.fr.method="post";
		document.fr.submit();
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
<%
request.setCharacterEncoding("utf-8");
int pblock = ((Integer)request.getAttribute("pblock")).intValue();

int endpage = ((Integer)request.getAttribute("endpage")).intValue();
int startp = ((Integer)request.getAttribute("startp")).intValue();
int pcount = ((Integer)request.getAttribute("pcount")).intValue();
int count = ((Integer)request.getAttribute("count")).intValue();
String pagenum = (String)request.getAttribute("pageNum");
int pageNum = Integer.parseInt(pagenum);
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
List<TBasketBEAN> ThingBasket=(List<TBasketBEAN>)request.getAttribute("MyThingBasket");

%>
<div id="article_head">
			<div id="article_title">상품 리스트</div>
			<div class="empty"></div>
			<div id="article_script">subject Count : <%=count %></div>
		</div>
		<div id="clear"></div>
		<article>
<form method ="post" name ="fr" id="goods_basket_list">
	<table>
			<tr>
			<th></th>
				<th>이미지</th>
				<th>상품명</th>
				<th>색상</th>
				<th>사이즈</th>
				<th>수량</th>
				<th>가격</th>
				<th>등록일</th>
			</tr>
			<%
				for (int i = 0; i <ThingBasket.size(); i++) {
						TBasketBEAN tbb = ThingBasket.get(i);
						DecimalFormat Commas = new DecimalFormat("#,###");
						String tbbcost = (String)Commas.format(tbb.getCost());
						
			%>
			<tr>
			<td class="chkbx"><input type = "checkbox" name="tch" value="<%=tbb.getNum()%>" id = "tch<%=i%>"></td>
				<td><img src ="./upload/<%=tbb.getImg() %>"></td>
				<td><%=tbb.getSubject() %><br>
				<%=tbb.getIntro() %></td>
				
				<td><%=tbb.getColor() %></td>
				<td><%=tbb.getSize() %></td>
				<td><select id = "tcount<%=i%>" name="tcount<%=i%>" onchange="thing_cal(<%=tbb.getOri_cost()%>,<%=i%>)">
				<%for(int j =1 ; j<tbb.getMaxcount()+1; j++){ %>
				<option value="<%=j%>" 
				<%if(j==tbb.getCount()){%>selected <%} %>><%=j %></option>
				<%} %>
				</select><br><br>
				<input type = "button" value="수량 변경"  onclick="Basket_Update(<%=i %>)" >
				</td>
				
				<td id="tcost<%=i%>"><%=tbbcost%></td>
				<td><%=sdf.format(tbb.getDate()) %></td>
				</tr>

			<%
				}
			%>
		</table>
		
		<input type="button" value="구입" onclick="return basket_submit()">
		<input type="button" value="삭제"	onclick="return basket_delete()">
			</form>
	<%
	if(count!=0){
				
		if(endpage > pcount)endpage = pcount;
		if(startp>pblock){
			 %><a href = "./MyThingBasketList.bns?pageNum=<%=startp-1%>" id="i">이전</a><%
		}
		for(int i = startp;i<=endpage;i++){
			%><a href="./MyThingBasketList.bns?pageNum=<%=i %>" id="i"><%=i %></a><%
		}
		if(endpage<pcount){
			%><a href = "./MyThingBasketList.bns?pageNum=<%=endpage+1%>" id="i">다음</a><%
		}
	}	//if(count%pagesize!=0)pcount+=1;
	%><br><input type = "button" value = "내주문" onclick="location.href='./MyOrderList.mo'">
		</div>
	<jsp:include page="../inc/footer.jsp"></jsp:include>
	<!--오른쪽 메뉴 -->
	<div>
		<jsp:include page="../inc/rightMenu.jsp"></jsp:include>
	</div>
	<!--오른쪽 메뉴 -->
</body>
</body>
</html>