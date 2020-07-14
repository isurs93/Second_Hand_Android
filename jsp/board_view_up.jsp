<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>        

<%
	request.setCharacterEncoding("utf-8");

	int seqno = Integer.parseInt(request.getParameter("board_Seqno"));

	String url_mysql = "jdbc:mysql://localhost/market?serverTimezone=UTC&characterEncoding=utf8&useSSL=FALSE";
	String id_mysql = "root";
	String pw_mysql = "qwer1234";

	PreparedStatement ps = null;

	try{
	    Class.forName("com.mysql.cj.jdbc.Driver");
	    Connection conn_mysql = DriverManager.getConnection(url_mysql,id_mysql,pw_mysql);
	    Statement stmt_mysql = conn_mysql.createStatement();
	
		String A = "update m_board set board_Hit = ifnull(board_Hit,0)+1 ";
		String B = "where board_Seqno = ?";

		ps = conn_mysql.prepareStatement(A+B);

	   	ps.setInt(1, seqno);
 
	    ps.executeUpdate();
	    conn_mysql.close();

	} 

	catch (Exception e){
	    e.printStackTrace();

	}

%>