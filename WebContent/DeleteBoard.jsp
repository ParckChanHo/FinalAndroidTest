<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ page import="java.sql.*" %>    
<%@ page import="org.json.simple.*"%>
<%@ page import="board.board"%> 
<%@ page import="board.boardDAO"%>
<%@ page import="java.util.ArrayList" %>

<%
	request.setCharacterEncoding("utf-8");

	String str_boardId = request.getParameter("boardId");
	int boardId = Integer.parseInt(str_boardId);
	
	boardDAO instance = new boardDAO();
	int result = instance.delete(boardId);

	if(result==-1)
		out.println("삭제에 실패했습니다.");
	else
		out.println("삭제에 성공했습니다."); 

%>