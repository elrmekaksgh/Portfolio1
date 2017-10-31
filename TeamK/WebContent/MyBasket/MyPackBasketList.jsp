<%@page import="java.text.SimpleDateFormat"%>
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
function Basket_update(num){
	$.ajax({
		type:"post",
		url:"./PackBasketUpdate.bns",
		data:{
			adult_count:$("#adult"+num+" option:selected").val(),
			child_count:$("#child"+num+" option:selected").val(),
			pcost:$("#pcost"+num).html(),
			num:$("#pch"+num).val(),
			success:function(){
				alert("sucess");
			}
		}
	});
}
function basket_delete(){
	
	if(confirm("정말 삭제하시겠습니까?")){
		if($('input:checkbox[name=pch]:checked').length==0){
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
	if($('input:checkbox[name=pch]:checked').length==0){
		alert("선택된 항목이 없습니다!");
		return false;
	}else{
		document.fr.action = "./MyOrderAddAction.mo";
		document.fr.method="post";
		document.fr.submit();
	}
}

//장바구니에서 패키지 상품 클릭 시 해당 패키지 정보로 이동
function pack_numchk(i)
{
	var numchk = $("#ori_num" + i).val();
	location.href="./PackContent.po?num=" + numchk;
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
List<PBasketBEAN> MyPackBasket=(List<PBasketBEAN>)request.getAttribute("MyPackBasket");
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
%>
<div id="article_head">
			<div id="article_title">패키지 리스트</div>
			<div class="empty"></div>
			<div id="article_script">subject Count : <%=count %></div>
		</div>
		<div id="clear"></div>
		<article>
<form name ="fr" method ="post" id="pack_basket_list">
<table>
<tr>
<th></th><th>이미지</th><th>제목</th><th>성인</th><th>유아</th><th>가격</th><th>출발일</th><th>비고</th></tr>
<%
				for (int i = 0; i < MyPackBasket.size(); i++) {
						PBasketBEAN pbb = MyPackBasket.get(i);
						String [] countp = pbb.getCountp();
						DecimalFormat Commas = new DecimalFormat("#,###");
						String pbbcost = (String)Commas.format(pbb.getCost());
			%>
			<tr>
				<input type="hidden" id="ori_num<%=i %>" value="<%=pbb.getOri_num() %>">
				<td class="chkbx"><input type ="checkbox" id="pch<%=i %>" name = "pch" value = "<%=pbb.getPb_num()%>">
				<td class="ev_hover" onclick="pack_numchk(<%=i %>)"><img src ="./upload/<%=pbb.getImg() %>"></td>
				<td class="ev_hover" onclick="pack_numchk(<%=i %>)"><%=pbb.getSubject()%><br>
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
				<td><%=sdf.format(pbb.getOri_date()) %>
				<td>
				<input type = "button" value="변경"  onclick="Basket_update(<%=i %>)" >
				</td>
				</tr>
				<%} %>
</table>
<input type="button" value="구입" onclick="return basket_submit()">
<input type="button" value="삭제"	onclick="return basket_delete()">

</form>
	<%
	if(count!=0){
				
		if(endpage > pcount)endpage = pcount;
		if(startp>pblock){
			 %><a href = "./MyPackBasketList.bns?pageNum=<%=startp-1%>" id="i">이전</a><%
		}
		for(int i = startp;i<=endpage;i++){
			%><a href="./MyPackBasketList.bns?pageNum=<%=i %>" id="i"><%=i %></a><%
		}
		if(endpage<pcount){
			%><a href = "./MyPackBasketList.bns?pageNum=<%=endpage+1%>" id="i">다음</a><%
		}
	}	//if(count%pagesize!=0)pcount+=1;
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