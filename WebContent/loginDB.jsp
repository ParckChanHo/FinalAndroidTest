<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ page import="java.sql.*" %>    
<%@ page import="org.json.simple.*"%>  
<% 
	request.setCharacterEncoding("utf-8");

	String r_id = request.getParameter("userId");
	String r_pwd = request.getParameter("userPasswd");

	Connection conn = null;
	
	PreparedStatement pstmt = null;
	PreparedStatement pstmt2 = null;
	ResultSet rs = null;
	ResultSet rs2 = null;
	
	String dbURL = "jdbc:mysql://101.101.209.108:3306/capstone";
	String dbID = "root";
	String dbPassword = "M4LL?*ATyd";
	
	JSONObject jObject1=null;
	JSONObject jObject2=null;
	JSONObject jObject3=null;
	
	//String dbURL = "jdbc:mysql://localhost:3306/capstone";
	//String dbID = "root";
	//String dbPassword = "1004";
	
	Class.forName("com.mysql.jdbc.Driver");
	conn = DriverManager.getConnection(dbURL,dbID,dbPassword);

	try{
		// select userPasswd from logindb where userID=?
		String sql = "select userPasswd from loginDB where userId=?";//일단 where절에서 패스워드를 찾았다는 것은 해당 아이디가 존재한다는 뜻!!	
		//String sql="select userId from logindb where userId = ? and userPasswd = ?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, r_id);
		
		//pstmt.setString(2, r_pwd);
		rs = pstmt.executeQuery(); //insert 시에는 executeUpdate();
		
		if(rs.next()) { //아이디가 존재하는 경우
			if(rs.getString(1).equals(r_pwd)){ //비밀번호가 일치함!!!
				String sql2 = "select userNickName from loginDB where userId = ? and userPasswd = ?";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, r_id);
				pstmt2.setString(2, r_pwd);
				
				rs2 = pstmt2.executeQuery();
				if(rs2.next()){
					jObject1 = new JSONObject();
					jObject1.put("login","1"); // 1 ==> 로그인 성공!!!
					jObject1.put("userNickName",rs2.getString(1));
				}				
				out.print(jObject1.toJSONString()); //로그인 성공
			}
				
			else{
				jObject2 = new JSONObject();
				jObject2.put("login","0"); //비밀번호 불일치
				// 0 ==> 비밀번호 불일치
			}
				out.print(jObject2.toJSONString());
		}
		else{
			jObject3 = new JSONObject();
			jObject3.put("login","2"); //아이디가 존재하지 않습니다.
			// 2 ==> 아이디가 존재하지 않음
		}
			out.print(jObject3.toJSONString()); //아이디가 존재하지 않습니다.
	}
	catch(Exception e) {
		e.printStackTrace();
	}
	
	finally{
		if(rs !=null)
			rs.close();
		if(pstmt != null)
			pstmt.close();
		if(conn != null)
			conn.close();
	}

%>