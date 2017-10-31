<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="net.pack.db.PackBean"%>
<%@ page import="java.util.List" %>
<%@ page import="java.text.DecimalFormat" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>패키지 상품 날짜 추가</title>
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="./js/jquery-3.2.0.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
</head>
<style>

#Date_modify
{
	width : 300px;
}

</style>
<script type="text/javascript">

	jQuery(document).ready(function($){
		// 달력 관련 소스
		$("#up_date").datepicker({
			dateFormat: 'yy-mm-dd',    // 날짜 포맷 형식
			minDate : 0,			   // 최소 날짜 설정      0이면 오늘부터 선택 가능
			numberOfMonths: 1,		   // 보여줄 달의 갯수
	        dayNamesMin: ['월', '화', '수', '목', '금', '토', '일'],  // 일(Day) 표기 형식
	        monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],   // 월(Month) 표기 형식
	        //showOn: "both",		// 버튼을 표시      both : input과 buttom 둘다 클릭 시 달력 표시           bottom  :  buttom 클릭 했을 때만 달력 표시
	        //buttonImage: "./img/calendar.png",   // 버튼에 사용될 이미지
	        //buttonImageOnly: true,					// 이미지만 표시한다    버튼모양 x
		});
		
		// 수량에 숫자만 들어가게 제어
		$("#up_stock").keyup(function(){
			$(this).val( $(this).val().replace(/[^0-9]/g,"") );
		});
		
		// 가격에 숫자만 들어가게 제어
		$("#up_cost").keyup(function(){
			$(this).val( $(this).val().replace(/[^0-9]/g,"") );
		});
	});

	// 날짜, 가격, 수량 수정
	function dateUpdate()
	{
		var up_cost = $("#up_cost").val();
		var up_stock = $("#up_stock").val();
		var up_date = $("#up_date").val();
		
		if (up_cost == "" || up_stock == "" || up_date == "") // 내용이 모두 비었을 경우
		{
			alert("수정할 내용을 입력해주세요");
		}
		else if (up_stock < 0) // 수량이 0보다 작을 경우
		{
			alert("수량이 0보다 작습니다");
		}
		else
		{
			$.ajax({
				type:"post",
				url:"./PackDateModifyAction.po",   // java로 보냄
				data:{
					num:$("#up_num").val(),
					cost:$("#up_cost").val(),
					stock:$("#up_stock").val(),
					date:$("#up_date").val()
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
	
	
	// 삭제
	function dateDel(select_num)
	{
		
		if(select_num != 0)
		{
			if (confirm("삭제하시겠습니까??") == true)
			{    //확인
				$.ajax({
					type:"post",
					url:"./PackDateDeleteAction.po",   // java로 보냄
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
		}
		else
		{
			alert("처음 등록된 날짜는 삭제불가합니다");
		}
		
			
		
	}
	
	// 날짜 추가 시 동일 날짜 체크
	function date_chk()
	{
// 		alert($("#subject").val());
// 		alert($("#up_date").val());
		var prev_date = $("#prev_date").val();
		
		$.ajax({   
			type:"post",
			url:"./PackDateAddChk.po",
			data:{				//제목, 날짜
				subject:$("#subject").val(),
				date:$("#up_date").val()
			},
			success:function(date)
			{
				if (date == 1)
				{
					alert("이미 추가된 날짜입니다");
					$("#up_date").val(prev_date);
				}
			}
		});
	}
	
	function date_x()
	{
		alert("처음 등록된 날짜는 수정불가합니다");
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
<br><br>
<%
	request.setCharacterEncoding("UTF-8");

	PackBean pb_up = (PackBean)request.getAttribute("pb_up");
	int select_num = ((Integer)request.getAttribute("select_num")).intValue();
%>
 

<form name="fr" method="POST">
<p><b>상품명 : <%=pb_up.getSubject() %></b></p>
<div id="Date_modify">
	<fieldset>
 		<legend><h4>수정 페이지</h4></legend>
 		<table>
			<tr>
				<td>
					날짜
				</td>
				<td>
					<input id="up_num" style="display: none;" value="<%=pb_up.getNum() %>"></input>
					<input id="subject" style="display: none;" value="<%=pb_up.getSubject() %>"></input>
					<input id="prev_date" style="display: none;" value="<%=pb_up.getDate() %>"></input>
					
					<%
					if(select_num == 0)
					{
					%>
					<input type="text" id="up_date0" value="<%=pb_up.getDate() %>" readonly onclick="date_x()">
					<%
					}
					else
					{
					%>
					<input type="text" id="up_date" value="<%=pb_up.getDate() %>" readonly onchange="date_chk()">
					<%
					}
					%>
				</td>
			</tr>
			
			<tr>
				<td>
					가격
				</td>
				<td>
					<input type="text" id="up_cost" value="<%=pb_up.getCost() %>">
				</td>
			</tr>
			
			<tr>
				<td>
					수량
				</td>
				<td>
					<input type="text" id="up_stock" value="<%=pb_up.getStock() %>">
				</td>
			</tr>
		</table>
		<br>
		<input type="button" value="수정" onclick="dateUpdate()">
		<input type="button" value="삭제" onclick="dateDel(<%=select_num %>)">
		<input type="button" value="닫기" onclick="cls()">
	</fieldset>
</div>

</form>
<br><br>
</center>
</body>
</html>