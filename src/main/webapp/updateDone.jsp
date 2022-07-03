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
<title>게시글 수정 완료</title>
</head>
<body>
	<%
	ReplyItemService replyItemService = new ReplyItemServiceImpl();

		request.setCharacterEncoding("UTF-8");
		String strId = request.getParameter("id");
		String title = request.getParameter("newTitle");
		String content = request.getParameter("newContent");
		
		ReplyItem boardItem = replyItemService.replyItemUpdateOne(title, content, strId);

		ServletContext context = getServletContext();
		context.setAttribute("boardItem", boardItem);
	%>

	<script>
		if (window.confirm("게시글이 수정되었습니다.")) {
			window.location.href = "index.jsp";
		}
	</script>
</body>
</html>