<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%
	request.setCharacterEncoding("utf-8");
	

	String id=request.getParameter("user_Id");

	String url_mysql="jdbc:mysql://localhost/market?serverTimezone=UTC&characterEncoding=utf8&useSSL=false";

	String id_mysql="root";
	String pw_mysql="qwer1234";
	
	String query1="select user_Id from m_userInfo where user_Id='"+id+"'";

	try{
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn_mysql =DriverManager.getConnection(url_mysql,id_mysql,pw_mysql);
		Statement stmt_mysql=conn_mysql.createStatement();
		
		ResultSet rs=stmt_mysql.executeQuery(query1);
		
%>	
		{
			"id_check" : [		
		<% if(rs.next()){ %>
			{
			"user_Id" : "<%=rs.getString(1) %>"
			}
<% }%>
			]
		}
<%			conn_mysql.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
%>