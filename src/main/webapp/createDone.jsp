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
<title>신규 게시글 추가 완료</title>
</head>
<body>
	<%
			request.setCharacterEncoding("UTF-8");
			String title = request.getParameter("newTitle");
			String content = request.getParameter("newContent");
			
			ReplyItemService replyItemService = new ReplyItemServiceImpl();
			
			Boolean result = replyItemService.replyItemCreateOne(title, content);

			ServletContext context = getServletContext();
			context.setAttribute("result", result);
	%>
	<script>
	if(${result}){
		if (window.confirm("게시글이 등록되었습니다.")) {
			window.location.href = "index.jsp";
		}
	} else {
		window.confirm("게시글이 등록 실패.")
	}
	
	</script>
</body>
</html>