package net.mod.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class RIDAO {
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
	public ReceiveInfoBEAN BasicReceiveAddress(String id){
		ReceiveInfoBEAN rib = new ReceiveInfoBEAN();
		try{
			conn = getconn();
			sql = "select * from receive_info where id=? and basic_setting=1";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()){
				rib.setId(rs.getString("id"));
				rib.setName(rs.getString("name"));
				rib.setMobile(rs.getString("mobile"));
				rib.setPostcode(rs.getString("postcode"));
				rib.setAddress1(rs.getString("address1"));
				rib.setAddress2(rs.getString("address2"));
				rib.setBasic_setting(rs.getInt("basic_setting"));
				rib.setRa_num(rs.getInt("ra_num"));
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
		return rib;
	}
	public List<ReceiveInfoBEAN> AddressList(String id){
		List<ReceiveInfoBEAN> ribList= new ArrayList<ReceiveInfoBEAN>();
		try{
			conn = getconn();
			sql = "select * from receive_info where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while(rs.next()){
				ReceiveInfoBEAN rib = new ReceiveInfoBEAN();
				rib.setId(rs.getString("id"));
				rib.setName(rs.getString("name"));
				rib.setMobile(rs.getString("mobile"));
				rib.setPostcode(rs.getString("postcode"));
				rib.setAddress1(rs.getString("address1"));
				rib.setAddress2(rs.getString("address2"));
				rib.setBasic_setting(rs.getInt("basic_setting"));
				rib.setRa_num(rs.getInt("ra_num"));
				ribList.add(rib);
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
		return ribList;
	}
	public int Address_Insert(ReceiveInfoBEAN rib){
		int check = 0;
		try{
			conn=getconn();
			if(rib.getBasic_setting()==1){
				sql = "update receive_info set basic_setting = 0 where id =?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, rib.getId());
				pstmt.executeUpdate();
			}
			int i = 1;
			sql = "select max(ra_num) from receive_info";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next())i = rs.getInt(1)+1;
			sql = "insert into receive_info(ra_num, id, name, mobile, postcode, address1, address2,basic_setting) values(?,?,?,?,?,?,?,?)";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, i);
			pstmt.setString(2, rib.getId());
			pstmt.setString(3, rib.getName());
			pstmt.setString(4, rib.getMobile());
			pstmt.setString(5,rib.getPostcode());
			pstmt.setString(6, rib.getAddress1());
			pstmt.setString(7, rib.getAddress2());
			pstmt.setInt(8, rib.getBasic_setting());
			pstmt.executeUpdate();
			check =1;
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
	public void basic_change(int ra_num, String id){
		try{
			conn=getconn();
			sql = "update receive_info set basic_setting=0 where id=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.executeUpdate();
			sql = "update receive_info set basic_setting=1 where ra_num=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, ra_num);
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
	public void receive_change(ReceiveInfoBEAN rib){
		
	}
}
