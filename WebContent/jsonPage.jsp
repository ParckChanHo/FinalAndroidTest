<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ page import="org.json.simple.*"%>    
    
<!--<%
	JSONObject jsonMain = new JSONObject();
	JSONArray jArray = new JSONArray();
	JSONObject jObject = new JSONObject();
	
	jObject.put("name","Kim");
	jObject.put("addr","HongEunDong");
	jObject.put("age",20);
	
	jArray.add(0,jObject);

	jsonMain.put("List", jArray);
	
	out.print(jsonMain.toJSONString());

%>-->


<% 
	JSONObject jsonMain2 = new JSONObject();
	JSONObject jObject2 = new JSONObject();

	jObject2.put("name","Kim");
	jObject2.put("addr","HongEunDong");
	jObject2.put("age",20);

	out.print(jsonMain.toJSONString());




%>