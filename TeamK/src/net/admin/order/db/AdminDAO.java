package net.admin.order.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOpt;

import net.mod.db.ModTradeInfoBEAN;

public class AdminDAO {
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
	public int Ti_Count(int status){
		int count = 0;
		try{
			conn = getconn();
			sql = "select count(ti_num) from trade_info where ti_status = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, status);
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
	public int Thing_Order_Count(String to_status, String ti_status){
		int count=0;
		try{
			conn = getconn();
			sql = "select count(ti_num) from trade_info "+
					"where ti_num in(select o_ti_num from thing_order where o_status"
					+to_status+" order by o_ti_num) and ti_status"+ti_status;
			pstmt = conn.prepareStatement(sql);
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
	public List<ModTradeInfoBEAN> StatusPayList(int ti_status, int start, int end){
		List<ModTradeInfoBEAN> BankPayList = new ArrayList<ModTradeInfoBEAN>();
		try{
			conn=getconn();
			sql = "select * from trade_info where ti_status = ? limit ?,?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ti_status);
			pstmt.setInt(2, start-1);
			pstmt.setInt(3, end);
			rs = pstmt.executeQuery();
			while(rs.next()){
				ModTradeInfoBEAN mtib = new ModTradeInfoBEAN();
				mtib.setTi_num(rs.getInt("ti_num"));
				mtib.setName(rs.getString("ti_receive_name"));
				mtib.setMobile(rs.getString("ti_receive_mobile"));
				mtib.setPostcode(rs.getString("ti_receive_postcode"));
				mtib.setAddress1(rs.getString("ti_receive_address1"));
				mtib.setAddress2(rs.getString("ti_receive_address2"));
				mtib.setMemo(rs.getString("ti_receive_memo"));
				mtib.setTrade_type(rs.getString("ti_trade_type"));
				mtib.setPayer(rs.getString("ti_trade_payer"));
				mtib.setTrade_date(rs.getTimestamp("ti_trade_date"));
				mtib.setTotal_cost(rs.getInt("ti_total_cost"));
				mtib.setStatus(rs.getInt("ti_status"));
				BankPayList.add(mtib);
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
		
		return BankPayList;
	}
	public List<ModTradeInfoBEAN> Thing_Order_List(String to_status, String ti_status, int start, int end){
		List<ModTradeInfoBEAN> BankPayList = new ArrayList<ModTradeInfoBEAN>();
		try{
			conn=getconn();
			sql = "select * from trade_info where ti_num "
					+"in(select o_ti_num from thing_order where o_status"+to_status
					+" order by o_ti_num) and ti_status"+ti_status+" limit ?,?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start-1);
			pstmt.setInt(2, end);
			rs = pstmt.executeQuery();
			while(rs.next()){
				ModTradeInfoBEAN mtib = new ModTradeInfoBEAN();
				mtib.setTi_num(rs.getInt("ti_num"));
				mtib.setId(rs.getString("id"));
				mtib.setName(rs.getString("ti_receive_name"));
				mtib.setMobile(rs.getString("ti_receive_mobile"));
				mtib.setPostcode(rs.getString("ti_receive_postcode"));
				mtib.setAddress1(rs.getString("ti_receive_address1"));
				mtib.setAddress2(rs.getString("ti_receive_address2"));
				mtib.setMemo(rs.getString("ti_receive_memo"));
				mtib.setTrade_type(rs.getString("ti_trade_type"));
				mtib.setPayer(rs.getString("ti_trade_payer"));
				mtib.setTrade_date(rs.getTimestamp("ti_trade_date"));
				mtib.setTotal_cost(rs.getInt("ti_total_cost"));
				mtib.setStatus(rs.getInt("ti_status"));
				mtib.setTrade_date(rs.getTimestamp("ti_trade_date"));
				BankPayList.add(mtib);
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
		
		return BankPayList;
	}
	public int TI_PO_Receive_Check(int num){
		int check =0;
		try{
			conn = getconn();
			sql ="select po_receive_check from pack_order where po_ti_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs=pstmt.executeQuery();
			if(rs.next()){
				if(rs.getInt(1)==1)check=1;
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
		return check;
	}
	public int TI_TO_Check(int num){
		int check = 0;
		try{
			conn = getconn();
			sql = "select * from thing_order where o_ti_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
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
	public void PO_Status_Update(int status, String po_num){
		try{
			conn = getconn();
			sql = "update pack_order set po_res_status = ? where po_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,status);
			pstmt.setInt(2,Integer.parseInt(po_num));
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
	public void PO_Status_Update(int status, int ti_num){
		try{
			conn = getconn();
			sql = "update pack_order set po_res_status = ? where po_ti_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, status);
			pstmt.setInt(2, ti_num);
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
	public void TO_Status_Update(int status, int ti_num){
		try{
			conn = getconn();
			sql ="update thing_order set o_status = ?,o_date=now() where o_ti_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, status);
			pstmt.setInt(2, ti_num);
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
	public void Thing_Stock_Plus(int ori_num, int t_count){
		try{
			conn = getconn();
			sql ="update thing set stock=? where num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, t_count);
			pstmt.setInt(2, ori_num);
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
	public ModTradeInfoBEAN TO_Info_Read(int o_num){
		ModTradeInfoBEAN mtib = new ModTradeInfoBEAN();
		try{
			conn = getconn();
			sql = "select A.*, B.stock from thing_order A "+
				"left outer join thing B on A.ori_num = B.num where o_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, o_num);
			rs = pstmt.executeQuery();
			if(rs.next()){
				mtib.setO_memo(rs.getString("o_memo"));;
				mtib.setOri_num(rs.getInt("ori_num"));
				mtib.setThing_count(rs.getInt("stock"));
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
		return mtib;
	}
	
	public void TO_Str_Status_Update(String status, int o_num){
		try{
			conn = getconn();
			sql ="update thing_order set "+status+
					", o_date=now() where o_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, o_num);
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
	public void BankPayChecked(int status, int ti_num){
		PO_Status_Update(status, ti_num);
		TO_Status_Update(status, ti_num);
		try{
			conn =getconn();
			sql = "update trade_info set ti_status = ? where ti_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, status);
			pstmt.setInt(2, ti_num);
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
	
	public void Trans_Num_Insert(String to_num, String Trans_Num){
		try{
			conn = getconn();
			sql = "update thing_order set o_trans_num =?, o_status=3, o_date=now() where o_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, Trans_Num);
			pstmt.setInt(2, Integer.parseInt(to_num));
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
	public int To_Trans_Search(int ti_num){
		int check = 0;
		try{
			conn= getconn();
			sql = "select count(o_num) from thing_order "+
					"where o_ti_num = ? and o_status <8";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ti_num);
			rs = pstmt.executeQuery();
			if(rs.next())check = rs.getInt(1);
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
	public List<ModTradeInfoBEAN> ADThingOrder(int ti_num, String to_status){
		List<ModTradeInfoBEAN> ADThingList = new ArrayList<ModTradeInfoBEAN>();
		try{
			conn =getconn();
			sql = "select A.*, B.subject, B.intro, B.img "
					+"from thing_order A left outer join thing B "
					+"on A.ori_num = B.num where A.o_ti_num=? and A.o_status"+to_status;
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ti_num);
			rs = pstmt.executeQuery();
			while(rs.next()){
				ModTradeInfoBEAN mtib = new ModTradeInfoBEAN();
				mtib.setNum(rs.getInt("o_num"));
				mtib.setOri_num(rs.getInt("ori_num"));
				mtib.setIntro(rs.getString("intro"));
				mtib.setSubject(rs.getString("subject"));
				mtib.setImg(rs.getString("img"));
				mtib.setTrade_num(rs.getString("o_trade_num"));
				mtib.setThing_count(rs.getInt("o_count"));
				mtib.setCost(rs.getInt("o_cost"));
				mtib.setColor(rs.getString("o_color"));
				mtib.setSize(rs.getString("o_size"));
				mtib.setTrade_date(rs.getTimestamp("o_date"));
				mtib.setO_memo(rs.getString("o_memo"));
				mtib.setTrans_num(rs.getString("o_trans_num"));
				int status = rs.getInt("o_status");
				String status_text = "";
				mtib.setStatus(status);
				switch(status){
					case 1: status_text="입금 확인";break;
					case 2: status_text="배송 준비";break;
					case 3: status_text="배송 중";break;
					case 4: status_text="배송 완료";break;
					case 5: status_text="교환 요청";break;
					case 6: status_text="환불 요청";break;
					case 9: status_text="환불 완료";break;
					case 10: status_text="구매 완료";break;
				}
				mtib.setStatus_text(status_text);
				ADThingList.add(mtib);
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
		return ADThingList;
	}
	public void Ti_Status_Complet_Update(int ti_num){
		int check = To_Trans_Search(ti_num);
		if(check ==0){
			try{
				conn =getconn();
				sql = "update trade_info set ti_status = 10 where ti_num = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, ti_num);
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
	}
	public void Trade_Info_Delete(int ti_num){
		try{
			conn =getconn();
			sql = "delete from trade_info where ti_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ti_num);
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
	public int Pack_Res_Count(String status){
		int count = 0;
		try{
			conn = getconn();
			sql = "select count(A.po_num) from pack_order A "
					+"left outer join(pack B cross join trade_info C) "
					+"on(A.ori_num = B.num and A.po_ti_num = C.ti_num)"
					+" where A.po_res_status"+status;
			pstmt = conn.prepareStatement(sql);
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
		}return count;
	}public void PO_cancel_Memo_Isert(String memo, int pnum){
		try{
			conn = getconn();
			sql = "update pack_order set po_memo = ? where po_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memo);
			pstmt.setInt(2, pnum);
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
	
	public List<ModTradeInfoBEAN> Pack_Res(String status, int start, int end, String Orderby){
		List<ModTradeInfoBEAN> Pack_Res_List = new ArrayList<ModTradeInfoBEAN>();
		try{
			conn = getconn();
			sql = "select A.*, B.subject, B.intro, B.file1, B.date,"
					+"C.id,C.ti_trade_payer,C.ti_trade_type "
					+"from pack_order A left outer join(pack B cross join trade_info C) "
					+"on(A.ori_num = B.num and A.po_ti_num = C.ti_num)"
					+" where A.po_res_status"+status
					+" order by "+Orderby+" limit ?,?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start-1);
			pstmt.setInt(2, end);
			rs = pstmt.executeQuery();
			while(rs.next()){
				ModTradeInfoBEAN mtib = new ModTradeInfoBEAN();
				mtib.setId(rs.getString("id"));
				mtib.setPayer(rs.getString("ti_trade_payer"));
				mtib.setTrade_type(rs.getString("ti_trade_type"));
				mtib.setSubject(rs.getString("subject"));
				mtib.setIntro(rs.getString("intro"));
				mtib.setImg(rs.getString("file1"));
				mtib.setDate(rs.getTimestamp("date"));
				int receive_check = rs.getInt("po_receive_check");
				if(receive_check==1){
					mtib.setTi_num(rs.getInt("po_ti_num"));
					mtib = po_receive_info_read(mtib);
				}
				mtib.setPo_receive_check(receive_check);
				mtib.setNum(rs.getInt("po_num"));
				mtib.setPack_count(rs.getString("po_count"));
				mtib.setTrade_num(rs.getString("po_trade_num"));
				mtib.setOri_num(rs.getInt("ori_num"));
				int stat = rs.getInt("po_res_status");
				String statustext = "";
				switch(stat){
					case 1: statustext = "입금 확인중"; break;
					case 2: statustext="예약 대기중";break;
					case 3: statustext="예약 완료";break;
					case 4: statustext="환불대기";break;
					case 9: statustext="환불 완료";break;
					case 10: statustext="완료";break;
				}
				mtib.setStatus(stat);
				mtib.setStatus_text(statustext);
				mtib.setMemo(rs.getString("po_memo"));
				Pack_Res_List.add(mtib);
				
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
		return Pack_Res_List;
	}
	public ModTradeInfoBEAN po_receive_info_read(ModTradeInfoBEAN mtib){
		try{
			Connection conn2 = getconn();
			String sql2 = "select * from trade_info where ti_num=?";
			PreparedStatement pstmt2 = conn2.prepareStatement(sql2);
			pstmt2.setInt(1, mtib.getTi_num());
			ResultSet rs2 = pstmt2.executeQuery();
			if(rs2.next()){
				mtib.setName(rs2.getString("ti_receive_name"));
				mtib.setPostcode(rs2.getString("ti_receive_postcode"));
				mtib.setAddress1(rs2.getString("ti_receive_address1"));
				mtib.setAddress2(rs2.getString("ti_receive_address2"));
				mtib.setMobile(rs2.getString("ti_receive_mobile"));
				mtib.setPayer(rs2.getString("ti_trade_payer"));
				String []t_type=rs2.getString("ti_trade_type").split(",");
				mtib.setTrade_type(t_type[0]);
				mtib.setTrade_date(rs2.getTimestamp("ti_trade_date"));
				rs2.close();
				pstmt2.close();
				conn2.close();
			}
		}catch (Exception e) {
			e.printStackTrace();
		} 
		return mtib;
	}
	public ModTradeInfoBEAN Exchange_info(int o_num){
		ModTradeInfoBEAN mtib = new ModTradeInfoBEAN();
		try{
			conn = getconn();
			sql ="select A.o_count, A.o_cost, A.o_size, A.o_color, A.o_memo,A.o_date, "+
					"A.ori_num, A.o_num,B.*,C.subject, C.cost from thing_order A "+
					"left outer join(trade_info B cross join thing C) "+
					"on (A.o_ti_num = B.ti_num and A.ori_num = C.num) where A.o_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, o_num);
			rs = pstmt.executeQuery();
			if(rs.next()){
				mtib.setThing_count(rs.getInt("o_count"));
				mtib.setCost(rs.getInt("o_cost"));
				mtib.setSize(rs.getString("o_size"));
				mtib.setColor(rs.getString("o_color"));
				mtib.setO_memo(rs.getString("o_memo"));
				mtib.setOri_num(rs.getInt("ori_num"));
				mtib.setTotal_cost(rs.getInt("cost"));
				mtib.setSubject(rs.getString("subject"));
				mtib.setTrade_date(rs.getTimestamp("o_date"));
				mtib.setName(rs.getString("ti_receive_name"));
				mtib.setMobile(rs.getString("ti_receive_mobile"));
				mtib.setPostcode(rs.getString("ti_receive_postcode"));
				mtib.setAddress1(rs.getString("ti_receive_address1"));
				mtib.setAddress2(rs.getString("ti_receive_address2"));
				mtib.setMemo(rs.getString("ti_receive_memo"));	
				mtib.setTrade_type(rs.getString("ti_trade_type"));
				mtib.setPayer(rs.getString("ti_trade_payer"));
				mtib.setId(rs.getString("id"));
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
		return mtib;
	}
	
	public List<String> Color_List(int ori_num){
		
		List<String> Color_List= new ArrayList<String>();
		try{
			conn = getconn();
			sql = "select color from thing "
					+"where subject = (select subject from thing where num=?) and num!=? "+
					"group by color order by num";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ori_num);
			pstmt.setInt(2, ori_num);
			rs = pstmt.executeQuery();
			while(rs.next()){
				Color_List.add(rs.getString("color"));
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
		return Color_List;
	}
	public JSONArray Size_Info_Read(int ori_num, String color){
		JSONArray Color_List= new JSONArray();
		try{
			conn = getconn();
			sql = "select size,num,stock from thing "+
					"where subject = (select subject from thing where num=?)"+
					"and num!=? and color =? order by size";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ori_num);
			pstmt.setInt(2, ori_num);
			pstmt.setString(3, color);
			rs = pstmt.executeQuery();
			while(rs.next()){
				JSONObject obj = new JSONObject();
				obj.put("size", rs.getString(1));
				obj.put("num", rs.getInt(2));
				obj.put("stock", rs.getInt(3));
				Color_List.add(obj);
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
		return Color_List;
	}
	public JSONObject Thing_Exchange_Add(int num){
		JSONObject obj = new JSONObject();
		try{
			conn = getconn();
			sql = "select size,stock,cost,color from thing where num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			while(rs.next()){
				obj.put("size", rs.getString(1));
				obj.put("stock", rs.getInt(2));
				obj.put("cost", rs.getInt(3));
				obj.put("color", rs.getString(4));
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
		return obj;
	}
	public int Thing_Stock_Check(int tnum){
		int stock=0;
		try{
			conn = getconn();
			sql = "select stock from thing where num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, tnum);
			rs = pstmt.executeQuery();
			if(rs.next())stock=rs.getInt(1);
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
		return stock;
	}
	public void Stock_Mul(int num, int count){
		try{
			conn = getconn();
			sql = "update thing set stock=stock-? where num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, count);
			pstmt.setInt(2, num);
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
	public void Stock_Add(int num, int count){
		System.out.println(num+">>>>>>>>"+count);
		try{
			conn = getconn();
			sql = "update thing set stock=stock+? where num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, count);
			pstmt.setInt(2, num);
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
	public void Thing_Exchange_Action(int o_num,String transnum, String memo){
		try{
			conn = getconn();
			sql = "update thing_order set o_memo=?, o_trans_num=?,o_status=3 "+
					"where o_num = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memo);
			pstmt.setString(2, transnum);
			pstmt.setInt(3, o_num);
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
	public String O_Memo_Read(int o_num){
		String memo="";
		try{
			conn = getconn();
			sql = "select o_memo from thing_order where o_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, o_num);
			rs = pstmt.executeQuery();
			if(rs.next())memo = rs.getString(1);
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
		return memo;
	}
	
}
