<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>/file/upload.jsp</h1>
	${map }
	
	<h2>아이디 : ${map.id }</h2>
	<h2>이름 : ${map.name }</h2> 
	
	<c:forEach var="fileName" items="${map.fileList }">
		<h2>파일명 : 
				<a href="/file/download?fileName=${fileName }">${fileName }</a>
		</h2>
		
		<img src="/file/download?fileName=${fileName }">
	</c:forEach>
	<br>
	
	<a href="/file/form">파일 업로드 페이지로 이동</a>
</body>
</html>