 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="net.member.db.*"%>
<%@ page import="java.util.*"%>
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
		
	    
	    
	 	// 파일 크기 제한 5MB
		var maxSize = 5*1024*1024;
		$("#file11").bind('change', function() {   // 파일 첨부 할 때 함수 호출
			var filesize_temp = (this.files[0].size)/1048576;
			var filesize = filesize_temp.toString().substring(0, 4);
			
			if (this.files[0].size > maxSize)
			{
				alert("5MB 이하의 이미지를 선택해주세요  (" + filesize + "MB)");
				$("#file1").val("");
			}
		});
		
		$("#file22").bind('change', function() {   // 파일 첨부 할 때 함수 호출
			var filesize_temp = (this.files[0].size)/1048576;
			var filesize = filesize_temp.toString().substring(0, 4);
			
			if (this.files[0].size > maxSize)
			{
				alert("5MB 이하의 이미지를 선택해주세요  (" + filesize + "MB)");
				$("#file2").val("");
			}
		});
		
		$("#file33").bind('change', function() {   // 파일 첨부 할 때 함수 호출
			var filesize_temp = (this.files[0].size)/1048576;
			var filesize = filesize_temp.toString().substring(0, 4);
			
			if (this.files[0].size > maxSize)
			{
				alert("5MB 이하의 이미지를 선택해주세요  (" + filesize + "MB)");
				$("#file3").val("");
			}
		});
		
		$("#file44").bind('change', function() {   // 파일 첨부 할 때 함수 호출
			var filesize_temp = (this.files[0].size)/1048576;
			var filesize = filesize_temp.toString().substring(0, 4);
			
			if (this.files[0].size > maxSize)
			{
				alert("5MB 이하의 이미지를 선택해주세요  (" + filesize + "MB)");
				$("#file4").val("");
			}
		});
		
		$("#file55").bind('change', function() {   // 파일 첨부 할 때 함수 호출
			var filesize_temp = (this.files[0].size)/1048576;
			var filesize = filesize_temp.toString().substring(0, 4);
			
			if (this.files[0].size > maxSize)
			{
				alert("5MB 이하의 이미지를 선택해주세요  (" + filesize + "MB)");
				$("#file5").val("");
			}
		});
	});
	
	
	function people_Calc2(str){			
		$(document).ready(function(){
			var val1 = $("#color option:selected").val();
			var val2 = str
			alert(val1);

			$("#size").find("option").remove();
			$('#size').append("<option  value = '' >선택하세요</option>");
			$.getJSON('./product/json3.jsp?num='+val2+'&color='+val1,function(data){
				$.each(data,function(index,qwer){
				//body태그 추가 key:value	
					$('#size').append("<option value=" + qwer.size + ">" + qwer.size + "</option");
				});
			});
		});
	}
				
				function people_Calc(num){			
					$(document).ready(function(){
						var val1 = $("#adult option:selected").val();
						var val2 = $("#child option:selected").val();
						
						if (num == 1)
						{
							$("#child").find("option").remove();
							for (i = 0; i <= val1; i++)
							{
								$('#child').append("<option value=" + i + ">" + i + "</option");
							}
						}
						
						$('#p').html(val1 * 200000 + val2 * 100000);
					});
				}
		
				function people_Calc3(str){			
					$(document).ready(function(){
						var val1 = $("#size option:selected").val();
						var val2 = $("#color option:selected").val();
						var val3 = str
						
					
						$("#stock2").remove();
						$.getJSON('./product/json4.jsp?color='+val2+'&size='+val1+'&num='+str,function(data){
							$.each(data,function(index,qwer){
							//body태그 추가 key:value	
							alert(val1);
							alert(val2);
							alert(qwer.stock);
								$('#stock').append("<input type='type' id = 'stock2' name='stock' value ='"+qwer.stock+"'>" );
							});
						});
					

					
					});
				}
	
	
</script>


<style type="text/css">

