<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%
	request.setCharacterEncoding("utf-8");

	int bSeqno = Integer.parseInt(request.getParameter("board_Seqno"));
	int uSeqno = Integer.parseInt(request.getParameter("user_Seqno"));

	String url_mysql="jdbc:mysql://localhost/market?serverTimezone=UTC&characterEncoding=utf8&useSSL=false";

	String id_mysql="root";
	String pw_mysql="qwer1234";

	PreparedStatement ps=null;
	
	try{
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn_mysql =DriverManager.getConnection(url_mysql,id_mysql,pw_mysql);
		Statement stmt_mysql=conn_mysql.createStatement();
		
		String A="insert into m_likes (like_bSeqno, like_uSeqno) values (?,?) ";
		
		ps= conn_mysql.prepareStatement(A);
		ps.setInt(1, bSeqno);
		ps.setInt(2, uSeqno);

		ps.executeUpdate();
		
		conn_mysql.close();
		
	}catch(Exception e){
		e.printStackTrace();
	}
%>