<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ page import="java.sql.*" %>    
<% 
	String r_name = request.getParameter("name");
	String r_pwd = request.getParameter("pwd");

	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	
	String dbURL = "jdbc:mysql://localhost:3306/users";
	String dbID = "root";
	String dbPassword = "1004";
	
	Class.forName("com.mysql.jdbc.Driver");
	conn = DriverManager.getConnection(dbURL,dbID,dbPassword);

	try{
		String sql="select name from users where name='"+r_name+"'and pwd='"+r_pwd+"'";
		stmt = conn.createStatement();
		rs = stmt.executeQuery(sql); //insert 시에는 executeUpdate();
		if(rs.next()){
			out.print(rs.getString("name"));
		}
		else{
			out.print("아이디 및 비번확인");
		}
		
	}
	catch(Exception e) {
		e.printStackTrace();
	}
	
	finally{
		if(rs !=null)
			rs.close();
		if(stmt != null)
			stmt.close();
		if(conn != null)
			conn.close();
	}

%>