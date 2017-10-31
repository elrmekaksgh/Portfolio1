<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="net.pack.db.PackBean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="./css/subpage.css" rel="stylesheet" type="text/css">
<script src="./js/jquery-3.2.0.js"></script>
<title>Insert title here</title>
</head>
<%
	PackBean pb = (PackBean)request.getAttribute("pb");
%>

<script type="text/javascript">


//주변 명소, 주변 맛집 클릭 시  검색할 값을 구글맵으로 보낸다
// 현재 페이지의 City, Sarea 값을 가져온다
var value="<%=pb.getCity()%> <%=pb.getSarea()%>";
jQuery(document).ready(function($){
	$('#middle2 input').click(function(){  // 주변명소, 주변맛집  버튼 클릭 시 이벤트 발생

   		var btn = $(this).attr("value");   // 클릭된 버튼의 value 값을 가져온다
		
   		if (btn == "주변 명소")
		{
			value = "<%=pb.getCity()%> " + "<%=pb.getSarea()%> " + btn; 
<%-- 				value = "<%=PB.getSarea()%> " + btn;  --%>
			// ex) value = 부산 해운대 주변 명소
		}
		else if (btn == "주변 맛집")
		{
			value = "<%=pb.getCity()%> " + "<%=pb.getSarea()%> " + btn;
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
});



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
	            	var contentString = "<table border='1'><tr><td rowspan='2'><img style='width:100px; height:100px' src=" + imgurl + "></td><td><p style='text-align: center;'>" + place.name + "</p></td></tr>"
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
					
					 
//						// 마커 위에 마우스가 올라갔을때 이벤트 처리 
//						google.maps.event.addListener(marker, 'mouseover', function() {
//				            infowindow1.open(map, this);
//				        });
						
//						// 마커 위에 마우스가 내려갔을때 이벤트 처리
//						google.maps.event.addListener(marker, 'mouseout', function() {
//				            infowindow1.close(map, this);
//				        });

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

</script>
<!-- 구글맵에 필요한 스크립트 -->
<script
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAABj1RgLJEG6AlmrrpOAyD9Jq_ciRKdt0&libraries=places&callback=initAutocomplete"
	async defer>
</script>
<!-- 구글맵에 필요한 스크립트 -->
<body>
<center>
<h3><%=pb.getSubject() %></h3>
<p><%=pb.getContent() %></p>
</center>

<!--구글맵 제어할 버튼 부분 -->
<div id="middle2">
	<hr>
	<center><h3 id="sub"><%=pb.getCity()%> <%=pb.getSarea()%></h3></center>
	<hr>
	<center>
	<input type="button" id="btn1" value="주변 명소"> 
	<input type="button" id="btn2" value="주변 맛집">
	</center>
</div>
<!--구글맵 제어할 버튼 부분 -->

<!--구글맵 -->
<input id="pac-input" class="controls" type="text" placeholder="Search Box" readonly>
<div id="map_canvas"></div>
<!--구글맵 -->
<br><br>
</body>
</html>