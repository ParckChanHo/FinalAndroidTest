<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.sql.*"%>
    
<%
	request.setCharacterEncoding("utf-8");

	String userNickName = request.getParameter("userNickName");
	String result="false";
	
	Connection conn =null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	String dbURL = "jdbc:mysql://101.101.209.108:3306/capstone";
	String dbID = "root";
	String dbPassword = "M4LL?*ATyd";
	//String dbURL = "jdbc:mysql://localhost:3306/capstone";
	//String dbID = "root";
	//String dbPassword = "1004";
	
	Class.forName("com.mysql.jdbc.Driver");
	conn = DriverManager.getConnection(dbURL,dbID,dbPassword);
	
	try{
		String sql = "select userNickName from loginDB where userNickName=?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, userNickName);
        rs = pstmt.executeQuery(); //insert 시에는 executeUpdate();
        if (rs.next()) {
           if (rs.getString("userNickName").equals(userNickName)) { // 이미 아이디가 있는 경우
              out.print(result); //회원가입 불가
           } 
        }
        else{
        	result="true";
        	out.print(result); //회원가입 가능	
        }
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
