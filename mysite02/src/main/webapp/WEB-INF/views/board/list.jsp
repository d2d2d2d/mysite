<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link
	href="${pageContext.servletContext.contextPath }/assets/css/board.css"
	rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="board">
				<form id="search_form" action="${pageContext.request.contextPath }/board?a=search" method="post">
					<input type="text" id="kwd" name="kwd" value=""> <input
						type="submit" value="찾기">
				</form>

				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					<c:forEach items="${list }" var='vo'>
						<tr>
							<td>[${vo.no }]</td>
							<td style="text-align:left; padding-left:${20*vo.dept }px">
								<c:if test="${vo.dept ne 0}">
									<img
										src='${pageContext.servletContext.contextPath }/assets/images/reply.png'>
								</c:if> <a
								href="${pageContext.servletContext.contextPath }/board?a=view&no=${vo.no }">
									${vo.title }</a>
							</td>
							<td>${vo.name }</td>
							<td>${vo.hit }</td>
							<td>${vo.regDate }</td>
							<td><c:if test="${not empty authUser && authUser.no eq vo.userNo }">
									<a
										href="${pageContext.request.contextPath }/board?a=delete&no=${vo.no }"
										class="del"> <img
										src='${pageContext.servletContext.contextPath }/assets/images/recycle.png'></a>
								</c:if></td>
						</tr>
					</c:forEach>
				</table>

				<!-- pager 추가 -->
				<div class="pager">
					<ul>
						<li><a href="${pageContext.request.contextPath }/board?a=list&pg=${pg-1 }">◀</a></li>
						<c:forEach var="i" begin="${fromPage }" end="${toPage }" step="1">
							<c:choose>
								<c:when test="${i eq pg }">
									<li class="selected"><c:out value="${i }" /></li>
								</c:when>
								<c:otherwise>
									<li><a
										href="${pageContext.request.contextPath }/board?a=list&pg=${i }"><c:out
												value="${i }" /></a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<li><a href="${pageContext.request.contextPath }/board?a=list&pg=${pg+1 }">▶</a></li>
					</ul>
				</div>
				<!-- pager 추가 -->

				<c:if test="${not empty authUser }">
					<div class="bottom">
						<a
							href="${pageContext.servletContext.contextPath }/board?a=writeform"
							id="new-book">글쓰기</a>
					</div>
				</c:if>

			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board" />
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>