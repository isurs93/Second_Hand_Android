<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import = "java.sql.*"%>
<%
    String user_Seqno = request.getParameter("user_Seqno");
	
	String url_mysql = "jdbc:mysql://localhost/market?serverTimezone=UTC&characterEncoding=utf8&useSSL=FALSE";
	String id_mysql = "root";
	String pw_mysql = "qwer1234";
	
	PreparedStatement ps = null;
	
	try{
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
		Statement stmt_mysql = conn_mysql.createStatement();
		
		String query = "select dm_Seqno, dm_Title, dm_Content, dm_bSend, " +
					" (select user_Id from m_userInfo where m_userInfo.user_Seqno = d.dm_bSend) " +
					" from m_dm as d where dm_bReceive = " + user_Seqno + " and dm_ReceiveDelete is null";
		
		
		ResultSet rs = stmt_mysql.executeQuery(query);

		int count = 0;
%>
{ "dm_receive" : [
<% 		
        while(rs.next()){

			if(count != 0){%>
				,
			<%}
%>
		{
            "dm_Seqno"	 	: "<%=rs.getInt(1) %>",
            "dm_Title"   	: "<%=rs.getString(2) %>",
            "dm_Content" 	: "<%=rs.getString(3) %>",
            "dm_bSend"   	: "<%=rs.getString(4) %>",
            "user_SendId"   : "<%=rs.getString(5) %>"            
		}
<% 
			count++;
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