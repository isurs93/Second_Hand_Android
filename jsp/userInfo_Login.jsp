<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import = "java.sql.*"%>
<%
    String id = request.getParameter("user_Id");
	String pw = request.getParameter("user_Pw");
	
    String url_mysql = "jdbc:mysql://localhost/market?serverTimezone=UTC&characterEncoding=utf8&useSSL=FALSE";
	String id_mysql = "root";
	String pw_mysql = "qwer1234";
	
	PreparedStatement ps = null;
	
	try{
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
		Statement stmt_mysql = conn_mysql.createStatement();
		
		String query = "select user_Seqno, user_Id, user_Password, user_Name, user_Telno, user_Email from m_userInfo where user_Id = '"+id+"' and user_Password = '"+pw+"'";
		ResultSet rs = stmt_mysql.executeQuery(query);

	
%>
{
"login_info" : [

<% 		
		if(rs.next()){
	
%>
		{
		"user_Seqno"	: "<%=rs.getInt(1) %>",
		"user_Id"       : "<%=rs.getString(2) %>",
		"user_Password" : "<%=rs.getString(3) %>",
		"user_Name"     : "<%=rs.getString(4) %>",
		"user_Telno"    : "<%=rs.getString(5) %>",
		"user_Email"    : "<%=rs.getString(6) %>"
		
		}
	
<% 
		}
%>
		]
}
<%
		conn_mysql.close();
	}catch(Exception e){
		e.printStackTrace();
	}
	

%>