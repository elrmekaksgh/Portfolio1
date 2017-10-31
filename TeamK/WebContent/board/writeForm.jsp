<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="./css/inc.css" rel="stylesheet" type="text/css">
<link href="./css/subpage.css" rel="stylesheet" type="text/css">
<%String id = (String)session.getAttribute("id");%>
<!-- Smart Editor -->
<script src="./js/jquery-3.2.0.js" type="text/javascript"></script>
<script type="text/javascript" src="./js/HuskyEZCreator.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>./photo_uploader/plugin/hp_SE2M_AttachQuickPhoto.js" charset="utf-8"></script>
<!-- Smart Editor -->
</head>
<body>
	<!--왼쪽 메뉴 -->
	<div>
		<jsp:include page="../inc/leftMenu.jsp"></jsp:include>
	</div>
	<!--왼쪽 메뉴 -->
	<div id="wrap">
		<div id="article_head">
			<div id="article_title"><img src="./img/review2.png" width="23px" style="margin-right: 8px; vertical-align: bottom;">리뷰 작성</div>
			<div class="empty"></div>
			<div id="article_script">상품이나 패키지 후기를 쓰는 곳 입니다.</div>
		</div>
		<div id="clear"></div>
		<article>
		<div id="board_write">
<form action="./BoardWriteAction.bo" method="post" name="fr" enctype="multipart/form-data">
<input type="hidden" value="1" name="type">
<input type="hidden" name="id" value="<%=id%>">
<div style="float: left;">글머리
<select name="select" id="type_select" style="margin-left: 68px;">
	<option value="기타">기타</option>
    <option value="상품리뷰">상품리뷰</option>
    <option value="패키지리뷰">패키지리뷰</option>
</select>
</div>
<div id="clear"></div>
<br>
<label for="subject">제목</label><input type="text" name="subject" id="subject" maxlength="40"><br><br>
<textarea id="ir1" rows="30" cols="80" name="content"></textarea><br><br>
<label for="file1">첨부파일1</label><input type="file" name="file1" id="file1"><br>
<label for="file2">첨부파일2</label><input type="file" name="file2" id="file2"><br>
<label for="file3">첨부파일3</label><input type="file" name="file3" id="file3"><br>
<label for="file4">첨부파일4</label><input type="file" name="file4" id="file4"><br>
<label for="file5">첨부파일5</label><input type="file" name="file5" id="file5"><br>
<div class="clear"></div><br>
<input type="submit" id="save" value="글쓰기" onclick="submitContents(this);">
<input type="button" value="글목록" onclick="location.href='./BoardList.bo?pageNum=1'">
</form>
</div>
</article>
	</div>
	<jsp:include page="../inc/footer.jsp"></jsp:include>
	<!--오른쪽 메뉴 -->
	<div>
		<jsp:include page="../inc/rightMenu.jsp"></jsp:include>
	</div>
	<!--오른쪽 메뉴 -->
</body>
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
	}, 
	fCreator: "createSEditor2"
});


function pasteHTML(fname) {
	var sHTML = '<img src="<%=request.getContextPath()%>/upload/'+ fname +'">';
	//alert(sHTML);
    oEditors.getById["ir1"].exec("PASTE_HTML", [sHTML]);
}

function showHTML() {
	var sHTML = oEditors.getById["ir1"].getIR();
	alert(sHTML);
}

$("#save").click(function(){

    var content = oEditors.getById["ir1"].getIR(); // Edit에 쓴 내용을 content 변수에 저장    값 : <br>
    
    if (document.fr.subject.value == ""){
		alert("제목을 입력하세요");
		document.fr.subject.focus();
		return false;
	} 
    
    if (content == "<br>")  // 빈공간 값 <br>
    {
       alert("글을 입력해주세요");  // 메시지 띄움
       return false;
    }else // 글내용 있을 시
    {
       oEditors.getById["ir1"].exec("UPDATE_CONTENTS_FIELD", []); // Edit에 쓴 내용을 textarea에 붙여넣어준다
        $("#fr").submit();  // form을 submit 시킨다
    }
 });
</script>
</html>