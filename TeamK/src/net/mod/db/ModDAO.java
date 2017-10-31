package net.mod.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import net.admin.order.db.AdminDAO;

public class ModDAO {
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
	public void Mul_Thing_Stock(ModTradeInfoBEAN mtib){
		try{
			conn =getconn();
			sql ="update thing set stock = ? where num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mtib.getStock());
			pstmt.setInt(2, mtib.getOri_num());
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
	public void Mul_Pack_Stock(ModTradeInfoBEAN mtib){
		try{
			conn =getconn();
			sql ="update pack set stock = ? where num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mtib.getStock());
			pstmt.setInt(2, mtib.getOri_num());
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
	public int PO_Count(String id, String status){
		int count = 0;
		try{
			conn = getconn();
			sql = "select count(po_num) from pack_order where po_id=? and po_res_status"+status;
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
	public int TI_Count(String id, String status){
		int count=0;
		try{
			conn = getconn();
			sql = "select count(ti_num) from trade_info where id=? and ti_status"+status
					+ " and to_null_check = 0";
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
	public ModTradeInfoBEAN Thing_Stock_Check(int num,ModTradeInfoBEAN mtib){
		try{
			conn = getconn();
			sql ="select subject, size, color, stock"+
					" from thing where num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			if(rs.next()){
				System.out.println(rs.getInt("stock")-mtib.getThing_count());
				if((rs.getInt("stock")-mtib.getThing_count())<0){
					mtib.setStock_check(1);
					mtib.setSubject(rs.getString("subject"));
					mtib.setSize(rs.getString("size"));
					mtib.setColor(rs.getString("color"));
				}
				mtib.setStock(rs.getInt("stock"));			
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
	public ModTradeInfoBEAN Pack_Stock_Check(int num,ModTradeInfoBEAN mtib){
		try{
			conn = getconn();
			sql ="select subject, stock, date"+
					" from pack where num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			if(rs.next()){
				if((rs.getInt("stock")-mtib.getThing_count())<0){
					mtib.setStock_check(1);
					mtib.setSubject(rs.getString("subject"));
					mtib.setDate(rs.getTimestamp("date"));
				}
				
				mtib.setStock(rs.getInt("stock"));
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
	
	public ModTradeInfoBEAN TBasketInfoToMTIB(int tch_num,ModTradeInfoBEAN mtib){
		try{
			conn = getconn();
			sql ="select A.ori_num, A.tb_count,A.tb_cost, B.color, B.size "+
					"from thing_basket A left outer join thing B "+
					"on A.ori_num=B.num where A.tb_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,tch_num);
			rs = pstmt.executeQuery();
			if(rs.next()){
				mtib.setCost(rs.getInt("tb_cost"));
				mtib.setOri_num(rs.getInt("ori_num"));
				mtib.setThing_count(rs.getInt("tb_count"));
				mtib.setColor(rs.getString("color"));
				mtib.setSize(rs.getString("size"));
				mtib.setNum(tch_num);
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
	public ModTradeInfoBEAN PBasketInfoToMTIB(int pch_num, ModTradeInfoBEAN mtib){
		try{
			conn = getconn();
			sql = "select A.ori_num, A.pb_count, A.pb_cost, B.date "+
			"from pack_basket A left outer join pack B "+
			"on A.ori_num = B.num where pb_num = ?"; 
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pch_num);
			rs = pstmt.executeQuery();
			if(rs.next()){
				mtib.setOri_num(rs.getInt("ori_num"));
				mtib.setCost(rs.getInt("pb_cost"));
				mtib.setPack_count(rs.getString("pb_count"));
				mtib.setDate(rs.getTimestamp("date"));
				mtib.setNum(pch_num);
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
	
	public void InsertThingOrder(ModTradeInfoBEAN mtib){
		try{
			conn = getconn();
			int i = 1;
			sql = "select max(o_num) from thing_order";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next())i=rs.getInt(1)+1;
			sql = "insert into thing_order "+
				"values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
			pstmt=conn.prepareStatement(sql);
			strtoday = sdf.format(c1);
			pstmt.setInt(1, i);
			pstmt.setString(2, "t"+strtoday+i);
			pstmt.setInt(3, mtib.getOri_num());
			pstmt.setString(4, mtib.getId());
			pstmt.setInt(5, mtib.getThing_count());
			pstmt.setString(6, mtib.getSize());
			pstmt.setString(7, mtib.getColor());
			pstmt.setInt(8, mtib.getTi_num());
			pstmt.setInt(9, mtib.getCost());
			pstmt.setString(10, "");
			pstmt.setTimestamp(11, c1);
			pstmt.setInt(12, mtib.getStatus());
			pstmt.setString(13, "");
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
	
	public int InsertPackOrder(ModTradeInfoBEAN mtib){
		int i = 1;
		try{
			conn=getconn();
			sql ="select max(po_num) from pack_order";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next())i = rs.getInt(1)+1;
			strtoday = sdf.format(c1);
			sql = "insert into pack_order "+
				"values(?,?,?,?,?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, i);
			pstmt.setString(2, mtib.getId());
			pstmt.setString(3, "p"+strtoday+i);
			pstmt.setInt(4, mtib.getOri_num());
			pstmt.setString(5, mtib.getPack_count());
			pstmt.setInt(6, mtib.getCost());
			pstmt.setInt(7, mtib.getPo_receive_check());
			pstmt.setInt(8,mtib.getTi_num());
			pstmt.setTimestamp(9, c1);
			pstmt.setInt(10, mtib.getStatus());
			pstmt.setString(11, "");
			pstmt.setTimestamp(12,mtib.getDate());
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
		}return i;
	}
	public ModTradeInfoBEAN CreateTradeInfo(ModTradeInfoBEAN mtib){
		try{
			conn=getconn();
			int i = 1;
			sql ="select max(ti_num) from trade_info";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next())i = rs.getInt(1)+1;
			mtib.setTi_num(i);
			sql = "insert into trade_info value(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, i);
			pstmt.setString(2, mtib.getName());
			pstmt.setString(3, mtib.getMobile());
			pstmt.setString(4, mtib.getPostcode());
			pstmt.setString(5, mtib.getAddress1());
			pstmt.setString(6, mtib.getAddress2());
			pstmt.setString(7, mtib.getMemo());
			pstmt.setString(8, mtib.getTrade_type());
			pstmt.setString(9, mtib.getPayer());
			pstmt.setTimestamp(10, c1);
			pstmt.setString(11, mtib.getId());
			pstmt.setInt(12, mtib.getTotal_cost());
			pstmt.setInt(13, mtib.getStatus());
			pstmt.setInt(14, mtib.getTo_null_check());
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
		return mtib;
	}
	public int TO_TINum_Search(int ti_num){
		int count = 0;
		try{
			Connection conn2 = getconn();
			PreparedStatement pstmt2 = conn2.prepareStatement(
					"select count(o_num) from thing_order where o_ti_num=?");
			pstmt2.setInt(1, ti_num);
			ResultSet rs2 = pstmt2.executeQuery();
			if(rs2.next())count = 1;
			rs2.close();
			pstmt2.close();
			conn2.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	public List<ModTradeInfoBEAN> TO_ReadModTI(String id,int start, int end,String status){
		List<ModTradeInfoBEAN> ModList = new ArrayList<ModTradeInfoBEAN>();
		try{
			conn=getconn();
			sql = "select * from trade_info where id = ? and ti_status"+status+
					" and to_null_check = 0 order by ti_num desc limit ?,?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
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
				String status_text = "";
				switch (rs.getInt("ti_status")) {
				case 1:
					status_text = "입금 확인 중";
					break;
				case 2:
					status_text = "결제 완료";
					break;
				case 9:
					status_text = "대기중";
					break;
				case 10:
					status_text = "완료";
					break;
				}
				mtib.setStatus_text(status_text);
				ModList.add(mtib);
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
		
		return ModList;
	}
	public List<ModTradeInfoBEAN> MyPackOrder(String id, int start, int end,String status, String orderby){
		List<ModTradeInfoBEAN> ModPackList = new ArrayList<ModTradeInfoBEAN>();
		try{
			conn =getconn();
			sql = "select A.*, B.subject, B.intro, B.file1, B.date "
					+"from pack_order A left outer join pack B "
					+"on A.ori_num = B.num where A.po_id=? and A.po_res_status"+status
					+" order by "+orderby+" limit ?,?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setInt(2, start-1);
			pstmt.setInt(3, end);	
			System.out.println(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				ModTradeInfoBEAN mtib = new ModTradeInfoBEAN();
				mtib.setNum(rs.getInt("po_num"));
				mtib.setOri_num(rs.getInt("ori_num"));
				mtib.setIntro(rs.getString("intro"));
				mtib.setSubject(rs.getString("subject"));
				mtib.setImg(rs.getString("file1"));
				mtib.setTrade_num(rs.getString("po_trade_num"));
				mtib.setPack_count(rs.getString("po_count"));
				mtib.setCost(rs.getInt("po_cost"));
				mtib.setTrade_date(rs.getTimestamp("date"));
				mtib.setStatus(rs.getInt("po_res_status"));
				mtib.setMemo(rs.getString("po_memo"));
				String statustext = "";
				switch(rs.getInt("po_res_status")){
					case 1: statustext = "입금 확인 중"; break;
					case 2: statustext = "예약 대기 중"; break;
					case 3: statustext = "예약 완료"; break;
					case 4: statustext = "예약 취소 중";break;
					case 9: statustext = "취소 완료";break;
					case 10:statustext = "완료";break;
				}
				mtib.setDate(rs.getTimestamp("date"));
				mtib.setStatus_text(statustext);
				mtib.setPo_receive_check(rs.getInt("po_receive_check"));
				ModPackList.add(mtib);
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
		
		return ModPackList;
	}
	public List<ModTradeInfoBEAN> PackOrder(int ti_num){
		List<ModTradeInfoBEAN> ModPackList = new ArrayList<ModTradeInfoBEAN>();
		try{
			conn =getconn();
			sql = "select A.*, B.subject, B.intro, B.file1 "
					+"from pack_order A left outer join pack B "
					+"on A.ori_num = B.num where A.po_ti_num = ? order by A.po_res_status, B.date";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ti_num);
			rs = pstmt.executeQuery();
			while(rs.next()){
				ModTradeInfoBEAN mtib = new ModTradeInfoBEAN();
				mtib.setNum(rs.getInt("po_num"));
				mtib.setOri_num(rs.getInt("ori_num"));
				mtib.setIntro(rs.getString("intro"));
				mtib.setSubject(rs.getString("subject"));
				mtib.setImg(rs.getString("file1"));
				mtib.setTrade_num(rs.getString("po_trade_num"));
				mtib.setPack_count(rs.getString("po_count"));
				mtib.setCost(rs.getInt("po_cost"));
				mtib.setTrade_date(rs.getTimestamp("po_res_date"));
				String statustext = "";
				switch(rs.getInt("po_res_status")){
					case 1: statustext = "입금 확인 중"; break;
					case 2: statustext = "예약 대기 중"; break;
					case 3: statustext = "예약 완료"; break;
					case 4: statustext = "결제 취소 확인 중";break;
					case 5: statustext = "환불 완료";break;
					case 10:statustext = "완료";break;
				}
				mtib.setStatus_text(statustext);
				mtib.setPo_receive_check(rs.getInt("po_receive_check"));
				ModPackList.add(mtib);
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
		
		return ModPackList;
	}
	public List<ModTradeInfoBEAN> MyThingOrder(int ti_num){
		List<ModTradeInfoBEAN> ModThingList = new ArrayList<ModTradeInfoBEAN>();
		try{
			conn =getconn();
			sql = "select A.*, B.subject, B.intro, B.img "
					+"from thing_order A left outer join thing B "
					+"on A.ori_num = B.num where A.o_ti_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ti_num);
			rs = pstmt.executeQuery();
			while(rs.next()){
				ModTradeInfoBEAN mtib = new ModTradeInfoBEAN();
				int o_num=rs.getInt("o_num");
				int o_count = rs.getInt("o_count");
				mtib.setNum(o_num);
				mtib.setOri_num(rs.getInt("ori_num"));
				mtib.setIntro(rs.getString("intro"));
				mtib.setSubject(rs.getString("subject"));
				mtib.setImg(rs.getString("img"));
				mtib.setTrade_num(rs.getString("o_trade_num"));
				mtib.setThing_count(o_count);
				mtib.setCost(rs.getInt("o_cost"));
				mtib.setColor(rs.getString("o_color"));
				mtib.setSize(rs.getString("o_size"));
				mtib.setTrade_date(rs.getTimestamp("o_date"));
				mtib.setTrans_num(rs.getString("o_trans_num"));
				mtib.setStatus(rs.getInt("o_status"));
				mtib.setMemo(rs.getString("o_memo"));
				String statustext = "";
				switch(rs.getInt("o_status")){
					case 1: statustext = "입금 확인중"; break;
					case 2: statustext = "배송 준비중"; break;
					case 3: statustext = "배송 중";break;
					case 4: statustext = "배송완료";break;
					case 5: statustext = "교환 중";break;
					case 6: statustext = "환불 중";break;
					case 9: statustext = "환불 완료";break;
					case 10: statustext = "구매 완료";break;
				}
				
				mtib.setStatus_text(statustext);
				mtib.setTrans_num(rs.getString("o_trans_num"));
				ModThingList.add(mtib);
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
		return ModThingList;
	}
	public void Po_Status_Update(int status, int po_num){
		try{
			conn =getconn();
			sql = "update pack_order set o_res_status = ? where po_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, status);
			pstmt.setInt(2, po_num);
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
	public void To_Status_Update(int status, int o_num, String memo){
		try{
			conn = getconn();
			sql = "update thing_order set o_status = ?, o_memo=?,o_date=now() where o_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, status);
			pstmt.setString(2, memo);
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
	public void receive_change(ReceiveInfoBEAN rib){
		try{
			conn = getconn();
			sql = "update trade_info set ti_receive_name=?, ti_receive_mobile=?, "+
					"ti_receive_postcode=?, ti_receive_address1=?, ti_receive_address2=? where ti_num =?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, rib.getName());
			pstmt.setString(2, rib.getMobile());
			pstmt.setString(3, rib.getPostcode());
			pstmt.setString(4, rib.getAddress1());
			pstmt.setString(5, rib.getAddress2());
			pstmt.setInt(6, rib.getRa_num());
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
	public ModTradeInfoBEAN PO_Info_Read(int po_num){
		ModTradeInfoBEAN mtib = new ModTradeInfoBEAN();
		try{
			conn =getconn();
			sql = "select A.*, B.subject, B.intro, B.file1,C.ti_trade_type,B.date "
					+"from pack_order A left outer join(pack B cross join trade_info C) "
					+"on(A.ori_num = B.num and A.po_ti_num = C.ti_num)where A.po_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, po_num);
			rs = pstmt.executeQuery();
			while(rs.next()){
				mtib.setNum(rs.getInt("po_num"));
				mtib.setOri_num(rs.getInt("ori_num"));
				mtib.setIntro(rs.getString("intro"));
				mtib.setSubject(rs.getString("subject"));
				mtib.setImg(rs.getString("file1"));
				mtib.setTrade_num(rs.getString("po_trade_num"));
				mtib.setPack_count(rs.getString("po_count"));
				mtib.setCost(rs.getInt("po_cost"));
				mtib.setTotal_cost(rs.getInt("po_cost"));
				String []t_type = rs.getString("ti_trade_type").split(",");
				mtib.setTrade_type(t_type[0]);
				mtib.setDate(rs.getTimestamp("date"));
				mtib.setTrade_date(rs.getTimestamp("po_res_date"));
				String statustext = "";
				switch(rs.getInt("po_res_status")){
					case 1: statustext = "입금 확인 중"; break;
					case 2: statustext = "예약 대기 중"; break;
					case 3: statustext = "예약 완료"; break;
					case 4: statustext = "결제 취소 확인 중";break;
					case 5: statustext = "환불 완료";break;
					case 10:statustext = "완료";break;
				}
				mtib.setStatus_text(statustext);
				mtib.setPo_receive_check(rs.getInt("po_receive_check"));
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
	public int Res_Cancel(String memo, int num){
		int check = 0;
		try{
			conn = getconn();
			sql = "update pack_order set po_res_status=4, "+
					"po_memo = ? where po_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memo);
			pstmt.setInt(2, num);
			pstmt.executeUpdate();
			check = 1;
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
		return check ;
	}
	public void Res_Completed(String id){
		try{
			conn=getconn();
			sql ="select A.po_num, B.date from pack_order A left outer join pack B "+
					"on A.ori_num = B.num where A.po_id = ? and A.po_res_status =3";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while(rs.next()){
				String po_date = sdf.format(rs.getDate("date"));
				String today = sdf.format(new Date());
				Date startDate = sdf.parse(po_date);
				Date endDate = sdf.parse(today);
				long mul_date = (startDate.getTime()-endDate.getTime())/(24 * 60 * 60 * 1000);
				if(mul_date<0){
					Connection conn2 = getconn();
					String sql2 = "update pack_order set po_res_status = 10 where po_num=?";
					PreparedStatement pstmt2 = conn2.prepareStatement(sql2);
					pstmt2.setInt(1, rs.getInt(1));
					pstmt2.executeUpdate();
					pstmt2.close();
					conn2.close();
				}
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
		
	}
	public ModTradeInfoBEAN TO_Cancel_or_Exchange(int o_num){
		ModTradeInfoBEAN mtib = new ModTradeInfoBEAN();
		try{
			conn = getconn();
			sql ="select A.*, B.subject, B.size, B.color,B.cost,C.* "
					+"from thing_order A left outer join(thing B cross join trade_info C) "
					+"on(A.ori_num =B.num and A.o_ti_num = C.ti_num) "
					+"where A.o_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, o_num);
			rs = pstmt.executeQuery();
			if(rs.next()){
				mtib.setSubject(rs.getString("subject"));
				mtib.setSize(rs.getString("size"));
				mtib.setColor(rs.getString("color"));
				mtib.setThing_count(rs.getInt("o_count"));
				mtib.setTotal_cost(rs.getInt("o_cost"));
				mtib.setCost(rs.getInt("cost"));
				mtib.setTrade_type(rs.getString("ti_trade_type"));
				mtib.setMemo(rs.getString("o_memo"));
				mtib.setStatus(rs.getInt("o_status"));
				mtib.setName(rs.getString("ti_receive_name"));
				mtib.setMobile(rs.getString("ti_receive_mobile"));
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
		}return mtib;
	}
}
