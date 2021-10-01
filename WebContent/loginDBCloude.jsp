<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="java.sql.*" %>

<%
   String r_name = request.getParameter("name");
   String r_pwd = request.getParameter("pwd");
   
   Connection conn = null;
   Statement stmt = null;
   ResultSet rs = null;
   
   String url = "jdbc:mysql://101.101.209.108:3306/mydb";
   String user = "root";
   String password = "M4LL?*ATyd";
   
   Class.forName("com.mysql.jdbc.Driver");
   conn = DriverManager.getConnection(url, user, password);
   try{
      String sql = "select name from users where name='"+r_name+"'and pwd='"+r_pwd+"'";
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);
      if(rs.next()){
         out.print(rs.getString("name"));
      }else{
         out.print("아이디비번확인");
      }
   }catch(SQLException e){
      out.println("SQLException"+e.getMessage());
   }finally{
      if(rs!=null) rs.close();
      if(stmt!=null)stmt.close();
      if(conn!=null)conn.close();
   }
%>