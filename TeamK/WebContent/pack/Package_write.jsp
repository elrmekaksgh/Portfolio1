<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="net.pack.db.PackDAO" %>
    <%@ page import="net.pack.db.PackBean" %>
    <%@ page import="net.pack.db.CategoryBean" %>
    <%@ page import="net.pack.db.CategoryDAO" %>
    <%@ page import="java.util.List" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="./css/inc.css" rel="stylesheet" type="text/css">
<link href="./css/subpage.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="./js/HuskyEZCreator.js" charset="utf-8"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="./js/jquery-3.2.0.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<script>
	jQuery(document).ready(function($){
		$("#date_from").datepicker({
			dateFormat: 'yy-mm-dd',    // 날짜 포맷 형식
			minDate : 0,			   // 최소 날짜 설정      0이면 오늘부터 선택 가능
			numberOfMonths: 1,		   // 보여줄 달의 갯수
	        dayNamesMin: ['월', '화', '수', '목', '금', '토', '일'],  // 일(Day) 표기 형식
	        monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],   // 월(Month) 표기 형식
	        showOn: "both",		// 버튼을 표시      both : input과 buttom 둘다 클릭 시 달력 표시           bottom  :  buttom 클릭 했을 때만 달력 표시
	        buttonImage: "./img/calendar.png",   // 버튼에 사용될 이미지
	        buttonImageOnly: true,					// 이미지만 표시한다    버튼모양 x
	        onClose: function(selectedDate){		// 닫힐 때 함수 호출
	        	$("#date_to").datepicker("option", "minDate", selectedDate);    // #date_to의 최소 날짜를 #date_from에서 선택된 날짜로 설정
	    		$('img.ui-datepicker-trigger').attr('align', 'absmiddle');
	        }
		});
		$("#txt_prodStart").datepicker();
	    $('img.ui-datepicker-trigger').attr('align', 'absmiddle');
	    
	    // 이미지 첨부 나머지 4부분은 숨겨둔다
	    $('#aa').hide();
		$('#aa2').hide();
		$('#aa3').hide();
		$('#aa4').hide();
		
		
		// 파일 크기 제한 5MB
		var maxSize = 5*1024*1024;
		var fileID = ["#file1", "#file2", "#file3", "#file4", "#file5"];
		
		for (var i = 0; i < fileID.length; i++)
		{
			$(fileID[i]).bind('change', function() {   // 파일 첨부 할 때 함수 호출
				var filesize_temp = (this.files[0].size)/1048576;
				var filesize = filesize_temp.toString().substring(0, 4);
				
				if (this.files[0].size > maxSize)
				{
					alert("5MB 이하의 이미지를 선택해주세요  (" + filesize + "MB)");
					$(this).val("");
				}
			});
		}
		
		// 수량에 숫자만 들어가게 제어
		$("#stock").keyup(function(){
			$(this).val( $(this).val().replace(/[^0-9]/g,"") );
		});
		
		// 가격에 숫자만 들어가게 제어
		$("#cost").keyup(function(){
			$(this).val( $(this).val().replace(/[^0-9]/g,"") );
		});
		
		
	});
	
	// 이미지추가 버튼 (+) 클릭 시 보여준다
	function winopen(num){
		switch(num)
		{
			case 1:
				$('#aa').show();
				break;
			case 2:
				$('#aa2').show();
				break;
			case 3:
				$('#aa3').show();
				break;
			case 4:
				$('#aa4').show();
				break;
		}
	}

	
	// 날짜를 입력할 때 이미 등록된 날짜가 있는지 체크
	function date_chk()
	{
		$("#subject").val();
		$("#date_from").val();
	}
	
</script>

<style type="text/css">



#subject, #intro
{
	width : 500px;
}


#ir1
{
	width:770px; 
	height:500px; 
	display:none;
}

#content_write
{
	width:770px; 
	height:500px;
}



</style>
<%
	CategoryDAO cdao = new CategoryDAO();
	List CategoryList = cdao.getCategoryList();
%>
</head>
<body>
<!-- 왼쪽 메뉴 -->
<jsp:include page="../inc/leftMenu.jsp"></jsp:include>
<!-- 왼쪽 메뉴 -->


