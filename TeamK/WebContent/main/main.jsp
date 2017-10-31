<%@page import="net.board.db.BoardBean"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="net.board.db.BoardDAO"%>
<%@ page import="net.pack.db.CategoryBean" %>
<%@ page import="net.pack.db.PackBean" %>
<%@ page import="net.member.db.ProductDAO" %>
<%@ page import="net.member.db.ProductBean" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="../css/inc.css" rel="stylesheet" type="text/css">
<link href="./css/subpage.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="./js/jquery-3.2.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="./js/jssor.slider-24.1.5.min.js" type="text/javascript"></script>
<script>
  jQuery(document).ready(function($){
	//Scheduler
	$("#from").datepicker({
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

    //Package
    $("#pack1")
    .mouseenter(function() {
      $(this).css('width', '50%');
      $("#pack2").css('width', '25%');
      $("#pack3").css('width', '25%');
    })
    .mouseleave(function() {
      $(this).css('width', '33.3%');
      $("#pack2").css('width', '33.3%');
      $("#pack3").css('width', '33.3%');
    });
    $("#pack2")
    .mouseenter(function() {
      $(this).css('width', '50%');
      $("#pack1").css('width', '25%');
      $("#pack3").css('width', '25%');
    })
    .mouseleave(function() {
      $(this).css('width', '33.3%');
      $("#pack1").css('width', '33.3%');
      $("#pack3").css('width', '33.3%');
    });
    $("#pack3")
    .mouseenter(function() {
      $(this).css('width', '50%');
      $("#pack1").css('width', '25%');
      $("#pack2").css('width', '25%');
    })
    .mouseleave(function() {
      $(this).css('width', '33.3%');
      $("#pack1").css('width', '33.3%');
      $("#pack2").css('width', '33.3%');
    });
  });
  
	//패키지 검색 시 지역 선택
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
	
	
	function bg(num)
	{
		var imgname = $("#pack_img" + num).val();
		$("#pack" + num).css({
			"background-image" : "url(./upload/" + imgname + ")",
// 			"width" : "333px",
			"overflow" : "hidden"
		});
	}

	//Goods Show의 슬라이더
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
<%
	List CategoryList = (List)request.getAttribute("CategoryList");
	List PackList = (List)request.getAttribute("PackList");
	List ProductImgList = (List)request.getAttribute("ProductImgList");
%>
</head>
<body>
	<!--왼쪽 메뉴 -->
	<div>
		<jsp:include page="../inc/leftMenu.jsp"></jsp:include>
	</div>
	<!--왼쪽 메뉴 -->
	<div id="wrap">
		<div id="datepicker">
			<div id="notice">
			<h1><a href="./BoardList3.bo">공지사항<span>＋</span></a></h1>
			<table>
				<%
				BoardDAO bdao=new BoardDAO();
				int count=bdao.getBoardCount();
				int count2=bdao.getBoardCount2();
				int count3=bdao.getBoardCount3();
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy.MM.dd");
				if(count3==0){%><tr><td colspan="3">글이 없습니다.</td></tr><%}else{
					List<BoardBean> boardList3=bdao.getBoardList3(1, 5);
					for(int i=0;i<boardList3.size();i++){
						BoardBean bb=boardList3.get(i);
						%>
						<tr><td id="num"><%=bb.getRe_ref()%></td><td class="contxt">
						<a href="./BoardContent3.bo?num=<%=bb.getNum()%>&pageNum=1"><%=bb.getSubject()%></a></td>
    					<td id="date"><%=sdf.format(bb.getDate())%></td></tr>		
						<%
					}
				}
				%>
			</table>
			</div>
			<div id="scheduler">
				<p>[ 내게 맞는 패키지 검색하기 ]</p>
				<form action="./PackSearchAction.po" method="post" name="fr" id="scheduler" onsubmit="return input_chk();">					
					<label for="from">날짜</label>
					<input type="text" id="from" name="startDate" readonly><br>
<!-- 					<label for="to">~</label> -->
<!-- 					<input type="text" id="to" name="endDate"><br> -->
					<label for="area">지역</label>
					<select id="area" name="area">
						<option value="">선택하세요</option>
						<%
							CategoryBean cb;
							for (int i = 0; i < CategoryList.size(); i++)
							{
								cb =(CategoryBean)CategoryList.get(i);
						%>	
							<option value="<%=cb.getCar_name() %>"><%=cb.getCar_name() %></option>
						<%
							}
						%>
					</select>
					<input type="submit" value="검색">
				</form>
			</div>
		</div>
		<div id="clear"></div>
		<div id="package_show">
			<%
				PackBean pb;
				for (int i = 0; i < PackList.size(); i++)
				{
					int j = i + 1;
					int z = PackList.size();
					pb =(PackBean)PackList.get(i);
					DecimalFormat df = new DecimalFormat("#,###");
				    String cost = df.format(pb.getCost());
					
			%>
				<input type="hidden" id="pack_img<%=j %>" value=<%=pb.getFile1() %>>
				<a href="./PackContent.po?num=<%=pb.getNum() %>" id="pack<%=j %>">
				<span id="pktt"><%=pb.getSubject() %></span><br>
				<span id="pksc"><%=pb.getIntro() %></span><br>
				<span id="pkpr"><%=cost %>원</span>
				<span id="pkdt"><%=pb.getDate() %></span>
				</a>
				<script> bg(<%=j %>);</script>
			<%
				}
			%>	
		</div>
		<div id="clear"></div>
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
		<div id="review_show">
		<div id="gds_rv">
		<h1><a href="./BoardList.bo">리뷰<span>＋</span></a></h1>
			<table>
				<%
				if(count==0){%><tr><td colspan="3">글이 없습니다.</td></tr><%}else{
					List<BoardBean> boardList=bdao.getBoardList(1, 5);
					for(int i=0;i<boardList.size();i++){
						BoardBean bb=boardList.get(i);
						%>
				<tr><td id="num"><%=bb.getRe_ref()%></td><td class="contxt">
				<a href="./BoardContent.bo?num=<%=bb.getNum()%>&pageNum=1"><%=bb.getSubject()%>
				<%if(bdao.getBoardReplyCount(bb.getNum())!=0){%>[<%=bdao.getBoardReplyCount(bb.getNum())%>]<%}%></a></td>
				    <td id="date"><%=sdf.format(bb.getDate())%></td></tr>		
						<%
					}
				}
				%>
			</table>
			</div>
			<div id="trv_rv">
			<h1><a href="./BoardList2.bo">Q&amp;A<span>＋</span></a></h1>
			<table>
				<%
				if(count2==0){%><tr><td colspan="3">글이 없습니다.</td></tr><%}else{
					List<BoardBean> boardList2=bdao.getBoardList2(1, 5);
					for(int i=0;i<boardList2.size();i++){
						BoardBean bb=boardList2.get(i);
						%>
				<tr><td id="num"><%=bb.getRe_ref()%></td><td class="contxt">
				<a href="./BoardContent2.bo?num=<%=bb.getNum()%>&pageNum=1"><%=bb.getSubject()%>
				<%if(bdao.getBoardReplyCount(bb.getNum())!=0){%>[<%=bdao.getBoardReplyCount(bb.getNum())%>]<%}%></a></td>
				    <td id="date"><%=sdf.format(bb.getDate())%></td></tr>		
						<%
					}
				}
				%>
			</table>
			</div>
		</div>
		<div id="clear"></div>
	</div>
	<!--오른쪽 메뉴 -->
	<jsp:include page="../inc/rightMenu.jsp"></jsp:include>
	<!--오른쪽 메뉴 -->
	<jsp:include page="../inc/footer.jsp"></jsp:include>
</body>
</html>