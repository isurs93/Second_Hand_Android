<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%
	request.setCharacterEncoding("utf-8");

	String id = request.getParameter("user_Id");	
	String pw = request.getParameter("user_Pw");	
	String name = request.getParameter("user_Name");	
	String telno = request.getParameter("user_Telno");	
	String email = request.getParameter("user_Email");
	
	
	String url_mysql="jdbc:mysql://localhost/market?serverTimezone=UTC&characterEncoding=utf8&useSSL=false";

	String id_mysql="root";
	String pw_mysql="qwer1234";
	PreparedStatement ps=null;
	
	try{
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn_mysql =DriverManager.getConnection(url_mysql,id_mysql,pw_mysql);
		Statement stmt_mysql=conn_mysql.createStatement();
		
		String A="insert into m_userInfo (user_Id, user_Password, user_Name ,user_Telno ,user_Email) values (?,?,?,?,?) ";
		
		ps= conn_mysql.prepareStatement(A);
		ps.setString(1, id);
		ps.setString(2, pw);
		ps.setString(3, name);
		ps.setString(4, telno);
		ps.setString(5, email);
		ps.executeUpdate();
		
		conn_mysql.close();
		
	}catch(Exception e){
		e.printStackTrace();
	}
%>