<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="net.member.db.*"%>
    <%@ page import="java.util.List" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="./js/HuskyEZCreator.js" charset="utf-8"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="./js/jquery-3.2.0.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>

<script>
	jQuery(document).ready(function($){
		
		$("#txt_prodStart").datepicker();
	    $('img.ui-datepicker-trigger').attr('align', 'absmiddle');
	    
	    $('#aa').hide();
		$('#aa2').hide();
		$('#aa3').hide();
		$('#aa4').hide();	
	});
	
 
</script>
<script type="text/javascript">	

 
	

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
	
	
</script>


</head>
<%
		request.setCharacterEncoding("utf-8");
		List productList = (List) request.getAttribute("productList");
		List productList2 = (List) request.getAttribute("productList2");
		List CategoryList = (List) request.getAttribute("CategoryList");
		//디비 객체 생성 BoardDAO bdao
		// int count = getBoardCount() 메서드호출 count(*)
		ProductBean pb = new ProductBean();
		ProductDAO pdao = new ProductDAO();
		CategoryBean cb = new CategoryBean();
		CategoryDAO cdao = new CategoryDAO();
	%>
<body>
<!-- 왼쪽 메뉴 -->
<jsp:include page="../inc/leftMenu.jsp"></jsp:include>
<!-- 왼쪽 메뉴 -->

<div id="wrap">
	<div id="article_head">
		<div id="article_title"><img src="./img/travel2.png" width="30px" style="margin-right: 8px; vertical-align: bottom;"> 상품페이지 등록</div>
	<div class="empty"></div>
</div>
<div id="wrap_pack">
<div id="wrap_pack_detail">
	<div>
		<form action="./ProductWriteAction.bo" id="fr" method="post" enctype="multipart/form-data">
			<table border="1">
				<tr>
					<td>상품명</td>
					<td><input type="type" name="name">
						</td>
				</tr>
				
				
				<tr >
					<td>글제목</td>
					<td><input type="type" name="subject"></td>	
				</tr>
				<tr>
				<td>카테고리</td>
				<td><select name="car_num">
						<option value="">선택하세요</option>
						<%
							for (int i = 0; i < productList2.size(); i++) {

								cb = (CategoryBean) productList2.get(i);
						%>
						<option value="<%=cb.getCar_num()%>"><%=cb.getCar_name()%></option>
						<%
							}
						%>
				</select></td>
				</tr>
				<tr>
					<td>타입</td>
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
					<td>지역</td>
					<td>
						<select id="area" name="area">
							<option value="">선택하세요</option>
							<%
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
			
				<tr id="select6"> 
					<td>가격</td>
					<td><input type="text" id="cost" name="cost" required="yes"></td>
				</tr>
				<tr>
					<td>색깔</td>
					<td><input type="type" name="color"></td>
				</tr>
				<tr>
					<td>사이즈</td>
					<td><input type="type" name="size"></td>
				</tr>
				<tr>
					<td>재고</td>
					<td><input type="type" name="stock"></td>
				</tr>
				<tr id = "select5">
					<td>인트로</td>
					<td><input type="type" name="intro"></td>
				</tr>
				
				
				<tr id = "select3">
					<td>글내용</td>
					<td style="width:770px; height:500px;">
					<textarea name="content" id="ir1" rows="10" cols="100" style="width:770px; height:500px; display:none;"></textarea>
					</td>
				</tr>
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
					var sHTML = '<img src="/TeamK/upload/2017_06_13_115706.png"><img src="/TeamK/upload/2017_06_13_115711.png"><br>';
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
//					    $("#fr").submit();  // form을 submit 시킨다
				}
			});
	
</script>
				
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