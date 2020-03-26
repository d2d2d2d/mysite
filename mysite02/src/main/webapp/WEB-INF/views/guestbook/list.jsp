<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/guestbook.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<jsp:include page="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<form action="${pageContext.servletContext.contextPath }/guestbook" method="post">
					<input type="hidden" name="a" value="insert">
					<table>
						<tr>
							<td>이름</td><td><input type="text" name="name"></td>
							<td>비밀번호</td><td><input type="password" name="pass"></td>
						</tr>
						<tr>
							<td colspan=4><textarea name="content" id="content"></textarea></td>
						</tr>
						<tr>
							<td colspan=4 align=right><input type="submit" name="a" VALUE=" 확인 "></td>
						</tr>
					</table>
				</form>
				<br>
		<table width=510 border=1>
		<c:forEach items="${list }" var='vo'>
			<tr>
				<td>[${vo.getNo() }]</td>
				<td>${vo.getName() }</td>
				<td>${vo.getRegDate() }</td>
				<td><a href="${pageContext.servletContext.contextPath }/guestbook?a=deleteform&no=${vo.getNo() }">삭제</a></td>
			</tr>
			<tr>
				<td style="white-space:pre;" colspan=4>${vo.getContents() }</td>
			</tr>
			</c:forEach>
		</table>
		<br>
			</div>
		</div>
		<jsp:include page="/WEB-INF/views/includes/navigation.jsp" />
		<jsp:include page="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>