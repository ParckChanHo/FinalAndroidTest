<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.sql.*"%>
    
<%
	request.setCharacterEncoding("utf-8");

	String r_name = request.getParameter("userId");
	String r_pwd = request.getParameter("userPasswd");
	String r_nickname = request.getParameter("userNickName");
	
	Connection conn =null;
	PreparedStatement pstmt = null;
	
	String dbURL = "jdbc:mysql://101.101.209.108:3306/capstone";
	String dbID = "root";
	String dbPassword = "M4LL?*ATyd";
	//String dbURL = "jdbc:mysql://localhost:3306/capstone";
	//String dbID = "root";
	//String dbPassword = "1004";
	
	Class.forName("com.mysql.jdbc.Driver");
	conn = DriverManager.getConnection(dbURL,dbID,dbPassword);
	
	try{
		String sql="insert into loginDB(userId,userPasswd,userNickName) values(?,?,?);";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1,r_name);
		pstmt.setString(2,r_pwd);
		pstmt.setString(3,r_nickname);
		
		pstmt.executeUpdate();
		out.println("logindb 테이블 삽입 성공");
	}
	catch(Exception e){
		out.println("logindb 테이블 삽입 실패 "+e.getMessage());
	}
		
	finally{
		if(pstmt != null)
			pstmt.close();
		if(conn != null)
			conn.close();
	}
%>








