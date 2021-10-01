<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<% 
	String id = request.getParameter("id");
	String pwd = request.getParameter("pwd");

	if(id.equals("mjc") && pwd.equals("inbo")){
		out.println("회원님 반갑습니다."); //클라이언트로 전송함
	}
	else {
		out.println("아이디 또는 비밀번호가 불일치합니다.");
	}

%>