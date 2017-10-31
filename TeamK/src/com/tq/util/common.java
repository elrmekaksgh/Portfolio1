package com.tq.util;

import java.sql.*;
import java.util.*;

public class common {

	// static factory Method
	public static<K,V> HashMap<K,V> newHashMap(){
		return new HashMap<K,V>();
	}
	
	public static<E> ArrayList<E> newArrayList() {
		return new ArrayList<E>();
	}
	
	// Connection connect
	public Connection connect() throws Exception{
		
		String dbUrl="jdbc:mysql://localhost/teamk";
		String dbId="root";
		String dbPass="1234";
		
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection(dbUrl, dbId, dbPass);
		
		return con;
	}
	
	// Connection Close
	public void close(Connection con) {
		
		if(con!=null){try {con.close();}catch (SQLException ex){}}
		
	}
	
	public void close(Connection con, PreparedStatement pstmt) {
		
		if(pstmt!=null){try {pstmt.close();}catch (SQLException ex){}}
		if(con!=null){try {con.close();}catch (SQLException ex){}}
		
	}
	
	public void close(Connection con, PreparedStatement pstmt, ResultSet rs) {
		
		if(rs!=null){try {rs.close();}catch (SQLException ex){}}
		if(pstmt!=null){try {pstmt.close();}catch (SQLException ex){}}
		if(con!=null){try {con.close();}catch (SQLException ex){}}
		
	}	
}