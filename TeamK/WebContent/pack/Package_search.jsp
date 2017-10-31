<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="net.pack.db.PackDAO" %>
    <%@ page import="net.pack.db.PackBean" %>
    <%@ page import="java.util.List" %>
    <%@ page import="net.pack.db.CategoryBean" %>
    <%@ page import="java.text.DecimalFormat" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="./css/inc.css" rel="stylesheet" type="text/css">
<link href="./css/subpage.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="./js/jssor.slider-23.1.6.min.js" type="text/javascript"></script>
<script src="./js/jquery-3.2.0.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<script>
	jQuery(document).ready(function($){
		
		// 달력 관련 소스
		$("#date_from").datepicker({
			dateFormat: 'yy-mm-dd',    // 날짜 포맷 형식
			minDate : 0,			   // 최소 날짜 설정      0이면 오늘부터 선택 가능
			numberOfMonths: 2,		   // 보여줄 달의 갯수
	        dayNamesMin: ['월', '화', '수', '목', '금', '토', '일'],  // 일(Day) 표기 형식
	        monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],   // 월(Month) 표기 형식
	        //showOn: "both",		// 버튼을 표시      both : input과 buttom 둘다 클릭 시 달력 표시           bottom  :  buttom 클릭 했을 때만 달력 표시
	        //buttonImage: "./img/calendar.png",   // 버튼에 사용될 이미지
	        //buttonImageOnly: true,					// 이미지만 표시한다    버튼모양 x
	        onClose: function(selectedDate){		// 닫힐 때 함수 호출
	        	if (selectedDate == "")  // 시작날 선택 안했을때
	       		{
	        		$("#to").datepicker("option", "minDate", 0);   		// #date_to의 최소 날짜를 오늘 날짜로 설정
	       		}
	        	else					// 시작날 선택 했을때
	        	{
	        		$("#to").datepicker("option", "minDate", selectedDate);    // #date_to의 최소 날짜를 #date_from에서 선택된 날짜로 설정
	        	}
	        }
		});
	});
	
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

	// 이미지 슬라이드 소스
	jssor_1_slider_init = function() {

		var jssor_1_SlideoTransitions = [
			[{b:900,d:2000,x:-379,e:{x:7}}],
			[{b:900,d:2000,x:-379,e:{x:7}}],
			[{b:-1,d:1,o:-1,sX:2,sY:2},{b:0,d:900,x:-171,y:-341,o:1,sX:-2,sY:-2,e:{x:3,y:3,sX:3,sY:3}},{b:900,d:1600,x:-283,o:-1,e:{x:16}}]
		];
		
		var jssor_1_options = {
			$AutoPlay: 1,
			$SlideDuration: 800,
			$SlideEasing: $Jease$.$OutQuint,
			$CaptionSliderOptions: {
			$Class: $JssorCaptionSlideo$,
			$Transitions: jssor_1_SlideoTransitions
			},
			$ArrowNavigatorOptions: {
			$Class: $JssorArrowNavigator$
			},
			$BulletNavigatorOptions: {
			$Class: $JssorBulletNavigator$
			}
		};
		var jssor_1_slider = new $JssorSlider$("jssor_1", jssor_1_options);
	};
	
	// 추천 패키지상품 배경이미지 지정
	function bg(num)
	{
		var imgname = $("#pack_img" + num).val();
		$("#pack" + num).css({
			"background-image" : "url(./upload/" + imgname + ")",
			"overflow" : "hidden"
		});
	}
	
	
</script>

<style type="text/css">

img.ui-datepicker-trigger
{
	cursor : pointer;
	margin-left : 5px;
}

#package_list tr:HOVER
{
/* 	cursor: pointer; */
}

