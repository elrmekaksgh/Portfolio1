<%@page import="com.sun.xml.internal.ws.api.config.management.Reconfigurable"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="net.pack.db.PackDAO"%>
<%@ page import="net.pack.db.PackBean"%>
<%@ page import="java.util.List"%>
<%@ page import="net.reply.db.ReplyDAO"%>
<%@ page import="net.reply.db.ReplyBean"%>
<%@ page import="net.pack.db.CategoryBean" %>
<%@ page import="net.member.db.ProductBean" %>
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
<!-- <script src="https://code.jquery.com/jquery-1.12.4.js"></script> -->
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
</head>
<%
	PackBean PB = (PackBean) session.getAttribute("PackBean");
	ReplyBean RB = (ReplyBean) session.getAttribute("rb");
	//세션으로 로그인한 아이디 값 받아오기
	String user_id = (String) session.getAttribute("id");
	
	if (user_id == null)
		user_id = "";
	if (PB.getSubject() == null)
		response.sendRedirect("./PackList.po");
	
	List replylist = (List)request.getAttribute("replylist");
	List CategoryList = (List)request.getAttribute("CategoryList");
	List date_list = (List)request.getAttribute("date_list");
	List RecommendProduct = (List)request.getAttribute("RecommendProduct");
	List PackReCommentList = (List)request.getAttribute("PackReCommentList");
	
	int count = ((Integer)request.getAttribute("count")).intValue();
	String repageNum = (String)request.getAttribute("repageNum");
	int pageCount = ((Integer)request.getAttribute("pageCount")).intValue();
	int pageBlock = ((Integer)request.getAttribute("pageBlock")).intValue();
	int startPage = ((Integer)request.getAttribute("startPage")).intValue();
	int endPage = ((Integer)request.getAttribute("endPage")).intValue();
	int currentPage = ((Integer)request.getAttribute("currentPage")).intValue();
	int pagesize = ((Integer)request.getAttribute("pagesize")).intValue();
	int pNum = Integer.parseInt(repageNum);
