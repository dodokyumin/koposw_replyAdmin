<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="kr.ac.kopo.ctc.kopo44.replyAdmin.service.ReplyItemServiceImpl"%>
<%@page import="kr.ac.kopo.ctc.kopo44.replyAdmin.service.ReplyItemService"%>
<%@page import="kr.ac.kopo.ctc.kopo44.replyAdmin.domain.ReplyItem"%>
<%@page import="kr.ac.kopo.ctc.kopo44.replyAdmin.service.Pagination"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>게시글 수정 페이지</title>
</head>
<body>
	<%
	String id = request.getParameter("id");

	ReplyItemService replyItemService = new ReplyItemServiceImpl();
	String cPage = replyItemService.checkcPage(id);

	ReplyItem replyItem = replyItemService.readOne(id);
	String newDate = replyItemService.newDate();
	pageContext.setAttribute("newDate", newDate);

	ServletContext context = getServletContext();
	context.setAttribute("replyItem", replyItem);
	%>
	<form action="updateDone.jsp" method="post">
		<table cellspacing=1 width=600 border=1>
			<tr>
				<td width=100px>번호</td>
				<td><input type="text" value="${replyItem.id}" name="id"
					readonly></td>
			</tr>
			<tr>
				<td width=100px>제목</td>
				<td><input type="text" value="${replyItem.title}"
					name="newTitle" pattern='^[가-힣a-zA-Z0-9\s?~!@#$%^&*()/ -]+$' required></td>
			</tr>
			<tr>
				<td width=100px>일자</td>
				<td><input type="text" value="${newDate}" readonly></td>
			</tr>
			<tr>
				<td width=100px>조회수</td>
				<td><input type="text" value="${replyItem.viewcnt}" readonly></td>
			</tr>
			<tr>
				<td width=100px>내용</td>
				<td><textarea style="width: 500px; height: 300px;"
						name="newContent" pattern='^[가-힣a-zA-Z0-9\s?~!@#$%^&*()/ -]+$' required>${replyItem.content}</textarea></td>
			</tr>
			<tr>
				<td width=100px>원글</td>
				<td><input type="text" value="${replyItem.rootid}" readonly></td>
			</tr>
			<tr>
				<td width=100px>댓글 수준</td>
				<td><input type="text" value="${replyItem.relevel}" readonly></td>
			</tr>
		</table>
		<c:if test="${replyItem.id != 0}">
			<table cellspacing=1 width=400 border=0>
				<tr>
					<td width="200"></td>
					<td width="100"><p align="center">
							<input type="submit" value="수정">
						</p></td>
					<td width="100"><p align="center">
							<input type="button" value="취소"
								onclick="location.href='readOne.jsp?id=${replyItem.id}'">
						</p></td>
				</tr>
			</table>
		</c:if>
	</form>
	<p align="center">
		<input type="submit" value="삭제"
			onclick="location.href='deleteOne.jsp?id=${replyItem.id}'">
</body>
</html>