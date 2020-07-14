<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%
	request.setCharacterEncoding("utf-8");

	int seqno = Integer.parseInt(request.getParameter("board_uSeqno"));
	String title = request.getParameter("board_Title");
	int price = Integer.parseInt(request.getParameter("board_Price"));
	String content = request.getParameter("board_Content");
	String sido = request.getParameter("board_Sido");
	String latitude = request.getParameter("board_Latitude");
	String longitude = request.getParameter("board_Longitude");

	String url_mysql="jdbc:mysql://localhost/market?serverTimezone=UTC&characterEncoding=utf8&useSSL=false";

	String id_mysql="root";
	String pw_mysql="qwer1234";
	PreparedStatement ps=null;
	
	try{
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn_mysql =DriverManager.getConnection(url_mysql,id_mysql,pw_mysql);
		Statement stmt_mysql=conn_mysql.createStatement();
		
		String A="insert into m_board (board_uSeqno, board_Title, board_Price, board_Content, board_Hit, board_Sido, board_Latitude, board_Longitude, board_isDone, board_InsertDate) values (?,?,?,?,0,?,?,?,0,now()) ";
		
		ps= conn_mysql.prepareStatement(A);
		ps.setInt(1, seqno);
		ps.setString(2, title);
		ps.setInt(3, price);
		ps.setString(4, content);
		ps.setString(5, sido);
		ps.setString(6, latitude);
		ps.setString(7, longitude);

		ps.executeUpdate();
		
		conn_mysql.close();
		
	}catch(Exception e){
		e.printStackTrace();
	}
%>