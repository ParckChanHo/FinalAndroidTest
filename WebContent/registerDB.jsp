<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ page import="java.sql.*"%>
    
<%
	String r_name = request.getParameter("name");
	String r_pwd = request.getParameter("pwd");
	out.println(r_name);
	out.println(r_pwd);
	
	Connection conn =null;
	PreparedStatement pstmt = null;
	
	//String dbURL = "jdbc:mysql://localhost:3306/users";
	String dbURL = "jdbc:mysql://101.101.209.108:3306/users";
	
	String dbID = "root";
	String dbPassword = "M4LL?*ATyd";
	
	Class.forName("com.mysql.jdbc.Driver");
	conn = DriverManager.getConnection(dbURL,dbID,dbPassword);
	
	try{
		String sql="insert into users(name,pwd) values(?,?);";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1,r_name);
		pstmt.setString(2,r_pwd);
		pstmt.executeUpdate();
		out.println("Member 테이블 삽입 성공");
	}
	catch(Exception e){
		out.println("Member 테이블 사입 실패 "+e.getMessage());
	}
		
	finally{
		if(pstmt != null)
			pstmt.close();
		if(conn != null)
			conn.close();
	}
%>