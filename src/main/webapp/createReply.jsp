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
<title>신규 댓글 추가</title>

</head>
<body>
	<h2>신규 댓글 추가</h2>
	<%
	ReplyItemService replyItemService = new ReplyItemServiceImpl();

	//원글의 rootid를 전달받음
	int rootid = Integer.parseInt(request.getParameter("rootid"));
	int relevel = Integer.parseInt(request.getParameter("relevel"));
	int recnt = Integer.parseInt(request.getParameter("recnt"));
	String newDate = replyItemService.newDate();

	pageContext.setAttribute("newDate", newDate);
	pageContext.setAttribute("rootid", rootid);
	pageContext.setAttribute("relevel", relevel);
	%>
	<form action="createReplyDone.jsp" method="post">
		<table cellspacing=1 width=600 border=1>
			<tr>
				<td width=100px>번호</td>
				<td><input type="text" value="자동 등록" readonly></td>
			</tr>
			<tr>
				<td width=100px>제목</td>
				<td><input type="text" placeholder="제목을 입력하세요." name="newTitle"
					pattern='^[가-힣a-zA-Z0-9\s?~!@#$%^&*()/ -]+$' required></td>
			</tr>
			<tr>
				<td width=100px>일자</td>
				<td><input type="text" value="${newDate}" readonly></td>
			</tr>
			<tr>
				<td width=100px>내용</td>
				<td><textarea style="width: 500px; height: 300px;"
						name="newContent" placeholder="내용을 입력하세요."
						pattern='^[가-힣a-zA-Z0-9\s?~!@#$%^&*()/ -]+$' required></textarea></td>
			</tr>
			<tr>
				<td width=100px>원글</td>
				<td><input type="text" name="rootid" value="${rootid}" readonly></td>
			</tr>
			<tr>
				<td width=100px>댓글수준</td>
				<td><input type="text" name="strReLevel" value="${relevel}"
					readonly></td>
			</tr>
			<tr>
				<td width=100px>댓글 내 순서</td>
				<td><input type="text" name="strRecnt"
					value="${replyItem.recnt}" readonly></td>
			</tr>
		</table>
		<table cellspacing=1 width=400 border=0>
			<tr>
				<td width="200"></td>
				<td width="100"><p align="center">
						<input type="submit" value="등록">
					</p></td>
				<td width="100"><p align="center">
						<input type="button" value="취소"
							onclick="location.href='index.jsp'">
					</p></td>
			</tr>
		</table>

	</form>
</body>
</html>