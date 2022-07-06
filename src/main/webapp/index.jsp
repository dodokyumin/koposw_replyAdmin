<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page
	import="kr.ac.kopo.ctc.kopo44.replyAdmin.service.ReplyItemServiceImpl"%>
<%@page
	import="kr.ac.kopo.ctc.kopo44.replyAdmin.service.ReplyItemService"%>
<%@page import="kr.ac.kopo.ctc.kopo44.replyAdmin.domain.ReplyItem"%>
<%@page import="kr.ac.kopo.ctc.kopo44.replyAdmin.service.Pagination"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>게시판</title>
</head>
<body> 
	<h1>게시판</h1>
	<table cellspacing=1 width=700 border=1>
		<tr style="background-color: grey;">
			<th width=100px>번호</th>
			<th width=400px>제목</th>
			<th width=100px>조회수</th>
			<th width=100px>등록일</th>
		</tr>
		<%
		String strcPage = request.getParameter("strcPage");

		ReplyItemService replyItemService = new ReplyItemServiceImpl();
		String cPage = replyItemService.checkcPage(strcPage);

		List<ReplyItem> replyItems = replyItemService.readAll(cPage);
		int rowCount = replyItemService.getRowCount();
		Pagination pagination = replyItemService.getPagination(cPage);

		ServletContext context = getServletContext();
		context.setAttribute("rowCount", rowCount);
		context.setAttribute("replyItems", replyItems);
		context.setAttribute("pagination", pagination);
		%>
		<c:if test="${rowCount == 0}">
			<tr>
				<td colspan="4" style="text-align: center">게시글이 없습니다.</td>
			</tr>
		</c:if>

		<c:forEach var="replyItem" items="${replyItems}">
			<tr>
				<td style="text-align: center">${replyItem.id}</td>
				<td><a href="readOne.jsp?id=${replyItem.id}">${replyItem.title}</a></td>
				<td style="text-align: center">${replyItem.viewcnt}</td>
				<td style="text-align: center">${replyItem.date}</td>
			</tr>
		</c:forEach>
	</table>
	<div>
		<input type="button" value="신규"
			onclick="location.href='createOne.jsp'"> <input type="button"
			value="초기화" onclick="location.href='deleteAll.jsp'"><br>


		<c:if test="${rowCount != 0}">
			<c:if test="${pagination.ppPage != 0 && pagination.pPage != 0}">
				<a href='index.jsp?strcPage=${pagination.getPpPage()}'> << </a>
				<a href='index.jsp?strcPage=${pagination.getpPage()}'> < </a>
			</c:if>


			<c:forEach var="noPage" begin="${pagination.firstPage}"
				end="${pagination.lastPage}">
				<c:if test="${noPage != 0}">
					<c:choose>
						<c:when test="${noPage == pagination.cPage}">
							<b><a style='text-decoration: underline;'
								href='index.jsp?strcPage=${noPage}'>${noPage}</a></b>
						</c:when>
						<c:when test="${noPage != pagination.getcPage()}">
							<a href='index.jsp?strcPage=${noPage}'>${noPage}</a>
						</c:when>
					</c:choose>
				</c:if>
			</c:forEach>

			<c:if test="${pagination.nnPage != 0 && pagination.nPage != 0}">
				<a href='index.jsp?strcPage=${pagination.nPage}'> > </a>
				<a href='index.jsp?strcPage=${pagination.nnPage}'> >> </a>
			</c:if>
		</c:if>
	</div>
</body>
</html>