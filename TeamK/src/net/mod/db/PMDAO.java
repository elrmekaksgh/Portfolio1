package net.mod.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.mysql.fabric.Response;

public class PMDAO {
	Connection conn = null;
	PreparedStatement pstmt = null;
	String sql="";
	ResultSet rs = null;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd",Locale.KOREA);
	Timestamp c1 = new Timestamp(System.currentTimeMillis());
	String strtoday;
	private Connection getconn() throws Exception{
		Connection conn = null;
		Context init = new InitialContext();
		DataSource ds = (DataSource)init.lookup("java:comp/env/jdbc/mysqlDB");
		conn = ds.getConnection();
		return conn;
	}
	public void PM_Create(PackMemberBEAN pm){
		try{
			conn  = getconn();
			sql = "select max(pm_num) from packmember";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			int count = 1;
			if(rs.next())count = rs.getInt(1)+1;
			sql = "insert into packmember(pm_num,pm_id,po_num,leader_check, adult_or_child)"
					+ " values(?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, count);
			pstmt.setString(2, pm.getPm_id());
			pstmt.setInt(3, pm.getPo_num());
			pstmt.setInt(4, pm.getLeader_check());
			pstmt.setInt(5, pm.getAdult_or_child());
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
	public void PM_Update(PackMemberBEAN pm){
		try{
			conn = getconn();
			sql = "update packmember set hangul_name=?,birthday=?, "+
					"first_name=?, last_name=?, mobile=? where pm_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, pm.getHangul_name());
			pstmt.setInt(2, pm.getBirth_day());
			pstmt.setString(3, pm.getFirst_name());
			pstmt.setString(4, pm.getLast_name());
			pstmt.setString(5, pm.getMobile());
			pstmt.setInt(6, pm.getPm_num());
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
	public List<PackMemberBEAN> PM_Read(int po_num){
		List<PackMemberBEAN> PMList = new ArrayList<PackMemberBEAN>();
		try{
			conn = getconn();
			sql = "select * from packmember where po_num =? order by leader_check desc";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, po_num);
			rs = pstmt.executeQuery();
			while(rs.next()){
				PackMemberBEAN pm = new PackMemberBEAN();
				pm.setPm_num(rs.getInt("pm_num"));
				pm.setAdult_or_child(rs.getInt("adult_or_child"));
				pm.setHangul_name(rs.getString("hangul_name"));
				pm.setBirth_day(rs.getInt("birthday"));
				pm.setMobile(rs.getString("mobile"));
				pm.setFirst_name(rs.getString("first_name"));
				pm.setLast_name(rs.getString("last_name"));	
				PMList.add(pm);
			}
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
		return PMList;
	}
	
}
