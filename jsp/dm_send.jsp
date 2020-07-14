<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>        

<%
	request.setCharacterEncoding("utf-8");

	int dm_bSend = Integer.parseInt(request.getParameter("dm_bSend"));
	String dm_Title = request.getParameter("dm_Title");
	String dm_Content = request.getParameter("dm_Content");
	int dm_bReceive = Integer.parseInt(request.getParameter("dm_bReceive"));

	String url_mysql = "jdbc:mysql://localhost/market?serverTimezone=UTC&characterEncoding=utf8&useSSL=FALSE";
	String id_mysql = "root";
	String pw_mysql = "qwer1234";

	PreparedStatement ps = null;

	try{
	    Class.forName("com.mysql.cj.jdbc.Driver");
	    Connection conn_mysql = DriverManager.getConnection(url_mysql,id_mysql,pw_mysql);
	    Statement stmt_mysql = conn_mysql.createStatement();
	
		String query = "insert into m_dm(dm_bSend, dm_bReceive, dm_Content, dm_title) values(?, ?, ?, ?)";

		ps = conn_mysql.prepareStatement(query);

           ps.setInt(1, dm_bSend);
           ps.setInt(2, dm_bReceive);
		   ps.setString(3, dm_Content);
		   ps.setString(4, dm_Title);

	    ps.executeUpdate();
	    conn_mysql.close();

	} 

	catch (Exception e){
	    e.printStackTrace();

	}

%>