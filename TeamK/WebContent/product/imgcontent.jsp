<%@page import="com.sun.xml.internal.ws.api.config.management.Reconfigurable"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ page import="net.member.db.*"%>
	<%@ page import="net.pack.db.PackDAO"%>
<%@ page import="net.pack.db.PackBean"%>
<%@ page import="java.util.List"%>
<%@ page import="java.text.DecimalFormat" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="./css/inc.css" rel="stylesheet" type="text/css">
<link href="./css/subpage.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="./js/jquery-3.2.0.js"></script>
<!-- <script src="https://code.jquery.com/jquery-1.12.4.js"></script> -->
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="./js/jssor.slider-24.1.5.min.js" type="text/javascript"></script>
</head>
<%	request.setCharacterEncoding("utf-8");
	ProductBean pb = new ProductBean();
	ProductDAO pdao = new ProductDAO();
	CategoryBean cb = new CategoryBean();
	CategoryDAO cdao = new CategoryDAO();
	CommentBean comb = new CommentBean();
	CommentDAO comdao = new CommentDAO();
	List productList2 = (List) request.getAttribute("productList2");
	List RecommendPack = (List)request.getAttribute("RecommendPack");

	 	int num = (int) request.getAttribute("num");
	 	int count = (int) request.getAttribute("count");
	 	int commentcount = (int) request.getAttribute("commentcount");
	 	int pageSize = (int) request.getAttribute("pageSize");
	 	String pageNum = (String) request.getAttribute("pageNum");
	 	int startRow = (int) request.getAttribute("startRow");
	 	int endRow = (int) request.getAttribute("endRow");
	 	List productList = (List) request.getAttribute("productList");
	 	int pageCount = (int) request.getAttribute("pageCount");
	 	int pageBlock = (int) request.getAttribute("pageBlock");
	 	int startPage = (int) request.getAttribute("startPage");
	 	int endPage = (int) request.getAttribute("endPage");
	 	List productList3 = (List) request.getAttribute("productList3");
	 	List commentList = (List) request.getAttribute("commentList");
	 	
		int pageSize2 = (int) request.getAttribute("pageSize2");
	 	String pageNum2 = (String) request.getAttribute("pageNum2");
	 	int startRow2 = (int) request.getAttribute("startRow2");
	 	int endRow2 = (int) request.getAttribute("endRow2");
		int pageCount2 = (int) request.getAttribute("pageCount2");
	 	int pageBlock2 = (int) request.getAttribute("pageBlock2");
	 	int startPage2 = (int) request.getAttribute("startPage2");
	 	int endPage2 = (int) request.getAttribute("endPage2");
		String user_id = (String) session.getAttribute("id");
		List ProductImgList = (List)request.getAttribute("ProductImgList");

if (user_id == null)
	user_id = "";
