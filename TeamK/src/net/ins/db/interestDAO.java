package net.ins.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class interestDAO {
	Connection conn = null;
	PreparedStatement pstmt = null;
	String sql="";
	ResultSet rs = null;
	private Connection getconn() throws Exception{
		Connection conn = null;
		Context init = new InitialContext();
		DataSource ds = (DataSource)init.lookup("java:comp/env/jdbc/mysqlDB");
		conn = ds.getConnection();
		return conn;
	}
	public int MyInterestCheck(interestBEAN inb){
		int check = 0;
		try{
			conn = getconn();
			sql = "select * from interest "+
			 "where id=? and type =? and ori_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, inb.getId());
			pstmt.setString(2, inb.getType());
			pstmt.setInt(3, inb.getOri_num());
			rs = pstmt.executeQuery();
			if(rs.next())check = 1;
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return check;
	}
	public void MyInterestAdd(interestBEAN inb){
		try{
			int i = 1;
			conn =getconn();
			sql = "select max(inter_num) from interest";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next())i = rs.getInt(1)+1;
			sql = "insert into interest values(?,?,?,?,now())";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, i);
			pstmt.setString(2, inb.getId());
			pstmt.setInt(3, inb.getOri_num());
			pstmt.setString(4, inb.getType());
			pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public List<interestBEAN> MyInterest(String id,String type,int start, int end){
		List<interestBEAN> MyInterest = new ArrayList<interestBEAN>();
		if(type.equals("P"))sql = "select B.inter_num, A.file1, A.subject, A.intro, A.cost, B.ori_num, B.date from pack A right outer join interest B on A.num = B.ori_num where B.id = ? and B.type =? order by B.inter_num desc limit ?,?";
		else if(type.equals("T"))sql = "select B.inter_num, A.car_num, A.img, A.subject, A.intro, A.cost, B.ori_num, B.date from thing A right outer join interest B on A.num = B.ori_num where B.id = ? and B.type =? order by B.inter_num desc limit ?,?";
		
		try{
			conn = getconn();
			pstmt= conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, type);
			pstmt.setInt(3, start-1);
			pstmt.setInt(4, end);
			rs=pstmt.executeQuery();
			while(rs.next()){
				interestBEAN inb = new interestBEAN();
				if(type.equals("P"))inb.setImg(rs.getString("file1"));
				if(type.equals("T"))
				{
					inb.setImg(rs.getString("img"));
					inb.setCar_num(rs.getInt("car_num"));
				}
				inb.setInter_num(rs.getInt("inter_num"));
				inb.setDate(rs.getTimestamp("date"));
				inb.setOri_num(rs.getInt("ori_num"));
				inb.setSubject(rs.getString("subject"));
				inb.setCost(rs.getInt("cost"));
				inb.setIntro(rs.getString("intro"));
				MyInterest.add(inb);
				
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return MyInterest;
	}
	public int InterestCount(String id,String type){
		int count = 0;
		try{
			conn = getconn();
			sql = "select count(inter_num) from interest where id =? and type = ? order by inter_num desc";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, type);
			rs = pstmt.executeQuery();
			if(rs.next())count = rs.getInt(1);			
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return count;
	}
	public void InterestDel(int num){
		try{
			conn = getconn();
			sql = "delete from interest where inter_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
			
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public void InterestDel(interestBEAN inb){
		try{
			System.out.println(inb.getId());
			System.out.println(inb.getOri_num());
			System.out.println(inb.getType());
			conn = getconn();
			sql = "delete from interest where ori_num=? and id = ? and type=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, inb.getOri_num());
			pstmt.setString(2, inb.getId());
			pstmt.setString(3, inb.getType());
			pstmt.executeUpdate();
			
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
 