.clear 
{
	clear: both;
}
</style>
</head>
<body>
<%

	List list = (List)request.getAttribute("list");
	List CategoryList = (List)request.getAttribute("CategoryList");
	List PackReCommentList = (List)request.getAttribute("PackReCommentList");
	
	int count = ((Integer)request.getAttribute("count")).intValue();
	String pageNum = (String)request.getAttribute("pageNum");
	int pageCount = ((Integer)request.getAttribute("pageCount")).intValue();
	int pageBlock = ((Integer)request.getAttribute("pageBlock")).intValue();
	int startPage = ((Integer)request.getAttribute("startPage")).intValue();
	int endPage = ((Integer)request.getAttribute("endPage")).intValue();
	int currentPage = ((Integer)request.getAttribute("currentPage")).intValue();
	int pagesize = ((Integer)request.getAttribute("pagesize")).intValue();
	
	String search = (String)request.getAttribute("search");
	String startDate = (String)request.getAttribute("startDate");
	String endDate = (String)request.getAttribute("endDate");

%>

<!--왼쪽 메뉴 -->
<div>
	<jsp:include page="../inc/leftMenu.jsp"></jsp:include>
</div>
<!--왼쪽 메뉴 -->
<div id="wrap">
	<div id="article_head">
		<div id="article_title"><img src="./img/travel2.png" width="30px" style="margin-right: 8px; vertical-align: bottom;">패키지</div>
	<div class="empty"></div>
	</div>
	<!--여행지 검색창 -->
	<div id="package_feat">
		<div id="package_slide">
			<div id="jssor_1" style="position:relative;margin:0 auto;top:0px;left:0px;width:600px;height:300px;overflow:hidden;visibility:hidden;">
			<!-- Loading Screen -->
				<div data-u="loading" style="position:absolute;top:0px;left:0px;background:url('./img/loading.gif') no-repeat 50% 50%;background-color:rgba(0, 0, 0, 0.7);"></div>
				<div data-u="slides" style="cursor:default;position:relative;top:0px;left:0px;width:600px;height:300px;overflow:hidden;">
				<%
					PackBean pb_slide;
					for (int i = 0; i < PackReCommentList.size(); i++)
					{
						int j = i + 1;
						pb_slide =(PackBean)PackReCommentList.get(i);
						DecimalFormat df = new DecimalFormat("#,###");
					    String cost = df.format(pb_slide.getCost());
				%>	
					<div>
						<input type="hidden" id="pack_img<%=j %>" value=<%=pb_slide.getFile1() %>>
						<a href="./PackContent.po?num=<%=pb_slide.getNum() %>" id="pack<%=j %>">
						<span id="pktt"><%=pb_slide.getSubject() %></span><br>
						<span id="pksc"><%=pb_slide.getIntro() %></span><br>
						<span id="pkpr"><%=cost %>원</span>
						<span id="pkdt"><%=pb_slide.getDate() %></span></a>
						<script> bg(<%=j %>);</script>
					</div>
				<%
					}
				%>
				<a data-u="any" href="https://www.jssor.com" style="display:none">js slider</a>
				</div>
				<!-- Bullet Navigator -->
				<div data-u="navigator" class="jssorb05" style="bottom:16px;right:16px;" data-autocenter="1">
				<!-- bullet navigator item prototype -->
				<div data-u="prototype" style="width:16px;height:16px;"></div>
				</div>
				<!-- Arrow Navigator -->
				<span data-u="arrowleft" class="jssora12l" style="top:0px;left:0px;width:30px;height:46px;" data-autocenter="2"></span>
				<span data-u="arrowright" class="jssora12r" style="top:0px;right:0px;width:30px;height:46px;" data-autocenter="2"></span>
			</div>
			<script type="text/javascript">jssor_1_slider_init();</script>
		</div>
		<div id="package_search">
			<br><p>내게 맞는 패키지 검색하기</p><br>
			<form action="./PackSearchAction.po" name="fr" method="get" id="scheduler" onsubmit="return input_chk();">
				<label for="date_from">출발</label><input type="text" id="date_from" class="input_style" name="startDate" value="<%=startDate %>" readonly><br><br>
				<label for="city_search">지역</label>
				<select id="area" name="area">
					<option value="">선택하세요</option>
					<%
						CategoryBean cb;
						for (int i = 0; i < CategoryList.size(); i++)
						{
							cb =(CategoryBean)CategoryList.get(i);
					%>	
						<option value="<%=cb.getCar_name() %>" <%if(search.equals(cb.getCar_name())){ %> selected <%} %>><%=cb.getCar_name() %></option>
					<%
						}
					%>
				</select>
				<input type="submit" value="검색" id="search_btn" class="input_style">
			</form>
		</div>
	</div>
		<div id="clear"></div>
		
		<%
			if(count == 0)
			{
		%>
				<br><h2>검색조건에 해당하는 상품이 총 <%=count %>개 있습니다</h2><br>
				<hr>
				<div id="package_list">
					<table style="width:1000px;">
						<tr>
							<th colspan="2">상품</th>
							<th>가격</th>
							<th>출발일자</th>
						</tr>
						<tr>
							<td colspan="5"><br><p style="text-align: center;">해당 검색 조건에 해당하는 상품이 없습니다</p><br></td>
						</tr>
					</table>
				</div>
		<%
			}
			if (count != 0)
			{
		%>
		
		
		<p>검색조건에 해당하는 상품이 총 <%=count %>개 있습니다</p>
		<hr>	

		<div id="package_list">
			<table>
				<tr>
					<td colspan="2">상품</td>
					<td>가격</td>
					<td>출발일자</td>
				</tr>
			<%
				PackBean pb;
				if (count!=0)
				{
					for (int i = 0; i <list.size(); i++)
					{
						pb =(PackBean)list.get(i);
						DecimalFormat Commas = new DecimalFormat("#,###");
						String cost = (String)Commas.format(pb.getCost());
			%>
				<tr>
					<td rowspan="2" id="thumb">
						<a href="./PackContent.po?num=<%=pb.getNum() %>">
						<img class="img_size" alt="" src="./upload/<%=pb.getFile1() %>">
						</a>
					</td>
					<td id="subject">
						<a href="./PackContent.po?num=<%=pb.getNum() %>">
						<%=pb.getSubject() %>
						</a>
					</td>
					
					<td rowspan="2"  id="price">
						<span><%=cost %>원~</span>
					</td>
					<td rowspan="2"  id="date">
						<span><%=pb.getDate() %>~</span>
					</td>
					
				</tr>
				<tr>
					<td id="context">
						<span><%=pb.getIntro() %></span>
					</td>
				</tr>
				
			<%
					}
				}
			%>
			</table>
		</div>
		<div id="pages">
				<%
					if (count != 0) {
						// 페이지 갯수 구하기
						pageCount = count / pagesize + (count % pagesize == 0 ? 0 : 1);
						pageBlock = 10;
						// 시작 페이지 구하기
						startPage = ((currentPage - 1) / pageBlock) * pageBlock + 1;
						// 끝페이지 구하기
						endPage = startPage + pageBlock - 1;
						if (endPage > pageCount) {
							endPage = pageCount;
						}
						//이전
						if (startPage > pageBlock) {
				%>
				<a href="./PackSearchAction.po?pageNum=<%=startPage - pageBlock%>" id="i">이전</a>
				<%
					}

						//페이지
						for (int i = startPage; i <= endPage; i++) {
				%>
				<a href="./PackSearchAction.po?pageNum=<%=i %>&area=<%=search %>" id="i"><%=i%></a>
				<%
					}

						//다음
						if (endPage < pageCount) {
				%>
				<a href="./PackSearchAction.po?pageNum=<%=startPage + pageBlock%>" id="i">다음</a>
				<%
					}
				}
				%>
		</div>
		<%
			}
		%>
	</div>

<!--오른쪽 메뉴 -->
	<jsp:include page="../inc/rightMenu.jsp"></jsp:include>
<!--오른쪽 메뉴 -->
<!--아래 메뉴-->
	<jsp:include page="../inc/footer.jsp"></jsp:include>
<!--아래 메뉴-->
</body>
</html>