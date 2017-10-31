<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="./js/jquery-3.2.0.js"></script>
<script type="text/javascript">
	function test(num){
		var arr = $('#test').find('tr');
		var check = 1;
		for(var i = 0; i<arr.length;i++){
			if($('#test tr').eq(i).attr('id')=="test"+num){
				
			}
		}
	}
	function test2(){
		alert($('table').find('.tstock').length);
		var sum = 0;
		for(var i = 0; i<$('table').find('.tstock').length; i++){
			var ints = $('table .tstock').eq(i).text().replace(",","");
			ints = ints.replace("원","");
			sum += parseInt(ints);
		}
		$('#ss').html(sum+"원");
	}
	function test3(){
		alert('1');
		$.getJSON('./product/json4.jsp?num=1&color=red',function(data){
			alert('2');
			$.each(data,function(index,test){
				alert('3');
			});
		});
	}
</script>
</head>
<body>

	<div id = "ss"></div>
	<table>
		<tr>
			<td class ="tstock">1원</td>
		</tr>
		<tr>
			<td class ="tstock">4원</td>
		</tr>
		<tr>
			<td class ="tstock">1,0원</td>
		</tr>
		<tr>
			<td class ="tstock">3,3원</td>
		</tr>
	</table>
	<input type = "button" value = "test2" onclick = "test3()">
</body>
</html>