<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="net.member.db.*"%>
<%@ page import="java.util.List"%>
<%@ page import="java.text.DecimalFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>패키지 상품 날짜 추가</title>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="./js/jquery-3.2.0.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
</head>
<style>
#Date_modify {
	width: 300px;
}
</style>
<script type="text/javascript">

	jQuery(document).ready(function($){
		// 숫자
		$(".number").keyup(function(){
			$(this).val( $(this).val().replace(/[^0-9]/g,"") );
		});
		// 영문자
		$(".str").keyup(function() {
			$(this).val( $(this).val().replace(/[0-9]|[^\!-z]/gi,"") ); 
		});
		
		
	});

	// 날짜, 가격, 수량 수정
	function dateUpdate()
	{

		if($("#add_size").val() == "" || $("#add_color").val() == "" || $("#add_size").val() == "" || $("#add_stock").val()== "" ){
			alert("빈공간이 없게 입력해주세요.")
		}else if($("#add_stock").val() == 0){
			alert("수량을 1부터 입력해주세요.")
		}
		else{
		var up_cost = $("#up_cost").val();
		var up_stock = $("#up_stock").val();
		var up_date = $("#up_date").val();
		
		if (up_cost == "" || up_stock == "" || up_date == "")
		{
			alert("수정할 내용을 입력해주세요");
		}
		else
		{
		$.ajax({
		type:"post",
		url:"./ProductModifyAction.bo",   // java로 보냄
		data:{
			num:$("#up_num").val(),
			size:$("#up_size").val(),
			stock:$("#up_stock").val(),
			color:$("#up_color").val(),
			cost:$("#up_cost").val()
		},
		success:function()
		{
		alert('수정되었습니다');
		opener.parent.location.reload();
		window.close();
		}
		});
	}
		}
	}			
	
	
	
	
	
	// 삭제
	function dateDel(select_num)
	{
		
		if(select_num != 0)
		{
		
		if (confirm("정말 삭제하시겠습니까??") == true)
		{    //확인
			$.ajax({
				type:"post",
				url:"./ProductDeleteAction.bo",   // java로 보냄
				data:{
					num:$("#up_num").val()
					},
				success:function()
				{
					alert('삭제되었습니다');
					opener.parent.location.reload();
					window.close();
				}
			});
		}
		else
		{   //취소
		    return;
		}
		}else
		{
			alert("처음 등록된 날짜는 삭제불가합니다");
		}
	}
	
	
	function colorsize_chk()
	{
 		$.ajax({   // 날짜를 클릭할때 마다 찜목록과 비교
			type:"post",
			url:"./ProductAddChk.bo",
			data:{
// 				제목, 날짜
				name:$("#name").val(),
				color:$("#add_color").val(),
				size:$("#add_size").val()
			},
			success:function(date)
			{
				if (date == 1)
				{
					alert("이미 추가된 상품정보입니다. 다시입력해주세요");
				}
			}
	});
	}
	
	// 창닫기
	function cls()
	{
		opener.parent.location.reload();
		window.close();
	}

	
</script>
<body>
	<center>
		<br> <br>
		<%
			request.setCharacterEncoding("UTF-8");

			ProductBean pb_up = (ProductBean) request.getAttribute("pb_up");
			int select_num = ((Integer)request.getAttribute("select_num")).intValue();
		%>


		<form name="fr" method="POST">
			<%-- <input type="text" name="id" value="<%=id %>"> --%>
			<!-- <input type="button" value="중복확인" onclick="idchk(document.fr.id.value)"><br><br> -->
			<p>
				<b>상품명 : <%=pb_up.getName()%></b>
			</p>



			<div id="Date_modify">
				<fieldset>
					<legend>
						<h4>수정 페이지</h4>
					</legend>
					<table>
						<tr>
							<td>color</td>
							<td><input id="up_num" style="display: none;" class="str"
								value="<%=pb_up.getNum()%>"></input> <input id="name"
								style="display: none;" value="<%=pb_up.getName()%>"></input> <input
								type="text" id="up_color" value="<%=pb_up.getColor()%>" readonly>
							</td>
						</tr>

						<tr>
							<td>Size</td>
							<td><input type="text" id="up_size" class="str"
								value="<%=pb_up.getSize()%>" readonly>
							</td>
						</tr>

						<tr>
							<td>수량</td>
							<td><input type="text" id="up_stock" class="number"
								value="<%=pb_up.getStock()%>"></td>
						</tr>
						<tr>
							<td>가격</td>
							<td><input type="text" id="up_cost" class="number"
								value="<%=pb_up.getCost()%>"></td>
						</tr>
					</table>
					<br> <input type="button" value="수정"
						onclick="dateUpdate()"> <input
						type="button" value="삭제" onclick="dateDel(<%=select_num %>)"> <input
						type="button" value="닫기" onclick="cls()">
				</fieldset>
			</div>

		</form>
		<br> <br>
	</center>
</body>
</html>