img.ui-datepicker-trigger
{
	cursor : pointer;
	margin-left : 5px;
}


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
request.setCharacterEncoding("utf-8");
List productList = (List) request.getAttribute("productList");
List productList2 = (List) request.getAttribute("productList2");
List productList3 = (List) request.getAttribute("productList3");
List CategoryList = (List) request.getAttribute("CategoryList");
ProductBean pb2= (ProductBean)request.getAttribute("pb");
String pageNum = request.getParameter("pageNum");
//디비 객체 생성 BoardDAO bdao
// int count = getBoardCount() 메서드호출 count(*)
ProductBean pb = new ProductBean();
ProductDAO pdao = new ProductDAO();
CategoryBean cb = new CategoryBean();
CategoryDAO cdao = new CategoryDAO();
%>

</head>
<body>
<!-- 왼쪽 메뉴 -->
<jsp:include page="../inc/leftMenu.jsp"></jsp:include>
<!-- 왼쪽 메뉴 -->
<div id="wrap">
<div id="article_head">
		<div id="article_title"><img src="./img/travel2.png" width="30px" style="margin-right: 8px; vertical-align: bottom;">상품페이지수정</div>
	<div class="empty"></div>
	</div>
<div id="wrap_pack">
	<div id="wrap_pack_detail">
	<div>
		<form action="./ProductUpdateAction.bo?&pageNum=<%=pageNum%>" id="fr" method="post" enctype="multipart/form-data">
			<table>
				<tr>
					<td>상품명</td>
					<input type="hidden" name="backname" value="<%=pb2.getName()%>">
					<input type="hidden" name="color" value="<%=pb2.getColor()%>">
					<input type="hidden" name="size" value="<%=pb2.getSize()%>">
					<input type="hidden" name="stock" value="<%=pb2.getStock()%>">
					<input type="hidden" name="num" value="<%=pb2.getNum()%>">
					<td><input type="type" name="name" value="<%=pb2.getName()%>"></td>
				</tr>
				<tr>
					<td>글제목</td>
					<td><input type="type" name="subject" value="<%=pb2.getSubject()%>"></td>
				</tr>
				<tr>
					<td>카테고리</td>
					<td>
						<select id="area" name="car_num">
							<option value="">선택하세요</option>
							<%
								
							for (int i = 0; i < productList2.size(); i++) {

								cb = (CategoryBean) productList2.get(i);
							%>	
								<option value="<%=cb.getCar_num()%>" <%if(pb2.getCar_num()==(cb.getCar_num())) {%> selected <%}%>><%=cb.getCar_name() %></option>
							<%
								}
							%>
						</select>
					</td>
				</tr>
				
				<tr>
						<td>타입</td>
						<td>
							<select id="type" name="type">
								<option value="city" <%="city".equals(pb2.getType()) ? "selected" : ""%>>도시</option>
								<option value="mount" <%="mount".equals(pb2.getType()) ? "selected" : ""%>>산</option>
								<option value="sea" <%="sea".equals(pb2.getType()) ? "selected" : ""%>>바다</option>
								<option value="country" <%="sea".equals(pb2.getType()) ? "selected" : ""%>>농촌</option>
							</select>
						</td>
					</tr>
				<tr>
					<td>지역</td>
					<td>
						<select id="area" name="area">
							<option value="">선택하세요</option>
							<%
								
							for (int i = 0; i < CategoryList.size(); i++) {

								cb = (CategoryBean) CategoryList.get(i);
							%>	
								<option value="<%=cb.getCar_name()%>" <%if(pb2.getArea().equals(cb.getCar_name())) {%> selected <%}%>><%=cb.getCar_name()%></option>
							<%
								}
							%>
						</select>
					</td>
	
				<tr>
					<td>인트로</td>
					<td><input type="type" name="intro" value="<%=pb2.getIntro()%>"></td>
				</tr>
				
				<tr>
					<td>글내용</td>
					<td id="content_write"><textarea name="content" id="ir1" rows="10" cols="100" ></textarea></td>
				</tr>
				
				<tr>
					<td>이미지첨부</td>
					<td>
						<input type="text" name="file1" id="file1" value="<%=pb2.getImg()%>" readonly style="width: 300px;">
												
						<input type="button" value="파일수정" class="dup" id="button" onclick="document.getElementById('file11').click();">
						
						<input type="button" value="파일삭제" class="dup" id="button" onclick="document.getElementById('file1').value='';">
						
						<input type="file" size="30" name="file1" id="file11" style="display: none;"
						onchange="document.getElementById('file1').value=this.value;" />
					</td>
				</tr>
				<script type="text/javascript">
					var oEditors = [];
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
							//예제 코드 로딩이 완료되면 본문에 삽입되는 내용
					 		var sHTML = '<%=pb2.getContent() %>';
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
		
					
					// 글수정 버튼 클릭 이벤트
					$("#fr").submit(function(){
						var file1 = $("#file1").val();
						var file2 = $("#file2").val();
						var file3 = $("#file3").val();
						var file4 = $("#file4").val();
						var file5 = $("#file5").val();
						
						var fileArr = [file1, file2, file3, file4, file5];
						
						if(file1 == "")
						{
							alert("첫번째 이미지는 필수로 넣어주세요");
							return false;
						}
	
						
						for(var i = 0; i < fileArr.length; i++)
						{
							var file = fileArr[i].substring(fileArr[i].lastIndexOf(".") + 1)
	
					 		if (file != "jpg" && file != "png" && file != "gif" && 
					 				file != "JPG" && file != "PNG" && file != "GIF" && file != "" && file != "null")
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
					});
					
				</script>
				<tr>
					<td>이미지첨부</td>
					<td>
						<input type="text" name="file2" id="file2" value="<%=pb2.getImg2()%>" readonly style="width: 300px;">
						
						<input type="button" value="파일수정" class="dup" id="button" onclick="document.getElementById('file22').click();">
						
						<input type="button" value="파일삭제" class="dup" id="button" onclick="document.getElementById('file2').value='';">
						
						<input type="file" size="30" name="file2" id="file22" style="display: none;"
						onchange="document.getElementById('file2').value=this.value;" />
					</td>
				</tr>
				<tr>
					<td>이미지첨부</td>
					<td>
						<input type="text" name="file3" id="file3" value="<%=pb2.getImg3()%>" readonly style="width: 300px;">
						
						<input type="button" value="파일수정" class="dup" id="button" onclick="document.getElementById('file33').click();"
						onchange="document.getElementById('file3').value=this.value;">
						
						<input type="button" value="파일삭제" class="dup" id="button" onclick="document.getElementById('file3').value='';">
						
						<input type="file" size="30" name="file3" id="file33" style="display: none;"
						onchange="document.getElementById('file3').value=this.value;" />
					</td>
				</tr>
				<tr>
					<td>이미지첨부</td>
					<td>
						<input type="text" name="file4" id="file4" value="<%=pb2.getImg4()%>" readonly style="width: 300px;">
						
						<input type="button" value="파일수정" class="dup" id="button" onclick="document.getElementById('file44').click();"
						onchange="document.getElementById('file4').value=this.value;">
						
						<input type="button" value="파일삭제" class="dup" id="button" onclick="document.getElementById('file4').value='';">
						
						<input type="file" size="30" name="file4" id="file44" style="display: none;"
						onchange="document.getElementById('file4').value=this.value;" />
					</td>
				</tr>
				<tr>
					<td>이미지첨부</td>
					<td>
						<input type="text" name="file5" id="file5" value="<%=pb2.getImg5()%>" readonly style="width: 300px;">
						
						<input type="button" value="파일수정" class="dup" id="button" onclick="document.getElementById('file55').click();"
						onchange="document.getElementById('file5').value=this.value;">
						
						<input type="button" value="파일삭제" class="dup" id="button" onclick="document.getElementById('file5').value='';">
						
						<input type="file" size="30" name="file5" id="file55" style="display: none;"
						onchange="document.getElementById('file5').value=this.value;" >
					</td>
				</tr>
				
			</table>
			</div>
			</div>
			<input type="submit" value="글수정" id="modify">
			<input type="button" value="취소" onclick="history.go(-1)">
		</form>
	</div>
</div>
<!-- 오른쪽 메뉴 -->
<jsp:include page="../inc/rightMenu.jsp"></jsp:include>
<!-- 오른쪽 메뉴 -->
</body>
</html>