%>
<body>
<script>

	jQuery(document).ready(function($){
		$("#remote_content").draggable();
		$("#avg_cost").html($('#avg_cost').html().replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,')+"원");
		 
		// 작은 이미지 클릭 시 큰 이미지부분이 클릭한 이미지로 교체
		$('.bxslider img').click(function(){
			
			// 모든 이미지의 테두리값을 없앤다
			$('.bxslider img').css("border", "");  
			
			// 클릭된 이미지에 회색 테두리를 만든다
			// 선택된 효과
			$(this).css({
				"border" : "5px solid #A6A6A6",
				"box-sizing" : "border-box"
			});
			
			// 클릭된 이미지의 src 주소값을 가져온다
			var imgurl = $(this).attr("src");
			// 큰 이미지 부분에 클릭된 작은이미지 src 적용
			$('#main').attr("src", imgurl);
		});
		
		
		$('#close').click(function(){
			$('#banner').hide();
			$('#banner_sub').hide();
		});
	
		// 달력 관련 소스
	
	
	});
	
	jQuery(document).ready(function($){
		var packnum = $("#ori_num_chk").val();
// 		alert(packnum);
		$.ajax({   // 날짜를 클릭할때 마다 찜목록과 비교
			type:"post",
			url:"./MyInterestCheck.ins",
			data:{
				num:packnum,
				type:"T"
			},
			success:function(data)
			{
				if (data == 1)  // 찜목록에 해당 날짜 패키지가 있을 시
				{
					$("#jjim_o").hide();  // 찜추가   버튼  숨기기
					$("#jjim_x").show();  // 찜삭제   버튼 보이기
				}
				else   // 찜목록에 해당 날짜 패키지가 없을 시
				{
					$("#jjim_o").show();  // 찜추가 버튼 보이기
					$("#jjim_x").hide();  // 찜삭제 버튼 숨기기
				}
			}
		});
	});
	
	
	
	// 찜하기, 예약하기 버튼 클릭 시 각각 버튼 마다 이동할 페이지
	function submit_fun(i, user_id)
	{
		if(user_id != "")
		{
			// i = 1  찜추가   i = 2 찜취소    i = 3  예약하기
			if (i == 1) // 로그인 되어 있을 경우 찜추가
			{
			
				$.ajax({
					type:"post",
					url:"./MyInterestAdd.ins",   // java로 보냄
					data:{
						type:"T",
// 						num:$("input[type=radio][name=chk]:checked").val()	
						num:$("#ori_num_chk").val()
					},
					success:function(){
						$("#jjim_o").hide();
						$("#jjim_x").show();
						alert("찜목록에 추가되었습니다");
//							window.location.reload(true);  // 페이지 새로고침
					}
				});
			}
			
			else if(i == 2)   // 로그인에 되어 있을 경우 찜취소
			{
				$.ajax({
					type:"post",
					url:"./MyInterestDel.ins",   // java로 보냄
					data:{
						type:"T",
// 						num:$("input[type=radio][name=chk]:checked").val()	
						num:$("#ori_num_chk").val()
					},
					success:function(){
						$("#jjim_o").show();
						$("#jjim_x").hide();
						alert("찜목록에서 삭제되었습니다");
//							window.location.reload(true);  // 페이지 새로고침
					}
				});
			}
			
			else if (i == 3)  // 로그인 되어 있을 경우  장바구니
			{
				
				var val1 = $("#color option:selected").val(); 
				var val2 = $("#size option:selected").val();
				if(val1 == ""){
					alert("color를 선택해주세요.")
				}else if(val2 ==""){
					alert("size를 선택해주세요.")
				}else{
				var cost_temp = $("#p").html(); // 총금액 받아오기
				str = String(cost_temp);		// 총금액 천원단위로 , 찍혀있는걸
			    cost = str.replace(/[^\d]+/g, '');   // 풉니다
			  			
			    $("#cost").val(cost);
			    $("#ori_num").val($("#size option:selected").val());
		
			    document.input_fr.action = "./MyBasketAddAction.bns";	// 장바구니 페이지로 이동
			    document.input_fr.method = "post";
			    document.input_fr.submit();
				}
			}
			
			else if (i == 4)  // 로그인 되어 있을 경우  예약하기
			{
				var val1 = $("#color option:selected").val(); 
				var val2 = $("#size option:selected").val();
				if(val1 == ""){
					alert("color를 선택해주세요.")
				}else if(val2 ==""){
					alert("size를 선택해주세요.")
				}else{
				var cost_temp = $("#p").html(); // 총금액 받아오기
				str = String(cost_temp);		// 총금액 천원단위로 , 찍혀있는걸
			    cost = str.replace(/[^\d]+/g, '');  // 풉니다
				
			    // 폼태그로 보내기때문에 hidden 숨겨둔 곳에 각각 값을 넣는다
			    $("#cost").val(cost);
			    $("#ori_num").val($("#size option:selected").val());

			    document.input_fr.action = "./MyOrderPay.mo";  // 예약하기 페이지로 이동
			    document.input_fr.method = "post";
			    document.input_fr.submit();
				}
			}
		}
		
		
		
		
		else if(user_id == "")	// 로그인 안되어 있을 경우
		{
			loginChk();
		}
	}
	
	
	// 주변 명소, 주변 맛집 클릭 시  검색할 값을 구글맵으로 보낸다
	// 현재 페이지의 City, Sarea 값을 가져온다
	

	// 댓글 쓰기
	function ReplyWrite(num)
	{
		if($(".secretChk").is(":checked"))  // 비밀글 체크 o
		{
			$.ajax({
				type:"post",
				url:"./ContenttWriteAction.bo",   // java로 보냄
				data:{
					id:$("#id").val(),
					content:$("#content").val(),
					num:num,
					secretChk:$(".secretChk").val()	// 비밀글 아닐 시 값 1
					},
					success:function()
					{
						window.location.reload(true);  // 페이지 새로고침
					}
				});
		}
		else							 // 비밀글 체크 x
		{
			$.ajax({
				type:"post",
				url:"./ContenttWriteAction.bo",
				data:{
					id:$("#id").val(),
					content:$("#content").val(),
					num:num,
					secretChk:"0"     // 비밀글 아닐 시 값 0
				},
				success:function(){
					window.location.reload(true);   // 페이지 새로고침
				}
			});
		}
	}

	// 대댓글 작성 시 
	function Re_Reply_Write(num)
	{
		if($(".re_secretChk").is(":checked"))  // 비밀글 체크 o
		{
			$.ajax({
				type:"post",
				url:"./ContenttWriteAction2.bo",
				data:{
					id:$("#reid").val(),
					content:$("#recontent"+num).val(),
					num:$("#num").val(),
					repageNum:$("#repageNum").val(),
					replynum:$("#replynum").val(),
					re_ref:$("#re_ref"+num).val(),
					re_lev:$("#re_lev").val(),
					re_seq:$("#re_seq").val(),
					secretChk:$(".re_secretChk").val(),
					success:function(){
						window.location.reload(true);
					}
				}				
			});
		}
		else		  // 비밀글 체크 x
		{
			$.ajax({
				type:"post",
				url:"./ContenttWriteAction2.bo",
				data:{
					num:$("#num").val(),
					id:$("#reid").val(),
					content:$("#recontent"+num).val(),
					repageNum:$("#repageNum").val(),
					replynum:$("#replynum").val(),
					re_ref:$("#re_ref"+num).val(),
					re_lev:$("#re_lev").val(),
					re_seq:$("#re_seq").val(),
					secretChk:"0",
					success:function(){
						window.location.reload(true);
					}
				}
				
			});
		}
	}

	// 댓글 삭제
	function ReplyDel(renum, id)
	{
		
		$.ajax({
			type:"post",
			url:"./ContentDeleteAction.bo",
			data:{
				num:renum,
				id:id,				
				success:function(){
					window.location.reload(true);
				}
			}
		});
	}

	// 댓글 수정
	function reUpdateAction(num)
	{
		if($(".up_secretChk"+num).is(":checked"))
		{
			$.ajax({
				type:"post",
				url:"./ContentUpdateAction.bo",
				data:{
					content:$("#contentup"+num).val(),
					num:num,
					secretChk:$(".up_secretChk"+num).val(),
					success:function(){
						window.location.reload(true);
					}
				}				
			});
		}
		
		else
		{
			$.ajax({
				type:"post",
				url:"./ContentUpdateAction.bo",
				data:{
					num:num,
					content:$("#contentup"+num).val(),
					secretChk:"0",
					success:function(){
						window.location.reload(true);
					}				
				}
			});
		}
	}

	

	// 대댓글 작성 시 해당 댓글 밑에 입력창 보여주기/숨기기
	function rewrite(renum){
		$('#con' + renum).toggle();
	}
	
	// 대댓글 작성 시 해당 댓글 밑에 입력창 보여주기/숨기기
	function reupdate(renum){
		$('#conup' + renum).toggle();
		$("#relist" + renum).toggle();
	}

	// 비로그인 때 상품문의글 쓰려고 하면 실행
	function loginChk()
	{
		if (confirm("로그인이 필요한 서비스입니다\n로그인 화면으로 이동하시겠습니까?") == true){    //확인
		    location.href="./MemberLogin.me";
		}
		else //취소
		    return;
	}

	// 패키지 검색 시 지역 선택
	function input_chk()
    {
    	var val = $("#area option:selected").val(); 
    	if (val == "")
		{
    		alert("지역을 선택해주세요");
	    		return false;
		}
		return true;
    }
	function Rcom_move(select)
	{
		var select_num = $("#num" + select).html();
		location.href="./PackContent.po?num=" + select_num;
	}
	
	// 리모컨 닫기 이벤트
	function remote_close()
	{
		$('#remote_control').hide();
	}
	
	// 리모컨으로 태그 화면 이동
	function fnMove(seq){
        var offset = $(seq).offset();
        $('html, body').animate({scrollTop : offset.top}, 400);
    }
	
	// 리모컨 마우스 Drag&Drop 이벤트
	jQuery(document).ready(function($){
	
	});
	
	// 날짜 추가 버튼 클릭 이벤트
	function winOpen(num) {
		win = window.open("./ProductAdd.bo?num=" + num , "ProductAdd.jsp",
				"width=800, height=700, left=100, top=100");
	}
	
	
	// 날짜 선택시 이벤트
	function select_date(select_num)
	{
		var packnum = $("#select_rbtn" + select_num).val();
// 		alert(packnum);
		$(".select_color").css("background-color","");
		$("#select_rbtn" + select_num).prop("checked", "true");
		$("#select_date" + select_num).css("background-color", "#D5D5D5");
		
		$.ajax({
			type:"post",
			url:"./MyInterestCheck.ins",
			data:{
				num:packnum,
				type:"T"
			},
			success:function(data)
			{
// 				alert(data);
				if (data == 1)
				{
					$("#jjim_o").hide();
					$("#jjim_x").show();
				}
				else
				{
					$("#jjim_o").show();
					$("#jjim_x").hide();
				}
			}
		});
	}

	//슬라이더
	jssor_1_slider_init = function() {
        var jssor_1_options = {
          $AutoPlay: 1,
          $Idle: 0,
          $SlideDuration: 5000,
          $SlideEasing: $Jease$.$Linear,
          $PauseOnHover: 4,
          $SlideWidth: 300, //각 슬라이더의 가로 길이
          $Cols: 5 //최소 슬라이더(div)의 개수. 이보다 div를 한 개 이상 더 넣어야 원활하게 작동 됨.
        };

        var jssor_1_slider = new $JssorSlider$("jssor_1", jssor_1_options);
    };

</script>





	<div id="remote_control">
		<table id="remote_content">
			<tr>
				<td><span onclick="remote_close()">close</span></td>
			</tr>
			<tr>
				<td><span onclick="fnMove('body')">Top</span></td>
			</tr>
			<tr>
				<td><span onclick="fnMove('#contentdiv2')">상품정보</span></td>
			</tr>
		
			<tr>
				<td style="border-bottom: none;"><span onclick="fnMove('#QnA')">상품문의</span></td>
			</tr>
		</table>
	</div>
	<%
	if (RecommendPack.size() > 0)
	{
	%>
	<div id="banner">
		<table id="banner_content">
			<tr>
				<tr>
				<%
					if (RecommendPack.size() == 1)
					{
				%>
						<td>
							<div id="banner_sub">추천패키지</div>
							<div id="close">close</div>
						</td>
						
				<%
					}
					else if (RecommendPack.size() == 2)
					{
				%>
						<td><div id="banner_sub">추천패키지</div></td>
						<td><div id="close">close</div></td>
				<%
					}
					else if (RecommendPack.size() == 3)
					{
				%>
						<td><div id="banner_sub">추천패키지</div></td>
						<td></td>
						<td><div id="close">close</div></td>
				<%
					}
				%>
				</tr>
				<%
				PackBean pdb = new PackBean();
					for(int i = 0; i < RecommendPack.size(); i++)
					{
						pdb =(PackBean)RecommendPack.get(i);
						DecimalFormat df = new DecimalFormat("#,###");
						
				%>
				
				<td>
					<table>
						<tr>
							<td id="num<%=i %>" style="display: none;"><%=pdb.getNum() %></td>
			
							<td><img id="Rcom_pd" src="./upload/<%=pdb.getFile1() %>" onclick="Rcom_move(<%=i %>)"></td>
						</tr>
						<tr>
							<td><div class="info"><%=df.format(pdb.getCost()) %>원</div></td>
						</tr>
					</table>
				</td>
				<%
					}
				%>
				
			</tr>
		</table>
	</div>
	<%
	}
	%>

	<!-- 왼쪽 메뉴 -->
	<jsp:include page="../inc/leftMenu.jsp"></jsp:include>
	<!-- 왼쪽 메뉴 -->
	<!--여행지 검색창 -->
	<div id="wrap"> 
	<div id="article_head">
		<div id="article_title"><img src="./img/shop2.png" width="26px" style="margin:0 8px 3px 0;  vertical-align: bottom;">상품</div>
			<div class="empty"></div>
			<div id="clear"></div>
	</div>
	<!--여행지 검색창 -->
	<div id="goods_feat">
		<div id="goods_show">
			<div id="jssor_1" style="position:relative;margin:0 auto;top:0px;left:0px;width:1000px;height:250px;overflow:hidden;visibility:hidden;">
        <!-- Loading Screen -->
        <div data-u="loading" style="position:absolute;top:0px;left:0px;background:url('./img/loading.gif') no-repeat 50% 50%;background-color:rgba(0, 0, 0, 0.7);"></div>
        <div data-u="slides" style="cursor:default;position:relative;top:0px;left:0px;width:1000px;height:250px;overflow:hidden;">
          <%
				ProductBean prob;
				for (int i = 0; i < ProductImgList.size(); i++)
				{
					int j = i + 1;
					int z = ProductImgList.size();
					prob =(ProductBean)ProductImgList.get(i);
					DecimalFormat df = new DecimalFormat("#,###");
				    String cost = df.format(prob.getCost());
					
			%>
            <div>
            	<div id="gdsld">
                <a href="./ProductContent.bo?num=<%=prob.getNum()%>&car_num=<%=prob.getCar_num()%>"><img src="./upload/<%=prob.getImg() %>" /><br><br>
                <h2><%=prob.getName() %></h2>
                <h3>￦<%= cost%></h3></a>
                </div>
            </div>
           <%} %>
            <a data-u="any" href="https://wordpress.org/plugins/jssor-slider/" style="display:none">wordpress slider</a>
        </div>
    </div>
    <script type="text/javascript">jssor_1_slider_init();</script>
		</div>
		<div id="goods_search">
			<form action="./ProductSearchAction.bo" name="fr" method="get" id="scheduler" onsubmit="return input_chk();">
				<label for="date_from">검색명</label><input type="text" id="serch_data" class="input_style" name="serch_data" required="yes">
				<input type="submit" value="검색" id="search_btn" class="input_style">
			</form>
		</div>
	</div>
	<div id="clear"></div>
	<!--여행지 검색창 -->
	<%
				for (int i = 0; i < productList.size(); i++) {

						pb = (ProductBean) productList.get(i);
			%>
		<div id="pack_btn">
		<!--글제목 -->
		<br>
		<h3><%=pb.getSubject()%></h3>
		<!--글제목 -->
		<!--관리자만 보이게 -->
		<%
		if (user_id != null)
		{
			if (user_id.equals("admin"))
			{
		%>
			<input type="button" value="상품편집" onclick="winOpen(<%=num %>);">
			<input type="button" value="상품글수정" onclick="location.href='./ProductUpdate.bo?num=<%=num%>&pageNum=<%=pageNum%>'">
		<%
			}
		}
		%>
		</div>
		<!--관리자만 보이게 -->
		<hr>
		<div id="top">
			<!--상품 이미지 -->
			<div id="imgdiv">
				<!--첫번째 이미지는 무조건 첨부하게 제어 -->
				<img src="./upload/<%=pb.getImg() %>" id="main">
				<!--첫번째 이미지는 무조건 첨부하게 제어 -->
				<ul class="bxslider">
					<li><img src="./upload/<%=pb.getImg() %>" style="box-sizing : border-box; border : 5px solid #A6A6A6;"></li>
					<%
					// 2~5번째 이미지는 null이 아닐 경우 출력 o   null 경우 출력 x
					String img[] = {pb.getImg2(), pb.getImg3(), pb.getImg4(), pb.getImg5()};
					
					for (int j = 0; j < img.length; j++)
					{
						if(img[j] != null && !img[j].equals(""))
						{
						%>
							<li><img src="./upload/<%=img[j] %>"></li>
						<%
						}
					}
					%>
				</ul>
			</div>
			<!--상품 이미지 -->
			
			<!--인원수, 가격 -->
				<div id="contentdiv1">
				<form name="input_fr" method="post" onsubmit="return false">
					<table>
						<tr>
							<td class="contentdiv1_1">판매가<input type ="hidden" id ="ori_cost" value="<%=pb.getCost() %>"></td>
							<td class="contentdiv1_2" id="avg_cost">
							<%=pb.getCost() %></td><td></td>
							
							<td class="contentdiv1_3"></td>
						</tr>
							<%} %>
						<tr>
							<td class="contentdiv1_1">color</td>
								<td class="contentdiv1_2"><select id="color" onchange="people_Calc2(<%=num%>)" style="width:150px;">
								<option value ="">선택하세요</option>
								<%
				for (int i = 0; i < productList3.size(); i++) {

						pb = (ProductBean) productList3.get(i);
			%>
	
								<option value = "<%=pb.getColor()%>"><%=pb.getColor()%></option>
								<%} %>
							</select></td>
							<td class="contentdiv1_3"></td>
						</tr>
						<tr>
							<td class="contentdiv1_1">size</td>
							<td class="contentdiv1_2">
							<select  id ="size"class="size" onchange="people_Calc3(<%=num%>)" style ="width : 150px;">
								<option  value = "5555" >선택하세요</option>
								
								</select></td>
				
								<td class="contentdiv1_3"><input type="hidden" id="hstock" value=""></td>
								<div id="dstock"></div>
						</tr>
						
						
						<tr>
							<td class="contentdiv1_1">합계</td>
							<td colspan="2">
								<input type="hidden" name="type" value="T">
								<input type="hidden" name="totalcost">
								<p id="p">0원</p>
							</td>
						</tr>
						
						
					</table>
					<br>
				<div style="overflow-x:auto; overflow-y:auto; height: 100px;">
					<table id ="stocktable"></table>
				</div>
										<%
				for (int i = 0; i < productList.size(); i++) {

						pb = (ProductBean) productList.get(i);
			%>
							<input type="hidden" id="ori_num_chk" value="<%=pb.getNum() %>">
								<%} %>
					
				
					<input type="button" class="contentbtn" id="jjim_o" value="♡ 찜" onclick="submit_fun(1, '<%=user_id %>')">
					<input type="button" class="contentbtn" id="jjim_x" value="♥ 찜" style="display:none;" onclick="submit_fun(2, '<%=user_id %>')">
					<input type="button" class="contentbtn2" value="장바구니" onclick="submit_fun(3, '<%=user_id %>')">
					<input type="button" class="contentbtn2" value="구매하기" onclick="submit_fun(4, '<%=user_id %>')">
					
					
					<p id="content_notice">※color를 선택해주셔야 size부분이 나옵니다!!!</p>
					
							<script type="text/javascript">
// 							function up(){
								
// 								var val = $("#avg_cost").html();
// 								var str = $("#stack").val();
								
// 								var str2 = $("#size option:selected").val();
// 								var str3 = parseInt($("#dstock"+str2).val());
// 								alert(str3);
								
// // 								var str2 = $("#hstock").val().split(",");
// // 								var str3 = parseInt($("#size option").index($("#size option:selected")));
// 								var max = 10;
// 								var alertmsg = "";
// 								if(str3 != 0){
// 									str++;
// 									document.input_fr.stack.value = str;
// 								if(str2[str3]<max){
// 									max = str2[str3];
// 									alertmsg = "재고가 부족합니다\n"+str2[str3]+"만큼 선택해주세요.";
									
// 								}else{
// 									alertmsg = "10개 이하로 주문해주세요";
								
// 								}
								
// 								if(str > max){
// 									alert(alertmsg);
// 									$("#stack").val(parseInt($("#stack").val())-1);
// 								}else{
// 									var sum = str * val;
									
// 								    str = String(sum);
// 								    var commasum = str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
// 									$('#p').html(commasum);
								
// 								}
								
								
// 								}else{
// 									alert("size를 선택하여주세요.");
// 								}
// 							}
						function up(){
								
								var val = $("#avg_cost").html();
								var str = $("#stack").val();
								
								var str2 = $("#size option:selected").val();
								var str3 = $("#stock"+num).val();
								
								
// 								var str2 = $("#hstock").val().split(",");
// 								var str3 = parseInt($("#size option").index($("#size option:selected")));
								var max = 10;
								var alertmsg = "";
								if(str3 != 0){
									str++;
									document.input_fr.stack.value = str;
								if(str3<max){
									max = str3;
									alertmsg = "재고가 부족합니다\n"+str3+"만큼 선택해주세요.";
									
								}else{
									alertmsg = "10개 이하로 주문해주세요";
								
								}
								
								if(str > max){
									alert(alertmsg);
									$("#stack").val(parseInt($("#stack").val())-1);
								}else{
									var sum = str * val;
									
								    str = String(sum);
								    var commasum = str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
									$('#p').html(commasum+"원");
								
								}
								
								
								}else{
									alert("size를 선택하여주세요.");
								}
								totalP();
							}
						function up(num){
							var val = $('#o_cost'+num).val();
							var str = parseInt($("#stack2"+num).val());
							
							var str2 = $("#size option:selected").val();
							var str3 = $("#stock"+num).val();
							
//								var str2 = $("#hstock").val().split(",");
//								var str3 = parseInt($("#size option").index($("#size option:selected")));
							var max = 10;
							var alertmsg = "";
							if(str3 != 0){
								str++;
								$("#stack2"+num).val(str);
							if(str3<max){
								max = str3;
								alertmsg = "재고가 부족합니다\n"+str3+"만큼 선택해주세요.";
								
							}else{
								alertmsg = "10개 이하로 주문해주세요";
								
							}
							
							if(str > max){
								alert(alertmsg);
								$("#stack2"+num).val(parseInt($("#stack2"+num).val())-1);
							}else{
								var sum = str * val;
								$('#cost'+num).val(sum);
							    str = String(sum);
							    var commasum = str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
								$('#p'+num).html(commasum+"원");
							
							}
							
							
							}else{
								alert("size를 선택하여주세요.");
							}
							totalP();
						}
						
						
							function down(num){
								
								var val = $('#o_cost'+num).val();
								var str = parseInt($("#stack2"+num).val());
								if(str <= 1){
									if(confirm('해당 상품을 리스트에서 삭제하시겠습니까?'))trdel(num);
								}else{
								str--;
								$("#stack2"+num).val(str);
								var sum = str * val;
								$('#cost'+num).val(sum);
							    str = String(sum);
							    var commasum = str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
								$('#p'+num).html(commasum+"원");
								totalP();
								}
								
							}
							
							function keyup(num){
								 if (window.event.keyCode == 13) {
								var val = $("#avg_cost").html();
								var str = parseInt($("#stack2"+num).val());
								
								var str2 = $("#size option:selected").val();
								var str3 = $("#stock"+num).val();
								
								
//									var str2 = $("#hstock").val().split(",");
//									var str3 = parseInt($("#size option").index($("#size option:selected")));
								var max = 10;
								var alertmsg = "";
								if(str3 > 0){
									$("#stack2"+num).val(str);
									if(str3<max){
										max = str3;
										alertmsg = "재고가 부족합니다\n"+str3+"만큼 선택해주세요.";
									
									}else{
										alertmsg = "10개 이하로 주문해주세요";
									
									}
								
								if(str > max){
									alert(alertmsg);
									
								
								}else{
									var sum = str * val;
									
								    str = String(sum);
								    var commasum = str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
									$('#p'+num).html(commasum+"원");
								
								}
								
								
								}else{
									alert("size를 선택하여주세요.");
								}
							}
								 totalP();
							}
							
							
							
							function people_Calc2(str){			
								$(document).ready(function(){
									var val1 = $("#color option:selected").val();
									var val2 = str
									
									
									
									$("#size").find("option").remove();
									$("#dstock").find("input").remove();
									$('#size').append("<option  value = '5555' >선택하세요</option>");
									$.getJSON('./product/json3.jsp?num='+val2+'&color='+val1,function(data){
										var stocktest = "";
										$.each(data,function(index,qwer){
										//body태그 추가 key:value	
											var cost_cal = qwer.cost - parseInt($("#ori_cost").val());
											var sss = "";
											if(cost_cal > 0){
												sss = "+";
											}
											
										if(qwer.stock <= 0){
										$('#size').append("<option value=" + qwer.num + " disabled>" + qwer.size+ "------재고수량:▶[품절]</option>");
										}else{
										$('#size').append("<option value=" + qwer.num + ">" + qwer.size + "------재고수량:▶"+qwer.stock+"개&nbsp;["+ sss+cost_cal +"원] </option>");
										$('#dstock').append("<input type = 'hidden' value ='"+qwer.stock+"'id='dstock"+qwer.num+"'>");
										}	
										stocktest+=","+ qwer.stock;
									
										});
										$("#hstock").val(stocktest);
									});
								});
							}
							function people_Calc3(str){
								$(document).ready(function(){
									var val1 = $("#color option:selected").val();
									var val2 = str
									var val3 = $("#size option:selected").val();
									

									if(val3 != 5555){
									
									
									$.getJSON('./product/json4.jsp?num='+val3+'&color='+val1,function(data){
										var stocktest = "";
										$.each(data,function(index,qqqq){
										//body태그 추가 key:value
											var tnum = qqqq.num;
											var cost_cal = qqqq.cost - parseInt($("#ori_cost").val());
											var sss = "";
											if(cost_cal > 0){
												sss = "+";
											}
											
											var check = 1;
											for(var i = 0; i<$('#stocktable').find('tr').length;i++){
												if($('#stocktable tr').eq(i).attr('id')=="stocktr"+tnum)check=0;
											}
											if(check == 1){
											$('#stocktable').prepend(
													"<tr id='stocktr"+tnum+"'><td class='contentdiv1_2'>"
													+qqqq.color+"-"+qqqq.size+"&nbsp&nbsp&nbsp<br>"+sss+cost_cal+"원</td><td>"+
													"<input type='button' value='▲' onclick='up("+tnum+")'>"+
													"<input type='text' id = 'stack2"+tnum+"' size='1'"+ 
													" name = 'count' value='1' onkeydown='keyup("+tnum+")'"+
													"class='trcount' readonly>"+
													"<input type='button' value='▼' onclick='down("+tnum+")'>"+
													"</td><td><input type='hidden' id='stock"+tnum+"'"+
																"name='stock' value='"+qqqq.stock+"'>"+
																"<input type='hidden' id='cost"+tnum+"'"+
																	"name='cost' value='"+qqqq.cost+"'>"+
																"<input type='hidden' id='o_cost"+tnum+"'"+
																	"name='o_cost' value='"+qqqq.cost+"'>"+
														  "<input type='hidden'"+"name='tnum' value='"+tnum+"'>"+
														  "<input type='hidden'"+"name='color' value='"+qqqq.color+"'>"+
														  "<input type='hidden'"+"name='size' value='"+qqqq.size+"'>"+
													"<p class='tcost' id='p"+tnum+"'></p></td>"+
													"<td><img src='./img/delete.png' onclick='trdel("+tnum+")'></td></tr>");
												var cost = numberWithCommas(qqqq.cost);
												$('#p'+tnum).html(cost+"원");
												totalP();
											}else{
												alert('이미 구매리스트에 추가 되어 있는 상품 입니다');
											}
										});
									
									});
									}
								});
							}
							function trdel(tnum){
								$('#stocktr'+tnum).remove();
								totalP();
							}
							function numberWithCommas(x) {
							    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
							}
							function totalP(){
								var sum = 0;
								for(var i = 0; i<$('#stocktable').find('[name=cost]').length; i++){
									
									var ints = $('#stocktable [name=cost]').eq(i).val();
									sum += parseInt(ints);
								}
								$('input:hidden[name=totalcost]').val(sum);
								$('#p').html(numberWithCommas(sum)+"원");
							}
							
						</script>
				</form>
			</div>
			<!--인원수, 가격 -->
			<div class="clear"></div>
		</div>
		<div class="clear"></div>
		

		
		<!--상품 정보, 내용이 들어가는 영역 -->
	
		<div id="middle1">
		<%
				for (int i = 0; i < productList.size(); i++) {

						pb = (ProductBean) productList.get(i);
			%>
			<div id="contentdiv2">
				<div id="contentdiv2_1"><center><%=pb.getContent() %></center>
				</div>
				</div>
			<%} %>
		</div>
		<!--상품 정보,내용이 들어가는 영역 -->

		

		<!--상품 문의 -->
			<div id="QnA">
			<div id="pack_btn">
			<br>
			<h3>상품 문의</h3>
			</div>
			<hr>
			<div id="middle3">
			<table border="1" id="replyTable">
				<tr>
<!-- 					<td>번호</td> -->
<!-- 					<td>작성자</td> -->
<!-- 					<td>내용</td> -->
				</tr>
				<%
				
				if(commentcount == 0)
				{
				%>
					<tr>
						<td style="width:800px; text-align: center; height:50px;">문의 내역이 없습니다</td>
					</tr>
				<%
				}
				
					if (commentcount != 0) {
						for (int i = 0; i < commentList.size(); i++) 
						{
							comb  = (CommentBean)commentList.get(i);
				%>

				<tr id="relist<%=comb.getNum()%>">
<%-- 					<td><%=rb.getNum()%></td> --%>
					<td><%=comb.getId()%></td>
					<%
					if ((comb.getId().equals(user_id) && comb.getH_or_s() == 1) || comb.getH_or_s() == 0){
					%>
					<td id="replyContent">
						<span class="reply_align">
						
						<%
							// 답글 들여쓰기 모양
							int wid = 0;
							if (comb.getRe_lev() > 0) {
								wid = 10 * comb.getRe_lev();
						%> 
						<%--<img src="level.gif" id="reimg" width=<%=wid%>> <img src="re.gif"> --%>
							<img src="./img/reply.png" height="12px">
							<span>[답변]</span>
						<%
							}
							else
							{
						
						%>
						<span>[문의]</span>
						<%
						}
						%> 
						
						<%=comb.getContent()%><span style="font-size: 0.8em; margin-left:5px;">(<%=comb.getDate() %>)</span>
						
						<%
						if(comb.getH_or_s() == 1)
						{
						%>
						<img src="./img/lock.png" width="10px" height="10px">
						
						<%
						}
						%>
						</span>
						<span style="float: right;">
						<%
						if(user_id.equals("admin"))
						{
						%>
						
						<input type="button" value="답글" id="rereply" onclick="rewrite(<%=comb.getNum()%>)">
						<%
						}
						if(comb.getId().equals(user_id))
						{
						%>
						<input type="button" value="수정" id="re_update" onclick="reupdate(<%=comb.getNum() %>)">
						<%
						}
						if(comb.getId().equals(user_id) || user_id.equals("admin"))
						{
						%>
						<input type="button" value="삭제" id="re_delete" onclick="ReplyDel(<%=comb.getNum() %>, '<%=user_id%>');">
						<%
						}
						%>
						</span>
					</td>
					<%
					}
					else if (comb.getH_or_s() == 1 && !comb.getId().equals(user_id)){
					%>
					<td style="height:50px;">
						<span class="reply_align">
						비밀글입니다<img src="./img/lock.png" width="10px" height="10px">
<%-- 						<span>(<%=rb.getDate() %>)</span> --%>
						(<%=comb.getDate() %>)
						</span>
						<span style="float: right;">
						<%
						if(user_id.equals("admin"))
						{
						%>
						<input type="button" value="답글" id="rereply" onclick="rewrite(<%=comb.getNum()%>)">
						<%
						}
						if(comb.getId().equals(user_id))
						{
						%>
						<input type="button" value="수정" id="re_update" onclick="reupdate(<%=comb.getNum() %>)">
						<%
						}
						if(comb.getId().equals(user_id) || user_id.equals("admin"))
						{
						%>
						<input type="button" value="삭제" id="re_delete" onclick="ReplyDel(<%=comb.getNum() %>, '<%=user_id%>');">
						<%
						}
						%>
						</span>
					</td>
					<%
					}
					%>
					
					
				</tr>
				
				<tr id="conup<%=comb.getNum()%>" style="display: none;">
					<td>
						<%=user_id %>
					</td>
					
					<td>
						<span class="reply_align">
						<textarea style="width: 550px;" cols="60" rows="2" id="contentup<%=comb.getNum() %>" name="contentup"><%=comb.getContent() %></textarea>
						</span>
						<span style="float: right;">
						<input type="button" value="수정" onclick="reUpdateAction(<%=comb.getNum() %>)">
						<input type="button" value="취소" onclick="reupdate(<%=comb.getNum() %>)"><br>
						<input type="checkbox" class="up_secretChk<%=comb.getNum() %>" name="secretChk" value="1" <%if(comb.getH_or_s() == 1){%>checked<%} %>>비밀글
						</span>
					</td>
					</td>
				</tr>
				<tr id="con<%=comb.getNum()%>" style="display: none;">
					<td>
						<input type="hidden" id="num" name="num" value="<%=num%>">
						<input type="hidden" id="repageNum" name="pageNum" value="<%=pageNum%>">
						<input type="hidden" id="replynum" name="replynum" value="<%=comb.getNum()%>">
						<input type="hidden" id="re_ref<%=comb.getNum() %>" name="re_ref" value="<%=comb.getRe_ref()%>">
						<input type="hidden" id="re_lev" name="re_lev" value="<%=comb.getRe_lev()%>">
						<input type="hidden" id="re_seq" name="re_seq" value="<%=comb.getRe_seq()%>">
						<p><%=user_id %></p>
						<input type="hidden" id="reid" name="id" class="box" value="<%=user_id %>">
					</td>
					<td>
						<span class="reply_align">
						<textarea style="width: 550px;" cols="60" rows="2" id="recontent<%=comb.getNum() %>" name="recontent"></textarea>
						</span>
						<span style="float: right;">
						<input type="button" value="답글등록" onclick="Re_Reply_Write(<%=comb.getNum() %>)">
						<input type="button" value="취소" onclick="rewrite(<%=comb.getNum() %>)"><br>
						<input type="checkbox" class="re_secretChk" name="secretChk" value="1">비밀글
						</span>
					</td>
				</tr>
			
				<%
					}
						// 최근글위로 re_ref 그룹별 내림차순 re_se q 오름차순
						// 			re_ref desc   re_seq asc
						// 글잘라오기 limit 시작행-1, 개수
					}
				%>
				
			</table>
			<br>
			
			
			<center>
			<%
	if(commentcount!=0){
		if(endPage > pageCount){
			endPage = pageCount;
		}
		//Prev
		if(startPage>pageBlock){
			%><a href="imgcontent2.jsp?pageNum=<%=startPage-pageBlock%>&num=<%=num%>">[이전]</a> <% 
		}
		//1..10

		for(int i=startPage; i<=endPage; i++){
			%><a href="imgcontent2.jsp?pageNum=<%=i%>&num=<%=num%>">[<%=i%>]</a><% 
		}
		// 다음
		if(endPage < pageCount){
			%><a href="imgcontent2.jsp?pageNum=<%=startPage+pageBlock%>&num=<%=num%>">[다음]</a><% 
		}
	}
	
	
	%>
			</center>

			<br>
			
			<table id="replyWrite">
				<tr>
					<%
						if (user_id.equals(""))
						{
					%>
					<td>
						<textarea type="text" id="content" name="content" class="box" style="width:790px; height:50px;" placeholder="로그인이 필요한 서비스입니다" readonly onclick="loginChk()"></textarea>
					</td>
					<tr>
							<td>
								<div style="text-align:right; margin-top:15px; width:790px;">
									<input type="checkbox" class="secretChk" name="secretChk" value="1" onclick="loginChk()">비밀글
									<input type="button" value="문의글쓰기" onclick="loginChk()">
								</div>
							</td>
						</tr>
					<td>
					<%
						}
						else
						{
					%><!-- 글넣는부분 -->
						<td>
							<textarea type="text" id="content" name="content" class="box" style="width:790px; height:50px;"></textarea>
							<input type="hidden" id="id" name="id" class="box" value="<%=user_id %>">
							<input type="hidden" name="pageNum" value="<%=pageNum%>">
							<input type="hidden" id="num" name="num" value="<%=num %>">
						</td>
						<tr>
							<td>
								<div style="text-align:right; margin-top:15px; width:790px;">
									<input type="checkbox" class="secretChk" name="secretChk" value="1">비밀글
									<input type="button" value="문의글쓰기" onclick="ReplyWrite(<%=num %>)">
								</div>
							</td>
						</tr>
					<%
						}
					%>
				</tr>
			</table>
		</div>
			</div>
	<!--상품 문의 -->
	</div>
	<!-- 오른쪽 메뉴 -->
	<jsp:include page="../inc/rightMenu.jsp"></jsp:include>
	<!-- 오른쪽 메뉴 -->
	<jsp:include page="../inc/footer.jsp"></jsp:include>
</body>
</html>