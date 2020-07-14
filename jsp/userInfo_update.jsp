<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>        

<%
	request.setCharacterEncoding("utf-8");

	String seqno = request.getParameter("user_Seqno");
	String pw = request.getParameter("user_Password");
	String name = request.getParameter("user_Name");
	String telno = request.getParameter("user_Telno");
	String email = request.getParameter("user_Email");

	String url_mysql = "jdbc:mysql://localhost/market?serverTimezone=UTC&characterEncoding=utf8&useSSL=FALSE";
	String id_mysql = "root";
	String pw_mysql = "qwer1234";

	PreparedStatement ps = null;

	try{
	    Class.forName("com.mysql.cj.jdbc.Driver");
	    Connection conn_mysql = DriverManager.getConnection(url_mysql,id_mysql,pw_mysql);
	    Statement stmt_mysql = conn_mysql.createStatement();
	
		String A = "update m_userInfo set user_Password = ?, user_Name = ?, user_Telno = ?, user_Email = ? ";
		String B = "where user_Seqno = ?";

		ps = conn_mysql.prepareStatement(A+B);

	   	ps.setString(1, pw);
	    	ps.setString(2, name);
		ps.setString(3, telno);
		ps.setString(4, email);
		ps.setInt(5, seqno)
 
	    ps.executeUpdate();
	    conn_mysql.close();

	} 

	catch (Exception e){
	    e.printStackTrace();

	}

%>