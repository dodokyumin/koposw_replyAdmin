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
<title>게시글 상세</title>
</head>
<body>
	<%
	String id = request.getParameter("id");

	ReplyItemService replyItemService = new ReplyItemServiceImpl();
	String cPage = replyItemService.checkcPage(id);

	ReplyItem replyItem = replyItemService.readOne(id);
	
	int relevel = replyItem.getRelevel();
	ServletContext context = getServletContext();
	context.setAttribute("replyItem", replyItem);
	context.setAttribute("relevel", relevel);
	%>
	<table cellspacing=1 width=600 border=1>
		<tr>
			<td width=100px>번호</td>
			<td>${replyItem.id}</td>
		</tr>
		<tr>
			<td width=100px>제목</td>
			<td>${replyItem.title}</td>
		</tr>
		<tr>
			<td width=100px>일자</td>
			<td>${replyItem.date}</td>
		</tr>
		<tr>
			<td width=100px>조회수</td>
			<td>${replyItem.viewcnt}</td>
		</tr>
		<tr>
			<td width=100px>내용</td>
			<td>${replyItem.content}</td>
		</tr>
		<tr>
			<td width=100px>원글</td>
			<td>${replyItem.rootid}</td>
		</tr>
		<tr>
			<td width=100px>댓글 수준</td>
			<td><input type="text" name="relevel" value="${relevel}" readonly></td>
		</tr>
		<tr>
			<td width=100px>댓글 순번</td>
			<td><input type="text" name="recnt" value="${replyItem.recnt}" readonly></td>
		</tr>
	</table>
	<c:if test="${scoreItem.id != 0}">
		<table cellspacing=1 width=400 border=0>
			<tr>
				<td width="200"></td>
				<td width="100"><p align="center">
						<input type="submit" value="수정"
							onclick="location.href='updateOne.jsp?id=${replyItem.id}'">
					</p></td>
				<td width="100"><p align="center">
						<input type="submit" value="삭제"
							onclick="location.href='deleteOne.jsp?id=${replyItem.id}'">
					</p></td>
				<td width="100"><p align="center">
						<input type="submit" value="목록"
							onclick="location.href='index.jsp'">
					</p></td>
				<td width="100"><p align="center">
						<input type="submit" value="댓글추가"
							onclick="location.href='createReply.jsp?rootid=${replyItem.rootid}&relevel=${replyItem.relevel}&recnt=${replyItem.recnt}'">
					</p></td>
			</tr>
		</table>
	</c:if>

</body>
</html>