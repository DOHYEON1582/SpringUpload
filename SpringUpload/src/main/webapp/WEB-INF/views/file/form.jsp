<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- jquery 라이브러리 -->
<script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.min.js"></script>
<script type="text/javascript">
	var cnt = 1;
	
	function addFile(){
		//alert("첨부파일 추가");
		// dFile div에 파일 업로드 input 태그 추가
		$(".dFile").append("<input type='file' name='file"+cnt+"'><br>");
		
		cnt++;
	}



</script>

</head>
<body>
	<h1>/file/form.jsp</h1>
	
	<fieldset>
		<legend>파일 업로드</legend>
		<form action="/file/upload" method="post" enctype="multipart/form-data">
			아이디 : <input type="text" name="id"><br>
			이름 : <input type="text" name="name"><br>
			
			<hr>
			<div class="dFile"></div>
			
			<input type="button" value="첨부파일 추가" onclick="addFile();">
			
			<!-- 첨부파일 : <input type="file" name="file"><hr> -->
		
			<input type="submit" value="파일 업로드">
		</form>
		
	</fieldset>
	
	

</body>
</html>