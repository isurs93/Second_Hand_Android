<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%
	request.setCharacterEncoding("utf-8");

	String user_Seqno = request.getParameter("user_Seqno");
	String query = "";

		query="select board_Seqno, board_uSeqno, board_Title, board_Price, board_Content, board_Hit, image_String, board_Sido, board_Latitude, board_Longitude, board_isDone,"
		+ " (select count(like_Seqno) from m_likes, m_board where m_board.board_Seqno = m_likes.like_bSeqno and board_Seqno = b.board_Seqno),"
		+ " (select count(like_Seqno) from m_likes, m_board where m_board.board_Seqno = m_likes.like_bSeqno and board_Seqno = b.board_Seqno and like_uSeqno = "+user_Seqno+")"
		+ " from m_board as b, m_image as i where (select count(like_Seqno) from m_likes, m_board where m_board.board_Seqno = m_likes.like_bSeqno and board_Seqno = b.board_Seqno and like_uSeqno = "+user_Seqno+") and b.board_Seqno = i.image_bSeqno and board_DeleteDate is null order by board_Seqno desc";
	

	String url_mysql="jdbc:mysql://localhost/market?serverTimezone=UTC&characterEncoding=utf8&useSSL=false";

	String id_mysql="root";
	String pw_mysql="qwer1234";

	try{
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn_mysql =DriverManager.getConnection(url_mysql,id_mysql,pw_mysql);
		Statement stmt_mysql=conn_mysql.createStatement();
		
		ResultSet rs=stmt_mysql.executeQuery(query);
		
%>	
		{
			"my_bList" : [		
		<% while(rs.next()){ %>
			{
			"board_Seqno" : "<%=rs.getInt(1) %>",
			"board_uSeqno" : "<%=rs.getInt(2) %>",
			"board_Title" : "<%=rs.getString(3) %>",
			"board_Price" : "<%=rs.getString(4) %>",
			"board_Content" : "<%=rs.getString(5) %>",
			"board_Hit" : "<%=rs.getInt(6) %>",
			"image_String" : "<%=rs.getString(7) %>",
			"board_Sido" : "<%=rs.getString(8) %>",
			"board_Latitude" : "<%=rs.getString(9) %>",
			"board_Longitude" : "<%=rs.getString(10) %>",
			"board_isDone" : "<%=rs.getString(11) %>",
			"likeCount" : "<%=rs.getInt(12)%>",
			"likeCheck" : "<%=rs.getInt(13)%>"
			},
<% }%>
			]
		}
<%			conn_mysql.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
%>