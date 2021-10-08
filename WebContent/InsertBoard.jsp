<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ page import="java.sql.*" %>    
<%@ page import="org.json.simple.*"%>
<%@ page import="board.board"%> 
<%@ page import="board.boardDAO"%>
<%@ page import="java.util.ArrayList" %>

<% 
	request.setCharacterEncoding("utf-8");
	
	String boardTitle = request.getParameter("boardTitle");
	String boardContent = request.getParameter("boardContent");
	
	boardDAO instance = new boardDAO();
	int result = instance.write(boardTitle, boardContent);
	
	if(result != -1) // 글쓰기 성공
		out.println("글쓰기에 성공했습니다.");
	else
		out.println("글쓰기에 실패했습니다.");
%>