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
#datecontent 
{
	width: 390px;
	height: 350px;
	border: 3px solid gray;
	overflow: auto;
	text-align: center;
}

#datecontent table
{
	border : 1px solid black;
	border-collapse: collapse;
}

#datecontent tr:FIRST-CHILD
{
	background-color: gray;
	height : 30px;
}

#datecontent tr:HOVER
{
	cursor: pointer;
	background-color: #D5D5D5;
}

#datecontent .date_td_size
{
	height : 50px;
	border : 1px solid black;
}

#datecontent #date_date
{
	width : 150px;
}
#datecontent #date_cost
{
	width : 150px;
}

#datecontent #date_stock
{
	width : 70px;
}

#sub_notice
{
	font-size: 0.8em;
	color : gray;
}

#Date_add
{
	width : 300px;
	margin-top : 20px;
}


</style>
<script type="text/javascript">

	jQuery(document).ready(function($){
		// 달력 관련 소스
		$("#add_date").datepicker({
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
		$("#add_stock").keyup(function(){
			$(this).val( $(this).val().replace(/[^0-9]/g,"") );
		});
		
		// 가격에 숫자만 들어가게 제어
		$("#add_cost").keyup(function(){
			$(this).val( $(this).val().replace(/[^0-9]/g,"") );
		});
		
	});

	// 날짜 추가
	function dateAdd()
	{
		date = $("#add_date").val();
		cost = $("#add_cost").val();
		stock = $("#add_stock").val();
		
		if (date == "" || cost == "" || stock == "")  // 값이 비었을 경우
		{
			alert("날짜, 가격, 수량 모두 입력해주세요");
		}
		else if (stock < 0)  // 수량이 0 보다 작을 경우 
		{
			alert("수량이 0보다 작습니다");
		}
		else  // 모두 입력되었을 경우
		{
			$.ajax({
				type:"post",
				url:"./PackDateAddAction.po",   // java로 보냄
				data:{
					subject:$("#subject").val(),
					intro:$("#intro").val(),
					type:$("#type").val(),
					area:$("#area").val(),
					city:$("#city").val(),
					sarea:$("#sarea").val(),
					cost:$("#add_cost").val(),
					stock:$("#add_stock").val(),
					date:$("#add_date").val(),
					file1:$("#file1").val(),
					file2:$("#file2").val(),
					file3:$("#file3").val(),
					file4:$("#file4").val(),
					file5:$("#file5").val()
					},
				success:function()
				{
					window.location.reload(true);  // 페이지 새로고침
				}
			});
		}
	}
	
	// 날짜를 입력할 때 이미 등록된 날짜가 있는지 체크
	function date_chk()
	{
		$("#subject").val();
		$("#add_date").val();
		
		
		$.ajax({   
			type:"post",
			url:"./PackDateAddChk.po",
			data:{
// 				제목, 날짜
				subject:$("#subject").val(),
				date:$("#add_date").val()
			},
			success:function(date)
			{
				if (date == 1)
				{
					alert("이미 추가된 날짜입니다");
					$("#add_date").val("");
				}
			}
		});
	}
	
	
	// 날짜 클릭 이벤트
	function winOpen(num, select_num) {
// 		var num = $("#num" + select).val();
		win = window.open("./PackDateModify.po?num=" + num + "&select_num=" + select_num, "Package_date_modify.jsp",
				"width=500, height=400, left=800, top=100");
	}
	
	// 날짜 선택시 이벤트
	function select_date(select_num)
	{
		var packnum = $("#select_rbtn" + select_num).val();  // 해당 라디오버튼의 글번호 값을 불러온다
		$(".select_color").css("background-color","");		// tr 부분 모든 배경색을 없앤다
		$("#select_rbtn" + select_num).prop("checked", "true"); // 클릭된 라디오 버튼을 체크로 바꾼다
		$("#select_date" + select_num).css("background-color", "#D5D5D5");  // 클릭된 tr 부분의 배경색을 #D5D5D5로 바꾼다		
		
		var num = $("#num" + select_num).val();
		winOpen(num, select_num);
		
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

// 	String subject = request.getParameter("subject");
	List date_list;
	date_list = (List)request.getAttribute("date_list");
	
// 	PackBean pb_up = (PackBean)request.getAttribute("pb_up");
	PackBean subject = (PackBean)date_list.get(0);
%>
 

<form name="fr" method="POST">
<%-- <input type="text" name="id" value="<%=id %>"> --%>
<!-- <input type="button" value="중복확인" onclick="idchk(document.fr.id.value)"><br><br> -->
<p><b>상품명 : <%=subject.getSubject() %></b></p>
<p id="sub_notice">※해당 날짜 클릭 시 수정/삭제 페이지로 이동합니다</p>
<div id="datecontent">
	<table>
		<tr>
			<td id="date_date">출발일자</td>
			<td id="date_cost">상품가격</td>
			<td id="date_stock">갯수</td>
		</tr>
		
		<%
			PackBean pb;
			for (int i = 0; i < date_list.size(); i++)
			{
				pb =(PackBean)date_list.get(i);
				DecimalFormat Commas = new DecimalFormat("#,###");
				String cost = (String)Commas.format(pb.getCost());
		%>	
			<tr id="select_date<%=i %>" class="select_color" onclick="select_date(<%=i %>)">
				<input id="num<%=i %>" style="display:none;" value="<%=pb.getNum() %>"></input>
				<input id="subject" style="display:none;" value="<%=pb.getSubject() %>"></input>
				<input id="intro" style="display:none;" value="<%=pb.getIntro() %>"></input>
				<input id="type" style="display:none;" value="<%=pb.getType() %>"></input>
				<input id="area" style="display:none;" value="<%=pb.getArea() %>"></input>
				<input id="city" style="display:none;" value="<%=pb.getCity() %>"></input>
				<input id="sarea" style="display:none;" value="<%=pb.getSarea() %>"></input>
				<input id="file1" style="display:none;" value="<%=pb.getFile1() %>"></input>
				<input id="file2" style="display:none;" value="<%=pb.getFile2() %>"></input>
				<input id="file3" style="display:none;" value="<%=pb.getFile3() %>"></input>
				<input id="file4" style="display:none;" value="<%=pb.getFile4() %>"></input>
				<input id="file5" style="display:none;" value="<%=pb.getFile5() %>"></input>
				<td class="date_td_size" ><%=pb.getDate() %></td>
				<td class="date_td_size" ><%=cost %></td>
				<td class="date_td_size" ><%=pb.getStock() %></td>
			</tr>
		<%
			}
		%>
	</table>
</div>

</form>
 <div id="Date_add">
 	<fieldset>
 		<legend><h4>추가 페이지</h4></legend>
 		<table>
			<tr>
				<td>
					날짜
				</td>
				<td>
					<input type="text" id="add_date" readonly onchange="date_chk()"><br>
				</td>
			</tr>
			
			<tr>
				<td>
					가격
				</td>
				<td>
					<input type="text" id="add_cost">
				</td>
			</tr>
			
			<tr>
				<td>
					수량
				</td>
				<td>
					<input type="text" id="add_stock">
				</td>
			</tr>
		</table>
		<br>
		<input type="button" value="추가" onclick="dateAdd()">
		<input type="button" value="닫기" onclick="cls()">
 	</fieldset>
</div>
</center>
</body>
</html>