%>
<body>
<script>

	jQuery(document).ready(function($){
		
		// ▼ 페이지 로딩 될 때 첫번쨰 선택된 날짜 값으로 초기값 설정 부분
		var adult_max;
		var stockArr = new Array();   // 각각 라디오 버튼 받을 배열
		var stock_t = 0;
		var radio_len = $('input:radio[name=chk]').length;  // 라디오 버튼 갯수 구하기
		
		// 각 날짜별 수량 가져오기
		for (var i = 0; i < radio_len; i++)
		{
			stock_t = $("#stock" + i).html(); // 각 날짜 마다 수량 값을 가져온다
			if (stock_t == null) 			  // 0일 경우 null로 인식해서 0으로 변환
				stock_t = 0;
			stockArr[i] = stock_t;
		}
		
		// 날짜별로 수량이 0인 품목은 비활성화 및 클릭 이벤트 해제
		for (var i = 0; i < radio_len; i++)
		{
			if (stockArr[i] == 0)	// 수량 0		
			{
				$("#select_date" + i).css("color", "#BDBDBD");
				$("#select_rbtn" + i).attr("disabled", true);  // 라디오 버튼 비활성화
				$("#select_date" + i).attr("onclick", "");  // 클릭 이벤트 없앰
			}
		}
		
		// 첫 로딩 시 재고가 품절이 아닌 품목이 선택되어 있게 
		for (var i = 0; i < radio_len; i++)
		{
			if (stockArr[i] != 0)		
			{
				$("#select_rbtn" + i).attr("checked", true);  // 품절이 아닌 품목 라디오버튼 체크 되게
				$("#select_date" + i).css("background-color", "#D5D5D5");  // 품절이 아닌 품목 배경색을 #D5D5D5
				$("#stock").val($("#stock" + i).html());		// 품절 아닌 품목의 수량을 id="stock"에 값을 넣어준다
				adult_max = $("#stock" + i).html()			// 품절이 아닌 품목의 수량을 어른 수 맥스 값으로 설정
				break;
			}
		}
		
		// 최대 10명까지 선택 가능하게 제어
		if (adult_max > 10)
			adult_max = 10;
		
		// 남은 갯수에 따라 어른 최대값 설정 
		for (var i = 1; i <= adult_max; i++)  
		{
			$('#adult').append("<option value=" + i + ">" + i + "</option");
		}
		people_Calc(1);  // 어른 값 변경에 따른 아이값 변경
		
		var num = $("input:radio[name=chk]:checked").val();  // 체크된 품목의 넘버값 가져온다
		var cost = $("#cost" + num).html();  // 체크된 품목의 값 가져온다
		
	    var str = String(cost);
	    uncomma_cost = str.replace(/[^\d]+/g, ''); // 금액 자릿수 ,를 없앤다  cost는 어른 금액
	    uncomma_cost2 = uncomma_cost / 2;  // cost2는 아이 금액
		
		str = String(uncomma_cost);
		var comma_cost =  str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');  // 금액 자릿수 ,를 붙인다
		str1 = String(uncomma_cost2);
		var comma_cost2 =  str1.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');  // 금액 자릿수 ,를 붙인다
		
		$("#cost_adult").html(comma_cost);  // 어른 값 설정
		$("#cost_child").html(comma_cost2);  // 아이 값 설정
		$("#p").html(comma_cost);			// 합계 = 어른 1명 선택값 
		// ▲ 페이지 로딩 될 때 첫번쨰 선택된 날짜 값으로 초기값 설정 부분
		
		
		
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
	
	
	
	// 주변 명소, 주변 맛집 클릭 시  검색할 값을 구글맵으로 보낸다
	// 현재 페이지의 City, Sarea 값을 가져온다
	var value="<%=PB.getCity()%> <%=PB.getSarea()%>";
	jQuery(document).ready(function($){
		$('#middle2 input').click(function(){  // 주변명소, 주변맛집  버튼 클릭 시 이벤트 발생
	
	   		var btn = $(this).attr("value");   // 클릭된 버튼의 value 값을 가져온다
			
	   		if (btn == "관광 명소")
			{
				value = "<%=PB.getCity()%> " + "<%=PB.getSarea()%> " + btn; 
<%-- 				value = "<%=PB.getSarea()%> " + btn;  --%>
				// ex) value = 부산 해운대 주변 명소
			}
			else if (btn == "맛집")
			{
				value = "<%=PB.getCity()%> " + "<%=PB.getSarea()%> 주변 " + btn;
<%-- 				value = "<%=PB.getSarea()%> " + btn; --%>
				// ex) value = 부산 해운대 주변 맛집
			}
	   		
			// value 값을 구글맵 검색 input에 넘겨준다
	    	$("#pac-input").attr("value", value);
	    	
	    	// 구글맵 검색 input에 포커스 및 엔터 입력 되게 처리 (input 클릭 시 엔터 입력되서 자동 검색)
	    	var input = document.getElementById('pac-input');
	    	google.maps.event.trigger(input, 'focus');
		    google.maps.event.trigger(input, 'keydown', { keyCode: 13 });
		});
		
		
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
		
		// 추천 상품 닫기
		$('#close').click(function(){
			$('#banner').hide();
			$('#banner_sub').hide();
		});
	
	});

	// 댓글 쓰기
	function ReplyWrite(num)
	{
		if($(".secretChk").is(":checked"))  // 비밀글 체크 o
		{
			$.ajax({
				type:"post",
				url:"./ReplyWrite.ro",   // java로 보냄
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
				url:"./ReplyWrite.ro",
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
				url:"./Re_ReplyWriteAction.ro",
				data:{
					id:$("#reid").val(),
					content:$("#recontent"+num).val(),
					num:$("#num").val(),
					repageNum:$("#repageNum").val(),
					replynum:$("#replynum").val(),
					re_ref:$("#re_ref"+num).val(),
					re_lev:$("#re_lev").val(),
					re_seq:$("#re_seq").val(),
					secretChk:$(".re_secretChk").val()
				},
				success:function(){
					window.location.reload(true);
				}
			});
		}
		else		  // 비밀글 체크 x
		{
			$.ajax({
				type:"post",
				url:"./Re_ReplyWriteAction.ro",
				data:{
					num:$("#num").val(),
					id:$("#reid").val(),
					content:$("#recontent"+num).val(),
					repageNum:$("#repageNum").val(),
					replynum:$("#replynum").val(),
					re_ref:$("#re_ref"+num).val(),
					re_lev:$("#re_lev").val(),
					re_seq:$("#re_seq").val(),
					secretChk:"0"
				},
				success:function(){
					window.location.reload(true);
				}
			});
		}
	}

	// 댓글 삭제
	function ReplyDel(renum, id)
	{
		$.ajax({
			type:"post",
			url:"./ReplyDelAction.ro",
			data:{
				renum:renum,
				id:id
			},				
			success:function(){
				window.location.reload(true);
			}
		});
	}

	// 댓글 수정
	function reUpdateAction(num)
	{
// 		alert(num);
		if($(".up_secretChk"+num).is(":checked"))
		{
			$.ajax({
				type:"post",
				url:"./ReplyUpdateActoin.ro",
				data:{
					content:$("#contentup"+num).val(),
					num:num,
					secretChk:$(".up_secretChk"+num).val()
				},
				success:function(){
					window.location.reload(true);
				}			
			});
		}
		
		else
		{
			$.ajax({
				type:"post",
				url:"./ReplyUpdateActoin.ro",
				data:{
					num:num,
					content:$("#contentup"+num).val(),
					secretChk:"0"				
				},
				success:function(){
					window.location.reload(true);
				}
			});
		}
	}

	//구글맵 v3
	function initAutocomplete() {

		var geocoder = new google.maps.Geocoder();

		var addr = value;  // 버튼 클릭 시 넘겨받을 장소
		var lat = "";   // 위도값
		var lng = "";   // 경도값
		var prev_infowindow = false; // 이전 infowindow값 저장할 변수
		
		geocoder.geocode({
			'address' : addr
		},

		function(results, status) {

			if (results != "") {

				var location = results[0].geometry.location;	// 검색하는 장소의 위치값을 가져온다

				lat = location.lat();   // 검색하는 장소의 위도값
				lng = location.lng();   // 검색하는 장소의 경도값

				var latlng = new google.maps.LatLng(lat, lng);  // 위도, 경도 설정
				var myOptions = {
					zoom : 14,									// 구글맵 줌 거리 설정
					center : latlng,							// 구글맵 좌표 설정
					mapTypeControl : true,
					mapTypeId : google.maps.MapTypeId.ROADMAP
				};
				var map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);  // 구글맵 생성
				
				var infoWindow = new google.maps.InfoWindow({
					map : map
				});
		    	

			    
				// 검색창과 UI요소와 연결
				var input = document.getElementById('pac-input');
				var searchBox = new google.maps.places.SearchBox(input);
				map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);

				// Bias the SearchBox results towards current map's viewport.
				map.addListener('bounds_changed', function() {
					searchBox.setBounds(map.getBounds());
				});

				var markers = [];
				// 검색할때 이벤트 처리 
				searchBox.addListener('places_changed', function() {
					var places = searchBox.getPlaces();

					if (places.length == 0) {
						return;
					}

					// 오래된 마커를 지웁니다
					markers.forEach(function(marker) {
						marker.setMap(null);
					});
					markers = [];

					
					
					// 그 장소에 대한 더 자세한 내용
					// 각 장소의 아이콘과 이름, 위치를 가져온다
					var bounds = new google.maps.LatLngBounds();
					places.forEach(function(place) {
						if (!place.geometry) {
							console.log("Returned place contains no geometry");
							return;
						}
						
						// 해당 장소의 사진을 가져온다
						var photos = place.photos;
						  if (!photos) {
						    return;
					 	}
						
						// 해당 장소에 마커를 만든다
						var marker = new google.maps.Marker({
						  map: map,
						  position: place.geometry.location,
						  title: place.name
						  
						});
		            	markers.push(marker);

		            	
		            	// 말풍선에 넣을 이미지 및 문구 설정
		            	var imgurl = photos[0].getUrl({'maxWidth': 150, 'maxHeight': 150});
		            	var contentString = "<table><tr><td rowspan='2'><img style='width:100px; height:100px' src=" + imgurl + "></td><td><p style='text-align: center;'>" + place.name + "</p></td></tr>"
		            	 + "<tr><td><p style='text-align: center;'>" + place.formatted_address + "</p></td></tr></table>";
						
						var infowindow1 = new google.maps.InfoWindow({ content: contentString});
						 
						
						// 마커를 클릭했을 때 이벤트 처리 
						google.maps.event.addListener(marker, 'click', function() {
							if (prev_infowindow)  // 값이 있을 시 실행
								{
									prev_infowindow.close();  // 이전 정보창을 닫는다
								}
							prev_infowindow = infowindow1;  // prev_infowindow에 현재 inforwindow1 값을 저장
							infowindow1.open(map, this);	// 클릭된 마커의 정보창을 연다
				        });
						
						 
// 						// 마커 위에 마우스가 올라갔을때 이벤트 처리 
// 						google.maps.event.addListener(marker, 'mouseover', function() {
// 				            infowindow1.open(map, this);
// 				        });
							
// 						// 마커 위에 마우스가 내려갔을때 이벤트 처리
// 						google.maps.event.addListener(marker, 'mouseout', function() {
// 				            infowindow1.close(map, this);
// 				        });

						if (place.geometry.viewport) {
							// Only geocodes have viewport.
							bounds.union(place.geometry.viewport);
						} else {
							bounds.extend(place.geometry.location);
						}
					});
					map.fitBounds(bounds);
				});
			} else
				$("#map_canvas").html("위도와 경도를 찾을 수 없습니다.");	
		});
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
		$("#remote_content").draggable();
	});
	
	// 날짜 추가 버튼 클릭 이벤트
	function winOpen(num) {
		win = window.open("./PackDateAdd.po?num=" + num, "Package_dateAdd.jsp",
				"width=800, height=700, left=100, top=100");
	}
	
	
	// 날짜 선택시 이벤트
	function select_date(select_num)
	{
		var packnum = $("#select_rbtn" + select_num).val();  // 해당 라디오버튼의 글번호 값을 불러온다
		var stock = $("#stock" + select_num).html(); // 선택된 날짜의 수량을 가져온다
		
		$("#stock").val(stock);  // 수량을 id="stock"인 곳에 값을 넣어준다 
		
		var stock_temp; 
		
		if (stock > 10)  // stock이 10보다 클 경우
		{
			stock_temp = 10;
		}
		else			// stock이 10보다 작을 경우
		{
			stock_temp = stock;
		}
		
		$("#adult").find("option").remove();  // 어른에서 선택 값 전부 삭제
		for (i = 1; i <= stock_temp; i++)  // stock_temp가 최대치인 어른 선택값 생성 
		{
			$('#adult').append("<option value=" + i + ">" + i + "</option");
		}
		
		
		
		
		$(".select_color").css("background-color","");		// tr 부분 모든 배경색을 없앤다
		$("#select_rbtn" + select_num).prop("checked", "true"); // 클릭된 라디오 버튼을 체크로 바꾼다
		$("#select_date" + select_num).css("background-color", "#D5D5D5");  // 클릭된 tr 부분의 배경색을 #D5D5D5로 바꾼다
		
		
		var cost = $("#cost" + packnum).html();
		
	    var str = String(cost);
	    var uncomma_cost = str.replace(/[^\d]+/g, '');  // 콤마 삭제
	    var uncomma_cost2 = uncomma_cost/2;
		
		
		str = String(uncomma_cost);
		var comma_cost =  str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
		str1 = String(uncomma_cost2);
		var comma_cost2 =  str1.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
		
		$("#cost_adult").html(comma_cost);
		$("#cost_child").html(comma_cost2);
		$("#p").html(comma_cost);
		
		people_Calc(1);  // 어른 값 변경에 따른 아이값 변경
	}
	
	
	jQuery(document).ready(function($){
		var packnum = $("#ori_num_chk").val();
// 		alert(packnum);
		$.ajax({   // 날짜를 클릭할때 마다 찜목록과 비교
			type:"post",
			url:"./MyInterestCheck.ins",
			data:{
				num:packnum,
				type:"P"
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
						type:"P",
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
						type:"P",
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
				var cost_temp = $("#p").html(); // 총금액 받아오기
				str = String(cost_temp);		// 총금액 천원단위로 , 찍혀있는걸
			    cost = str.replace(/[^\d]+/g, '');   // 풉니다
				
			    $("#cost").val(cost);
			    $("#ori_num").val($("input[type=radio][name=chk]:checked").val());

			    document.input_fr.action = "./MyBasketAddAction.bns";	// 장바구니 페이지로 이동
			    document.input_fr.method = "post";
			    document.input_fr.submit();
			}
			
			else if (i == 4)  // 로그인 되어 있을 경우  예약하기
			{
				var cost_temp = $("#p").html(); // 총금액 받아오기
				str = String(cost_temp);		// 총금액 천원단위로 , 찍혀있는걸
			    cost = str.replace(/[^\d]+/g, '');  // 풉니다
				
			    // 폼태그로 보내기때문에 hidden 숨겨둔 곳에 각각 값을 넣는다
			    $("#cost").val(cost);
			    $("#ori_num").val($("input[type=radio][name=chk]:checked").val());

			    document.input_fr.action = "./MyOrderPay.mo";  // 예약하기 페이지로 이동
			    document.input_fr.method = "post";
			    document.input_fr.submit();
			}
		}
		
		else if(user_id == "")	// 로그인 안되어 있을 경우
		{
			loginChk();
		}
	}

	// 추천상품 클릭 시 이동
	function Rcom_move(select)
	{
		var select_num = $("#num" + select).html();
		var car_num = $("#car_num" + select).html();
		location.href="./ProductContent.bo?num=" + select_num + "&car_num=" + car_num;
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
	
	//패키지 상품 삭제
	function PackDel(num)
	{
		if (confirm("※해당 패키지의 모든 날짜가 삭제됩니다") == true){    //확인
			location.href='PackDeleteAction.po?num='+num;
		}
		else //취소
		    return;
	}
	
</script>

<!-- 구글맵에 필요한 스크립트 -->
<script
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAABj1RgLJEG6AlmrrpOAyD9Jq_ciRKdt0&libraries=places&callback=initAutocomplete"
	async defer>
</script>
<!-- 구글맵에 필요한 스크립트 -->

<style type="text/css">

.clear {
	clear: both;
}

</style>
	<div id="remote_control">
		<table id="remote_content">
			<tr>
				<td><span onclick="remote_close()">close</span></td>
			</tr>
			<tr>
				<td><span onclick="fnMove('body')">Top</span></td>
			</tr>
			<tr>
				<td><span onclick="fnMove('#contentdiv2')">여행정보</span></td>
			</tr>
			<tr>
				<td><span onclick="fnMove('#middle2')">지도뷰</span></td>
			</tr>
			<tr>
				<td style="border-bottom: none;"><span onclick="fnMove('#QnA')">상품문의</span></td>
			</tr>
		</table>
	</div>
	
	<%
	if (RecommendProduct.size() > 0)
	{
	%>
	<div id="banner">
		<table id="banner_content">
			<tr>
				<tr>
				<%
					if (RecommendProduct.size() == 1)
					{
				%>
						<td>
							<div id="banner_sub">추천상품</div>
							<div id="close">close</div>
						</td>
						
				<%
					}
					else if (RecommendProduct.size() == 2)
					{
				%>
						<td><div id="banner_sub">추천상품</div></td>
						<td><div id="close">close</div></td>
				<%
					}
					else if (RecommendProduct.size() == 3)
					{
				%>
						<td><div id="banner_sub">추천상품</div></td>
						<td></td>
						<td><div id="close">close</div></td>
				<%
					}
				%>
				</tr>
				<%
					ProductBean pdb;
					for(int i = 0; i < RecommendProduct.size(); i++)
					{
						pdb =(ProductBean)RecommendProduct.get(i);
						DecimalFormat df = new DecimalFormat("#,###");
					    String Recommedcost = df.format(pdb.getCost());
				%>
				
				<td>
					<table>
						<tr>
							<td id="num<%=i %>" style="display: none;"><%=pdb.getNum() %></td>
							<td id="car_num<%=i %>" style="display: none;"><%=pdb.getCar_num() %></td>
							<td><img id="Rcom_pd" src="./upload/<%=pdb.getImg() %>" onclick="Rcom_move(<%=i %>)"></td>
						</tr>
						<tr>
							<td><div class="info"><%=Recommedcost %>원</div></td>
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
	<div id="wrap_pack">
	<div id="article_head">
		<div id="article_title"><img src="./img/travel2.png" width="30px" style="margin-right: 8px; vertical-align: bottom;">패키지</div>
	<div class="empty"></div>
	</div>
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
				<label for="date_from">출발</label><input type="text" id="date_from" class="input_style" name="startDate" readonly><br><br>
				<label for="city_search">지역</label>
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
				<input type="submit" value="검색" id="search_btn" class="input_style">
			</form>
		</div>
	</div>
	<div id="clear"></div>
	<!--여행지 검색창 -->
	<div id="pack_btn">
		<!--글제목 -->
		<br>
		<h3><%=PB.getSubject()%></h3>
		<!--글제목 -->
		<!--관리자만 보이게 -->
		<%
		if (user_id != null)
		{
			if (user_id.equals("admin"))
			{
		%>
			<input type="button" value="날짜편집" onclick="winOpen(<%=PB.getNum() %>);">
			<input type="button" value="상품내용수정" onclick="location.href='PackModify.po?num=<%=PB.getNum() %>'">
			<input type="button" value="상품삭제" onclick="PackDel(<%=PB.getNum() %>);">
		<%
			}
		}
		%>
		</div>
		<!--관리자만 보이게 -->
		<hr>
		<div id="top">
		<div id="top_content">
			<!--상품 이미지 -->
			<div id="imgdiv">
				<!--첫번째 이미지는 무조건 첨부하게 제어 -->
				<img src="./upload/<%=PB.getFile1() %>" id="main">
				<!--첫번째 이미지는 무조건 첨부하게 제어 -->
				<ul class="bxslider">
					<li><img src="./upload/<%=PB.getFile1() %>" style="box-sizing : border-box; border : 5px solid #A6A6A6;"></li>
					<%
					// 2~5번째 이미지는 null이 아닐 경우 출력 o   null 경우 출력 x
					String img[] = {PB.getFile2(), PB.getFile3(), PB.getFile4(), PB.getFile5()};
					
					for (int i = 0; i < img.length; i++)
					{
						if(img[i] != null && !img[i].equals(""))
						{
						%>
							<li><img src="./upload/<%=img[i] %>"></li>
						<%
						}
					}
					%>
				</ul>
			</div>
			<!--상품 이미지 -->
			
			<!--인원수, 가격 -->
			<div id="contentdiv1">
				<form name="input_fr" method="post">
					<table>
						<tr>
							<td class="contentdiv1_1">성인(12세이상)</td>
							<td class="contentdiv1_2" id="cost_adult"></td>
							<td class="contentdiv1_3">
								<!--최대 10명까지 선택가능하게 생성 -->
								<select id="adult" name="adult" onchange="people_Calc(1)">
								</select>
								<!--최대 10명까지 선택가능하게 생성 -->
							</td>
						</tr>
						<tr>
							<td class="contentdiv1_1">아동(12세미만)</td>
							<td id="cost_child" class="contentdiv1_2"></td>
							<td class="contentdiv1_3">
							<!--초기값은 1명까지 선택되게 생성 -->
							<select id="child" name="child" onchange="people_Calc()">
									<option value="0">0</option>
							</select>
							<!--초기값은 1명까지 선택되게 생성 -->
							</td>
						</tr>
						<tr>
							<td class="contentdiv1_1">합계</td>
							<td colspan="2">
								<input type="hidden" id="stock" value="">
								<input type="hidden" id="cost" name="cost" value="">
								<input type="hidden" id="ori_num" name="pnum" value="">
								<input type="hidden" name="type" value="P">
								<p id="p"></p>
							</td>
						</tr>
					</table>
					<br>
					<input type="hidden" id="ori_num_chk" value="<%=PB.getNum() %>">
					<input type="button" class="contentbtn" id="jjim_o" value="♡ 찜" onclick="submit_fun(1, '<%=user_id %>')">
					<input type="button" class="contentbtn" id="jjim_x" value="♥ 찜" style="display:none;" onclick="submit_fun(2, '<%=user_id %>')">
					<input type="button" class="contentbtn2" value="장바구니" onclick="submit_fun(3, '<%=user_id %>')">
					<input type="button" class="contentbtn2" value="예약하기" onclick="submit_fun(4, '<%=user_id %>')">
					
					
					<p id="content_notice">※성인 1명당 아이 1명으로 제한됩니다</p>
					
					<script type="text/javascript">
							// 선택된 인원 수에 따라 가격 변경, 어른 인원에 따라 아이인원제한
							function people_Calc(num){			
								$(document).ready(function(){
									var val1 = $("#adult option:selected").val();  // 어른 인원 선택된 값을 가져온다
									var val2 = $("#child option:selected").val();  // 아이 인원 선택된 값을 가져온다									
									
									var stock = $("#stock").val();
									
									// 어른 수에 따라 아이 수 제한
									var maxval = 0;
									if (num == 1) // 어른 선택 시 호출
									{
										if (val1 > stock - val1) 
										{
											maxval = stock - val1;
										}
										else
										{
											maxval = val1;
										}
										$("#child").find("option").remove();  // 아이에서 선택 값 전부 삭제
										
										for (i = 0; i <= maxval; i++)  // 선택된 어른 수가 최대치인 아이 선택값 생성     ex) 어른 3명 선택 시 아이도 3명까지 선택 가능
										{
											$('#child').append("<option value=" + i + ">" + i + "</option");
										}
										val2 = $("#child option:selected").val();
									}
									
// 									alert(val1);
// 									alert(val2);
									
									var sum = 0;
									// 선택된 어른 수, 아이 수에 따라 가격 계산 후 출력
									if (val1 < 1 && val2 < 1)
									{
										sum = 0;
									}
									else
									{
										sum = val1 * <%=PB.getCost() %> + val2 * <%=PB.getCost()/2 %>
									}
									
									
								    var str = String(sum);
								    var commasum = str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');

									$('#p').html(commasum);
								});
							}
					</script>
				</form>
			</div>
			<!--인원수, 가격 -->
		</div>
		</div>
		<div class="clear"></div>
		
		<!--날짜정보 영역 -->
		<div id="datecontent">
			<table>
				<tr>
					<td></td>
					<td id="date_date">출발일자</td>
					<td id="date_subject">상품명</td>
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
					<td class="date_td_size"><input type="radio" class="select_rbtn" id="select_rbtn<%=i %>" name="chk" value="<%=pb.getNum() %>"></td>
					<td class="date_td_size"><%=pb.getDate() %></td>
					<td class="date_td_size"><%=pb.getSubject() %></td>
					<td class="date_td_size" id="cost<%=pb.getNum() %>"><%=cost %></td>
					<%
					if(pb.getStock() == 0)
					{
					%>
					<td class="date_td_size">품절</td>
					<%
					}
					else
					{
					%>
					<td class="date_td_size" id="stock<%=i %>"><%=pb.getStock() %></td>
					<%
					}
					%>
				</tr>
				<%
					}
				%>
			</table>
		</div>
		<!--날짜정보 영역 -->
		
		<!--상품 정보, 내용이 들어가는 영역 -->
		<div id="middle1">
			<div id="contentdiv2">
				<div id="contentdiv2_1">
				<center><%=PB.getContent() %></center>
				
				</div>
			</div>
		</div>
		<!--상품 정보, 내용이 들어가는 영역 -->

		<!--구글맵 제어할 버튼 부분 -->
		<div id="middle2">
			<hr>
			<div id="pack_btn"><h3 id="sub"><br><%=PB.getCity()%> <%=PB.getSarea()%></h3></div>
			<hr>
			<br><br>
			<input type="button" id="btn1" value="관광 명소"> 
			<input type="button" id="btn2" value="맛집">
		</div><br><br>
		<!--구글맵 제어할 버튼 부분 -->
		
		<!--구글맵 -->
		<input id="pac-input" class="controls" type="text" placeholder="Search Box" readonly>
		<div id="map_canvas"></div>
		<!--구글맵 -->

		<!--패키지 문의 -->
		<div id="QnA">
			<hr>
			<div id="pack_btn">
			<br>
			<h3>패키지 문의</h3>
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
				
				if(count == 0)
				{
				%>
					<tr>
						<td style="width:800px; text-align: center; height:50px;">문의 내역이 없습니다</td>
					</tr>
				<%
				}
				
					ReplyBean rb;
					if (count != 0) {
						for (int i = 0; i < replylist.size(); i++) 
						{
							rb = (ReplyBean)replylist.get(i);
				%>
			<!-- 상품문의 글보기 -->
				<tr id="relist<%=rb.getNum()%>">
<%-- 					<td><%=rb.getNum()%></td> --%>
					<td><%=rb.getId()%></td>
					<%
					if ((rb.getId().equals(user_id) && rb.getH_or_s() == 1) || rb.getH_or_s() == 0){
					%>
					<td id="replyContent">
						<span class="reply_align">
						
						<%
							// 답글 들여쓰기 모양
							int wid = 0;
							if (rb.getRe_lev() > 0) {
								wid = 10 * rb.getRe_lev();
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
						
						<%=rb.getContent()%><span style="font-size: 0.8em; margin-left:5px;">(<%=rb.getDate() %>)</span>
						
						<%
						// 비밀글일 경우 자물쇠 이미지 표시
						if(rb.getH_or_s() == 1)
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
						
						<input type="button" value="답글" id="rereply" onclick="rewrite(<%=rb.getNum()%>)">
						<%
						}
						if(rb.getId().equals(user_id))
						{
						%>
						<input type="button" value="수정" id="re_update" onclick="reupdate(<%=rb.getNum() %>)">
						<%
						}
						if(rb.getId().equals(user_id) || user_id.equals("admin"))
						{
						%>
						<input type="button" value="삭제" id="re_delete" onclick="ReplyDel(<%=rb.getNum() %>, '<%=user_id%>');">
						<%
						}
						%>
						</span>
					</td>
					<%
					}
					// 비밀글이며, 로그인했을 시
					else if (rb.getH_or_s() == 1 && !rb.getId().equals(user_id)){
					%>
					<td style="height:50px;">
						<span class="reply_align">
						비밀글입니다<img src="./img/lock.png" width="10px" height="10px">
						(<%=rb.getDate() %>)
						</span>
						<span style="float: right;">
						<%
						if(user_id.equals("admin"))
						{
						%>
						<input type="button" value="답글" id="rereply" onclick="rewrite(<%=rb.getNum()%>)">
						<%
						}
						if(rb.getId().equals(user_id))
						{
						%>
						<input type="button" value="수정" id="re_update" onclick="reupdate(<%=rb.getNum() %>)">
						<%
						}
						if(rb.getId().equals(user_id) || user_id.equals("admin"))
						{
						%>
						<input type="button" value="삭제" id="re_delete" onclick="ReplyDel(<%=rb.getNum() %>, '<%=user_id%>');">
						<%
						}
						%>
						</span>
					</td>
					<%
					}
					%>
				</tr>
				<!-- 상품문의 글보기 -->
				
				<!-- 상품문의 수정 -->
				<tr id="conup<%=rb.getNum()%>" style="display: none;">
					<td>
						<%=user_id %>
					</td>
					
					<td>
						<span class="reply_align">
						<textarea style="width: 550px;" cols="60" rows="2" id="contentup<%=rb.getNum() %>" name="contentup"><%=rb.getContent() %></textarea>
						</span>
						<span style="float: right;">
						<input type="button" value="수정" onclick="reUpdateAction(<%=rb.getNum() %>)">
						<input type="button" value="취소" onclick="reupdate(<%=rb.getNum() %>)"><br>
						<input type="checkbox" class="up_secretChk<%=rb.getNum() %>" name="secretChk" value="1" <%if(rb.getH_or_s() == 1){%>checked<%} %>>비밀글
						</span>
					</td>
					</td>
				</tr>
				<!-- 상품문의 수정 -->
				
				<!-- 답글 등록 -->
				<tr id="con<%=rb.getNum()%>" style="display: none;">
					<td>
						<input type="hidden" id="num" name="num" value="<%=PB.getNum()%>">
						<input type="hidden" id="repageNum" name="repageNum" value="<%=repageNum%>">
						<input type="hidden" id="replynum" name="replynum" value="<%=rb.getNum()%>">
						<input type="hidden" id="re_ref<%=rb.getNum() %>" name="re_ref" value="<%=rb.getRe_ref()%>">
						<input type="hidden" id="re_lev" name="re_lev" value="<%=rb.getRe_lev()%>">
						<input type="hidden" id="re_seq" name="re_seq" value="<%=rb.getRe_seq()%>">
						<p><%=user_id %></p>
						<input type="hidden" id="reid" name="id" class="box" value="<%=user_id %>">
					</td>
					<td>
						<span class="reply_align">
						<textarea style="width: 550px;" cols="60" rows="2" id="recontent<%=rb.getNum() %>" name="recontent"></textarea>
						</span>
						<span style="float: right;">
						<input type="button" value="답글등록" onclick="Re_Reply_Write(<%=rb.getNum() %>)">
						<input type="button" value="취소" onclick="rewrite(<%=rb.getNum() %>)"><br>
						<input type="checkbox" class="re_secretChk" name="secretChk" value="1">비밀글
						</span>
					</td>
				</tr>
				<!-- 답글 등록 -->
			
				<%
					}
					}
				%>
				
			</table>
			<br>
			<!-- 문의글 쓰기 -->
			<table id="replyWrite">
				<tr>
				<!-- 비로그인 시 처리 -->
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
				<!-- 문의글 쓰기 -->
				
				<!-- 로그인 시 문의글 쓰기 -->
					<%
						}
					
						else
						{
					%>
						<td>
							<textarea type="text" id="content" name="content" class="box" placeholder="문의글을 입력해주세요" style="width:790px; height:50px;"></textarea>
							<input type="hidden" id="id" name="id" class="box" value="<%=user_id %>">
							<input type="hidden" name="pageNum" value="<%=repageNum%>">
							<input type="hidden" id="num" name="num" value="<%=PB.getNum() %>">
						</td>
						<tr>
							<td>
								<div style="text-align:right; margin-top:15px; width:790px;">
									<input type="checkbox" class="secretChk" name="secretChk" value="1">비밀글
									<input type="button" value="문의글쓰기" onclick="ReplyWrite(<%=PB.getNum() %>)">
								</div>
							</td>
						</tr>
					<%
						}
					%>
				<!-- 로그인 시 문의글 쓰기 -->
				</tr>
			</table>
			<!-- 문의글 쓰기 -->
			
			<center>
				<%
				//페이지 출력
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
					if(startPage>pageBlock){
						%><a href="./PackContent.po?num=<%=PB.getNum() %>&repageNum=<%=startPage - pageBlock%>#QnA">[이전]</a><%
					}

					//해당 페이지 이동
					for(int i=startPage; i<=endPage; i++)
					{
						if(i==pNum)
							{%><span id="i"><%=i%></span><%}
						else
						{
						%>
						<a id="i" href="./PackContent.po?num=<%=PB.getNum() %>&repageNum=<%=i %>#QnA"><%=i%></a>
						<%
						}
					}
					//다음
					if(endPage < pageCount)
					{
						%>
						<a href="./PackContent.po?num=<%=PB.getNum() %>&repageNum=<%=startPage + pageBlock%>#QnA">[다음]</a>
						<%
					}
				}
				%>
			</center>
		</div>
	</div>
	<!--상품 문의 -->
	</div>
	</div>
	<!-- 오른쪽 메뉴 -->
	<jsp:include page="../inc/rightMenu.jsp"></jsp:include>
	<!-- 오른쪽 메뉴 -->
	<!-- 푸터 메뉴 -->
	<jsp:include page="../inc/footer.jsp"></jsp:include>
	<!-- 푸터 메뉴 -->
</body>
</html>