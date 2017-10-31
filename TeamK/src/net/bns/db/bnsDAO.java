package net.bns.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class bnsDAO {
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
	public int PBasketCheck(PBasketBEAN pbb){
		int check = 0;
		try{
			conn =getconn();
			sql = "select * from pack_basket where id=? and ori_num=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, pbb.getId());
			pstmt.setInt(2, pbb.getOri_num());
			rs = pstmt.executeQuery();
			if(rs.next())check=1;
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
	public int TBasketCheck(TBasketBEAN tbb){
		int check = 0;
		try{
			conn =getconn();
			sql = "select * from thing_basket where id=? and ori_num=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, tbb.getId());
			pstmt.setInt(2, tbb.getOri_num());
			rs = pstmt.executeQuery();
			if(rs.next())check=1;
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
	public void PBasketAddAction(PBasketBEAN pbb){
		
		try{
			int i = 1;
			conn =getconn();
			sql = "select max(pb_num) from pack_basket";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next())i=rs.getInt(1)+1;
			sql = "insert into pack_basket values(?,?,?,?,?,now())";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, i);
			pstmt.setInt(2,pbb.getOri_num());
			pstmt.setString(3, pbb.getId());
			String [] countp = pbb.getCountp();
			pstmt.setString(4,countp[0]+","+countp[1]);
			pstmt.setInt(5, pbb.getCost());
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
	public void TBasketAddAction(TBasketBEAN tbb){
		try{
			int i = 1;
			conn =getconn();
			sql = "select max(tb_num) from thing_basket";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next())i=rs.getInt(1)+1;
			sql = "insert into thing_basket values(?,?,?,?,?,now())";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, i);
			pstmt.setInt(2,tbb.getOri_num());
			pstmt.setString(3, tbb.getId());
			pstmt.setInt(4, tbb.getCount());
			pstmt.setInt(5, tbb.getCost());
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
	
	public int TBasketUpdate(TBasketBEAN tbb){
		int check = 0;
		try{
			conn =getconn();
			sql = "update thing_basket set tb_count = ?, tb_cost=? where tb_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, tbb.getCount());
			pstmt.setInt(2, tbb.getCost());
			pstmt.setInt(3, tbb.getNum());
			pstmt.executeUpdate();
			check = 1;
		}catch (Exception e) {
			check = 0;
			e.printStackTrace();
		} finally {
			try {
				
				pstmt.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return check;
	}
	public int PBasketUpdate(PBasketBEAN pbb){
		int check = 0;
		try{
			conn =getconn();
			sql = "update pack_basket set pb_count = ?, pb_cost=? where pb_num = ?";
			pstmt = conn.prepareStatement(sql);
			String []countp = pbb.getCountp();
			String C = countp[0]+","+countp[1];
			pstmt.setString(1, C);
			pstmt.setInt(2, pbb.getCost());
			pstmt.setInt(3, pbb.getPb_num());
			pstmt.executeUpdate();
			check = 1;
		}catch (Exception e) {
			check = 0;
			e.printStackTrace();
		} finally {
			try {
				
				pstmt.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return check;
	}
	public List<PBasketBEAN> PackBasket(String id, int start, int end){
		List<PBasketBEAN> PackBasket = new ArrayList<PBasketBEAN>();
		try{
			conn =getconn();
			sql= "select A.subject,A.intro, A.cost,A.stock, A.file1, A.date, "+
					"B.pb_date,B.pb_num,B.pb_count, B.ori_num "+
					"from pack A left outer join pack_basket B on A.num = B.ori_num "+
					"where B.id = ? order by B.pb_num desc limit ?,?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setInt(2, start-1);
			pstmt.setInt(3, end);
			rs = pstmt.executeQuery();
			while(rs.next()){
				PBasketBEAN pbb = new PBasketBEAN();
				pbb.setPb_num(rs.getInt("pb_num"));
				pbb.setOri_num(rs.getInt("ori_num"));
				pbb.setSubject(rs.getString("subject"));
				pbb.setIntro(rs.getString("intro"));
				pbb.setCountp(rs.getString("pb_count").split(","));
				String [] countp = rs.getString("pb_count").split(",");
				int c = rs.getInt("cost");
				pbb.setOri_cost(c);
				pbb.setCost((c*Integer.parseInt(countp[0]))+((c/2)*Integer.parseInt(countp[1])));
				pbb.setImg(rs.getString("file1"));
				pbb.setOri_date(rs.getTimestamp("date"));
				PackBasket.add(pbb);
				
			}
		} catch (Exception e) {
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
		return PackBasket;
	}
	public PBasketBEAN PackBasketToPay(PBasketBEAN pb){
		PBasketBEAN pbb = new PBasketBEAN();
		try{
			conn =getconn();
			sql= "select * from pack where num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pb.getOri_num());
			rs = pstmt.executeQuery();
			while(rs.next()){
				pbb.setPb_num(0);
				pbb.setSubject(rs.getString("subject"));
				pbb.setIntro(rs.getString("intro"));
				pbb.setCountp(pb.getCountp());
				pbb.setCost(pb.getCost());
				pbb.setImg(rs.getString("file1"));
				pbb.setOri_num(pb.getOri_num());
			}
		} catch (Exception e) {
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
		return pbb;
	}
	public TBasketBEAN ThingBasketToPay(TBasketBEAN tb){
		TBasketBEAN tbb = new TBasketBEAN();
		try{
			conn =getconn();
			sql= "select * from thing where num =?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, tb.getOri_num());
			rs = pstmt.executeQuery();
			while(rs.next()){
				tbb.setSubject(rs.getString("subject"));
				tbb.setIntro(rs.getString("intro"));
				tbb.setCount(tb.getCount());
				tbb.setSize(rs.getString("size"));
				tbb.setColor(rs.getString("color"));
				tbb.setOri_num(tb.getOri_num());
				tbb.setCost(tb.getCost());
				tbb.setImg(rs.getString("img"));
				tbb.setNum(0);
			}
		} catch (Exception e) {
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
		return tbb;
	}
	public TBasketBEAN ThingBasketToPay(int num){
		TBasketBEAN tbb = new TBasketBEAN();
		try{
			conn =getconn();
			sql= "select A.subject,A.intro,A.color, A.size, A.img,B.tb_num, B.tb_cost, B.tb_count "+
					"from thing A left outer join thing_basket B on A.num = B.ori_num "+
					"where B.tb_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			while(rs.next()){
				tbb.setSubject(rs.getString("subject"));
				tbb.setNum(rs.getInt("tb_num"));
				tbb.setIntro(rs.getString("intro"));
				tbb.setCount(rs.getInt("tb_count"));
				tbb.setSize(rs.getString("size"));
				tbb.setColor(rs.getString("color"));
				tbb.setCost(rs.getInt("tb_cost"));
				tbb.setImg(rs.getString("img"));
			}
		} catch (Exception e) {
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
		return tbb;
	}
	public PBasketBEAN PackBasketToPay(int num){
		PBasketBEAN pbb = new PBasketBEAN();
		try{
			conn =getconn();
			sql= "select A.subject,A.intro, A.cost,A.file1, B.pb_num, B.pb_cost,B.pb_count "+
					"from pack A left outer join pack_basket B on A.num = B.ori_num "+
					"where B.pb_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			while(rs.next()){
				pbb.setPb_num(rs.getInt("pb_num"));
				pbb.setSubject(rs.getString("subject"));
				pbb.setIntro(rs.getString("intro"));
				pbb.setCountp(rs.getString("pb_count").split(","));
				pbb.setCost(rs.getInt("pb_cost"));
				pbb.setImg(rs.getString("file1"));
								
			}
		} catch (Exception e) {
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
		return pbb;
	}
	public List<TBasketBEAN> ThingBasket(String id, int start, int end){
		List<TBasketBEAN> ThingBasket = new ArrayList<TBasketBEAN>();
		try{
			conn =getconn();
			sql= "select A.car_num, A.subject,A.intro,A.stock, A.cost,A.color, A.size, A.img,B.tb_num, B.ori_num, B.tb_date,B.tb_count "+
					"from thing A left outer join thing_basket B on A.num = B.ori_num "+
					"where B.id = ? order by B.tb_num desc limit ?,?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setInt(2, start-1);
			pstmt.setInt(3, end);
			rs = pstmt.executeQuery();
			while(rs.next()){
				TBasketBEAN tbb = new TBasketBEAN();
				tbb.setSubject(rs.getString("subject"));
				int max = 10; 
				if(rs.getInt("stock")<11)max=rs.getInt("stock");
				tbb.setMaxcount(max);
				tbb.setNum(rs.getInt("tb_num"));
				tbb.setCar_num(rs.getInt("car_num"));
				tbb.setIntro(rs.getString("intro"));
				tbb.setOri_num(rs.getInt("ori_num"));
				tbb.setCount(rs.getInt("tb_count"));
				tbb.setSize(rs.getString("size"));
				tbb.setColor(rs.getString("color"));
				tbb.setOri_cost(rs.getInt("cost"));
				tbb.setCost(rs.getInt("cost")*rs.getInt("tb_count"));
				tbb.setImg(rs.getString("img"));
				tbb.setDate(rs.getTimestamp("tb_date"));
				ThingBasket.add(tbb);
				
			}
		} catch (Exception e) {
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
		return ThingBasket;
	}
	public int BasketCount(String id,String type){
		int count=0;
		
		try{
			conn = getconn();
			if(type.equals("P"))sql = "select count(pb_num) from pack_basket where id =?";
			else if(type.equals("T"))sql = "select count(tb_num) from thing_basket where id =?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
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
	public void PackBasketDelete(int num){
		try{
			conn = getconn();
			sql = "delete from pack_basket where pb_num = ?";
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
	public void ThingBasketDelete(int num){
		try{
			conn = getconn();
			sql = "delete from thing_basket where tb_num = ?";
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
	
}