<div id="wrap">
	<div id="article_head">
		<div id="article_title"><img src="./img/travel2.png" width="30px" style="margin-right: 8px; vertical-align: bottom;">패키지 상품등록</div>
	<div class="empty"></div>
	</div>
	<div id="wrap_pack">
	<div id="wrap_pack_detail">
		<div>
			<form action="./PackWriteAction.po" id="fr" method="post" enctype="multipart/form-data" onsubmit="return frsubmit()">
				<table border="1">
					<tr>
						<td class="td_size">출발일자</td>
						<td><input type="text" id="date_from" class="input_style" name="startDate"  required="yes"></td>	
					</tr>
					<tr>
						<td class="td_size">지역</td>
						<td>
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
						</td>
					</tr>
					<tr>
						<td class="td_size">도시</td>
						<td><input type="text" id="city" name="city" required="yes" placeholder="ex) 서울, 부산, 대구, 대전"></td>
					</tr>
					<tr>
						<td class="td_size">소분류</td>
						<td><input type="text" id="sarea" name="sarea" required="yes" placeholder="ex) 강남, 명동"></td>
					</tr>
					<tr>
						<td class="td_size">타입</td>
						<td>
							<select id="type" name="type">
								<option value="city">도시</option>
								<option value="mount">산</option>
								<option value="sea">바다</option>
								<option value="country">농촌</option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="td_size">가격</td>
						<td><input type="text" id="cost" name="cost" required="yes"></td>
					</tr>
					<tr>
						<td class="td_size">수량</td>
						<td><input type="text" id="stock" name="stock" required="yes"></td>
					</tr>
					<tr>
						<td class="td_size">글제목</td>
						<td><input type="text" id="subject" name="subject" required="yes"></td>
					</tr>
					<tr>
						<td class="td_size">소제목</td>
						<td><input type="text" name="intro" id="intro" required="yes"></td>
					</tr>
					<tr>
						<td class="td_size">글내용</td>
						<td id="content_write">
						<textarea name="content" id="ir1" rows="10" cols="100"></textarea>
						</td>
					</tr>
					
					<tr id="select2">
						<td class="td_size">이미지첨부</td>
						<td>
						<input type="text" name="file1" id="file1" readonly style="width: 300px; accept=".gif, .jpg, .png">
						<input type="button" value="파일첨부" class="dup" id="button" onclick="document.getElementById('file11').click();">
						<input type="button" value="파일삭제" class="dup" id="button" onclick="document.getElementById('file1').value='';">
						<input type="button" value="+" onclick="winopen(1)">
						<input type="file" size="30" name="file1" id="file11" style="display: none;"
						onchange="document.getElementById('file1').value=this.value;" />		
						</td>
					</tr>
					<tr id="aa">
						<td class="td_size">이미지첨부</td>
						<td>
						<input type="text" name="file2" id="file2" readonly style="width: 300px; accept=".gif, .jpg, .png">
						<input type="button" value="파일첨부" class="dup" id="button" onclick="document.getElementById('file22').click();">
						<input type="button" value="파일삭제" class="dup" id="button" onclick="document.getElementById('file2').value='';">
						<input type="button" value="+" onclick="winopen(2)">
						<input type="file" size="30" name="file2" id="file22" style="display: none;"
						onchange="document.getElementById('file2').value=this.value;" />
						</td>
					</tr>
					<tr id="aa2">
						<td class="td_size">이미지첨부</td>
						<td>
						<input type="text" name="file3" id="file3" readonly style="width: 300px; accept=".gif, .jpg, .png">
						<input type="button" value="파일첨부" class="dup" id="button" onclick="document.getElementById('file33').click();">
						<input type="button" value="파일삭제" class="dup" id="button" onclick="document.getElementById('file3').value='';">
						<input type="button" value="+" onclick="winopen(3)">
						<input type="file" size="30" name="file3" id="file33" style="display: none;"
						onchange="document.getElementById('file3').value=this.value;" />
						</td>
					</tr>	
					<tr id="aa3">
						<td class="td_size">이미지첨부</td>
						<td>
						<input type="text" name="file4" id="file4" readonly style="width: 300px; accept=".gif, .jpg, .png">
						<input type="button" value="파일첨부" class="dup" id="button" onclick="document.getElementById('file44').click();">
						<input type="button" value="파일삭제" class="dup" id="button" onclick="document.getElementById('file4').value='';">
						<input type="button" value="+" onclick="winopen(4)">
						<input type="file" size="30" name="file4" id="file44" style="display: none;"
						onchange="document.getElementById('file4').value=this.value;" />
						</td>
					</tr>
					<tr id="aa4">
						<td class="td_size">이미지첨부</td>
						<td>
						<input type="text" name="file5" id="file5" readonly style="width: 300px; accept=".gif, .jpg, .png">
						<input type="button" value="파일첨부" class="dup" id="button" onclick="document.getElementById('file55').click();">
						<input type="button" value="파일삭제" class="dup" id="button" onclick="document.getElementById('file5').value='';">
						<input type="file" size="30" name="file5" id="file55" style="display: none;"
						onchange="document.getElementById('file5').value=this.value;" />
						</td>
					</tr>
				</table>
				<script type="text/javascript">
					var oEditors = [];
					// 스마트에디터 생성
					nhn.husky.EZCreator.createInIFrame({
						oAppRef: oEditors,
						elPlaceHolder: "ir1",
						sSkinURI: "./SmartEditor2Skin.html",	
						htParams : {
							bUseToolbar : true,				// 툴바 사용 여부 (true:사용/ false:사용하지 않음)
							bUseVerticalResizer : true,		// 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
							bUseModeChanger : true,			// 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
							fOnBeforeUnload : function(){
								
							}
						}, //boolean
						fOnAppLoad : function(){
							// 로딩이 완료 시 textarea 부분에 보여주는 내용
							var sHTML = '<table border="0" cellpadding="0" cellspacing="0" style="border:1px solid #cccccc; border-left:0; border-bottom:0;" class="__se_tbl"><tbody><tr><td style="border-width: 0px 0px 1px 1px; border-bottom-style: solid; border-left-style: solid; border-bottom-color: rgb(204, 204, 204); border-left-color: rgb(204, 204, 204); border-image: initial; border-top-style: initial; border-top-color: initial; border-right-style: initial; border-right-color: initial; width: 717px; height: 54px; background-color: rgb(235, 235, 235);" class=""><p><b><span style="font-size: 12pt;">&nbsp;여행 정보</span></b></p></td></tr><tr><td style="border-width: 0px 0px 1px 1px; border-bottom-style: solid; border-left-style: solid; border-bottom-color: rgb(204, 204, 204); border-left-color: rgb(204, 204, 204); border-image: initial; border-top-style: initial; border-top-color: initial; border-right-style: initial; border-right-color: initial; width: 717px; height: 54px; background-color: rgb(255, 255, 255);" class=""><p>&nbsp;</p><p>&nbsp;<img src="/TeamK/upload/2017_06_02_112656.png"></p><p><br></p></td></tr></tbody></table><br><table border="0" cellpadding="0" cellspacing="0" style="border:1px solid #cccccc; border-left:0; border-bottom:0;" class="__se_tbl"><tbody>' + 
							'<tr><td style="border-width: 0px 0px 1px 1px; border-bottom-style: solid; border-left-style: solid; border-bottom-color: rgb(204, 204, 204); border-left-color: rgb(204, 204, 204); border-image: initial; border-top-style: initial; border-top-color: initial; border-right-style: initial; border-right-color: initial; width: 717px; height: 47px; background-color: rgb(235, 235, 235);" class=""><p>&nbsp;<b><span style="font-size: 12pt;">여행 조건</span></b></p></td>' + 
							'</tr><tr><td style="border-width: 0px 0px 1px 1px; border-bottom-style: solid; border-left-style: solid; border-bottom-color: rgb(204, 204, 204); border-left-color: rgb(204, 204, 204); border-image: initial; border-top-style: initial; border-top-color: initial; border-right-style: initial; border-right-color: initial; width: 717px; height: 18px; background-color: rgb(255, 255, 255);" class=""><p><br></p><p>&nbsp; &nbsp;<b style="color: rgb(55, 55, 55); font-family: &quot;Nanum Gothic&quot;, NanumGothic, 나눔고딕, NanumGothicWeb, &quot;맑은 고딕&quot;, &quot;Malgun Gothic&quot;, Dotum, applegothic, sans-serif, arial; letter-spacing: -0.25px; margin: 0px; padding: 0px; border: 0px; vertical-align: baseline; outline: 0px;">&lt; 여행예약 시 주의사항&nbsp;<font color="#ff0000" style="margin: 0px; padding: 0px;">(반드시 필독)</font>&nbsp;&gt;&nbsp;</b></p><div style="margin: 0px; padding: 0px; border: 0px; font-family: &quot;Nanum Gothic&quot;, NanumGothic, 나눔고딕, NanumGothicWeb, &quot;맑은 고딕&quot;, &quot;Malgun Gothic&quot;, Dotum, applegothic, sans-serif, arial; vertical-align: baseline; outline: 0px; color: rgb(55, 55, 55); letter-spacing: -0.25px;">&nbsp; 1. 울릉도 상품은 날씨의 영향을 많이 받으므로, 기상으로 인하여 현지 일정이 조정될 수 있습니다.</div><div style="margin: 0px; padding: 0px; border: 0px; font-family: &quot;Nanum Gothic&quot;, NanumGothic, 나눔고딕, NanumGothicWeb, &quot;맑은 고딕&quot;, &quot;Malgun Gothic&quot;, Dotum, applegothic, sans-serif, arial; vertical-align: baseline; outline: 0px; color: rgb(55, 55, 55); letter-spacing: -0.25px;">&nbsp; 2. 관광객이 폭주하는 연휴/성수기에는 일정표 외 가이드 안내에 따른 관광일정으로 진행됩니다.</div><div style="margin: 0px; padding: 0px; border: 0px; font-family: &quot;Nanum Gothic&quot;, NanumGothic, 나눔고딕, NanumGothicWeb, &quot;맑은 고딕&quot;, &quot;Malgun Gothic&quot;, Dotum, applegothic, sans-serif, arial; vertical-align: baseline; outline: 0px; color: rgb(55, 55, 55); letter-spacing: -0.25px;">&nbsp; 3. 본 상품은 여행계약 후 에도 여객선사정으로&nbsp;&nbsp;<font color="#ff3366" style="margin: 0px; padding: 0px;">월별 선박출항 / 입항시간이 변경</font>&nbsp;될 수 있으며,</div><div style="margin: 0px; padding: 0px; border: 0px; font-family: &quot;Nanum Gothic&quot;, NanumGothic, 나눔고딕, NanumGothicWeb, &quot;맑은 고딕&quot;, &quot;Malgun Gothic&quot;, Dotum, applegothic, sans-serif, arial; vertical-align: baseline; outline: 0px; color: rgb(55, 55, 55); letter-spacing: -0.25px;">&nbsp; &nbsp; &nbsp;최종 운항시간은 출발 5일전 확정나며, 변경 시 출발 전 전화로 알려드립니다.</div><div style="margin: 0px; padding: 0px; border: 0px; font-family: &quot;Nanum Gothic&quot;, NanumGothic, 나눔고딕, NanumGothicWeb, &quot;맑은 고딕&quot;, &quot;Malgun Gothic&quot;, Dotum, applegothic, sans-serif, arial; vertical-align: baseline; outline: 0px; color: rgb(55, 55, 55); letter-spacing: -0.25px;">&nbsp; 4.&nbsp;<font color="#ff3366" style="margin: 0px; padding: 0px;">안내전화는 출발 1일전</font>&nbsp;최종적으로 기상체크 후 나가오니, 안내전화가 늦더라도 양해하여 주시기 바랍니다.</div><div style="margin: 0px; padding: 0px; border: 0px; font-family: &quot;Nanum Gothic&quot;, NanumGothic, 나눔고딕, NanumGothicWeb, &quot;맑은 고딕&quot;, &quot;Malgun Gothic&quot;, Dotum, applegothic, sans-serif, arial; vertical-align: baseline; outline: 0px; color: rgb(55, 55, 55); letter-spacing: -0.25px;">&nbsp; 5. 대아리조트는 시내와는 거리가 먼곳에 위치하고 있으며, 정해진 셔틀운행시간 이외에는 개별적으로</div><div style="margin: 0px; padding: 0px; border: 0px; font-family: &quot;Nanum Gothic&quot;, NanumGothic, 나눔고딕, NanumGothicWeb, &quot;맑은 고딕&quot;, &quot;Malgun Gothic&quot;, Dotum, applegothic, sans-serif, arial; vertical-align: baseline; outline: 0px; color: rgb(55, 55, 55); letter-spacing: -0.25px;">&nbsp; &nbsp; &nbsp;택시를 이용하셔야 하며, 아침식사(조식)는 리조트식 으로 제공 됩니다.</div><div style="margin: 0px; padding: 0px; border: 0px; font-family: &quot;Nanum Gothic&quot;, NanumGothic, 나눔고딕, NanumGothicWeb, &quot;맑은 고딕&quot;, &quot;Malgun Gothic&quot;, Dotum, applegothic, sans-serif, arial; vertical-align: baseline; outline: 0px; color: rgb(55, 55, 55); letter-spacing: -0.25px;">&nbsp; 6. 울릉도 숙박 및 기타 편의시설은 도시보다 굉장히 열악하므로 고객님의 협조과</div><div style="margin: 0px; padding: 0px; border: 0px; font-family: &quot;Nanum Gothic&quot;, NanumGothic, 나눔고딕, NanumGothicWeb, &quot;맑은 고딕&quot;, &quot;Malgun Gothic&quot;, Dotum, applegothic, sans-serif, arial; vertical-align: baseline; outline: 0px; color: rgb(55, 55, 55); letter-spacing: -0.25px;">&nbsp; &nbsp; &nbsp;이해가 절실히 필요합니다.&nbsp;<font color="#ff3366" style="margin: 0px; padding: 0px;">민박/모텔/대아리조트</font>로 나누어지며,&nbsp;<font color="#ff3366" style="margin: 0px; padding: 0px;">개인 세면도구(칫솔,수건,샴푸 등)를 준비</font>하셔야 합니다.<b style="margin: 0px; padding: 0px; border: 0px; vertical-align: baseline; outline: 0px;"></b></div><div style="margin: 0px; padding: 0px; border: 0px; font-family: &quot;Nanum Gothic&quot;, NanumGothic, 나눔고딕, NanumGothicWeb, &quot;맑은 고딕&quot;, &quot;Malgun Gothic&quot;, Dotum, applegothic, sans-serif, arial; vertical-align: baseline; outline: 0px; color: rgb(55, 55, 55); letter-spacing: -0.25px;"><b style="margin: 0px; padding: 0px; border: 0px; vertical-align: baseline; outline: 0px;"></b>&nbsp;</div><div style="margin: 0px; padding: 0px; border: 0px; font-family: &quot;Nanum Gothic&quot;, NanumGothic, 나눔고딕, NanumGothicWeb, &quot;맑은 고딕&quot;, &quot;Malgun Gothic&quot;, Dotum, applegothic, sans-serif, arial; vertical-align: baseline; outline: 0px; color: rgb(55, 55, 55); letter-spacing: -0.25px;"><b style="margin: 0px; padding: 0px; border: 0px; vertical-align: baseline; outline: 0px;">&nbsp; &lt; 갑작스런 기상악화 발생 시 예약내용 변경안내 &gt;</b></div><div style="margin: 0px; padding: 0px; border: 0px; font-family: &quot;Nanum Gothic&quot;, NanumGothic, 나눔고딕, NanumGothicWeb, &quot;맑은 고딕&quot;, &quot;Malgun Gothic&quot;, Dotum, applegothic, sans-serif, arial; vertical-align: baseline; outline: 0px; color: rgb(55, 55, 55); letter-spacing: -0.25px;">&nbsp; 1. 출발 전 : 주의보 및 선박 결항 시 전화로 행사 취소를 알려드리며, 전액 환불됩니다.</div><div style="margin: 0px; padding: 0px; border: 0px; font-family: &quot;Nanum Gothic&quot;, NanumGothic, 나눔고딕, NanumGothicWeb, &quot;맑은 고딕&quot;, &quot;Malgun Gothic&quot;, Dotum, applegothic, sans-serif, arial; vertical-align: baseline; outline: 0px; color: rgb(55, 55, 55); letter-spacing: -0.25px;">&nbsp; 2. 출발 후 : 묵호/포항 도착 후 선박결항 / 입항이 불가능할 경우 사용하신<font color="#ff3366" style="margin: 0px; padding: 0px;">실비공제 후 환불</font>됩니다.</div><div style="margin: 0px; padding: 0px; border: 0px; font-family: &quot;Nanum Gothic&quot;, NanumGothic, 나눔고딕, NanumGothicWeb, &quot;맑은 고딕&quot;, &quot;Malgun Gothic&quot;, Dotum, applegothic, sans-serif, arial; vertical-align: baseline; outline: 0px; color: rgb(55, 55, 55); letter-spacing: -0.25px;">&nbsp; 3. 체류 후 : 울릉도 출항(→묵호항/포항) 순서는 울릉도 입도 순서에 따르며,</div><div style="margin: 0px; padding: 0px; border: 0px; font-family: &quot;Nanum Gothic&quot;, NanumGothic, 나눔고딕, NanumGothicWeb, &quot;맑은 고딕&quot;, &quot;Malgun Gothic&quot;, Dotum, applegothic, sans-serif, arial; vertical-align: baseline; outline: 0px; color: rgb(55, 55, 55); letter-spacing: -0.25px;">&nbsp; &nbsp; &nbsp;예약 코스가 아닌 다른 항구 / 셔틀로 변경 출항 시 발생되는&nbsp;<font color="#ff3366" style="margin: 0px; padding: 0px;">차액금은 개별적으로 부담</font>하셔야 합니다.<b style="margin: 0px; padding: 0px; border: 0px; vertical-align: baseline; outline: 0px;"><font color="#ff0000" style="margin: 0px; padding: 0px;">&nbsp;</font></b></div><div style="margin: 0px; padding: 0px; border: 0px; font-family: &quot;Nanum Gothic&quot;, NanumGothic, 나눔고딕, NanumGothicWeb, &quot;맑은 고딕&quot;, &quot;Malgun Gothic&quot;, Dotum, applegothic, sans-serif, arial; vertical-align: baseline; outline: 0px; color: rgb(55, 55, 55); letter-spacing: -0.25px;"><b style="margin: 0px; padding: 0px; border: 0px; vertical-align: baseline; outline: 0px;"><font color="#ff0000" style="margin: 0px; padding: 0px;"><br></font></b></div></td>' + 
							'</tr></tbody></table><p><br></p><table border="0" cellpadding="0" cellspacing="0" style="border:1px solid #cccccc; border-left:0; border-bottom:0;" class="__se_tbl"><tbody><tr><td style="border-width: 0px 0px 1px 1px; border-bottom-style: solid; border-left-style: solid; border-bottom-color: rgb(204, 204, 204); border-left-color: rgb(204, 204, 204); border-image: initial; border-top-style: initial; border-top-color: initial; border-right-style: initial; border-right-color: initial; width: 717px; height: 52px; background-color: rgb(235, 235, 235);" class=""><p>&nbsp;<b style="font-size: 12pt;">연 락 처</b>&nbsp;</p></td></tr><tr><td style="border-width: 0px 0px 1px 1px; border-bottom-style: solid; border-left-style: solid; border-bottom-color: rgb(204, 204, 204); border-left-color: rgb(204, 204, 204); border-image: initial; border-top-style: initial; border-top-color: initial; border-right-style: initial; border-right-color: initial; width: 717px; height: 144px; background-color: rgb(255, 255, 255);" class=""><p>&nbsp;</p><div style="margin: 0px; padding: 0px; border: 0px; font-family: &quot;Nanum Gothic&quot;, NanumGothic, 나눔고딕, NanumGothicWeb, &quot;맑은 고딕&quot;, &quot;Malgun Gothic&quot;, Dotum, applegothic, sans-serif, arial; vertical-align: baseline; outline: 0px; color: rgb(129, 21, 205); letter-spacing: -0.25px;"><b style="margin: 0px; padding: 0px; border: 0px; vertical-align: baseline; outline: 0px;"><font color="#8115cd" style="margin: 0px; padding: 0px;">&nbsp; 국내사업부 대표번호 : 02-3455-0003<br></font><font color="#000000" style="margin: 0px; padding: 0px;">&nbsp; 담당자 :</font></b><font color="#000000" style="margin: 0px; padding: 0px;">&nbsp;<b style="margin: 0px; padding: 0px; border: 0px; vertical-align: baseline; outline: 0px;">조정희</b></font><b style="margin: 0px; padding: 0px; border: 0px; vertical-align: baseline; outline: 0px;"><font color="#000000" style="margin: 0px; padding: 0px;">&nbsp;사원<br></font><font color="#107ad8" style="margin: 0px; padding: 0px;">&nbsp; 직통번호</font></b><font color="#107ad8" style="margin: 0px; padding: 0px;">&nbsp;:&nbsp;</font><font color="#107ad8" style="margin: 0px; padding: 0px;"><b style="margin: 0px; padding: 0px; border: 0px; vertical-align: baseline; outline: 0px;">02-3455-0067</b></font></div><div style="margin: 0px; padding: 0px; border: 0px; font-family: &quot;Nanum Gothic&quot;, NanumGothic, 나눔고딕, NanumGothicWeb, &quot;맑은 고딕&quot;, &quot;Malgun Gothic&quot;, Dotum, applegothic, sans-serif, arial; vertical-align: baseline; outline: 0px; color: rgb(129, 21, 205); letter-spacing: -0.25px;"><font color="#107ad8" style="margin: 0px; padding: 0px;"><b style="margin: 0px; padding: 0px; border: 0px; vertical-align: baseline; outline: 0px;">&nbsp; 팩스 : 02-3783-3865<br></b></font><font color="#000000" style="margin: 0px; padding: 0px;">&nbsp; 메일 :&nbsp;<a href="mailto:zhao_hee@jautour.com" style="margin: 0px; padding: 0px; border: 0px; color: rgb(68, 68, 68); text-decoration-line: none;">zhao_hee@jautour.com</a></font><br><font color="#000000" style="margin: 0px; padding: 0px;"><b style="margin: 0px; padding: 0px; border: 0px; vertical-align: baseline; outline: 0px;">&nbsp; 문자 : #37833865</b></font></div><p><br></p></td></tr></tbody></table><p><br></p><table border="0" cellpadding="0" cellspacing="0" style="border:1px solid #cccccc; border-left:0; border-bottom:0;" class="__se_tbl"><tbody><tr><td style="border-width: 0px 0px 1px 1px; border-bottom-style: solid; border-left-style: solid; border-bottom-color: rgb(204, 204, 204); border-left-color: rgb(204, 204, 204); border-image: initial; border-top-style: initial; border-top-color: initial; border-right-style: initial; border-right-color: initial; width: 717px; height: 49px; background-color: rgb(235, 235, 235);" class=""><p><b><span style="font-size: 12pt;">&nbsp; 결제 안내</span></b></p></td></tr><tr><td style="border-width: 0px 0px 1px 1px; border-bottom-style: solid; border-left-style: solid; border-bottom-color: rgb(204, 204, 204); border-left-color: rgb(204, 204, 204); border-image: initial; border-top-style: initial; border-top-color: initial; border-right-style: initial; border-right-color: initial; width: 717px; height: 126px; background-color: rgb(255, 255, 255);" class=""><p style="margin-left: 0px;"><b style="color: rgb(55, 55, 55); font-family: &quot;Nanum Gothic&quot;, NanumGothic, 나눔고딕, NanumGothicWeb, &quot;맑은 고딕&quot;, &quot;Malgun Gothic&quot;, Dotum, applegothic, sans-serif, arial; letter-spacing: -0.25px; margin: 0px; padding: 0px; border: 0px; vertical-align: baseline; outline: 0px;"><font color="#107ad8" style="margin: 0px; padding: 0px;">&nbsp; ◇ 입금계좌 안내 신한은행 : 100-029-154721 예금주 (주)자유투어</font></b></p><div style="margin: 0px; padding: 0px; border: 0px; font-family: &quot;Nanum Gothic&quot;, NanumGothic, 나눔고딕, NanumGothicWeb, &quot;맑은 고딕&quot;, &quot;Malgun Gothic&quot;, Dotum, applegothic, sans-serif, arial; vertical-align: baseline; outline: 0px; color: rgb(55, 55, 55); letter-spacing: -0.25px;"><font color="#000000" style="margin: 0px; padding: 0px;">&nbsp; ◇ 예약후 계약금 100,000원을 결제하셔야 예약이 확정됩니다. 잔금은 출발 일주일 전까지 완납하셔야 합니다.</font></div><div style="margin: 0px; padding: 0px; border: 0px; font-family: &quot;Nanum Gothic&quot;, NanumGothic, 나눔고딕, NanumGothicWeb, &quot;맑은 고딕&quot;, &quot;Malgun Gothic&quot;, Dotum, applegothic, sans-serif, arial; vertical-align: baseline; outline: 0px; color: rgb(55, 55, 55); letter-spacing: -0.25px;"><b style="margin: 0px; padding: 0px; border: 0px; vertical-align: baseline; outline: 0px;"><font color="#e1568f" style="margin: 0px; padding: 0px;"></font></b>&nbsp;</div><div style="margin: 0px; padding: 0px; border: 0px; font-family: &quot;Nanum Gothic&quot;, NanumGothic, 나눔고딕, NanumGothicWeb, &quot;맑은 고딕&quot;, &quot;Malgun Gothic&quot;, Dotum, applegothic, sans-serif, arial; vertical-align: baseline; outline: 0px; color: rgb(55, 55, 55); letter-spacing: -0.25px;"><font color="#e42121" style="margin: 0px; padding: 0px;">&nbsp; ※취소수수료는 출발일 전으로 계산되기 때문에&nbsp;&nbsp;&nbsp;</font></div><div style="margin: 0px; padding: 0px; border: 0px; font-family: &quot;Nanum Gothic&quot;, NanumGothic, 나눔고딕, NanumGothicWeb, &quot;맑은 고딕&quot;, &quot;Malgun Gothic&quot;, Dotum, applegothic, sans-serif, arial; vertical-align: baseline; outline: 0px; color: rgb(55, 55, 55); letter-spacing: -0.25px;"><font color="#e42121" style="margin: 0px; padding: 0px;">&nbsp; 취소수수료가 적용되는 기간안에 오전 예약결제후 예약한 날 오후에 바로취소하셔도 취소수수료가 적용됩니다.<br><br></font></div></td></tr></tbody></table><p><br></p><table border="0" cellpadding="0" cellspacing="0" style="border:1px solid #cccccc; border-left:0; border-bottom:0;" class="__se_tbl"><tbody><tr><td style="border-width: 0px 0px 1px 1px; border-bottom-style: solid; border-left-style: solid; border-bottom-color: rgb(204, 204, 204); border-left-color: rgb(204, 204, 204); border-image: initial; border-top-style: initial; border-top-color: initial; border-right-style: initial; border-right-color: initial; width: 717px; height: 51px; background-color: rgb(235, 235, 235);" class=""><p><b style="font-size: 12pt;">&nbsp;여행자 보험정보</b></p></td></tr><tr><td style="border-width: 0px 0px 1px 1px; border-bottom-style: solid; border-left-style: solid; border-bottom-color: rgb(204, 204, 204); border-left-color: rgb(204, 204, 204); border-image: initial; border-top-style: initial; border-top-color: initial; border-right-style: initial; border-right-color: initial; width: 717px; height: 90px; background-color: rgb(255, 255, 255);" class=""><p>&nbsp;</p><div style="margin: 0px; padding: 0px; border: 0px; font-family: &quot;Nanum Gothic&quot;, NanumGothic, 나눔고딕, NanumGothicWeb, &quot;맑은 고딕&quot;, &quot;Malgun Gothic&quot;, Dotum, applegothic, sans-serif, arial; vertical-align: baseline; outline: 0px; color: rgb(55, 55, 55); letter-spacing: -0.25px;"><b style="margin: 0px; padding: 0px; border: 0px; vertical-align: baseline; outline: 0px;"><font color="#e42121" style="margin: 0px; padding: 0px;">&nbsp; ※2014년 8월 1일부터 출발하는 자사의 모든 국내여행상품은 개인정보 보호법</font></b></div><div style="margin: 0px; padding: 0px; border: 0px; font-family: &quot;Nanum Gothic&quot;, NanumGothic, 나눔고딕, NanumGothicWeb, &quot;맑은 고딕&quot;, &quot;Malgun Gothic&quot;, Dotum, applegothic, sans-serif, arial; vertical-align: baseline; outline: 0px; color: rgb(55, 55, 55); letter-spacing: -0.25px;"><b style="margin: 0px; padding: 0px; border: 0px; vertical-align: baseline; outline: 0px;"><font color="#e42121" style="margin: 0px; padding: 0px;">&nbsp; &nbsp; &nbsp;제 24조 2 (주민등록번호 처리의 제한) 에 의거하여 주민번호 수집이 불가함에 따라</font></b></div><div style="margin: 0px; padding: 0px; border: 0px; font-family: &quot;Nanum Gothic&quot;, NanumGothic, 나눔고딕, NanumGothicWeb, &quot;맑은 고딕&quot;, &quot;Malgun Gothic&quot;, Dotum, applegothic, sans-serif, arial; vertical-align: baseline; outline: 0px; color: rgb(55, 55, 55); letter-spacing: -0.25px;"><b style="margin: 0px; padding: 0px; border: 0px; vertical-align: baseline; outline: 0px;"><font color="#e42121" style="margin: 0px; padding: 0px;">&nbsp; &nbsp; &nbsp;여행자보험가입이 불포함으로 변경되었습니다</font></b></div><p><br></p></td></tr></tbody></table><p><br></p><p><br></p><table border="0" cellpadding="0" cellspacing="0" style="border:1px solid #cccccc; border-left:0; border-bottom:0;" class="__se_tbl"><tbody><tr><td style="border-width: 0px 0px 1px 1px; border-bottom-style: solid; border-left-style: solid; border-bottom-color: rgb(204, 204, 204); border-left-color: rgb(204, 204, 204); border-image: initial; border-top-style: initial; border-top-color: initial; border-right-style: initial; border-right-color: initial; width: 717px; height: 57px; background-color: rgb(235, 235, 235);" class=""><p>&nbsp;&nbsp;<b><span style="font-size: 12pt;">특별 약관</span></b></p></td></tr><tr><td style="border-width: 0px 0px 1px 1px; border-bottom-style: solid; border-left-style: solid; border-bottom-color: rgb(204, 204, 204); border-left-color: rgb(204, 204, 204); border-image: initial; border-top-style: initial; border-top-color: initial; border-right-style: initial; border-right-color: initial; background-color: rgb(255, 255, 255); width: 717px; height: 522px;"><p>&nbsp;</p><div style="margin: 0px; padding: 0px; border: 0px; font-family: &quot;Nanum Gothic&quot;, NanumGothic, 나눔고딕, NanumGothicWeb, &quot;맑은 고딕&quot;, &quot;Malgun Gothic&quot;, Dotum, applegothic, sans-serif, arial; vertical-align: baseline; outline: 0px; color: rgb(55, 55, 55); letter-spacing: -0.25px;"><font color="#e42121" style="margin: 0px; padding: 0px;"><b style="margin: 0px; padding: 0px; border: 0px; vertical-align: baseline; outline: 0px;">&nbsp; ★예약취소료 규정★</b></font></div><div style="margin: 0px; padding: 0px; border: 0px; font-family: &quot;Nanum Gothic&quot;, NanumGothic, 나눔고딕, NanumGothicWeb, &quot;맑은 고딕&quot;, &quot;Malgun Gothic&quot;, Dotum, applegothic, sans-serif, arial; vertical-align: baseline; outline: 0px; color: rgb(55, 55, 55); letter-spacing: -0.25px;">&nbsp;</div><div style="margin: 0px; padding: 0px; border: 0px; font-family: &quot;Nanum Gothic&quot;, NanumGothic, 나눔고딕, NanumGothicWeb, &quot;맑은 고딕&quot;, &quot;Malgun Gothic&quot;, Dotum, applegothic, sans-serif, arial; vertical-align: baseline; outline: 0px; color: rgb(55, 55, 55); letter-spacing: -0.25px;">&nbsp; [하단 취소료규정은 당사 국내여행 표준약관보다 우선 적용되는 특약규정입니다.]</div><div style="margin: 0px; padding: 0px; border: 0px; font-family: &quot;Nanum Gothic&quot;, NanumGothic, 나눔고딕, NanumGothicWeb, &quot;맑은 고딕&quot;, &quot;Malgun Gothic&quot;, Dotum, applegothic, sans-serif, arial; vertical-align: baseline; outline: 0px; color: rgb(55, 55, 55); letter-spacing: -0.25px;">&nbsp; 공정거래위원회 소비자분쟁 해결기준과 별도로 진행되는 규정입니다. 예약 취소 시&nbsp;<u style="margin: 0px; padding: 0px; border: 0px;"><font color="#e42121" style="margin: 0px; padding: 0px;">국내여행 약관</font></u></div><div style="margin: 0px; padding: 0px; border: 0px; font-family: &quot;Nanum Gothic&quot;, NanumGothic, 나눔고딕, NanumGothicWeb, &quot;맑은 고딕&quot;, &quot;Malgun Gothic&quot;, Dotum, applegothic, sans-serif, arial; vertical-align: baseline; outline: 0px; color: rgb(55, 55, 55); letter-spacing: -0.25px;"><u style="margin: 0px; padding: 0px; border: 0px;"><font color="#e42121" style="margin: 0px; padding: 0px;">&nbsp; 제5조(특약)에&nbsp;</font></u><u style="margin: 0px; padding: 0px; border: 0px;"><font color="#e42121" style="margin: 0px; padding: 0px;">의한 자체 특별약관이 적용됨을&nbsp;</font></u>양지하여 주시기 바랍니다.</div><div style="margin: 0px; padding: 0px; border: 0px; font-family: &quot;Nanum Gothic&quot;, NanumGothic, 나눔고딕, NanumGothicWeb, &quot;맑은 고딕&quot;, &quot;Malgun Gothic&quot;, Dotum, applegothic, sans-serif, arial; vertical-align: baseline; outline: 0px; color: rgb(55, 55, 55); letter-spacing: -0.25px;">&nbsp;</div><div style="margin: 0px; padding: 0px; border: 0px; font-family: &quot;Nanum Gothic&quot;, NanumGothic, 나눔고딕, NanumGothicWeb, &quot;맑은 고딕&quot;, &quot;Malgun Gothic&quot;, Dotum, applegothic, sans-serif, arial; vertical-align: baseline; outline: 0px; color: rgb(55, 55, 55); letter-spacing: -0.25px;">&nbsp; &nbsp;▶ 인터넷상에서 예약/결제 취소 및 변경은 불가능하오니, 예약/결제 취소나 여행자정보 변경을 원하시면</div><div style="margin: 0px; padding: 0px; border: 0px; font-family: &quot;Nanum Gothic&quot;, NanumGothic, 나눔고딕, NanumGothicWeb, &quot;맑은 고딕&quot;, &quot;Malgun Gothic&quot;, Dotum, applegothic, sans-serif, arial; vertical-align: baseline; outline: 0px; color: rgb(55, 55, 55); letter-spacing: -0.25px;">&nbsp; &nbsp; &nbsp; &nbsp;반드시 예약담당자에게 연락하여 주시기바랍니다.</div><div style="margin: 0px; padding: 0px; border: 0px; font-family: &quot;Nanum Gothic&quot;, NanumGothic, 나눔고딕, NanumGothicWeb, &quot;맑은 고딕&quot;, &quot;Malgun Gothic&quot;, Dotum, applegothic, sans-serif, arial; vertical-align: baseline; outline: 0px; color: rgb(55, 55, 55); letter-spacing: -0.25px;">&nbsp;</div><div style="margin: 0px; padding: 0px; border: 0px; font-family: &quot;Nanum Gothic&quot;, NanumGothic, 나눔고딕, NanumGothicWeb, &quot;맑은 고딕&quot;, &quot;Malgun Gothic&quot;, Dotum, applegothic, sans-serif, arial; vertical-align: baseline; outline: 0px; color: rgb(55, 55, 55); letter-spacing: -0.25px;">&nbsp; ※ 여행자의 취소요청시 하기의 경우에 해당시 여행약관에 의거 취소료가 부과됩니다.</div><div style="margin: 0px; padding: 0px; border: 0px; font-family: &quot;Nanum Gothic&quot;, NanumGothic, 나눔고딕, NanumGothicWeb, &quot;맑은 고딕&quot;, &quot;Malgun Gothic&quot;, Dotum, applegothic, sans-serif, arial; vertical-align: baseline; outline: 0px; color: rgb(55, 55, 55); letter-spacing: -0.25px;">&nbsp;</div><div style="margin: 0px; padding: 0px; border: 0px; font-family: &quot;Nanum Gothic&quot;, NanumGothic, 나눔고딕, NanumGothicWeb, &quot;맑은 고딕&quot;, &quot;Malgun Gothic&quot;, Dotum, applegothic, sans-serif, arial; vertical-align: baseline; outline: 0px; color: rgb(55, 55, 55); letter-spacing: -0.25px;"><span style="margin: 0px; padding: 0px; vertical-align: baseline; outline: 0px; display: block; color: rgb(255, 0, 153);"><b style="margin: 0px; padding: 0px; border: 0px; vertical-align: baseline; outline: 0px;">&nbsp; ■ 예약 변경 및 취소시 수수료에 대한 규정 ■&nbsp;<br><br></b></span><b style="margin: 0px; padding: 0px; border: 0px; vertical-align: baseline; outline: 0px;"><span style="margin: 0px; padding: 0px; vertical-align: baseline; outline: 0px; display: block; color: rgb(255, 102, 0);">&nbsp; 1.비수기 일반규정</span></b><span style="margin: 0px; padding: 0px; vertical-align: baseline; outline: 0px; display: block; color: rgb(255, 102, 0);"><br></span>&nbsp; - 출발 7일전 취소시 총 여행경비 10%공제 후 환불<br>&nbsp; - 출발 5일전 취소시 총 여행경비 20%공제 후 환불<br>&nbsp; - 출발 3일전 취소시 총 여행경비 30% 공제후 환불<br>&nbsp; - 출발 1일전 취소시 총 여행경비 50%공제 후 환불<br>&nbsp; - 당일 취소시 여행경비 환불 불가<br><br><b style="margin: 0px; padding: 0px; border: 0px; vertical-align: baseline; outline: 0px;"><span style="margin: 0px; padding: 0px; vertical-align: baseline; outline: 0px; display: block; color: rgb(255, 102, 0);">&nbsp; 2.연휴 및 성수기 특별규정<br></span></b>&nbsp; - 출발 15일전 취소시 총 여행경비 30%공제 후 환불<br>&nbsp; - 출발 10일전 취소시 총 여행경비 40%공제 후 환불<br>&nbsp; - 출발 07일전 취소시 총 여행경비 50%공제 후 환불<br>&nbsp; - 출발 04일전 취소시 총 여행경비 70%공제 후 환불<br>&nbsp; - 출발 03일전 ~ 당일 취소시 여행경비 환불 불가&nbsp;</div><p><br></p></td></tr></tbody></table><p><br></p><br>';
							oEditors.getById["ir1"].exec("PASTE_HTML", [sHTML]);
						},
						fCreator: "createSEditor2"
					});
					
					function pasteHTML(filepath) {
						// textarea에 이미지를 넣어줍니다
						var sHTML = '<img src="<%=request.getContextPath()%>/upload/'+filepath+'">';
					    oEditors.getById["ir1"].exec("PASTE_HTML", [sHTML]);
					}
					
					// 글자체, 크기 기본 셋팅
					function setDefaultFont() {
						var sDefaultFont = '궁서';
						var nFontSize = 24;
						oEditors.getById["ir1"].setDefaultFont(sDefaultFont, nFontSize);
					}
	
					// 글쓰기 버튼 클릭 이벤트
					$("#fr").submit(function(){
// 					function frsubmit()
// 					{
						var file1 = $("#file1").val();
						var file2 = $("#file2").val();
						var file3 = $("#file3").val();
						var file4 = $("#file4").val();
						var file5 = $("#file5").val();
						
						var fileArr = [file1, file2, file3, file4, file5];
						
						//첫번쨰 이미지 필수 등록
						if(file1 == "")
						{
							alert("첫번째 이미지는 필수로 넣어주세요");
							return false;
						}
						// 파일 등록 확장자로 제한
						for(var i = 0; i < fileArr.length; i++)
						{
							var file = fileArr[i].substring(fileArr[i].lastIndexOf(".") + 1)
	
					 		if (file != "jpg" && file != "png" && file != "gif" && 
					 				file != "JPG" && file != "PNG" && file != "GIF" && file != "" && file != null)
					 		{
					 			alert("jpg, png, gif 파일만 업로드 가능합니다");
					 			return false;
					 		}
						}
						
						var content = oEditors.getById["ir1"].getIR(); // Edit에 쓴 내용을 content 변수에 저장    값 : <br>
						
						if (content == "<br>")  // 빈공간 값 <br>
						{
							alert("글을 입력해주세요");  // 메시지 띄움
							return false;
						}
						else // 글내용 있을 시
						{
							oEditors.getById["ir1"].exec("UPDATE_CONTENTS_FIELD", []); // Edit에 쓴 내용을 textarea에 붙여넣어준다
	// 					    $("#fr").submit();  // form을 submit 시킨다
						}
						
				 		
						var chk = 0;
						$.ajax({   
							type:"post",
							url:"./PackDateAddChk.po",
							async: false,
							data:{
//				 				제목, 날짜
								subject:$("#subject").val(),
								date:$("#date_from").val()								
							},
							success:function(data)
							{
								if (data == 1)
								{
									alert("이미 추가된 상품입니다\n날짜를 추가하려면 해당상품에서 [날짜편집]을 이용해주세요");
									r = false;
								}
								else
								{
									r = true;
								}
							}
						});
							
						return r;
						
						
					});
// 					}
				</script>
			</div>
		</div>
		<input type="submit" id="save" value="글쓰기">
		<input type="button" value="취소" onclick="history.back()">
		</form>
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